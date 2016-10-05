package control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import model.PasswordService;
import model.UserAccount;

/**
 *
 * @author karensaroc
 */
@ManagedBean
@Named(value = "loginControl")
@SessionScoped
public class LoginControl {

    private DataManager manager;

    private String email;
    private String password;

    private String topMessage;
    private String errorMessage;

    public LoginControl() {

        clearMessages();
        try {
            manager = DataManager.getInstance();
        } catch (IOException e) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, e);
            topMessage = "Sorry. We have encountered a problem \nconnecting with the database.\n"
                    + "Please try again later.";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String login() {
        String returnPage = "unauthorized";
        PasswordService ps = null;
        while (ps == null) {
            ps = PasswordService.getInstance();
        }
        String encryptedPW = "";
        try {
            encryptedPW = ps.encrypt(password);
            try {
                UserAccount user = manager.findUser(email);
                if (user != null) {
                    if (encryptedPW.equals(user.getPassword())) {
                        topMessage = "login successful";
                        returnPage = "main";
                    } else {
                        clearMessages();
                        errorMessage = "Incorrect password or email.";
                    }
                } else {
                    clearMessages();
                    errorMessage = "Incorrect email or password.";

                }
            } catch (Exception e) {
                errorMessage = "Could not connect to the database. \nTry again later.";
            }

        } catch (Exception ex) {
            Logger.getLogger(LoginControl.class
                    .getName()).log(Level.SEVERE, null, ex);
            clearMessages();
            errorMessage = "An error has occured. Try again later.";
        }

        return returnPage;
    }

    private void clearMessages() {
        topMessage = "";
        errorMessage = "";
    }

}
