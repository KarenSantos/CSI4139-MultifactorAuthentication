package control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author karensaroc
 */
@Named("protected")
@SessionScoped
public class ProtectedControl implements Serializable {

    private String text;
    private String title;

    /**
     * Creates a new instance of InputControl
     */
    public ProtectedControl() {
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
        if (isTextClean(text)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, text));
        } else {
            String warning = "Your input was not valid. Please enter a valid input.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning:", warning));
        }
    }

    private boolean isTextClean(String text) {
        boolean result = true;
        if (text.contains("/") || text.contains("\"") || text.contains("&")){
            result = false;
        } else if (text.contains("<") || text.contains(">") || text.contains("+") || text.contains("%")){
            result = false;
        }
        return result;
    }

}
