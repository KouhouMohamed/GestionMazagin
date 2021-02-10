package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import Classes.Categorie;
import ConnectionDB.ConnectToBD;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class FormAddCategorie {
	VBox root = new VBox();
	Scene scene = new Scene(root);
	Stage window = new Stage();
	Label titleLabel = new Label("Nouveau Categorie");
	
	Label CodeCatLabel = new Label("Code Categorie : ");
	TextField CodeCatText = new TextField();
	Label codeinfos = new Label();
	
	Label IntituleLabel = new Label("Intitule  : ");
	TextField IntituleText = new TextField();
	Label intituleinfos = new Label();
	
	HBox buttonsBox = new HBox();
	Button addBtn = new Button("Ajouter");
	Button cancelBtn = new Button("Annuler");
	Button emptyBtn = new Button("Vider");
	
	Alert message = new Alert(Alert.AlertType.WARNING);
	

	private void addNodesToWindow() {
		buttonsBox.getChildren().addAll(addBtn, emptyBtn, cancelBtn);
		root.getChildren().add(titleLabel);
		root.getChildren().addAll(CodeCatLabel,CodeCatText,codeinfos);
		root.getChildren().addAll(IntituleLabel, IntituleText, intituleinfos);
		root.getChildren().add(buttonsBox);
		
	}
	
	private void initWindow() {
		window.setScene(scene);
		window.setWidth(600);
		window.setHeight(450);
		window.setTitle("Ajouter une nouvelle categorie");
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
			ConnectToBD addCat = new ConnectToBD();
			if(CodeCatText.getText().isEmpty()) {CodeCatText.getStyleClass().add("labelError");}
			if(IntituleText.getText().isEmpty()) {IntituleText.getStyleClass().add("labelError");}
			if(!IntituleText.getStyleClass().contains("labelError")&&!CodeCatText.getStyleClass().contains("labelError")) {

				long code = Long.parseLong(CodeCatText.getText());
				String intitule = IntituleText.getText();
				String requet = "select * from Categorie where CodeCat = "+code;
				ResultSet result = addCat.queryExecute(requet);
				//System.out.println();
				try {
					if(result.next()==false){
							addCat.addCategorie(code, intitule);
							
							message.setAlertType(Alert.AlertType.INFORMATION);
							message.setTitle("Ajout avec succé ...");
						 	message.setHeaderText("La categorie : "+intitule+ " est ajouté avec succé");
						 	message.setResizable(false);
						 	message.showAndWait();
						 	
							CodeCatText.setText("");
							IntituleText.setText("");
							CodeCatText.getStyleClass().add("labelError");
							IntituleText.getStyleClass().add("labelError");
							codeinfos.setText("");
							intituleinfos.setText("");
							intituleinfos.setText("");
							MainWindows.Category.getItems().add(intitule);
							window.close();
							FormAddProduct.Category.getSelectionModel().selectLast();
							FormAddProduct.listOfCategories.add(new Categorie(code,intitule));
					}
					else {
							CodeCatText.getStyleClass().add("labelError");
						 	message.setTitle("Attention...");
						 	message.setHeaderText("Erreur dans le code de la categorie, vous avez saisi un code déja existant");
						 	message.setContentText("Essayez un autre ID de 4 chiffres!");
						 	message.showAndWait();
						 	codeinfos.getStyleClass().setAll("labelinfosError");
							codeinfos.setText("*ce ID est déja existe");
							}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				CodeCatText.getStyleClass().add("labelError");
			 	message.setTitle("Attention...");
			 	message.setHeaderText("Un champ ou plusieurs sont vides");
			 	message.setContentText("Remplir les champs et essayer à nouveau ");
			 	message.showAndWait();
			}
		});
		
		
		
		cancelBtn.setOnAction(event ->{
			window.close();
			
		});
		
		emptyBtn.setOnAction(event ->{
			CodeCatText.setText("");
			IntituleText.setText("");
			CodeCatText.getStyleClass().add("labelError");
			IntituleText.getStyleClass().add("labelError");
			
			codeinfos.getStyleClass().setAll("labelinfosError");
			codeinfos.setText("*quantite incorrecte");
			intituleinfos.getStyleClass().setAll("labelinfosError");
			intituleinfos.setText("*quantite incorrecte");
			intituleinfos.getStyleClass().setAll("labelinfosError");
			intituleinfos.setText("*quantite incorrecte");
			
		});
		CodeCatText.setOnKeyTyped(event ->{
			CodeCatLabel.getStyleClass().setAll("labelOn");
			IntituleLabel.getStyleClass().setAll("labelForm");
			IntituleLabel.getStyleClass().setAll("labelForm");
			if(!CodeCatText.getText().matches("\\d{4,}")) {
				CodeCatText.getStyleClass().add("labelError");
				codeinfos.getStyleClass().setAll("labelinfosError");
				codeinfos.setText("*code incorrect");
				
			}
			else {
				CodeCatText.getStyleClass().removeAll("labelError");
				codeinfos.getStyleClass().setAll("labelinfosValid");
				codeinfos.setText("*code valide");
			}
			
		});
		
		IntituleText.setOnKeyTyped(event ->{
			IntituleLabel.getStyleClass().setAll("labelOn");
			CodeCatLabel.getStyleClass().setAll("labelForm");
			if(!IntituleText.getText().matches("[a-zA-Z-]{3,}")) {
				IntituleText.getStyleClass().add("labelError");
				intituleinfos.getStyleClass().setAll("labelinfosError");
				intituleinfos.setText("*intitule incorrect");
			}
			else {
				IntituleText.getStyleClass().removeAll("labelError");
				intituleinfos.getStyleClass().setAll("labelinfosValid");
				intituleinfos.setText("*intitule valide");
			}
			
		});
		
			
		
		
	}
	private void addStyleToNodes() {
		scene.getStylesheets().add("css/style.css");
		root.getStyleClass().add("ajoutWindow");
		root.setSpacing(5);
		
		Insets marginText = new Insets(0, 0, 0, 20);
		Insets marginLabel = new Insets(0, 0, 0, 5);
		root.setMargin(CodeCatLabel,marginLabel);
		root.setMargin(CodeCatText,marginText);
		root.setMargin(IntituleLabel,marginLabel);
		root.setMargin(IntituleText,marginText);
		root.setMargin(buttonsBox, marginLabel);
		root.setMargin(codeinfos, marginText);
		root.setMargin(intituleinfos, marginText);
		
		
		titleLabel.getStyleClass().add("labelTitle");
		titleLabel.setMinWidth(window.getWidth());
		titleLabel.setMinHeight(100);
		titleLabel.setAlignment(Pos.CENTER);
		
		CodeCatText.setMaxWidth(570);
		CodeCatText.setFont(Font.font(15));
		IntituleText.setMaxWidth(570);
		IntituleText.setFont(Font.font(15));
		
		CodeCatLabel.setMaxWidth(570);
		CodeCatLabel.getStyleClass().add("labelForm");
		IntituleLabel.setMaxWidth(570);
		IntituleLabel.getStyleClass().add("labelForm");
		codeinfos.setMaxWidth(570);
		codeinfos.setMaxHeight(5);
		intituleinfos.setMaxWidth(570);
		intituleinfos.setMaxHeight(5);
		
		buttonsBox.setMaxWidth(570);
		buttonsBox.setSpacing(50);
		addBtn.getStyleClass().add("addBtn");
		cancelBtn.getStyleClass().add("cancelBtn");
		emptyBtn.getStyleClass().add("emptyBtn");
		
		
		
	}
	public FormAddCategorie() {
		initWindow();
		addStyleToNodes();

		window.show();
	}
}
