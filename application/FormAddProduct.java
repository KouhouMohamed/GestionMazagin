package application;

import java.beans.DesignMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Classes.Categorie;
import ConnectionDB.ConnectToBD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class FormAddProduct {
	ScrollPane sp = new ScrollPane();
	VBox root = new VBox();
	VBox rootV = new VBox();
	
	Scene scene = new Scene(sp, 200,0);
	Stage window = new Stage();
	Label titleLabel = new Label("Nouveau Produit");
	
	Label ProdCodeLabel = new Label("Code : ");
	TextField ProdCodeText = new TextField();
	Label codeinfos = new Label();
	
	Label ProdDesgnLabel = new Label("Designation : ");
	TextField ProdDesgnText = new TextField();
	Label desgninfos = new Label();
	
	Label ProdPrixAchLabel = new Label("Prix Achat : ");
	TextField ProdPrixAchText = new TextField();
	Label prixachinfos = new Label();
	
	Label ProdPrixVenLabel = new Label("Prix Vente : ");
	TextField ProdPrixVenText = new TextField();
	Label prixveninfos = new Label();
	
	Label ProdQteLabel = new Label("Quantité : ");
	TextField ProdQteText = new TextField();
	Label qteinfos = new Label();
	
	HBox catBox = new HBox();
	Label CategoryLabel = new Label("Categorie : ");
	static ChoiceBox<String> Category = MainWindows.Category;
	Button addCat = new Button("Ajouter catégorie");
	Label catinfos = new Label();
	
	Label DateDAjoutLabel = new Label("Date : ");
	DatePicker DatedAjoutPicker = new DatePicker(LocalDate.now());
	
	HBox buttonsBox = new HBox();
	Button addBtn = new Button("Ajouter");
	Button cancelBtn = new Button("Annuler");
	Button emptyBtn = new Button("Vider");
	
	Alert message = new Alert(Alert.AlertType.WARNING);
	static public List<Categorie> listOfCategories;
	
	private void initWindow() {
		sp.setContent(rootV);
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		window.setScene(scene);
		window.setWidth(600);
		window.setHeight(600);
		window.setTitle("Ajouter un nouveau produit");
		window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(true);
		addNodesToWindow();
		addEvent();
	}
	private void addNodesToWindow() {
		buttonsBox.getChildren().addAll(addBtn, emptyBtn, cancelBtn);
		catBox.getChildren().addAll(Category, addCat);
		rootV.getChildren().add(titleLabel);
		root.getChildren().addAll(ProdCodeLabel,ProdCodeText,codeinfos);
		root.getChildren().addAll(ProdDesgnLabel, ProdDesgnText, desgninfos);
		root.getChildren().addAll(ProdPrixAchLabel, ProdPrixAchText, prixachinfos);
		root.getChildren().addAll(ProdPrixVenLabel, ProdPrixVenText, prixveninfos);
		root.getChildren().addAll(ProdQteLabel, ProdQteText, qteinfos);
		root.getChildren().addAll(CategoryLabel,catBox,catinfos);
		root.getChildren().addAll(DateDAjoutLabel, DatedAjoutPicker);
		root.getChildren().add(buttonsBox);
		rootV.getChildren().add(root);
		
	}
	private void addEvent() {
		
		window.setOnCloseRequest(event ->{
			event.consume();
		});
		
		cancelBtn.setOnAction(event ->{
			ListProducts.Remplirtable();
			window.close();
		});
		//Ajouter des events au btns
		addBtn.setOnAction(event ->{
			ConnectToBD addProd = new ConnectToBD();
			
			if(ProdCodeText.getText().isEmpty()) {ProdCodeText.getStyleClass().add("labelError");}
			if(ProdDesgnText.getText().isEmpty()) {ProdDesgnText.getStyleClass().add("labelError");}
			if(ProdPrixAchText.getText().isEmpty()) {ProdPrixAchText.getStyleClass().add("labelError");}
			if(ProdPrixVenText.getText().isEmpty()) {ProdPrixVenText.getStyleClass().add("labelError");}
			if(ProdQteText.getText().isEmpty()) {ProdQteText.getStyleClass().add("labelError");}
			if(Category.getValue()=="Choisir !!") {
					catinfos.getStyleClass().setAll("labelinfosError");
					catinfos.setText("*choisir une categorie");}
			
			if(!ProdCodeText.getStyleClass().contains("labelError")&&!ProdDesgnText.getStyleClass().contains("labelError")
					&&!ProdQteText.getStyleClass().contains("labelError")&&!ProdPrixAchText.getStyleClass().contains("labelError")
					&&!ProdPrixVenText.getStyleClass().contains("labelError") &&(Category.getValue()!="Choisir !!")) {
				long code = Long.parseLong(ProdCodeText.getText());
				String designation = ProdDesgnText.getText().toUpperCase();
				double prixach = Double.valueOf(ProdPrixAchText.getText());
				double prixven = Double.valueOf(ProdPrixVenText.getText());
				String dateajout = DatedAjoutPicker.getValue().toString();
				long idCat;
				
				int quantity = Integer.valueOf(ProdQteText.getText());
				String requet1 = "select * from produits where CodeProd = "+code;
				String requet2 = "select * from produits where Designation = '"+designation+"'";
				ResultSet result1 = addProd.queryExecute(requet1);
				ResultSet result2 = addProd.queryExecute(requet2);
				qteinfos.getStyleClass().setAll("labelinfosValid");
				qteinfos.setText("*quantite valide");
				try {
					if(result1.next()==false){
						if(result2.next()==false) {
							for (Categorie cat : listOfCategories) {
								if(cat.getIntitule().equals(Category.getValue())) {
									idCat = cat.getCodeCat();
									addProd.addProduct(code, designation, prixach, prixven, dateajout, idCat,quantity);
									
									break;
								}
								
							}
							
							
							message.setAlertType(Alert.AlertType.INFORMATION);
							message.setTitle("Ajout avec succé ...");
						 	message.setHeaderText("Le produit "+code+ " est ajouté avec succé");
						 	message.setResizable(false);
						 	message.showAndWait();
						 	
							ProdCodeText.setText("");
							ProdDesgnText.setText("");
							ProdPrixAchText.setText("");
							ProdPrixVenText.setText("");
							ProdQteText.setText("");
							ProdCodeText.getStyleClass().add("labelError");
							ProdDesgnText.getStyleClass().add("labelError");
							ProdPrixAchText.getStyleClass().add("labelError");
							ProdPrixVenText.getStyleClass().add("labelError");
							ProdQteText.getStyleClass().add("labelError");
							//Category.getStyleClass().add("labelError");
							codeinfos.setText("");
							desgninfos.setText("");
							prixachinfos.setText("");
							prixveninfos.setText("");
							qteinfos.setText("");
							catinfos.setText("");
						 	ListProducts.Remplirtable();
						}
						else {
							ProdDesgnText.getStyleClass().add("labelError");
						 	message.setTitle("Attention...");
						 	message.setHeaderText("le produit dons le nom ' "+designation+" ' déja existe");
						 	message.setContentText("Essayez une autre designation");
						 	message.showAndWait();
						 	desgninfos.getStyleClass().setAll("labelinfosError");
						 	desgninfos.setText("*ce nom est déja existe");
					  }
						
					}
					else {
							ProdCodeText.getStyleClass().add("labelError");
						 	message.setTitle("Attention...");
						 	message.setHeaderText("Erreur dans le code, vous avez saisi un code déja existant");
						 	message.setContentText("Essayez un autre code de 4 chiffres!");
						 	message.showAndWait();
						 	codeinfos.getStyleClass().setAll("labelinfosError");
							codeinfos.setText("*ce code est déja existe");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
			 	message.setTitle("Attention...");
			 	message.setHeaderText("Un champ ou plusieurs sont vides");
			 	message.setContentText("Remplir les champs vides et essayer à nouveau ");
			 	message.showAndWait();
			}
		});
		
		
		emptyBtn.setOnAction(event ->{
			ProdCodeText.setText("");
			ProdDesgnText.setText("");
			ProdPrixAchText.setText("");
			ProdPrixVenText.setText("");
			ProdQteText.setText("");
			ProdCodeText.getStyleClass().add("labelError");
			ProdDesgnText.getStyleClass().add("labelError");
			ProdPrixAchText.getStyleClass().add("labelError");
			ProdPrixVenText.getStyleClass().add("labelError");
			ProdQteText.getStyleClass().add("labelError");
			Category.getSelectionModel().selectFirst();
			
			codeinfos.getStyleClass().setAll("labelinfosError");
			codeinfos.setText("*code incorrect");
			desgninfos.getStyleClass().setAll("labelinfosError");
			desgninfos.setText("*designation incorrecte");
			prixachinfos.getStyleClass().setAll("labelinfosError");
			prixachinfos.setText("*prix incorrecte");
			prixveninfos.getStyleClass().setAll("labelinfosError");
			prixveninfos.setText("*prix incorrecte");
			qteinfos.getStyleClass().setAll("labelinfosError");
			qteinfos.setText("*quantite incorrecte");
			catinfos.getStyleClass().setAll("labelinfosError");
			catinfos.setText("*choisir une catégorie");
			
		});
		addCat.setOnAction(event ->{
			new FormAddCategorie();
		});
		
		
		ProdCodeText.setOnKeyTyped(event ->{
			ProdCodeLabel.getStyleClass().setAll("labelOn");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			if(!ProdCodeText.getText().matches("\\d{4,}")) {
				ProdCodeText.getStyleClass().add("labelError");
				codeinfos.getStyleClass().setAll("labelinfosError");
				codeinfos.setText("*code incorrect");
				
			}
			else {
				ProdCodeText.getStyleClass().removeAll("labelError");
				codeinfos.getStyleClass().setAll("labelinfosValid");
				codeinfos.setText("*code valide");
			}
			
		});
		
		ProdDesgnText.setOnKeyTyped(event ->{
			ProdDesgnLabel.getStyleClass().setAll("labelOn");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			if(!ProdDesgnText.getText().matches("[a-zA-Z-]+[a-zA-Z0-9\\-]+")) {
				ProdDesgnText.getStyleClass().add("labelError");
				desgninfos.getStyleClass().setAll("labelinfosError");
				desgninfos.setText("*designation incorrect");
			}
			else {
				ProdDesgnText.getStyleClass().removeAll("labelError");
				desgninfos.getStyleClass().setAll("labelinfosValid");
				desgninfos.setText("*designation valide");
			}
			
		});
		ProdPrixAchText.setOnKeyTyped(event ->{
			ProdPrixAchLabel.getStyleClass().setAll("labelOn");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			if(!ProdPrixAchText.getText().matches("\\d+((,|\\.)\\d+)?")) {
				ProdPrixAchText.getStyleClass().add("labelError");
				prixachinfos.getStyleClass().setAll("labelinfosError");
				prixachinfos.setText("*prix incorrect");
			}
			else if(!ProdPrixVenText.getText().isEmpty()) {
				if(Double.valueOf(ProdPrixAchText.getText()) > Double.valueOf(ProdPrixVenText.getText())){
					ProdPrixVenText.getStyleClass().add("labelError");
					prixveninfos.getStyleClass().setAll("labelinfosError");
					prixveninfos.setText("*Prix de vente est inférieur au prix d'achat");
					prixachinfos.getStyleClass().setAll("labelinfosValid");
					prixachinfos.setText("prix valide");
				}
				else {
					ProdPrixVenText.getStyleClass().removeAll("labelError");
					prixveninfos.getStyleClass().setAll("labelinfosValid");
					prixveninfos.setText("*prix valide");
				}
			}
			else {
				ProdPrixAchText.getStyleClass().removeAll("labelError");
				prixachinfos.getStyleClass().setAll("labelinfosValid");
				prixachinfos.setText("*prix valide");
			}	
		});
		
		ProdPrixVenText.setOnKeyTyped(event ->{
			ProdPrixVenLabel.getStyleClass().setAll("labelOn");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");;
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			if(!ProdPrixVenText.getText().matches("\\d+((,|\\.)\\d+)?")) {
				ProdPrixVenText.getStyleClass().add("labelError");
				prixveninfos.getStyleClass().setAll("labelinfosError");
				prixveninfos.setText("*prix incorrect");
			}
			else if(!ProdPrixAchText.getText().isEmpty()) {
				if(Double.valueOf(ProdPrixAchText.getText()) > Double.valueOf(ProdPrixVenText.getText())){
					ProdPrixVenText.getStyleClass().add("labelError");
					prixveninfos.getStyleClass().setAll("labelinfosError");
					prixveninfos.setText("*Prix de vente est inférieur au prix d'achat");
				}
				else {
					
					ProdPrixVenText.getStyleClass().removeAll("labelError");
					prixveninfos.getStyleClass().setAll("labelinfosValid");
					prixveninfos.setText("*prix valide");
				}
			}
			else {
				ProdPrixVenText.getStyleClass().removeAll("labelError");
				prixveninfos.getStyleClass().setAll("labelinfosValid");
				prixveninfos.setText("*prix valide");
			}
		});
		ProdQteText.setOnKeyTyped(event ->{
			ProdQteLabel.getStyleClass().setAll("labelOn");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
		
			if(!ProdQteText.getText().matches("\\d+")) {
				ProdQteText.getStyleClass().add("labelError");
				qteinfos.getStyleClass().setAll("labelinfosError");
				qteinfos.setText("*quantite incorrecte");
			}
			else {
				ProdQteText.getStyleClass().removeAll("labelError");
				qteinfos.getStyleClass().setAll("labelinfosValid");
				qteinfos.setText("*quantite valide");
			}
		});
		DatedAjoutPicker.setOnMouseClicked(event ->{
			DateDAjoutLabel.getStyleClass().setAll("labelOn");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			
		});
		ProdCodeText.setOnMouseClicked(event ->{
			ProdCodeLabel.getStyleClass().setAll("labelOn");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			
		});
		
		ProdDesgnText.setOnMouseClicked(event ->{
			ProdDesgnLabel.getStyleClass().setAll("labelOn");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			
		});
		ProdPrixAchText.setOnMouseClicked(event ->{
			ProdPrixAchLabel.getStyleClass().setAll("labelOn");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
		});
		ProdPrixVenText.setOnMouseClicked(event ->{
			ProdPrixVenLabel.getStyleClass().setAll("labelOn");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			ProdQteLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
		});
		ProdQteText.setOnMouseClicked(event ->{
			ProdQteLabel.getStyleClass().setAll("labelOn");
			ProdCodeLabel.getStyleClass().setAll("labelForm");
			ProdPrixVenLabel.getStyleClass().setAll("labelForm");
			ProdPrixAchLabel.getStyleClass().setAll("labelForm");
			ProdDesgnLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			
		});
		Category.setOnAction(event ->{
			if(Category.getValue() != "Choisir !!") {catinfos.setText("");}
		});
		
		
	}
	private void addStyleToNodes() {
		scene.getStylesheets().add("css/style.css");
		root.getStyleClass().add("ajoutWindow");
		root.setSpacing(5);
		Category.getSelectionModel().selectFirst();
		DatedAjoutPicker.setEditable(false);
		root.setAlignment(Pos.CENTER);
		Insets marginText = new Insets(0, 0, 0, 20);
		Insets marginLabel = new Insets(0, 0, 0, 5);
		root.setMargin(ProdCodeLabel,marginLabel);
		root.setMargin(ProdCodeText,marginText);
		root.setMargin(ProdDesgnLabel,marginLabel);
		root.setMargin(ProdDesgnText,marginText);
		root.setMargin(ProdPrixAchLabel,marginLabel);
		root.setMargin(ProdPrixAchText,marginText);
		root.setMargin(ProdPrixVenLabel,marginLabel);
		root.setMargin(ProdPrixVenText,marginText);
		root.setMargin(ProdQteLabel,marginLabel);
		root.setMargin(ProdQteText,marginText);
		root.setMargin(CategoryLabel,marginLabel);
		root.setMargin(Category,marginText);
		root.setMargin(DateDAjoutLabel,marginLabel);
		root.setMargin(DatedAjoutPicker,marginText);
		root.setMargin(buttonsBox, marginLabel);
		root.setMargin(codeinfos, marginText);
		root.setMargin(desgninfos, marginText);
		root.setMargin(prixachinfos, marginText);
		root.setMargin(prixveninfos, marginText);
		root.setMargin(qteinfos, marginText);
		root.setMargin(catinfos, marginText);
	
		
		titleLabel.getStyleClass().add("labelTitle");
		titleLabel.setMinWidth(window.getWidth()-5);
		titleLabel.setMinWidth(window.getWidth()-5);
		titleLabel.setMinHeight(100);
		titleLabel.setAlignment(Pos.CENTER);
		
		ProdCodeText.setMaxWidth(380);
		ProdCodeText.setFont(Font.font(15));
		ProdDesgnText.setMaxWidth(380);
		ProdDesgnText.setFont(Font.font(15));
		ProdPrixAchText.setMaxWidth(380);
		ProdPrixAchText.setFont(Font.font(15));
		ProdPrixVenText.setMaxWidth(380);
		ProdPrixVenText.setFont(Font.font(15));
		ProdQteText.setMaxWidth(380);
		ProdQteText.setFont(Font.font(15));
		
		
		DatedAjoutPicker.setMaxWidth(380);
		DatedAjoutPicker.getStyleClass().add("textfield");
		
		ProdCodeLabel.setMaxWidth(380);
		ProdCodeLabel.getStyleClass().add("labelForm");
		ProdDesgnLabel.setMaxWidth(380);
		ProdDesgnLabel.getStyleClass().add("labelForm");
		ProdPrixAchLabel.setMaxWidth(380);
		ProdPrixAchLabel.getStyleClass().add("labelForm");
		ProdPrixVenLabel.setMaxWidth(380);
		ProdPrixVenLabel.getStyleClass().add("labelForm");
		ProdQteLabel.setMaxWidth(380);
		ProdQteLabel.getStyleClass().add("labelForm");
		CategoryLabel.setMaxWidth(380);
		CategoryLabel.getStyleClass().add("labelForm");
		DateDAjoutLabel.setMaxWidth(380);
		DateDAjoutLabel.getStyleClass().add("labelForm");
		
		//codeinfos.setMaxWidth(380);
		codeinfos.setMaxHeight(5);
		desgninfos.setMaxHeight(5);
		prixachinfos.setMaxHeight(5);
		prixveninfos.setMaxHeight(5);
		qteinfos.setMaxHeight(5);
		catinfos.setMaxHeight(5);
		
		catBox.setMaxWidth(380);
		Category.setMinWidth(250);
		
		buttonsBox.setMaxWidth(380);
		buttonsBox.setSpacing(50);
		addBtn.getStyleClass().add("addBtn");
		cancelBtn.getStyleClass().add("cancelBtn");
		emptyBtn.getStyleClass().add("emptyBtn");
		
		
		
	}
	

	public FormAddProduct() {
		ConnectToBD connect = new ConnectToBD();
		listOfCategories = connect.getListOfCategories();
		initWindow();
		addStyleToNodes();

		window.show();
	}
}
