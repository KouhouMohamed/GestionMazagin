package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ConnectionDB.ConnectToBD;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;



public class FormAddClient {
	ScrollPane sp = new ScrollPane();
	VBox root = new VBox();
	VBox rootV = new VBox();
	
	Scene scene = new Scene(sp, 200,0);
	Stage window = new Stage();
	Label titleLabel = new Label("Nouveau Client");
	
	Label ClientNomLabel = new Label("Nom : ");
	TextField ClientNomText = new TextField();
	Label nominfos = new Label();
	
	Label ClientPrenomLabel = new Label("Prénom : ");
	TextField ClientPrenomText = new TextField();
	Label prenominfos = new Label();
	
	Label ClientNumTelLabel = new Label("Numero de Tel : ");
	TextField ClientNumTelText = new TextField();
	Label telinfos = new Label();
	
	Label ClientEmailLabel = new Label("Email : ");
	TextField ClientEmailText = new TextField();
	Label emailinfos = new Label();
	
	Label ClientAdressLabel = new Label("Address : ");
	TextField ClientAdressText = new TextField();
	Label addressinfos = new Label();
	
	HBox buttonsBox = new HBox();
	Button addBtn = new Button("Ajouter");
	Button cancelBtn = new Button("Annuler");
	Button emptyBtn = new Button("Vider");
	Label messageLabel = new Label();
	
	Alert message = new Alert(Alert.AlertType.WARNING);
	private void addNodesToWindow() {
		buttonsBox.getChildren().addAll(addBtn, emptyBtn, cancelBtn);
		rootV.getChildren().add(titleLabel);
		root.getChildren().addAll(ClientNomLabel, ClientNomText, nominfos);
		root.getChildren().addAll(ClientPrenomLabel, ClientPrenomText, prenominfos);
		root.getChildren().addAll(ClientNumTelLabel,ClientNumTelText,telinfos);
		root.getChildren().addAll(ClientEmailLabel, ClientEmailText, emailinfos);
		root.getChildren().addAll(ClientAdressLabel, ClientAdressText,addressinfos);
		root.getChildren().add(buttonsBox);
		rootV.getChildren().add(root);
		
	}
	
	private void initWindow() {
		sp.setContent(rootV);
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		window.setScene(scene);
		window.setWidth(600);
		window.setHeight(600);
		window.setTitle("Ajouter un nouveau client");
		window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		addNodesToWindow();
		addEvent();
	}
	private void addEvent() {
		window.setOnCloseRequest(event ->{
			event.consume();
		});
		//Ajouter des events au btns
		addBtn.setOnAction(event ->{
			ConnectToBD addClient = new ConnectToBD();
			if(ClientNomText.getText().isEmpty()) {ClientNomText.getStyleClass().add("labelError");}
			if(ClientPrenomText.getText().isEmpty()) {ClientPrenomText.getStyleClass().add("labelError");}
			if(ClientNumTelText.getText().isEmpty()) {ClientNumTelText.getStyleClass().add("labelError");}
			if(ClientEmailText.getText().isEmpty()) {ClientEmailText.getStyleClass().add("labelError");}
			if(ClientAdressText.getText().isEmpty()) {ClientAdressText.getStyleClass().add("labelError");}
			if(!ClientNumTelText.getStyleClass().contains("labelError")&&!ClientNomText.getStyleClass().contains("labelError")
					&&!ClientPrenomText.getStyleClass().contains("labelError")&&!ClientEmailText.getStyleClass().contains("labelError")&&!ClientAdressText.getStyleClass().contains("labelError")) {

				
				String nomCli = ClientNomText.getText().toUpperCase();
				String prenomCli = ClientPrenomText.getText().toUpperCase();
				long numTel = Long.parseLong(ClientNumTelText.getText());
				String email = ClientEmailText.getText();
				String address = ClientAdressText.getText();
				String requet = "select * from Clients where Nom ='"+nomCli+"' and Prenom='"+prenomCli+"' and NumTelephone="+numTel+" and Email ='"+email+"' and Address='"+address+"'";

				ResultSet result = addClient.queryExecute(requet);
				//System.out.println();
				try {
					if(result.next()==false || (result.getString("Nom").equals(nomCli)&&result.getString("Prenom").equals(prenomCli)&&result.getString("Email").equals(email)&&result.getString("Address").equals(address)&&(result.getLong("NumTelephone")==numTel))){
							addClient.addClient(nomCli, prenomCli, numTel, email, address);
							
							
							ClientNomText.setText("");
							ClientPrenomText.setText("");
							ClientNumTelText.setText("");
							ClientEmailText.setText("");
							ClientAdressText.setText("");

							ClientNomText.getStyleClass().add("labelError");
							ClientPrenomText.getStyleClass().add("labelError");
							ClientNumTelText.getStyleClass().add("labelError");
							ClientEmailText.getStyleClass().add("labelError");
							ClientAdressText.getStyleClass().add("labelError");
							
							nominfos.setText("");
							prenominfos.setText("");
							telinfos.setText("");
							addressinfos.setText("");
							emailinfos.setText("");
							messageLabel.getStyleClass().setAll("messageValid");
						 	messageLabel.setText("Client ajouter avec succé !!!");	
						 	ListClients.Remplirtable();
					}
					else {
							messageLabel.getStyleClass().setAll("messageError");
							messageLabel.setText("Attention le client "+nomCli+" "+prenomCli +"est déja dans la base!!!");	
						 	
							nominfos.getStyleClass().setAll("labelinfosError");
							nominfos.setText("*ce Nom est déja existe");
							prenominfos.getStyleClass().setAll("labelinfosError");
							prenominfos.setText("*ce prenom est déja existe");
							telinfos.getStyleClass().setAll("labelinfosError");
							telinfos.setText("*ce numéro est déja existe");
							emailinfos.getStyleClass().setAll("labelinfosError");
							emailinfos.setText("*ce mail est déja existe");
							addressinfos.getStyleClass().setAll("labelinfosError");
							addressinfos.setText("*cette addresse est déja existe");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				messageLabel.getStyleClass().setAll("messageError");
				messageLabel.setText("Remplir tous les champs est réssayer à nouveau !");	
			 	
			}
		});
		
		
		
		cancelBtn.setOnAction(event ->{
			window.close();
		});
		
		emptyBtn.setOnAction(event ->{
			
			ClientNomText.setText("");
			ClientPrenomText.setText("");
			ClientNumTelText.setText("");
			ClientEmailText.setText("");
			ClientAdressText.setText("");
			
			ClientNomText.getStyleClass().add("labelError");
			ClientPrenomText.getStyleClass().add("labelError");
			ClientNumTelText.getStyleClass().add("labelError");
			ClientEmailText.getStyleClass().add("labelError");
			ClientAdressText.getStyleClass().add("labelError");
			
			
			
		});
		
		
		ClientNomText.setOnKeyTyped(event ->{
			ClientNomLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
			if(!ClientNomText.getText().matches("[a-zA-Z-]{3,}")) {
				ClientNomText.getStyleClass().add("labelError");
				nominfos.getStyleClass().setAll("labelinfosError");
				nominfos.setText("*nom incorrect");
			}
			else {
				ClientNomText.getStyleClass().removeAll("labelError");
				nominfos.getStyleClass().setAll("labelinfosValid");
				nominfos.setText("*nom valide");
			}
			
		});
		ClientPrenomText.setOnKeyTyped(event ->{
			ClientPrenomLabel.getStyleClass().setAll("labelOn");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
			if(!ClientPrenomText.getText().matches("[a-zA-Z-]{3,}")) {
				ClientPrenomText.getStyleClass().add("labelError");
				prenominfos.getStyleClass().setAll("labelinfosError");
				prenominfos.setText("*prénom incorrect");
			}
			else {
				ClientPrenomText.getStyleClass().removeAll("labelError");
				prenominfos.getStyleClass().setAll("labelinfosValid");
				prenominfos.setText("*prénom valide");
			}
		});
		ClientNumTelText.setOnKeyTyped(event ->{
			ClientNumTelLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
		
			if(!ClientNumTelText.getText().matches("0{1}(6|7){1}\\d{8}")) {
				ClientNumTelText.getStyleClass().add("labelError");
				telinfos.getStyleClass().setAll("labelinfosError");
				telinfos.setText("*numéro incorrect");
			}
			else {
				ClientNumTelText.getStyleClass().removeAll("labelError");
				telinfos.getStyleClass().setAll("labelinfosValid");
				telinfos.setText("*numéro valide");
			}
		});
		
		ClientEmailText.setOnKeyTyped(event ->{
			ClientEmailLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
		
			if(!ClientEmailText.getText().matches("[a-zA-Z]+[a-zA-Z0-9]+@[a-zA-Z]+(\\.)[a-zA-Z]{2,3}")) {
				ClientEmailText.getStyleClass().add("labelError");
				emailinfos.getStyleClass().setAll("labelinfosError");
				emailinfos.setText("*email incorrect");
			}
			else {
				ClientEmailText.getStyleClass().removeAll("labelError");
				emailinfos.getStyleClass().setAll("labelinfosValid");
				emailinfos.setText("*email valide");
			}
		});
		ClientAdressText.setOnKeyTyped(event ->{
			ClientAdressLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
		
//			if(!ClientAdressText.getText().matches("(^[a-zA-Z]+|[a-zA-Z]$)")) {
//				ClientAdressText.getStyleClass().add("labelError");
//				addressinfos.getStyleClass().setAll("labelinfosError");
//				addressinfos.setText("*address incorrect");
//			}
//			else {
//				ClientAdressText.getStyleClass().removeAll("labelError");
//				addressinfos.getStyleClass().setAll("labelinfosValid");
//				addressinfos.setText("*address valide");
//			}
		});
		
		ClientNomText.setOnMouseClicked(event ->{
			ClientNomLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
			
		});
		ClientPrenomText.setOnMouseClicked(event ->{
			ClientPrenomLabel.getStyleClass().setAll("labelOn");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
		});
		ClientNumTelText.setOnMouseClicked(event ->{
			ClientNumTelLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
			
		});
		ClientEmailText.setOnMouseClicked(event ->{
			ClientEmailLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientAdressLabel.getStyleClass().setAll("labelForm");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			
		});
		ClientAdressText.setOnMouseClicked(event ->{
			ClientAdressLabel.getStyleClass().setAll("labelOn");
			ClientPrenomLabel.getStyleClass().setAll("labelForm");
			ClientNumTelLabel.getStyleClass().setAll("labelForm");
			ClientNomLabel.getStyleClass().setAll("labelForm");
			ClientEmailLabel.getStyleClass().setAll("labelForm");
			
		});	
		
		
	}
	private void addStyleToNodes() {
		scene.getStylesheets().add("css/style.css");
		root.getStyleClass().add("ajoutWindow");
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		
		
		Insets marginText = new Insets(0, 0, 0, 20);
		Insets marginLabel = new Insets(0, 0, 0, 5);
		root.setMargin(ClientNomLabel,marginLabel);
		root.setMargin(ClientNomText,marginText);
		root.setMargin(ClientPrenomLabel,marginLabel);
		root.setMargin(ClientPrenomText,marginText);
		root.setMargin(ClientNumTelLabel,marginLabel);
		root.setMargin(ClientNumTelText,marginText);
		root.setMargin(ClientEmailLabel,marginLabel);
		root.setMargin(ClientEmailText,marginText);
		root.setMargin(ClientAdressLabel,marginLabel);
		root.setMargin(ClientAdressText,marginText);
		root.setMargin(buttonsBox, marginLabel);
		root.setMargin(telinfos, marginText);
		root.setMargin(nominfos, marginText);
		root.setMargin(prenominfos, marginText);
		root.setMargin(emailinfos, marginText);
		root.setMargin(addressinfos, marginText);
		
		
		titleLabel.getStyleClass().add("labelTitle");
		titleLabel.setMinWidth(window.getWidth());
		titleLabel.setMinHeight(100);
		titleLabel.setAlignment(Pos.CENTER);
		
		ClientNomText.setMaxWidth(380);
		ClientNomText.setFont(Font.font(15));
		ClientPrenomText.setMaxWidth(380);
		ClientPrenomText.setFont(Font.font(15));
		ClientNumTelText.setMaxWidth(380);
		ClientNumTelText.setFont(Font.font(15));
		ClientEmailText.setMaxWidth(380);
		ClientEmailText.getStyleClass().add("textfield");
		ClientAdressText.setMaxWidth(380);
		ClientAdressText.getStyleClass().add("textfield");
		
		
		ClientNomLabel.setMaxWidth(380);
		ClientNomLabel.getStyleClass().add("labelForm");
		ClientPrenomLabel.setMaxWidth(380);
		ClientPrenomLabel.getStyleClass().add("labelForm");
		ClientNumTelLabel.setMaxWidth(380);
		ClientNumTelLabel.getStyleClass().add("labelForm");
		ClientEmailLabel.setMaxWidth(380);
		ClientEmailLabel.getStyleClass().add("labelForm");
		ClientAdressLabel.setMaxWidth(380);
		ClientAdressLabel.getStyleClass().add("labelForm");
		
		nominfos.setMaxHeight(5);
		prenominfos.setMaxHeight(5);
		telinfos.setMaxHeight(5);
		emailinfos.setMaxHeight(5);
		addressinfos.setMaxHeight(5);
		
		buttonsBox.setMaxWidth(380);
		buttonsBox.setSpacing(50);
		addBtn.getStyleClass().add("addBtn");
		cancelBtn.getStyleClass().add("cancelBtn");
		emptyBtn.getStyleClass().add("emptyBtn");
		
		
		
	}
	private void ToSetClient(int id) {
		ConnectToBD addClient = new ConnectToBD();
		ClientNumTelText.setDisable(true);
		ClientEmailText.setDisable(true);
		ClientAdressText.setDisable(true);
		if(id!=-1) {
			try {
				String requet;
				requet = "select * from Clients where IdClient ="+id;
				ResultSet result = addClient.queryExecute(requet);
				if(result.next()) {
					ClientNumTelText.setDisable(false);
					ClientEmailText.setDisable(false);
					ClientAdressText.setDisable(false);
					ClientNomText.setText(result.getString("Nom"));
					ClientPrenomText.setText(result.getString("Prenom"));
					ClientNumTelText.setText(String.valueOf(result.getString("NumTelephone")));
					ClientEmailText.setText(result.getString("Email"));
					ClientAdressText.setText(result.getString("Address"));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		addBtn.setText("Modifier");
		emptyBtn.setVisible(false);
		ClientNomText.setOnKeyTyped(event->{
			if(!ClientPrenomText.getText().isEmpty()) {
				prenominfos.getStyleClass().setAll("labelinfosValid");
				prenominfos.setText("");
				try {
					String requet;
					requet = "select * from Clients where Nom ='"+ClientNomText.getText()+"' and Prenom='"+ClientPrenomText.getText()+"'";
					ResultSet result = addClient.queryExecute(requet);
					if(result.next()) {
						ClientNumTelText.setDisable(false);
						ClientEmailText.setDisable(false);
						ClientAdressText.setDisable(false);
						//&result.getString("Email").equals(email)&&result.getString("Address").equals(address)&&(result.getLong("NumTelephone")==numTel)
						ClientNumTelText.setText(String.valueOf(result.getString("NumTelephone")));
						ClientEmailText.setText(result.getString("Email"));
						ClientAdressText.setText(result.getString("Address"));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else {
				nominfos.getStyleClass().setAll("labelinfosError");
				nominfos.setText("*saisir le prénom pour afficher les informations");
			}
		});
		
		ClientPrenomText.setOnKeyTyped(event->{
			nominfos.getStyleClass().setAll("labelinfosValid");
			nominfos.setText("");
			if(!ClientNomText.getText().isEmpty()) {
				try {
					String requet;
					requet = "select * from Clients where Nom ='"+ClientNomText.getText()+"' and Prenom='"+ClientPrenomText.getText()+"'";
					ResultSet result = addClient.queryExecute(requet);
					if(result.next()) {
						ClientNumTelText.setDisable(false);
						ClientEmailText.setDisable(false);
						ClientAdressText.setDisable(false);
						ClientNumTelText.setText(String.valueOf(result.getString("NumTelephone")));
						ClientEmailText.setText(result.getString("Email"));
						ClientAdressText.setText(result.getString("Address"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else {
				prenominfos.getStyleClass().setAll("labelinfosError");
				prenominfos.setText("*saisir le nom pour afficher les informations");
			}
		});
		addBtn.setOnAction(event->{
			try {
				String requet = " UPDATE `Clients` SET `NumTelephone`= "+Long.parseLong(ClientNumTelText.getText())+" , `Email`='"+ClientEmailText.getText()+"' , `Address`='"+ClientAdressText.getText()+"' WHERE `Nom`='"+ClientNomText.getText()+"' and `Prenom`='"+ClientPrenomText.getText()+"'";
				Statement sqlConnection = addClient.getConnection().createStatement();
				sqlConnection.executeUpdate(requet);
				ClientNumTelText.setDisable(true);
				ClientEmailText.setDisable(true);
				ClientAdressText.setDisable(true);
				telinfos.setText("");
				emailinfos.setText("");
				addressinfos.setText("");
				message.setTitle("Information...");
			 	message.setHeaderText("les infomation du client " + ClientNomText.getText().toUpperCase() +" "+ClientPrenomText.getText().toUpperCase()+" sont modifiés avec succé");
			 	message.setAlertType(AlertType.INFORMATION);
			 	PauseTransition delay = new PauseTransition();
			 	delay.setDuration(Duration.seconds(1));
			 	delay.setOnFinished(ev->{
			 		message.hide();
			 	});
			 	message.show();
			 	delay.play();
				ListClients.Remplirtable();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		});
	}
	public FormAddClient(boolean set, int id) {
		initWindow();
		addStyleToNodes();
		if(set) {ToSetClient(id);}

		window.show();
	}
}
