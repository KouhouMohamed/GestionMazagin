package application;
	

import java.util.List;

import Classes.Categorie;
import Classes.Ligne;
import Classes.Produit;
import ConnectionDB.ConnectToBD;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class MainWindows extends Application {
	static public ObservableList<Ligne> listLignes= FXCollections.observableArrayList();
	static private BorderPane root = new BorderPane();
	private Scene scene = new Scene(root);
	static MenuItem nouveauProd = new MenuItem("Nouveau produit");
	static MenuItem modifierProd = new MenuItem("modifier produit");
	static MenuItem listProd = new MenuItem("Liste des produits");
	static MenuItem listCat = new MenuItem("Liste des catégories");
	static MenuItem nouveauClient = new MenuItem("Nouveau client");
	static MenuItem modifierClient = new MenuItem("modifier client");
	static MenuItem listClient = new MenuItem("Liste des clients");

	static MenuItem nouvelleVente = new MenuItem("Nouvelle Vente");
	static MenuItem chercherVente = new MenuItem("Chercher Vente");
	static MenuItem ListVente = new MenuItem("Liste des Ventes");
	
	static MenuItem connexion = new MenuItem("Déconnecter");
	
	static ChoiceBox<String> Category = new ChoiceBox<String>();
	
	
	
	
	private void createMenu() {
		MenuBar menuBar = new MenuBar();
		
		Menu productMenu = new Menu("Produits");
		Menu clientMenu = new Menu("Clients");
		Menu venteMenu = new Menu("Ventes");
		Menu paiementMenu = new Menu("Paiements");
		Menu connexionMenu = new Menu("Connexion");
		
		productMenu.getItems().addAll(nouveauProd,modifierProd,listProd,listCat);
		clientMenu.getItems().addAll(nouveauClient,modifierClient,listClient);
		venteMenu.getItems().addAll(nouvelleVente,chercherVente,ListVente);
		connexionMenu.getItems().add(connexion);
		
		menuBar.getMenus().addAll(productMenu,clientMenu,venteMenu,paiementMenu,connexionMenu);
		addEvent();
		root.setTop(menuBar); 
		
		
	}
	
	private void addEvent() {
		nouveauProd.setOnAction(event ->{
			new FormAddProduct();
		});
		modifierProd.setOnAction(event ->{
			new FormSetProduit("").ShowWindow();;
		});
		
		listProd.setOnAction(event ->{
			new ListProducts();
		});
		listCat.setOnAction(event->{
			new ListCategories();
		});
		
		nouveauClient.setOnAction(event ->{
			new FormAddClient(false,-1);
		});
		modifierClient.setOnAction(event->{
			new FormAddClient(true,-1);
		});
		
		listClient.setOnAction(event ->{
			new ListClients();
		});
		
		nouvelleVente.setOnAction(event->{
			new FormAddVente();
		});
		chercherVente.setOnAction(event->{
			new ChercherVente();
		});
		ListVente.setOnAction(event->{
			new ListVentes(-1);
		});
		connexion.setOnAction(event->{
			new Login();
		});
	}
	private void addStyleToNodes() {
		scene.getStylesheets().add("css/style.css");
		root.getStyleClass().add("mainWindow");
	}
	@Override
	public void start(Stage window) {
		try {
			createMenu();
			addStyleToNodes();
			window.setScene(scene);
			window.setTitle("GestionMagazin");
			window.setWidth(1100);
			window.setHeight(600);
			window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
			window.setResizable(false);
			window.show();
			new Login();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ChoiceBox<String> addCategories(List<Categorie> listOfCat) {
		
		Category.getItems().add("Choisir !!");
		for(Categorie cat : listOfCat) {
			
			Category.getItems().add(cat.getIntitule());
		}
		return Category;
	}
	
	public static void main(String[] args) {
		ConnectToBD connection = new ConnectToBD();
		addCategories(connection.getListOfCategories());
		
		launch(args);
		
		
	}

	static public MenuItem getNouveauProd() {
		return nouveauProd;
	}

	static public MenuItem getModifierProd() {
		return modifierProd;
	}

	static public MenuItem getListProd() {
		return listProd;
	}

	static public MenuItem getListCat() {
		return listCat;
	}

	static public MenuItem getNouveauClient() {
		return nouveauClient;
	}

	static public MenuItem getModifierClient() {
		return modifierClient;
	}

	static public MenuItem getListClient() {
		return listClient;
	}

	static public MenuItem getNouvelleVente() {
		return nouvelleVente;
	}

	static public MenuItem getConnexion() {
		return connexion;
	}
	static public BorderPane getRoot() {
		return root;
	}
	
}
