package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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



public class FormSetCategorie {
	VBox root = new VBox();
	Scene scene = new Scene(root);
	Stage window = new Stage();
	Label titleLabel = new Label("Modifier Categorie");
	
	Label CodeCatLabel = new Label("Code Categorie : ");
	TextField CodeCatText = new TextField();
	Label codeinfos = new Label();
	
	Label IntituleLabel = new Label("Intitule  : ");
	TextField IntituleText = new TextField();
	Label intituleinfos = new Label();
	
	HBox buttonsBox = new HBox();
	Button setBtn = new Button("Modifier");
	Button cancelBtn = new Button("Annuler");
	
	Alert message = new Alert(Alert.AlertType.WARNING);
	

	private void addNodesToWindow() {
		buttonsBox.getChildren().addAll(setBtn, cancelBtn);
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
		setBtn.setOnAction(event ->{
			
			ConnectToBD addCat = new ConnectToBD();
			if(CodeCatText.getText().isEmpty()) {CodeCatText.getStyleClass().add("labelError");}
			if(IntituleText.getText().isEmpty()) {IntituleText.getStyleClass().add("labelError");}
			if(!IntituleText.getStyleClass().contains("labelError")&&!CodeCatText.getStyleClass().contains("labelError")) {

				long code = Long.parseLong(CodeCatText.getText());
				String intitule = IntituleText.getText();
				String requet = "select * from Categorie where `intitule` = '"+intitule+"'";
				String requet1 =" UPDATE `Categorie` SET `intitule`= '"+intitule+"' WHERE CodeCat="+code;
				
				//System.out.println();
				try {
					ResultSet result = addCat.queryExecute(requet);
					if(result.next()==false){
						Statement sqlConnection = addCat.getConnection().createStatement();
						sqlConnection.executeUpdate(requet1);
						ListCategories.Remplirtable();
						window.close();
					}
					else {
							IntituleText.getStyleClass().add("labelError");
						 	intituleinfos.getStyleClass().setAll("labelinfosError");
							codeinfos.setText("*Cet intitule est déja existe");
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
		//IntituleText.setDisable(true);
		
		codeinfos.setMaxWidth(570);
		codeinfos.setMaxHeight(5);
		intituleinfos.setMaxWidth(570);
		intituleinfos.setMaxHeight(5);
		
		buttonsBox.setMaxWidth(570);
		buttonsBox.setSpacing(50);
		setBtn.getStyleClass().add("addBtn");
		cancelBtn.getStyleClass().add("cancelBtn");
		
		
		
	}
	
	public FormSetCategorie(long codeCat) {
		initWindow();
		addStyleToNodes();
		this.CodeCatText.setText(String.valueOf(codeCat));
		ConnectToBD connection = new ConnectToBD();
		String query = "select * from `Categorie` where CodeCat="+Long.parseLong(CodeCatText.getText());
		ResultSet result = connection.queryExecute(query);
		try {
			if(result.next()) {
				IntituleText.setText(result.getString("intitule"));
				
				CodeCatText.getStyleClass().removeAll("labelError");
				setBtn.setDisable(false);
				IntituleText.setDisable(false);
			}}
			catch (Exception e) {}
		window.show();
	}
}
