package application;

import java.beans.DesignMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Classes.Categorie;
import Classes.Produit;
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



public class FormSetProduit {
	VBox root = new VBox();
	VBox rootv = new VBox();
	Scene scene = new Scene(rootv);
	public Stage window = new Stage();
	Label titleLabel = new Label("Modifier Produit");
	
	Label ProdCodeLabel = new Label("Code : ");
	public static TextField ProdCodeText = new TextField();
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
	//static ChoiceBox<String> Category = new ChoiceBox<String>();
	static ChoiceBox<String> Category = MainWindows.Category;
	Button addCat = new Button("Ajouter catégorie");
	Label catinfos = new Label();
	
	Label DateDAjoutLabel = new Label("Date : ");
	DatePicker DatedAjoutPicker = new DatePicker(LocalDate.now());
	
	HBox buttonsBox = new HBox();
	Button setBtn = new Button("Modifier");
	Button cancelBtn = new Button("Annuler");
	List<Categorie> listOfCategories;
	
	private void addNodesToWindow() {
		buttonsBox.getChildren().addAll(setBtn, cancelBtn);
		catBox.getChildren().addAll(Category, addCat);
		rootv.getChildren().add(titleLabel);
		root.getChildren().addAll(ProdCodeLabel,ProdCodeText,codeinfos);
		root.getChildren().addAll(ProdDesgnLabel, ProdDesgnText, desgninfos);
		root.getChildren().addAll(ProdPrixAchLabel, ProdPrixAchText, prixachinfos);
		root.getChildren().addAll(ProdPrixVenLabel, ProdPrixVenText, prixveninfos);
		root.getChildren().addAll(ProdQteLabel, ProdQteText, qteinfos);
		root.getChildren().addAll(CategoryLabel,catBox,catinfos);
		root.getChildren().add(buttonsBox);
		rootv.getChildren().add(root);
		
		
	}
	
	private void initWindow() {
		window.setScene(scene);
		window.setWidth(600);
		window.setHeight(600);//550
		window.setTitle("Modifier un produit");
		window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		addNodesToWindow();
		addEvent();
		addStyleToNodes();
	}
	private void addEvent() {
		ConnectToBD connection = new ConnectToBD();
		window.setOnCloseRequest(event ->{
			event.consume();
		});
		//Ajouter des events au btns
		setBtn.setOnAction(event ->{
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
				long idCat;
				
				int quantity = Integer.valueOf(ProdQteText.getText());
				String requet1;
				String requet2 = "select * from produits where Designation = '"+designation+"'";
				ResultSet result2 = addProd.queryExecute(requet2);
				qteinfos.getStyleClass().setAll("labelinfosValid");
				qteinfos.setText("*quantite valide");
				try {
						if(result2.next()==false) {
							for (Categorie cat : listOfCategories) {
								if(cat.getIntitule().equals(Category.getValue())) {
									idCat = cat.getCodeCat();
									requet1 =" UPDATE `produits` SET `Designation`= '"+designation+"',`PrixAchat`="+prixach+",`PrixVente`="+prixven+",`CodeCategorie`="+idCat+",`Quantite`="+quantity+" WHERE CodeProd="+code;
									Statement sqlConnection = addProd.getConnection().createStatement();
									sqlConnection.executeUpdate(requet1);
									break;
								}
							}
							
							setBtn.setDisable(true);
							ProdDesgnText.setDisable(true);
							ProdPrixVenText.setDisable(true);
							ProdPrixAchText.setDisable(true);
							ProdQteText.setDisable(true);
							
							codeinfos.setText("");
							desgninfos.setText("");
							prixachinfos.setText("");
							prixveninfos.setText("");
							qteinfos.setText("");
							catinfos.setText("");
						 	ListProducts.Remplirtable();
						}
						else {
						if (designation.equals(result2.getString("Designation").toUpperCase())) {
							for (Categorie cat : listOfCategories) {
								if(cat.getIntitule().equals(Category.getValue())) {
									idCat = cat.getCodeCat();
									requet1 =" UPDATE `produits` SET `Designation`= '"+designation+"',`PrixAchat`="+prixach+",`PrixVente`="+prixven+",`CodeCategorie`="+idCat+",`Quantite`="+quantity+" WHERE CodeProd="+code;
									Statement sqlConnection = addProd.getConnection().createStatement();
									sqlConnection.executeUpdate(requet1);
									break;
								}
							}
							
							setBtn.setDisable(true);
							ProdDesgnText.setDisable(true);
							ProdPrixVenText.setDisable(true);
							ProdPrixAchText.setDisable(true);
							ProdQteText.setDisable(true);
							
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
						 	desgninfos.getStyleClass().setAll("labelinfosError");
						 	desgninfos.setText("*ce nom est déja existe");
						}
					  }
						
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		
		cancelBtn.setOnAction(event ->{
			ListProducts.Remplirtable();
			root.setVisible(false);
			root.setMaxHeight(0);
			root.setMinHeight(0);
			root.setMaxWidth(0);
			root.setMinWidth(0);
			window.close();
		});
		
		addCat.setOnAction(event ->{
			new FormAddCategorie();
		});
		ProdCodeText.setOnKeyTyped(event ->{
			if(!ProdCodeText.getText().matches("\\d{4,}")) {
				ProdCodeText.getStyleClass().add("labelError");
				codeinfos.getStyleClass().setAll("labelinfosError");
				codeinfos.setText("*code incorrect");
				ProdDesgnText.setText("");
				ProdPrixAchText.setText("");
				ProdPrixVenText.setText("");
				ProdQteText.setText("");
				Category.getSelectionModel().selectFirst();
				setBtn.setDisable(true);
				ProdDesgnText.setDisable(true);
				ProdPrixVenText.setDisable(true);
				ProdPrixAchText.setDisable(true);
				ProdQteText.setDisable(true);
				
			}
			else {
				ProdCodeText.getStyleClass().removeAll("labelError");
				String query = "select * from produits where CodeProd="+Long.parseLong(ProdCodeText.getText());
				ResultSet result = connection.queryExecute(query);
				ResultSet resultCategorie;
				
				
				try {
					if(result.next()) {
						ProdDesgnText.setText(result.getString("Designation"));
						ProdPrixAchText.setText(result.getString("PrixAchat"));
						ProdPrixVenText.setText(result.getString("PrixVente"));
						ProdQteText.setText(result.getString("Quantite"));
						try {
							query = "select * from Categorie where CodeCat="+result.getInt("CodeCategorie");
							resultCategorie = connection.queryExecute(query);
							if(resultCategorie.next()) {
								Category.getSelectionModel().select(resultCategorie.getString("intitule"));
							}
						}
							catch (Exception e) {}
						codeinfos.getStyleClass().setAll("labelinfosValid");
						codeinfos.setText("*code valid");
						setBtn.setDisable(false);
						ProdDesgnText.setDisable(false);
						ProdPrixVenText.setDisable(false);
						ProdPrixAchText.setDisable(false);
						ProdQteText.setDisable(false);
					}
					else {
						ProdCodeText.getStyleClass().add("labelError");
						codeinfos.getStyleClass().setAll("labelinfosError");
						codeinfos.setText("*Ce code ne fégure pas dans la liste");
						ProdDesgnText.setText("");
						ProdPrixAchText.setText("");
						ProdPrixVenText.setText("");
						ProdQteText.setText("");
						Category.getSelectionModel().selectFirst();
						setBtn.setDisable(true);
						ProdDesgnText.setDisable(true);
						ProdPrixVenText.setDisable(true);
						ProdPrixAchText.setDisable(true);
						ProdQteText.setDisable(true);
					}
					
					
				} catch (Exception e) {
					
				}
			}
			
		});
		
		ProdDesgnText.setOnKeyTyped(event ->{
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
	
		Category.setOnAction(event ->{
			if(Category.getValue() != "Choisir !!") {catinfos.setText("");}
		});
		
		
	}
	private void addStyleToNodes() {
		scene.getStylesheets().add("css/style.css");
		root.getStyleClass().add("ajoutWindow");
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		Category.getSelectionModel().selectFirst();
		DatedAjoutPicker.setEditable(false);
		
		cancelBtn.setAlignment(Pos.TOP_RIGHT);
		setBtn.setAlignment(Pos.TOP_LEFT);
		
		Insets marginText = new Insets(0, 0, 0, 12);
		Insets marginLabel = new Insets(0, 0, 0, 2.5);
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
		root.setMargin(buttonsBox, marginLabel);
		root.setMargin(codeinfos, marginText);
		root.setMargin(desgninfos, marginText);
		root.setMargin(prixachinfos, marginText);
		root.setMargin(prixveninfos, marginText);
		root.setMargin(qteinfos, marginText);
		root.setMargin(catinfos, marginText);
		
		int width = (int) window.getWidth() - 220;
		int font = 13;
		String style = "labelFormSet";
		titleLabel.getStyleClass().add("labelTitle");
		titleLabel.setMinWidth(window.getWidth());
		titleLabel.setMinHeight(50);
		titleLabel.setAlignment(Pos.CENTER);
		
		ProdCodeText.setMaxWidth(width);
		ProdCodeText.setFont(Font.font(font));
		
		ProdDesgnText.setMaxWidth(width);
		ProdDesgnText.setFont(Font.font(font));
		ProdDesgnText.setDisable(true);
		
		ProdPrixAchText.setMaxWidth(width);
		ProdPrixAchText.setFont(Font.font(font));
		ProdPrixAchText.setDisable(true);
		
		ProdPrixVenText.setMaxWidth(width);
		ProdPrixVenText.setFont(Font.font(font));
		ProdPrixVenText.setDisable(true);
		
		ProdQteText.setMaxWidth(width);
		ProdQteText.setFont(Font.font(font));
		ProdQteText.setDisable(true);
		
		ProdCodeLabel.setMaxWidth(width);
		ProdCodeLabel.getStyleClass().add(style);
		ProdDesgnLabel.setMaxWidth(width);
		ProdDesgnLabel.getStyleClass().add(style);
		
		ProdPrixAchLabel.setMaxWidth(width);
		ProdPrixAchLabel.getStyleClass().add(style);
		ProdPrixVenLabel.setMaxWidth(width);
		ProdPrixVenLabel.getStyleClass().add(style);
		ProdQteLabel.setMaxWidth(width);
		ProdQteLabel.getStyleClass().add(style);
		CategoryLabel.setMaxWidth(width);
		CategoryLabel.getStyleClass().add(style);
		DateDAjoutLabel.setMaxWidth(width);
		DateDAjoutLabel.getStyleClass().add(style);
		codeinfos.setMaxWidth(width);
		codeinfos.setMaxHeight(5);
		desgninfos.setMaxHeight(5);
		prixachinfos.setMaxHeight(5);
		prixveninfos.setMaxHeight(5);
		qteinfos.setMaxHeight(5);
		catinfos.setMaxHeight(5);
		
		catBox.setMaxWidth(width);
		Category.setMinWidth(190);
		addCat.setMinWidth(50);
		
		
		buttonsBox.setMaxWidth(width);
		setBtn.getStyleClass().add("addBtn");
		setBtn.setDisable(true);
		cancelBtn.getStyleClass().add("cancelBtn");
		
		
		
	}
	
	public FormSetProduit (String codeText) {
		ConnectToBD connect = new ConnectToBD();
		listOfCategories = connect.getListOfCategories();
		initWindow();
		getRoot(codeText);
	}
	public void ShowWindow() {this.window.show();}
	
	
	
	public void getRoot(String codeText) {
		this.ProdCodeText.setText(codeText);
		if(codeText != "" && !codeText.isEmpty()) {
			ConnectToBD connection = new ConnectToBD();
			String query = "select * from produits where CodeProd="+Long.parseLong(ProdCodeText.getText());
			ResultSet result = connection.queryExecute(query);
			ResultSet resultCategorie;
			try {
				if(result.next()) {
					ProdDesgnText.setText(result.getString("Designation"));
					ProdPrixAchText.setText(result.getString("PrixAchat"));
					ProdPrixVenText.setText(result.getString("PrixVente"));
					ProdQteText.setText(result.getString("Quantite"));
					try {
						query = "select * from Categorie where CodeCat="+result.getInt("CodeCategorie");
						resultCategorie = connection.queryExecute(query);
						if(resultCategorie.next()) {
							Category.getSelectionModel().select(resultCategorie.getString("intitule"));
						}
					}
					catch (Exception e) {}
					ProdCodeLabel.getStyleClass().removeAll("labelError");
					setBtn.setDisable(false);
					ProdDesgnText.setDisable(false);
					ProdPrixVenText.setDisable(false);
					ProdPrixAchText.setDisable(false);
					ProdQteText.setDisable(false);
				}}
				catch (Exception e) {}
		}
		}

	public VBox getRoot() {
		return root;
	}

	public VBox getRootv() {
		return rootv;
	}

	public Scene getScene() {
		return scene;
	}

	public Stage getWindow() {
		return window;
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public Label getProdCodeLabel() {
		return ProdCodeLabel;
	}

	public static TextField getProdCodeText() {
		return ProdCodeText;
	}

	public Label getCodeinfos() {
		return codeinfos;
	}

	public Label getProdDesgnLabel() {
		return ProdDesgnLabel;
	}

	public TextField getProdDesgnText() {
		return ProdDesgnText;
	}

	public Label getDesgninfos() {
		return desgninfos;
	}

	public Label getProdPrixAchLabel() {
		return ProdPrixAchLabel;
	}

	public TextField getProdPrixAchText() {
		return ProdPrixAchText;
	}

	public Label getPrixachinfos() {
		return prixachinfos;
	}

	public Label getProdPrixVenLabel() {
		return ProdPrixVenLabel;
	}

	public TextField getProdPrixVenText() {
		return ProdPrixVenText;
	}

	public Label getPrixveninfos() {
		return prixveninfos;
	}

	public Label getProdQteLabel() {
		return ProdQteLabel;
	}

	public TextField getProdQteText() {
		return ProdQteText;
	}

	public Label getQteinfos() {
		return qteinfos;
	}

	public HBox getCatBox() {
		return catBox;
	}

	public Label getCategoryLabel() {
		return CategoryLabel;
	}

	public static ChoiceBox<String> getCategory() {
		return Category;
	}

	public Button getAddCat() {
		return addCat;
	}

	public Label getCatinfos() {
		return catinfos;
	}

	public Label getDateDAjoutLabel() {
		return DateDAjoutLabel;
	}

	public DatePicker getDatedAjoutPicker() {
		return DatedAjoutPicker;
	}

	public HBox getButtonsBox() {
		return buttonsBox;
	}

	public Button getSetBtn() {
		return setBtn;
	}

	public Button getCancelBtn() {
		return cancelBtn;
	}

	public List<Categorie> getListOfCategories() {
		return listOfCategories;
	}

	public void setRoot(VBox root) {
		this.root = root;
	}

	public void setRootv(VBox rootv) {
		this.rootv = rootv;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setWindow(Stage window) {
		this.window = window;
	}

	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	public void setProdCodeLabel(Label prodCodeLabel) {
		ProdCodeLabel = prodCodeLabel;
	}

	public static void setProdCodeText(TextField prodCodeText) {
		ProdCodeText = prodCodeText;
	}

	public void setCodeinfos(Label codeinfos) {
		this.codeinfos = codeinfos;
	}

	public void setProdDesgnLabel(Label prodDesgnLabel) {
		ProdDesgnLabel = prodDesgnLabel;
	}

	public void setProdDesgnText(TextField prodDesgnText) {
		ProdDesgnText = prodDesgnText;
	}

	public void setDesgninfos(Label desgninfos) {
		this.desgninfos = desgninfos;
	}

	public void setProdPrixAchLabel(Label prodPrixAchLabel) {
		ProdPrixAchLabel = prodPrixAchLabel;
	}

	public void setProdPrixAchText(TextField prodPrixAchText) {
		ProdPrixAchText = prodPrixAchText;
	}

	public void setPrixachinfos(Label prixachinfos) {
		this.prixachinfos = prixachinfos;
	}

	public void setProdPrixVenLabel(Label prodPrixVenLabel) {
		ProdPrixVenLabel = prodPrixVenLabel;
	}

	public void setProdPrixVenText(TextField prodPrixVenText) {
		ProdPrixVenText = prodPrixVenText;
	}

	public void setPrixveninfos(Label prixveninfos) {
		this.prixveninfos = prixveninfos;
	}

	public void setProdQteLabel(Label prodQteLabel) {
		ProdQteLabel = prodQteLabel;
	}

	public void setProdQteText(TextField prodQteText) {
		ProdQteText = prodQteText;
	}

	public void setQteinfos(Label qteinfos) {
		this.qteinfos = qteinfos;
	}

	public void setCatBox(HBox catBox) {
		this.catBox = catBox;
	}

	public void setCategoryLabel(Label categoryLabel) {
		CategoryLabel = categoryLabel;
	}

	public static void setCategory(ChoiceBox<String> category) {
		Category = category;
	}

	public void setAddCat(Button addCat) {
		this.addCat = addCat;
	}

	public void setCatinfos(Label catinfos) {
		this.catinfos = catinfos;
	}

	public void setDateDAjoutLabel(Label dateDAjoutLabel) {
		DateDAjoutLabel = dateDAjoutLabel;
	}

	public void setDatedAjoutPicker(DatePicker datedAjoutPicker) {
		DatedAjoutPicker = datedAjoutPicker;
	}

	public void setButtonsBox(HBox buttonsBox) {
		this.buttonsBox = buttonsBox;
	}

	public void setSetBtn(Button setBtn) {
		this.setBtn = setBtn;
	}

	public void setCancelBtn(Button cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public void setListOfCategories(List<Categorie> listOfCategories) {
		this.listOfCategories = listOfCategories;
	}



}
