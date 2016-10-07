package control;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.DiffieHellman;
import model.Email;
import model.PasswordService;
import model.UserAccount;

/**
 *
 * @author karensaroc
 */
@Named("loginControl")
@SessionScoped
public class LoginControl implements Serializable {

    private final String CONTENT = "Your PIN number is: ";
    
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
        String returnPage = null;
        PasswordService ps = null;
        while (ps == null) {
            ps = PasswordService.getInstance();
        }
        String encryptedPW = "";
        try {
            encryptedPW = ps.encrypt(password);
            try {
                UserAccount user = manager.login(email);
                if (user != null) {
                    if (encryptedPW.equals(user.getPassword())) {

                        DiffieHellman dh = new DiffieHellman();

                        manager.setAlpha(dh.calcAlpha(password));

                        System.out.println("alpha: ");
                        System.out.println(bigToHexString(manager.getAlpha()));

                        BigInteger beta = dh.calcBeta();
                        manager.setBeta(beta);

                        System.out.println("beta: ");
                        System.out.println(bigToHexString(beta));

                        Email email = new Email(manager.getCurrentUser().getEmail());
                        email.sendGmail(CONTENT + bigToHexString(beta));

                        clearMessages();
                        topMessage = "login successful";
                        returnPage = "loginPin";
                    } else {
                        clearMessages();
                        errorMessage = "Incorrect password or email.";
                        returnPage = "login?faces-redirect=true";
                    }
                } else {
                    clearMessages();
                    errorMessage = "Incorrect email or password.";
                    returnPage = "login?faces-redirect=true";
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

    private String bigToHexString(BigInteger number) {
        String result = "";
        for (byte b : number.toByteArray()) {
            result += String.format("%02x", b);
        }
        return result;
    }
}
