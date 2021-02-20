package gestionCompte;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormNewCompte {

	Stage window = new Stage();
	VBox root =  new VBox();
	Scene scene = new Scene(root);
	
	HBox infoBox = new HBox();
	VBox labelsBox = new VBox();
	VBox textsBox = new VBox();
	
	Label nom = new Label("Nom:");
	Label prenom = new Label("Prénom:");
	Label compte = new Label("Compte:");
	
	TextField nomText = new TextField();
	TextField prenomText = new TextField();
	TextField compteText = new TextField();
	
	Button addCompte = new Button("Créer le compte");
	
	ConnectToBase connect = new ConnectToBase();
	public FormNewCompte() {
		initWindow();
		window.show();
	}
	private void initWindow() {
		scene.getStylesheets().add("css/styleExam.css");
		window.setScene(scene);
		window.setMaxHeight(600);
		window.setMaxWidth(600);
		window.setMinWidth(400);
		window.setTitle("Ajouter une nouvelle compte");
		window.initModality(Modality.APPLICATION_MODAL);
		addNodes();
		addProprties();
		addEvents();
	}
	private void addNodes() {
		labelsBox.getChildren().addAll(nom,prenom,compte);
		textsBox.getChildren().addAll(nomText,prenomText,compteText);
		infoBox.getChildren().addAll(labelsBox, textsBox);
		root.getChildren().addAll(infoBox, addCompte);
	}
	
	private void addProprties() {
		
		root.setSpacing(10);
		labelsBox.setSpacing(20);
		textsBox.setSpacing(20);
		infoBox.setSpacing(50);
		
		addCompte.setDisable(true);
		
		nomText.getStyleClass().add("textfield");
		prenomText.getStyleClass().add("textfield");
		compteText.getStyleClass().add("textfield");
		
		Insets margin = new Insets(20, 20, 20, 20);
		root.setMargin(addCompte,margin);
		root.setMargin(infoBox,margin);
		
	}
	private void addEvents() {
		addCompte.setOnAction(event->{
			if(!nomText.getStyleClass().contains("textfieldError") && !prenomText.getStyleClass().contains("textfieldError") && !compteText.getStyleClass().contains("textfieldError")) {
				Boolean add = connect.addCompte(Long.parseLong(compteText.getText()), nomText.getText().toUpperCase(), prenomText.getText().toUpperCase());
				System.out.println(add);
			}
		});
		
		nomText.setOnKeyTyped(event->{
			if(!nomText.getText().matches("[a-zA-Z]+")) {
				addCompte.setDisable(true);
				nomText.getStyleClass().setAll("textfieldError");
				
			}
			else {
				addCompte.setDisable(false);
				nomText.getStyleClass().setAll("textfield");
			}
		});
		
		prenomText.setOnKeyTyped(event->{
			if(!prenomText.getText().matches("[a-zA-Z]+")) {
				addCompte.setDisable(true);
				prenomText.getStyleClass().setAll("textfieldError");
			}
			else {
				addCompte.setDisable(false);
				prenomText.getStyleClass().setAll("textfield");
			}
		});
		
		compteText.setOnKeyTyped(event->{
			if(!compteText.getText().matches("[0-9a-zA-Z]+") || compteText.getText().length()>15) {
				addCompte.setDisable(true);
				compteText.getStyleClass().setAll("textfieldError");
			}
			else {
				addCompte.setDisable(false);
				compteText.getStyleClass().setAll("textfield");
			}
		});
	}
}
