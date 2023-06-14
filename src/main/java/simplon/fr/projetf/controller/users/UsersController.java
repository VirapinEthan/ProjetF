package simplon.fr.projetf.controller.users;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import simplon.fr.projetf.entity.Users;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class UsersController {
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private UserDetailsManager userDetailsManager;

    @Autowired
    public UsersController(
            DataSource pDataSource, PasswordEncoder pPasswordEncoder, UserDetailsManager pUserDetailsManager) {
        dataSource = pDataSource;
        passwordEncoder = pPasswordEncoder;
        userDetailsManager = pUserDetailsManager;
    }

    // Page de connexion
    @GetMapping("/connexion")
    public String login(Model model) {
        Users users = new Users();
        model.addAttribute("user", users);
        return "connexion";
    }

    // Page d'inscription
    @GetMapping("/inscription")
    public String getCreateUserForm(@ModelAttribute("user") Users user, Model model) {
        if (user == null || !model.containsAttribute("user")) {
            model.addAttribute("user", new Users());
        }
        return "inscription";
    }

    // Création d'un utilisateur
    @PostMapping("/inscription")
    @Transactional
    public String createUser(
            Principal principal,
            @Valid @ModelAttribute("user") Users user,
            BindingResult validation,
            Model model) {
        // Vérification de la correspondance des mots de passe
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            user.setConfirmPassword("");
            validation.addError(new FieldError("user", "confirmPassword", "Les mots de passe ne correspondent pas"));
        }

        // Vérification de l'existence de l'utilisateur
        if (userDetailsManager.userExists(user.getUsername())) {
            user.setUsername("");
            validation.addError(new ObjectError("user", "Cet utilisateur existe déjà"));
        }

        if (validation.hasErrors()) {
            return "/inscription";
        }

        // Encodage du mot de passe
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        // Rôles pour le nouvel utilisateur
        Collection<? extends GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new User(user.getUsername(), encodedPassword, roles);

        // Création du compte dans la base de données avec tous ses rôles
        userDetailsManager.createUser(userDetails);

        // Redirection en fonction du rôle de l'utilisateur connecté
        if (principal != null) {
            UserDetails userDetails1 = userDetailsManager.loadUserByUsername(principal.getName());
            Collection<? extends GrantedAuthority> authorities = userDetails1.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                boolean admin = authority.getAuthority().equals("ADMIN");
                if (admin) {
                    return "redirect:/admin/listUsers";
                }
            }
        }
        return "redirect:/connexion";
    }

    // Page d'administration - Liste des utilisateurs
    @GetMapping("/admin/listUsers")
    public String getUsers(Model model) {
        if (!model.containsAttribute("users")) {
            RowMapper<UserDetails> mapper = (rs, rowNum) -> {
                String username1 = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                return new User(username1, password, Collections.emptyList());
            };

            JdbcTemplate jdbc = new JdbcTemplate(dataSource);
            List<UserDetails> users = jdbc.query("select username,password,enabled from users", mapper);
            model.addAttribute("users", users);
        }
        return "/admin/listUsers";
    }

    // Déconnexion de l'utilisateur
    @PostMapping("logout")
    public void logout() {}
}