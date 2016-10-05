package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import model.UserAccount;

/**
 *
 * @author karensaroc
 */
public class DataManager {

    private static DataManager instance;
    private final String userDataPath = "web/resources/DB/userData/userData.txt";
    private final String passwordPath = "web/resources/DB/password/pwd.txt";
    
    private UserAccount currentUser;

    private DataManager() throws IOException {
        ///Applications/NetBeans/glassfish-4.1/glassfish/domains/domain1/config

        File userFile = new File(userDataPath);
        if (userFile.getParentFile() != null) {
            userFile.getParentFile().mkdirs();
        }
        userFile.createNewFile();

        File passwordFile = new File(passwordPath);
        if (passwordFile.getParentFile() != null) {
            passwordFile.getParentFile().mkdirs();
        }
        passwordFile.createNewFile();
    }

    public void register(UserAccount user) {

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
                    userDataPath, true)));
            writer.println(user.getEmail() + "   " + user.getFirstName() + "   " + user.getCellphone());
            writer.close();

            PrintWriter writer2 = new PrintWriter(new BufferedWriter(new FileWriter(
                    passwordPath, true)));
            writer2.println(user.getPassword());
            writer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRegistered(String email) throws IOException {
        return findUser(email) != null;
    }
    
    public UserAccount login(String email) throws IOException{
        currentUser = findUser(email);
        return currentUser;
    }
    
    public UserAccount getCurrentUser(){
        return currentUser;
    }

    private UserAccount findUser(String email) throws FileNotFoundException, IOException {
        UserAccount user = null;
        String currentLine;
        String currentPassword;

        BufferedReader brL;
        BufferedReader brL2;

        brL = new BufferedReader(new FileReader(userDataPath));
        brL2 = new BufferedReader(new FileReader(passwordPath));
        while ((currentLine = brL.readLine()) != null) {
            currentPassword = brL2.readLine();
            String userEmail = currentLine.split("   ")[0];
            if (email.equals(userEmail)) {
                String userName = currentLine.split("   ")[1];
                String userPhone = currentLine.split("   ")[2];
                user = new UserAccount(currentPassword, userName, userEmail, userPhone);
            }
        }
        brL.close();
        brL2.close();

        return user;
    }

    public static synchronized DataManager getInstance() throws IOException {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

}
