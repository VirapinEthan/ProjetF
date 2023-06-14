package simplon.fr.projetf.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Users {
        @NotBlank
        @Size(min=1,max=255)
        private String username;
        @NotBlank
        @Size(min=1,max=255)
        private String password;
        @NotBlank
        @Size(min=1,max=255)
        private String confirmPassword;


        public Users(String pLogin, String pPassword, String pConfirmPassword)
        {
            username = pLogin;
            password = pPassword;
            confirmPassword = pConfirmPassword;

        }

      public Users()
       {

       }

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String pLogin)
        {
            username = pLogin;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String pPassword)
        {
            password = pPassword;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public void setConfirmPassword(String pConfirmPassword)
        {
            confirmPassword = pConfirmPassword;
        }
    }


