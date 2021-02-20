package application;

import java.sql.ResultSet;
import Classes.Ligne;
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

public class FormViewVente {
	public static int idVent;
	ConnectToBD ViewConnect = new ConnectToBD();
	 ScrollPane scrollPane = new ScrollPane();
	 VBox root = new VBox();
	 static Stage window = new Stage();
	 Scene scene = new Scene(scrollPane);
	
	 HBox VenteBox = new HBox();
	 Label Ventelabel = new Label("Vente Numéro : ");
	 Label VentelabelNum = new Label();
	 
	 HBox ClientBox = new HBox();
	 Label Clientlabel = new Label("Client      : ");
	 Label ClientNom = new Label();

	 
	 HBox InfoHbox = new HBox();
	 VBox InfoVbox = new VBox();
	 public static  TableView<Ligne> tableLigne = new TableView<Ligne>(); 
	 TableColumn<Ligne, String> prodDesg = new TableColumn<Ligne, String>("Produit");
	 TableColumn<Ligne, Double> prixProd = new TableColumn<Ligne, Double>("Prix");
	 TableColumn<Ligne, Integer> prodQte = new TableColumn<Ligne, Integer>("Quantité");
	 TableColumn<Ligne, Double> totalLigne = new TableColumn<Ligne, Double>("Total");
	 TableColumn Actions = new TableColumn("Action à faire");
	
	 HBox TotalBox = new HBox();
	 Label totalLabel = new Label("Total du vente : ");
	 static Label TotalText = new Label();
	 static Label infoPaiment = new Label();
	 Button AddNew = new Button("Ajouter nouveau");

	 public static boolean etat;
	
	 public FormViewVente(int idVente) {
			initWindow();
			FormViewVente.idVent = idVente;
			getInfoClient(idVente);
			Remplirtable(idVente);
			
			window.show();
			
		}
	private void addNodesToWindow() {
		    tableLigne.getColumns().addAll(prodDesg,prixProd,prodQte,totalLigne,Actions);
			VenteBox.getChildren().addAll(Ventelabel,VentelabelNum);
			ClientBox.getChildren().addAll(Clientlabel,ClientNom);
			TotalBox.getChildren().addAll(totalLabel,TotalText,infoPaiment);
			
			InfoVbox.getChildren().addAll(VenteBox,ClientBox);
			InfoHbox.getChildren().addAll(InfoVbox, AddNew);
			root.getChildren().add(InfoHbox);
			root.getChildren().add(tableLigne);
			root.getChildren().add(TotalBox);
		}
	private void initWindow() {

		addNodesToWindow();
		scrollPane.setContent(root);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		window.setScene(scene);
		window.setWidth(930);
		window.setHeight(600);
		window.setTitle("Affichage de la vente");
		
		if(window.getModality() != Modality.APPLICATION_MODAL) {
			window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
			window.initModality(Modality.APPLICATION_MODAL);
		}
		window.setResizable(true);
		addProprety();
		addEvent();
		addStyleToNodes();
	}
	public void initTable() {
		tableLigne = new TableView<Ligne>(); 
		 TableColumn<Ligne, String> prodDesg = new TableColumn<Ligne, String>("Produit");
		 TableColumn<Ligne, Double> prixProd = new TableColumn<Ligne, Double>("Prix");
		 TableColumn<Ligne, Integer> prodQte = new TableColumn<Ligne, Integer>("Quantité");
		 TableColumn<Ligne, Double> totalLigne = new TableColumn<Ligne, Double>("Total");
		 TableColumn Actions = new TableColumn("Action à faire");
	}
	
	private void addEvent() {
		window.setOnCloseRequest(event ->{
			 initTable();
			
			 if(TotalText.getText().isEmpty()) {
				 ViewConnect.DeletQuery("Ventes", idVent);
			 }
			window.close();
		});
		AddNew.setOnAction(event->{
			FormSetLigne form = new FormSetLigne("0000", "0", idVent);
			form.getProdCodeText().setDisable(false);
			form.ProdQteVentText.setDisable(false);
			form.getSetBtn().setOnAction(even->{
				int codProd = Integer.valueOf(form.getProdCodeText().getText());
				int qte = Integer.valueOf(form.getProdQteVentText());
				int exist = -1;
				for(Ligne l : FormViewVente.tableLigne.getItems()) {
					if(l.getProduit().getCode() == codProd) {
						exist = 1;
						break;
					}
				}
				if(exist == -1) {
					ViewConnect.setLigne(codProd, idVent, qte);
					FormViewVente.Remplirtable(idVent);
					form.getWindow().close();
				}
				});
			
		});
		
		
	}

	private void addProprety() {
		prodDesg.setCellValueFactory(new PropertyValueFactory<Ligne, String>("designation"));
		prixProd.setCellValueFactory(new PropertyValueFactory<Ligne, Double>("prix"));
		prodQte.setCellValueFactory(new PropertyValueFactory<Ligne, Integer>("quantite"));
		totalLigne.setCellValueFactory(new PropertyValueFactory<Ligne, Double>("total"));
		Actions.setCellValueFactory(new PropertyValueFactory<Ligne,String>("buttons"));
			
	}		
	private  void addStyleToNodes() {
		
		scene.getStylesheets().add("css/style.css");
		AddNew.setAlignment(Pos.TOP_LEFT);

		root.setSpacing(20);
		VenteBox.setMaxHeight(50);
		VenteBox.setMaxWidth(window.getWidth());
		ClientBox.setMaxHeight(50);
		ClientBox.setMaxWidth(window.getWidth());
		TotalBox.setMaxWidth(window.getWidth());
		tableLigne.setMaxWidth(window.getWidth()-5);
		tableLigne.setMinWidth(window.getWidth()-5);
		
		double width = (window.getWidth()-10)/7;
		prodDesg.setMinWidth(width);
		prixProd.setMinWidth(width);
		prodQte.setMinWidth(width);
		totalLigne.setMinWidth(width);
		
		InfoHbox.setMaxWidth(window.getWidth());
		InfoHbox.setMinWidth(window.getWidth());
		InfoHbox.setSpacing(500);
		AddNew.setAlignment(Pos.TOP_RIGHT);
		InfoHbox.getStyleClass().add("addNVent");
		TotalBox.getStyleClass().add("labelTitle");
		AddNew.getStyleClass().add("NewprodInList");
		AddNew.setAlignment(Pos.TOP_RIGHT);
		infoPaiment.getStyleClass().setAll("ventePaye");
		
	}
	public static void  Remplirtable(int IdVente) {
		ConnectToBD connect = new ConnectToBD();
		ObservableList<Ligne> listOfLignes;
		listOfLignes = connect.getListOfLignes(IdVente);
		tableLigne.setItems(listOfLignes);
		
		double ln=0;
		for(Ligne l : tableLigne.getItems()) {
			ln += l.getTotal();
		}
		TotalText.setText(String.valueOf(ln));
	}
	public void getInfoClient(int id) {

		long idcli=-1;
		String requet = "select * from Ventes where codeVente= "+id;
		String requet1;
		ResultSet result1 =  ViewConnect.queryExecute(requet);
		try {
			if(result1.next()) {
				idcli = result1.getInt("CodeClient");
				VentelabelNum.setText("N° "+id + ", du "+result1.getString("dateVente"));
				etat = result1.getBoolean("payer");
				if(result1.getBoolean("payer")) {
					infoPaiment.setText("*Cette vente est payée");
					InfoHbox.getChildren().remove(AddNew);
				}
				else {
					infoPaiment.setText("*Cette vente n'est pas encore payée");
					AddNew.setDisable(false);
				}
				requet1 = "select * from Clients where IdClient= "+idcli;
				ResultSet result = ViewConnect.queryExecute(requet1);
				if(result.next()) {
					ClientNom.setText(result.getString("Nom")+" "+result.getString("Prenom"));
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
