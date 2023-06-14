package simplon.fr.projetf.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private DataSource dataSource;

    @Autowired
    public SpringSecurityConfig(DataSource pDataSource) {
        dataSource = pDataSource;
    }

    // Gestionnaire des détails des utilisateurs
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    // Encodeur de mot de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Configuration de la sécurité HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() // Désactiver la protection CSRF pour le moment
                .authorizeHttpRequests() //
                .requestMatchers("/ajout/**").authenticated() // Les requêtes vers "/ajout/**" nécessitent une authentification
                .requestMatchers(HttpMethod.PUT).authenticated() // Les requêtes PUT nécessitent une authentification
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN") // Les requêtes DELETE nécessitent le rôle "ADMIN"
                .requestMatchers("/admin/**").hasRole("ADMIN") // Les requêtes vers "/admin/**" nécessitent le rôle "ADMIN"
                .anyRequest().permitAll() // Toutes les autres requêtes sont autorisées sans authentification
                .and().httpBasic() // Authentification HTTP Basic
                .and().formLogin().loginPage("/connexion").permitAll() // Page de connexion personnalisée
                .and().passwordManagement(management -> management.changePasswordPage("/change-password")) // Gestion des mots de passe
                .build();
    }
}