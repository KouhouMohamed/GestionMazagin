package application;

import java.time.LocalDate;
import java.util.List;

import javax.swing.text.TabExpander;

import org.w3c.dom.Node;

import Classes.Produit;
import ConnectionDB.ConnectToBD;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListProducts {
	//public  HBox rootHbox = new HBox();
	 ScrollPane scrollPane = new ScrollPane();
	 VBox root = new VBox();
	public static  TableView<Produit> tableProduct = new TableView<Produit>(); 
	 static Stage window = new Stage();
	 Scene scene = new Scene(scrollPane);
	
	 Label WindowTitle = new Label("Liste des produits");
	 TableColumn<Produit, String> prodCode = new TableColumn<Produit, String>("CodeProduit");
	 TableColumn<Produit, String> prodDesgn = new TableColumn<Produit, String>("Designation");
	 TableColumn<Produit, String> prodQte = new TableColumn<Produit, String>("Quantité");
	 TableColumn<Produit, String> prodPrixAch = new TableColumn<Produit, String>("Prix d'Achat");
	 TableColumn<Produit, String> prodPrixVen = new TableColumn<Produit, String>("Prix de Vente");
	 TableColumn<Produit, String> Category = new TableColumn<Produit, String>("Catégorie");
	 TableColumn Actions = new TableColumn("Action à faire");
	
	 HBox buttonBox = new HBox();
	 Button AddNew = new Button("Ajouter nouveau");
	 Button Cancel = new Button("Quitter la liste");
 	//public static VBox setWindow = new VBox();

 	
// 	public static void setWindowStyle(int width, int height, long code) {
// 		
// 		
// 		window.setWidth(width);
// 		window.setHeight(height);
// 		
//		setWindow.setMaxHeight(500);
//		setWindow.setMinHeight(500);
//		setWindow.setMaxWidth(285);
//		setWindow.setMinWidth(285);
//		setWindow.setVisible(true);
//		FormSetProduit win = new FormSetProduit(String.valueOf(code));
//		VBox form = win.getRoot(String.valueOf(code));
//		form.setMaxHeight(setWindow.getMaxHeight());
//		form.setMinHeight(setWindow.getMinHeight());
//		form.setMaxWidth(setWindow.getMaxWidth());
//		form.setMinWidth(setWindow.getMinWidth());
//		setWindow.getChildren().setAll(form);
// 		
// 	}

	
	
	private void initWindow() {

		addNodesToWindow();
		scrollPane.setContent(root);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		window.setScene(scene);
		window.setWidth(930);
		window.setHeight(600);
		window.setTitle("Liste des produits");
		
		if(window.getModality() != Modality.APPLICATION_MODAL) {
			window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
			window.initModality(Modality.APPLICATION_MODAL);
		}
		window.setResizable(true);
		addProprety();
		addEvent();
		addStyleToNodes();
	}
	
	private void addEvent() {
		window.setOnCloseRequest(event ->{
			event.consume();
		});
		AddNew.setOnAction(event->{
			new FormAddProduct();
		});
		Cancel.setOnAction(event->{
			tableProduct = new TableView<Produit>();
			prodCode = new TableColumn<Produit, String>("CodeProduit");
			prodDesgn = new TableColumn<Produit, String>("Designation");
			prodQte = new TableColumn<Produit, String>("Quantité");
			prodPrixAch = new TableColumn<Produit, String>("Prix d'Achat");
			prodPrixVen = new TableColumn<Produit, String>("Prix de Vente");
			Category = new TableColumn<Produit, String>("Catégorie");
			Actions = new TableColumn<Produit,String>("Action à faire");

			
			window.close();
			
		});
		
		
	}

	private void addProprety() {
		
		prodCode.setCellValueFactory(new PropertyValueFactory<Produit, String>("code"));
		prodDesgn.setCellValueFactory(new PropertyValueFactory<Produit, String>("Designation"));
		prodQte.setCellValueFactory(new PropertyValueFactory<Produit, String>("quantity"));
		prodPrixAch.setCellValueFactory(new PropertyValueFactory<Produit, String>("prixAch"));
		prodPrixVen.setCellValueFactory(new PropertyValueFactory<Produit, String>("prixVen"));
		Category.setCellValueFactory(new PropertyValueFactory<Produit, String>("categorie"));
		Actions.setCellValueFactory(new PropertyValueFactory<Produit,String>("buttons"));
		
	}

	private void addNodesToWindow() {
		//rootHbox.getChildren().addAll(setWindow,tableProduct);
		tableProduct.getColumns().addAll(prodCode,prodDesgn,prodQte,prodPrixAch,prodPrixVen,Category,Actions);
		root.getChildren().add(WindowTitle);
		root.getChildren().add(tableProduct);
		buttonBox.getChildren().addAll(AddNew,Cancel);
		root.getChildren().add(buttonBox);
	}
	private  void addStyleToNodes() {
		
		scene.getStylesheets().add("css/style.css");
		WindowTitle.getStyleClass().add("labelTitle");
		WindowTitle.setMinWidth(window.getWidth());
		WindowTitle.setMaxHeight(50);
		WindowTitle.setAlignment(Pos.CENTER);
		AddNew.setAlignment(Pos.TOP_LEFT);
		Cancel.setAlignment(Pos.TOP_RIGHT);
		buttonBox.setMaxWidth(window.getWidth());
		root.setSpacing(20);
		prodCode.setMaxWidth(50);
//		rootHbox.setSpacing(15);
// 		rootHbox.setSpacing(15);
		double width = (window.getWidth()-10)/7;
		prodCode.setMinWidth(width);
		prodDesgn.setMinWidth(width);
		prodQte.setMinWidth(width);
		prodPrixAch.setMinWidth(width);
		prodPrixVen.setMinWidth(width);
		Category.setMinWidth(width);
		Actions.setMinWidth(width-15);
		Actions.setMaxWidth(width-15);
		
		AddNew.getStyleClass().add("NewprodInList");
		Cancel.getStyleClass().add("CancelInList");
		
	}
	public static void Remplirtable() {
		ConnectToBD connect = new ConnectToBD();
		ObservableList<Produit> listOfProducts;
		listOfProducts = connect.getListOfProducts();
		tableProduct.setItems(listOfProducts);

	}
	

	public ListProducts() {
		initWindow();
		Remplirtable();
		window.show();
		
	}

}
