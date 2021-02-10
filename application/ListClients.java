package application;

import java.time.LocalDate;
import java.util.List;

import javax.swing.text.TabExpander;

import org.w3c.dom.Node;

import Classes.Categorie;
import Classes.Client;
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

public class ListClients{
	public  HBox rootHbox = new HBox();
	 ScrollPane scrollPane = new ScrollPane();
	 VBox root = new VBox();
	public static  TableView<Client> tableCategorie = new TableView<Client>(); 
	 Stage window = new Stage();
	 Scene scene = new Scene(scrollPane);
	
	 Label WindowTitle = new Label("Liste des clients");
	 TableColumn<Client, String> IdCli = new TableColumn<Client, String>("Id Client");
	 TableColumn<Client, String> NomCli = new TableColumn<Client, String>("Nom");
	 TableColumn<Client, String> PrenomCli = new TableColumn<Client, String>("Prénom");
	 TableColumn<Client, String> NumeroCli = new TableColumn<Client, String>("Telephone");
	 TableColumn<Client, String> EmailCli = new TableColumn<Client, String>("Email");
	 TableColumn<Client, String> AddressCli = new TableColumn<Client, String>("Address");
	 TableColumn Actions = new TableColumn("Action à faire");
	
	 HBox buttonBox = new HBox();
	 Button AddNew = new Button("Ajouter nouveau");
	 Button Cancel = new Button("Quitter la liste");
	
	
	private void initWindow() {
		scrollPane.setContent(root);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		window.setScene(scene);
		window.setWidth(900);
		window.setHeight(600);
		window.setTitle("Liste des clients");
		window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		addNodesToWindow();
		addProprety();
		addEvent();
		addStyleToNodes();
	}
	
	private void addEvent() {
		window.setOnCloseRequest(event ->{
			event.consume();
		});
		AddNew.setOnAction(event->{
			new FormAddClient(false,-1);
		});
		Cancel.setOnAction(event->{
			tableCategorie = new TableView<Client>();
			IdCli = new TableColumn<Client, String>("Id Client");
			NomCli = new TableColumn<Client, String>("Nom");
			PrenomCli = new TableColumn<Client, String>("Prénom");
			NumeroCli = new TableColumn<Client, String>("Telephone");
			EmailCli = new TableColumn<Client, String>("Email");
			AddressCli = new TableColumn<Client, String>("Address");
			Actions = new TableColumn("Action à faire");
			window.close();
			
		});
		
		
	}

	private void addProprety() {
		
		IdCli.setCellValueFactory(new PropertyValueFactory<Client, String>("idCli"));
		NomCli.setCellValueFactory(new PropertyValueFactory<Client, String>("nomCli"));
		PrenomCli.setCellValueFactory(new PropertyValueFactory<Client, String>("prenomCli"));
		NumeroCli.setCellValueFactory(new PropertyValueFactory<Client, String>("numTelephoneCli"));
		EmailCli.setCellValueFactory(new PropertyValueFactory<Client, String>("emailCli"));
		AddressCli.setCellValueFactory(new PropertyValueFactory<Client, String>("adressCli"));
		Actions.setCellValueFactory(new PropertyValueFactory<Produit,String>("buttons"));
		
	}

	private void addNodesToWindow() {
		tableCategorie.getColumns().addAll(IdCli,NomCli,PrenomCli,NumeroCli,EmailCli,AddressCli,Actions);
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
		
		double width = (window.getWidth()- 10)/7;
		IdCli.setMinWidth(width/10);
		NomCli.setMinWidth(width-20);
		PrenomCli.setMinWidth(width-20);
		NumeroCli.setMinWidth(width-20);
		EmailCli.setMinWidth(width+40);
		AddressCli.setMinWidth(width+40);
		Actions.setMinWidth(width-15);
		Actions.setMaxWidth(width-15);
		
		AddNew.getStyleClass().add("NewprodInList");
		Cancel.getStyleClass().add("CancelInList");
		
	}
	public static void Remplirtable() {
		ConnectToBD connect = new ConnectToBD();
		ObservableList<Client> listOfCategorie;
		listOfCategorie = connect.getListOfClient();
		tableCategorie.setItems(listOfCategorie);

	}
	

	public ListClients() {
		initWindow();
		Remplirtable();
		window.show();
		
	}

}
