package control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author karensaroc
 */
@ManagedBean(name = "notProtected")
@SessionScoped
public class NotProtectedControl implements Serializable {

    private String text;
    private String title;

    /**
     * Creates a new instance of InputControl
     */
    public NotProtectedControl() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void inputText(ActionEvent actionEvent) {
        this.title = "Your input was:";
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, title, text));
    }

}
