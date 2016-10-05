package control;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import model.PasswordService;
import model.UserAccount;

/**
 *
 * @author karensantos
 */
@ManagedBean(name="userBean")
@RequestScoped
public class UserBean implements Serializable {

    private DataManager manager;

    private String email;
    private String password;
    private String firstName;
    private String cellphone;

    private String topMessage;
    private String addingStatus;

    public UserBean() {
    }

    @PostConstruct
    public void init() {
        try {
            manager = DataManager.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            addingStatus = "Sorry. We have encountered a problem \nconnecting with the database.\n"
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public String getAddingStatus() {
        return addingStatus;
    }

    public void setAddingStatus(String addstatus) {
        this.addingStatus = addstatus;
    }

    public String signUp(ActionEvent actionEvent) {
        try {
            manager = DataManager.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            addingStatus = "Error trying to connect to the Database. \nTry again later.";
        }
        if (!manager.isRegistered(email)) {
            PasswordService ps = null;
            while (ps == null) {
                ps = PasswordService.getInstance();
            }
            String encryptedPW = "";
            try {
                encryptedPW = ps.encrypt(password);
                UserAccount user = new UserAccount(encryptedPW, firstName, email, cellphone);
                manager.register(user);
                this.addingStatus = "User profile created successfully";
            } catch (Exception ex) {
                addingStatus = "Error trying to create account. \nTry again later.";
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            addingStatus = "This email account already exists.";
        }
        return null;
    }
}
