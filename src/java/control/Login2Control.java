
package control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import model.UserAccount;

/**
 *
 * @author karensaroc
 */
@ManagedBean
@Named(value = "login2Control")
@SessionScoped
public class Login2Control {

    private UserAccount user;
    private DataManager manager;

    private String topMessage;
    private String errorMessage;

    public Login2Control() {
        clearMessages();
        try {
            manager = DataManager.getInstance();
            user = manager.getCurrentUser();
            topMessage = "Welcome " + user.getFirstName() + "\nPlease enter the PIN we have sent by phone text.";

        } catch (IOException e) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, e);
            topMessage = "Sorry. We have encountered a problem \nconnecting with the database.\n"
                    + "Please try again later.";
        }

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

    private void clearMessages() {
        topMessage = "";
        errorMessage = "";
    }
}
