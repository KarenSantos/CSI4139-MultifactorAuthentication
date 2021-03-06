package control;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.UserAccount;

/**
 *
 * @author karensaroc
 */
@Named(value = "loginPinControl")
@SessionScoped
public class LoginPinControl implements Serializable {

    private UserAccount user;
    private DataManager manager;
    private String pin;

    private String topMessage;
    private String errorMessage;

    public LoginPinControl() {
        clearMessages();
        try {
            manager = DataManager.getInstance();
            user = manager.getCurrentUser();
           
            topMessage = "Welcome " + user.getFirstName() + ".\n Please enter the PIN we have sent by phone text.";

        } catch (IOException e) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, e);
            topMessage = "Sorry. We have encountered a problem \nconnecting with the database.\n"
                    + "Please try again later.";
        }

    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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
        // TODO limit number of failures!! Start over with new PIN.
        String returnPage;
        System.out.println("Entered PIN: \n" + pin);
        System.out.println("Beta: \n" + manager.getBeta());
        
        if (pin.equals(bigToHexString(manager.getBeta()))) {
            returnPage = "main";
        } else {
            clearMessages();
            errorMessage = "PIN Incorrect. Please check your phone.";
            returnPage = "loginPin?faces-redirect=true";
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
