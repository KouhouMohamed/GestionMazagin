package application;

import ConnectionDB.ConnectToBD;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login {
	ConnectToBD connect = new ConnectToBD();
	VBox root = new VBox();
	Scene scene =  new Scene(root);
	Stage window = new Stage();
	
	Label TitleLabel = new Label("Login");

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
	
	public Login() {
		InitWindow();
		
		window.show();
	}
	
	public void InitWindow() {
		window.setScene(scene);
		window.setWidth(500);
		window.setHeight(700);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Login");
		window.setResizable(false);
		
		addStyle();
		addNodes();
		addEvent();
		
	}
	public void addNodes() {
		RadioBox.getChildren().addAll(clientRadio,adminiRadio);
		root.getChildren().add(TitleLabel);
		root.getChildren().addAll(EmailLabel,EmailText,emailinfos);
		root.getChildren().addAll(PasswordLabel,PasswordText,passinfos);
		root.getChildren().addAll(RadioBox);
		root.getChildren().add(loginBtn);
	}
	private void addStyle() {
		clientRadio.setToggleGroup(RadioGroup);
		adminiRadio.setToggleGroup(RadioGroup);
		clientRadio.setSelected(true);
	} 
	public void addEvent() {
		loginBtn.setOnAction(event->{
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
					
//					MainWindows.getlist().setDisable(true);
//					MainWindows.getNouveauClient().setVisible(false);
//					
//					MainWindows.getNouveauClient().setDisable(true);
//					MainWindows.getNouveauClient().setVisible(false);
					window.close();
				}
			}
		});
	}
}
