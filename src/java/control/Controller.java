/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import model.UserAccount;

/**
 *
 * @author karensaroc
 */
@ManagedBean
@Named(value = "controller")
@RequestScoped
public class Controller {

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