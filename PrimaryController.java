/* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ppe4;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * FXML Controller class
 *
 * @author claude.boulay
 */
public class PrimaryController implements Initializable {


    @FXML
    private Button dateDemandes;
    @FXML
    private Button dateReservation;
    @FXML
    private Button nom;
    @FXML
    private Button email;
    @FXML
    private Button ID;
    @FXML
    private VBox centerBox;
    @FXML
    private VBox rigthBox;
    @FXML
    private VBox leftBox;
    @FXML
    static public VBox info;
    @FXML
    private Button X;
    
    public void close(){
        while(PrimaryController.info.getChildren().size()>1){
            PrimaryController.info.getChildren().remove(1);
        }
        PrimaryController.info.setVisible(false);
        PrimaryController.info.setDisable(true);
    }
    
    public static Button Send=new Button("Send");
    public static Button Infirmer=new Button("Infirmer");
   
    /**
     * Initializes the controller class.
     */
   
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void TrierParDateDemande(ActionEvent event) {
        App.updateFromBDD("SELECT * FROM CLIENT ORDER BY date_send");
    }

    @FXML
    private void TrierParNom(ActionEvent event) {
        App.updateFromBDD("SELECT * FROM CLIENT ORDER BY nom_client");
    }

    @FXML
    private void TrierParEmail(ActionEvent event) {
        App.updateFromBDD("SELECT * FROM CLIENT ORDER BY mail_client");
    }

    @FXML
    private void TrierParID(ActionEvent event) {
        App.updateFromBDD("SELECT * FROM CLIENT ORDER BY ID_client");
    }

    @FXML
    private void TrierParDateReservation(ActionEvent event) {
        App.updateFromBDD("SELECT * FROM CLIENT ORDER BY date_rdv");
    }
    
    /**
     *
     * @throws IOException
     * @throws URISyntaxException
     */
   

    
}






    


   