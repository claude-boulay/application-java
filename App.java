package com.mycompany.ppe4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.*;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.print.PrintColor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.tools.ant.taskdefs.Input;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ResultSet result;
    private static Connection Connection;
    private static Statement Statement;
    private static int result2;
    private static int mailsent;

    private static boolean connected;
    private static VBox leftBox;
    private static VBox centerBox;
    private static VBox rigthBox;
    public static String currentMail;
    public static String currentMessage;


    /*@Override*/;
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1920, 1080);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Industro Data Base");

        leftBox=(VBox) scene.lookup("#leftBox");
        centerBox=(VBox) scene.lookup("#centerBox");
        rigthBox=(VBox) scene.lookup("#rigthBox");
        leftBox.setPadding(new Insets(50,50,100,50));
        centerBox.setPadding(new Insets(50,50,50,50));
        rigthBox.setPadding(new Insets(50,50,100,50));
        leftBox.setFillWidth(true);
        leftBox.setPrefWidth(640);
        centerBox.setFillWidth(true);
        centerBox.setPrefWidth(640);
        rigthBox.setFillWidth(true);
        rigthBox.setPrefWidth(640);
        rigthBox.setAlignment(Pos.TOP_RIGHT);
        leftBox.setAlignment(Pos.TOP_LEFT);
        centerBox.setAlignment(Pos.CENTER);
        leftBox.alignmentProperty();
        leftBox.setSpacing(10);
        rigthBox.setSpacing(10);
        centerBox.setSpacing(10);
        PrimaryController.info=(VBox) scene.lookup("#info");
        connectBDD();
        updateFromBDD("SELECT * FROM CLIENT");
    }

    public void stop() throws IOException{
        deconnectedBDD();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void connectBDD(){
        try{

            String url="jdbc:mysql://localhost:3306/Industro";
            String user ="root";
            String password="root";

            Connection = DriverManager.getConnection(url,user,password);
            Statement =Connection.createStatement();

            connected =true;
            System.out.println("SQL:Connected !");
        }
        catch (Exception error) {
            System.out.println(error);
        }
    }

    private static void deconnectedBDD(){
        if(connected){
            try{
                result.close();
                Statement.close();
                Connection.close();
                connected=false;
                System.out.println("disconnected !");
            }
            catch(Exception error){
                System.out.println(error);
            }
        }
    }

    public static void updateFromBDD(String sql){
        if(connected){
            try{
                leftBox.getChildren().clear();
                centerBox.getChildren().clear();
                rigthBox.getChildren().clear();

                result = Statement.executeQuery(sql);
                int index=0;
                
                while(result.next()){

                    Label nom=new Label(result.getString("nom_client"));
                    nom.setFont(new Font(20));


                    Label prenom=new Label(result.getString("prenom_client"));
                    prenom.setFont(new Font(20));

                    Label mail=new Label(result.getString("mail_client"));
                    Label message=new Label(result.getString("messages"));

                    Label date=new Label(result.getDate("date_rdv").toString());
                    date.setFont(new Font(15));
                    
                    Label date_send=new Label(result.getDate("date_send").toString());
                    date_send.setFont(new Font(15));
                    
                   Label nom2=new Label(result.getString("nom_client"));
                   nom2.setFont(new Font(20));
                   Label prenom2=new Label(result.getString("prenom_client"));
                   prenom2.setFont(new Font(20));
                   
                   Label time=new Label(result.getTime("heure_rdv").toString());
                  
                   Label date2=new Label(result.getDate("date_rdv").toString());
                   date2.setFont(new Font(15));
                   Label date_send2=date_send;
                   date_send2.setFont(new Font(15));
                   
                   mailsent=result.getInt("reponseEffectuer");
                    
                   Label envoieMail=new Label("Le mail a été envoyé");
                   date2.setFont(new Font(15));                  
                   
                    Button button=new Button("Afficher plus");
                    Label envoieMail2=new Label("Le mail n'a pas été envoyé");
                    
                    Label label1 = new Label("Ajout de mail :");
                    TextField textField = new TextField ();
                    
                    
                    
                    
                    button.setId("afficher");
                    button.setPrefSize(100, 30);
                    button.setTextFill(Color.RED);
                    button.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
                    button.setBorder(new Border(new BorderStroke(Color.BEIGE,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.FULL)));
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                        while (PrimaryController.info.getChildren().size()>1) {
                           PrimaryController.info.getChildren().remove(1);                           
                        }
                        HBox ajout=new HBox();
                        
                        currentMail=mail.getText();
                        PrimaryController.info.setVisible(true);
                        PrimaryController.info.setDisable(false);
                        PrimaryController.info.getChildren().add(ajout);
                        PrimaryController.info.getChildren().addAll(nom , prenom);
                        PrimaryController.info.getChildren().addAll(date_send2,date);
                        PrimaryController.info.getChildren().add(time);
                        PrimaryController.info.getChildren().add(mail);
                        PrimaryController.info.getChildren().add(message);
                        
                        PrimaryController.info.getChildren().addAll(label1,textField);
                        
                        PrimaryController.info.getChildren().addAll(PrimaryController.Infirmer,PrimaryController.Send);
                        
                        PrimaryController.Send.setId("envoyer");
                        PrimaryController.Send.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE,CornerRadii.EMPTY,Insets.EMPTY)));
                        PrimaryController.Send.setTextFill(Color.BLUE);
                        PrimaryController.Send.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent)->{
                           Properties props=new Properties();
                           PrimaryController.Send.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
                            PrimaryController.Send.setTextFill(Color.BLACK);
                            props.put("mail.smtp.auth", true);
                            props.put("mail.smtp.startls.enable", true);
                            props.put("mail.smtp.host", "in-v3.mailjet.com");
                            props.put("mail.smtp.port", "587");
                            props.put("mail.debug", "true");

                            //Récupère la session
                            Session sessionMail = Session.getInstance(props,null);

                            //Définition du message
                            MimeMessage message3 = new MimeMessage(sessionMail);

                            //Afficher Mode Debug
                            sessionMail.setDebug(true);


                            try{
                            message3.setFrom(new InternetAddress("varanraptor41@gmail.com"));
                            //Spécification du destinataire
                            message3.addRecipient(Message.RecipientType.TO, new InternetAddress(currentMail));
                            //Sujet du message
                            message3.setSubject("Demande de rdv avec Industro");
                            //Texte du message
                            currentMessage="Bonjour.Nous vous contactons par rapport à votre demande de rendez vous avec Industro."+textField.getText();
                            message3.setText(currentMessage);
                            //Envoie le message avec un smtp authentifié
                            message3.saveChanges(); // implicit with send()
                            Transport transport = sessionMail.getTransport("smtp");
                            transport.connect("in-v3.mailjet.com","93cfd70b756e3c3f93966e3b67faad6f","e93168384a2e4d0bf9ea66f1f6db4ba7");
                            transport.sendMessage(message3, message3.getAllRecipients());
                            transport.close();
                               try {
                                   String nomequal=nom.getText();
                                   result2=Statement.executeUpdate("UPDATE CLIENT SET reponseEffectuer='1' WHERE nom_client='"+nomequal+"'");
                               } catch (SQLException ex) {
                                   Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                               }
                            PrimaryController.info.getChildren().add(envoieMail);
                            }catch(MessagingException e){
                                e.printStackTrace();

                               PrimaryController.info.getChildren().add(envoieMail2);

                        }});
                        
                        PrimaryController.Infirmer.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent)->{
                           Properties props=new Properties();
                           PrimaryController.Infirmer.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
                            PrimaryController.Infirmer.setTextFill(Color.BLACK);
                            props.put("mail.smtp.auth", true);
                            props.put("mail.smtp.startls.enable", true);
                            props.put("mail.smtp.host", "in-v3.mailjet.com");
                            props.put("mail.smtp.port", "587");
                            props.put("mail.debug", "true");

                            //Récupère la session
                            Session sessionMail = Session.getInstance(props,null);

                            //Définition du message
                            MimeMessage message3 = new MimeMessage(sessionMail);

                            //Afficher Mode Debug
                            sessionMail.setDebug(true);


                            try{
                            message3.setFrom(new InternetAddress("varanraptor41@gmail.com"));
                            //Spécification du destinataire
                            message3.addRecipient(Message.RecipientType.TO, new InternetAddress(currentMail));
                            //Sujet du message
                            message3.setSubject("Demande de rdv avec Industro");
                            //Texte du message
                            
                            message3.setText("Nous sommes désolée mais nous ne pourrons pas assurez votre rdv pour la date prévu merci de reprendre rdv si nécéssaire a une date ultérieur");
                            //Envoie le message avec un smtp authentifié
                            message3.saveChanges(); // implicit with send()
                            Transport transport = sessionMail.getTransport("smtp");
                            transport.connect("in-v3.mailjet.com","93cfd70b756e3c3f93966e3b67faad6f","e93168384a2e4d0bf9ea66f1f6db4ba7");
                            transport.sendMessage(message3, message3.getAllRecipients());
                            transport.close();
                               try {
                                   String nomequal=nom.getText();
                                   result2=Statement.executeUpdate("UPDATE CLIENT SET reponseEffectuer='2' WHERE nom_client='"+nomequal+"'");
                               } catch (SQLException ex) {
                                   Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                               }
                            PrimaryController.info.getChildren().add(envoieMail);
                            }catch(MessagingException e){
                                e.printStackTrace();

                               PrimaryController.info.getChildren().add(envoieMail2);

                        }});
                                              
                    });
                    
                   
                    VBox boite=new VBox();
                    
                    boite.setAlignment(Pos.TOP_CENTER);
                    boite.setCursor(Cursor.HAND);
                    boite.setPadding(new Insets(10,10,10,10));
                    boite.setFillWidth(false);
                    boite.setPrefWidth(300);
                    boite.setStyle(
                    "-fx-background-color:#C1BDB3;"+
                    "-fx-background-radius:15px;");
                    HBox hBox=new HBox();
                    hBox.setAlignment(Pos.TOP_CENTER);
                    hBox.setPadding(new Insets(10,10,10,10));
                    hBox.setSpacing(5);


                    hBox.getChildren().addAll(nom2,prenom2);

                    boite.getChildren().add(hBox);
                    boite.getChildren().addAll(date2);
                    if(mailsent==1){
                        Label mailLabel=new Label("mail déjà envoyé");
                        boite.getChildren().add(mailLabel);
                        boite.setStyle(
                    "-fx-background-color:#37d9bf;"+
                    "-fx-background-radius:15px;");
                    }else if(mailsent==2){
                        Label mailLabel=new Label("mail à été infirmé");
                        boite.getChildren().add(mailLabel);
                        boite.setStyle(
                    "-fx-background-color:#fe99cb;"+
                    "-fx-background-radius:15px;");
                    }
                    
                    boite.getChildren().addAll(button);
                    


                    if(index%3==0){
                        leftBox.getChildren().add(boite);
                    }
                    else if(index%3==1){
                        centerBox.getChildren().add(boite);

                    }
                    else{
                        rigthBox.getChildren().add(boite);
                    }

                    index++;

                }
            }
            catch(Exception error){
                System.out.println(error);
            }
        }
    }


}


