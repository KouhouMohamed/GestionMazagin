package gestionCompte;

import application.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application{
	private BorderPane root = new BorderPane();
	private Scene scene = new Scene(root);
	MenuItem NouveauCompte = new MenuItem("Nouveau compte");
	MenuItem DetailCompte = new MenuItem("Details du compte");
	
	public void createMenu() {
		MenuBar menuBar = new MenuBar();	
		Menu compteMenu = new Menu("Comptes");
		
		compteMenu.getItems().addAll(NouveauCompte,DetailCompte);
		menuBar.getMenus().add(compteMenu);
		root.setTop(menuBar);
		
	}
	
	public void addEvents() {
		NouveauCompte.setOnAction(event->{
			new FormNewCompte();
		});
		
		DetailCompte.setOnAction(event->{
			new DetailsCompte();
		});
	}

	@Override
	public void start(Stage window) {
		try {
			createMenu();
			window.setScene(scene);
			window.setTitle("Gestion des Compte bancaires");
			window.setWidth(800);
			window.setHeight(600);
			window.setResizable(false);
			addEvents();
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConnectToBase connection = new ConnectToBase();
		launch(args);
	}
	
}
