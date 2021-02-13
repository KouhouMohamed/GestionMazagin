package application;

import ConnectionDB.ConnectToBD;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import Classes.*;

public class Login {
	ConnectToBD connect = new ConnectToBD();
	VBox root = new VBox();
	Scene scene =  new Scene(root);
	Stage window = new Stage();
	
	Label EmailLabel = new Label("Email : ");
	TextField EmailText = new TextField();
	Label emailinfos = new Label();
	
	Label PasswordLabel = new Label("Password : ");
	PasswordField PasswordText = new PasswordField();
	Label passinfos = new Label();
	
	HBox RadioBox = new HBox();
	ToggleGroup RadioGroup = new ToggleGroup();
	RadioButton clientRadio = new RadioButton("Client");
	RadioButton adminiRadio = new RadioButton("Administration");
	
	Button loginBtn = new Button("Login");
	Button CreateBtn = new Button("Nouveau Compte");
	HBox buttons = new HBox();
	static public String UserFirstName="";
	static public String UserSecondName="";
	static public int codeClient=-1;
	
	public Login() {
		InitWindow();
		
		window.show();
	}
	
	public void InitWindow() {
		window.setScene(scene);
		window.setWidth(500);
		window.setHeight(500);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Login");
		window.setResizable(false);
		
		addStyle();
		addNodes();
		addEvent();
		
	}
	public void addNodes() {
		RadioBox.getChildren().addAll(clientRadio,adminiRadio);
		buttons.getChildren().addAll(loginBtn,CreateBtn);
		root.getChildren().addAll(EmailLabel,EmailText,emailinfos);
		root.getChildren().addAll(PasswordLabel,PasswordText,passinfos);
		root.getChildren().addAll(RadioBox);
		root.getChildren().add(buttons);
		clientRadio.setToggleGroup(RadioGroup);
		adminiRadio.setToggleGroup(RadioGroup);
		clientRadio.setSelected(true);
	}
	private void addStyle() {
		scene.getStylesheets().add("css/style.css");
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10);
		buttons.setSpacing(20);
		buttons.setAlignment(Pos.CENTER);
		root.getStyleClass().add("Loginroot");
		double width = 200;
		EmailText.setMaxWidth(width);
		PasswordText.setMaxWidth(width);
		EmailText.getStyleClass().add("textfield");
		PasswordText.getStyleClass().add("textfield");
		RadioBox.setAlignment(Pos.CENTER);
		
		EmailLabel.getStyleClass().add("loginLabel");
		PasswordLabel.getStyleClass().add("loginLabel");
		
		loginBtn.getStyleClass().add("loginBtn");
		CreateBtn.getStyleClass().add("createBtn");
		
	} 
	public void addEvent() {
		window.setOnCloseRequest(event->{
			MainWindows.getRoot().getTop().setDisable(true);
			window.close();
		});
		loginBtn.setOnAction(event->{
			Alert message = new Alert(AlertType.ERROR);
			message.setTitle("Information...");
		 	message.setHeaderText("Email ou mot de passe incorects");
		 	PauseTransition delay = new PauseTransition();
		 	delay.setDuration(Duration.seconds(1));
		 	delay.setOnFinished(ev->{
		 		message.hide();
		 	});
			String email = EmailText.getText();
			String password = PasswordText.getText();
			char fct;
			if(clientRadio.isSelected()) {
				fct = 'C';
			}
			else {
				fct = 'A';
			}
			Boolean login = connect.checkLogin(email, password, fct);
			if(login) {
				if(fct == 'A') {
					window.close();
				}
				else {
					
					MainWindowClient();
					window.close();
				}
			}
			else {
				EmailText.getStyleClass().add("labelError");
				PasswordText.getStyleClass().add("labelError");
			 	message.show();
			 	delay.play();
			}
		});
		CreateBtn.setOnAction(event->{
			new FormAddClient(false, -1);
		});
	}
	public void MainWindowClient() {
		MainWindows.getNouveauProd().setDisable(true);
		MainWindows.getNouveauProd().setVisible(false);
		
		MainWindows.getModifierClient().setDisable(true);
		MainWindows.getModifierClient().setVisible(false);
		
		MainWindows.getModifierProd().setDisable(true);
		MainWindows.getModifierProd().setVisible(false);
		
		MainWindows.getListClient().setDisable(true);
		MainWindows.getListClient().setVisible(false);
		
		MainWindows.getNouveauClient().setDisable(true);
		MainWindows.getNouveauClient().setVisible(false);
		
		MainWindows.getListProd().setOnAction(event->{
			ListProducts listProds = new ListProducts(true);
			listProds.getActions().setCellValueFactory(new PropertyValueFactory<Produit,String>("selectBtn"));
		});
		MainWindows.getListCat().setOnAction(event->{
			ListCategories listCats = new ListCategories(true);
			listCats.getActions().setCellValueFactory(new PropertyValueFactory<Categorie,String>("viewButt"));

		});
		MainWindows.chercherVente.setOnAction(event->{
			new ChercherVente(UserFirstName,UserSecondName);
		});
		MainWindows.nouvelleVente.setOnAction(event->{
			new FormAddVente(UserFirstName,UserSecondName);
			FormAddVente.tabLignes = MainWindows.listLignes;
		});
		MainWindows.ListVente.setOnAction(event->{
			new ListVentes(codeClient);
		});
		MainWindows.ListVente.setText("Mes achats");
	}
}
