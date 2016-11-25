// fix reset button
// css
// http://www.smsgateway.ca/
// http://www.emailtextmessages.com/
// https://www.twilio.com/

package control;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.UserAccount;

/**
 *
 * @author karensaroc
 */
@Named("controller")
@SessionScoped
public class Controller implements Serializable{

    private DataManager manager;
    private String topMessage;
    private String statusMessage;

    private UserAccount user;
    
    public Controller(){
        try {
            manager = DataManager.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
