package application;

import Classes.Categorie;
import Classes.Produit;
import ConnectionDB.ConnectToBD;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
		window.setOnCloseRequest(event ->{
			event.consume();
		});
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
		Actions.setCellValueFactory(new PropertyValueFactory<Categorie,String>("buttons"));
		
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
	/*Si le compte connecter est un Client*/
	public ListCategories(boolean change) {
		initWindow();
		Actions.setCellValueFactory(new PropertyValueFactory<Categorie,String>("viewButt"));
		Remplirtable();
		AddNew.setVisible(false);
		window.show();
	}

	public HBox getRootHbox() {
		return rootHbox;
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public VBox getRoot() {
		return root;
	}

	public static TableView<Categorie> getTableCategorie() {
		return tableCategorie;
	}

	public Stage getWindow() {
		return window;
	}

	public Scene getScene() {
		return scene;
	}

	public Label getWindowTitle() {
		return WindowTitle;
	}

	public TableColumn<Categorie, String> getCodeCat() {
		return CodeCat;
	}

	public TableColumn<Categorie, String> getIntitule() {
		return Intitule;
	}

	public TableColumn getActions() {
		return Actions;
	}

	public HBox getButtonBox() {
		return buttonBox;
	}

	public Button getAddNew() {
		return AddNew;
	}

	public Button getCancel() {
		return Cancel;
	}

	public void setRootHbox(HBox rootHbox) {
		this.rootHbox = rootHbox;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void setRoot(VBox root) {
		this.root = root;
	}

	public static void setTableCategorie(TableView<Categorie> tableCategorie) {
		ListCategories.tableCategorie = tableCategorie;
	}

	public void setWindow(Stage window) {
		this.window = window;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setWindowTitle(Label windowTitle) {
		WindowTitle = windowTitle;
	}

	public void setCodeCat(TableColumn<Categorie, String> codeCat) {
		CodeCat = codeCat;
	}

	public void setIntitule(TableColumn<Categorie, String> intitule) {
		Intitule = intitule;
	}

	public void setActions(TableColumn actions) {
		Actions = actions;
	}

	public void setButtonBox(HBox buttonBox) {
		this.buttonBox = buttonBox;
	}

	public void setAddNew(Button addNew) {
		AddNew = addNew;
	}

	public void setCancel(Button cancel) {
		Cancel = cancel;
	}
	

}
