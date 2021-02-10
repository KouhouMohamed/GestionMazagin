package application;

import java.time.LocalDate;
import java.util.List;

import javax.swing.text.TabExpander;

import org.w3c.dom.Node;

import Classes.Categorie;
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

public class ListCategories {
	public  HBox rootHbox = new HBox();
	 ScrollPane scrollPane = new ScrollPane();
	 VBox root = new VBox();
	public static TableView<Categorie> tableCategorie = new TableView<Categorie>(); 
	 Stage window = new Stage();
	 Scene scene = new Scene(scrollPane);
	
	 Label WindowTitle = new Label("Liste des catégorie");
	 TableColumn<Categorie, String> CodeCat = new TableColumn<Categorie, String>("Code Catégorie");
	 TableColumn<Categorie, String> Intitule = new TableColumn<Categorie, String>("Intitule");
	 TableColumn Actions = new TableColumn("Action à faire");
	
	 HBox buttonBox = new HBox();
	 Button AddNew = new Button("Ajouter nouveau");
	 Button Cancel = new Button("Quitter la liste");
	
	
	private void initWindow() {
		scrollPane.setContent(root);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		window.setScene(scene);
		window.setWidth(600);
		window.setHeight(600);
		window.setTitle("Liste des produits");
		window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		addNodesToWindow();
		addProprety();
		addEvent();
		addStyleToNodes();
	}
	
	private void addEvent() {
//		window.setOnCloseRequest(event ->{
//			event.consume();
//		});
		AddNew.setOnAction(event->{
			new FormAddProduct();
		});
		Cancel.setOnAction(event->{
			tableCategorie = new TableView<Categorie>();
			CodeCat = new TableColumn<Categorie, String>("Code Catégorie");
			Intitule = new TableColumn<Categorie, String>("Intitule");
			window.close();
			
		});
		
		
	}

	private void addProprety() {
		
		CodeCat.setCellValueFactory(new PropertyValueFactory<Categorie, String>("codeCat"));
		Intitule.setCellValueFactory(new PropertyValueFactory<Categorie, String>("intitule"));
		Actions.setCellValueFactory(new PropertyValueFactory<Produit,String>("buttons"));
		
	}

	private void addNodesToWindow() {
		tableCategorie.getColumns().addAll(CodeCat,Intitule,Actions);
		root.getChildren().add(WindowTitle);
		root.getChildren().add(tableCategorie);
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
		
		double width = (window.getWidth()- 10)/3;
		CodeCat.setMinWidth(width);
		Intitule.setMinWidth(width);
		Actions.setMinWidth(width-15);
		Actions.setMaxWidth(width-15);
		
		AddNew.getStyleClass().add("NewprodInList");
		Cancel.getStyleClass().add("CancelInList");
		
	}
	public static void Remplirtable() {
		ConnectToBD connect = new ConnectToBD();
		ObservableList<Categorie> listOfCategorie;
		listOfCategorie = connect.getListOfCategories();
		tableCategorie.setItems(listOfCategorie);

	}
	

	public ListCategories() {
		initWindow();
		Remplirtable();
		window.show();
		
	}

}
