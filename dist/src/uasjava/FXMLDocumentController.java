package uasjava;

import java.sql.Connection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private Button si_loginbtn;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_Createbtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private Button su_signupbtn;

    @FXML
    private TextField su_username;
    
    @FXML
    private Button side_alreadyHave;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;   
    
    public void lgnBtn(){
        if (si_username.getText().isEmpty()|| si_password.getText().isEmpty()){
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle("error Message");
           alert.setHeaderText(null);
           alert.setContentText("incorect Username/Password");
           alert.showAndWait();
        }else{
            String selctData = "SELECT username, password FROM employee WHERE username = ? and password = ?";
            connect = database.connetDB();
            try{
                prepare = connect.prepareStatement(selctData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());
                result = prepare.executeQuery();
                if(result.next()){
                    data.username = si_username.getText();
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Login");
                    alert.showAndWait();
                    Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
                    Stage stage =new Stage();
                    Scene scene = new Scene(root);         
                    stage.setTitle("Cafe Shop Management Sysytem");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);  
                    stage.setScene(scene);
                    stage.show();
                    
                    si_loginbtn.getScene().getWindow().hide();
                    
                }else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorect username/password");
                    alert.showAndWait();
                }
                
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void regBtn(){
        
        if (su_username.getText().isEmpty() || 
            su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null
            || su_answer.getText().isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();   
        }else {
            String regData ="INSERT INTO employee(username, password, question, answer, date)"+"VALUES(?,?,?,?,?)";
            connect = database.connetDB();
            
            try{
                
                String checkUsername = "SELECT username FROM employee WHERE username='"
                        +su_username.getText()+"'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();
                
                if(result.next()){
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText()+"is already taken");
                    alert.showAndWait();
                }else if(su_password.getText().length()< 8){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("invalid password atleast 8 characters is needed");
                    alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(regData);
                prepare.setString(1, su_username.getText());
                prepare.setString(2, su_password.getText());
                prepare.setString(3, (String)su_question.getSelectionModel().getSelectedItem());
                prepare.setString(4, su_answer.getText());
                
                
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(5, String.valueOf(sqlDate));
                prepare.executeLargeUpdate();
                prepare.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText("Successfuly registered Account!");
                alert.showAndWait(); 
                su_username.setText("");
                su_password.setText("");
                su_question.getSelectionModel().clearSelection();
                su_answer.setText("");
                TranslateTransition slider = new TranslateTransition();
                slider.setNode(side_form);
                slider.setToX(0);
                slider.setDuration(Duration.seconds(.5));
                slider.setOnFinished((ActionEvent e) -> {
                    side_alreadyHave.setVisible(false);
                    side_Createbtn.setVisible(true);
                });
                slider.play();
                }
                
            }catch (Exception e) {
                
                e.printStackTrace();
            }
        }
    }
       
    private String[] questionList = {"what is your favorite color?","what is your favorite food","what is your bith date?"};
    public void regLquetsionsList(){
        List<String> listQ = new ArrayList<>();
        
        for (String data: questionList){
            listQ.add(data);   
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }
      
    public void switchForm(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource()==side_Createbtn){
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) ->{
                side_alreadyHave.setVisible(true);
                side_Createbtn.setVisible(false);
                regLquetsionsList();     
            });
            
            slider.play();
        }else if (event.getSource()==side_alreadyHave){
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            
            slider.setOnFinished((ActionEvent e) ->{
                side_alreadyHave.setVisible(false);
                side_Createbtn.setVisible(true); 
            });
            slider.play();     
        }   
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }      
}
