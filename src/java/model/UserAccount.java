package model;

/**
 *
 * @author karensaroc
 */
public class UserAccount {
    
    private String password;
    private String firstName;
    private String email;
    private String cellphone;

    public UserAccount() {
    }

    public UserAccount(String password, String firstName, String email, String cellphone){

        this.password = password;
        this.firstName = firstName;
        this.email = email;
        this.cellphone = cellphone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCellphone(){
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (this.getClass() == obj.getClass()) {
                if (this.getEmail().equals(((UserAccount) obj).getEmail())) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "UserAccount{" + firstName + ", " + email + '}';
    }
    
}
