package application;

import java.util.ArrayList;
import Classes.LabelVent;
import Classes.Vente;
import ConnectionDB.ConnectToBD;
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



public class ListVentes {

	

	
	 ScrollPane scrollPane = new ScrollPane();
	 VBox root = new VBox();
	 Stage window = new Stage();
	 Scene scene = new Scene(scrollPane);
	
	 Label WindowTitle = new Label("Liste des Ventes");
	 TableView<LabelVent> tableVentes = new TableView<LabelVent>(); 
	 TableColumn<LabelVent, String> clientName = new TableColumn<LabelVent, String>("Client");
	 TableColumn<LabelVent, String> dateVente = new TableColumn<LabelVent, String>("Date");
	 TableColumn<LabelVent, String> totalVente = new TableColumn<LabelVent, String>("Total");
	 TableColumn<LabelVent, String> etatVente = new TableColumn<LabelVent, String>("Etat");
	 TableColumn Actions = new TableColumn("Action à faire");
	
	 HBox buttonBox = new HBox();
	 Button Cancel = new Button("Quitter la liste");
	 public ListVentes(int codeClient) {
		 initWindow(codeClient);
		 window.show();
	 }
		private void initWindow(int codeClient) {

			addNodesToWindow();
			scrollPane.setContent(root);
			scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			window.setScene(scene);
			window.setWidth(930);
			window.setHeight(600);
			window.setTitle("Liste des produits");
			tableVentes.setMaxWidth(window.getWidth());
			if(window.getModality() != Modality.APPLICATION_MODAL) {
				window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
				window.initModality(Modality.APPLICATION_MODAL);
			}
			window.setResizable(true);
			addProprety();
			addEvent();
			addStyleToNodes();
			remplirList(codeClient);
		}

		private void addStyleToNodes() {
			scene.getStylesheets().add("css/style.css");
			scene.getStylesheets().add("css/style.css");
			WindowTitle.getStyleClass().add("labelTitle");
			WindowTitle.setMinWidth(window.getWidth());
			WindowTitle.setMaxHeight(50);
			WindowTitle.setAlignment(Pos.CENTER);
			root.setSpacing(20);
			
			double width = (window.getWidth()- 20)/5;
			clientName.setMinWidth(width);
			dateVente.setMinWidth(width);
			etatVente.setMinWidth(width);
			totalVente.setMinWidth(width);
			Actions.setMinWidth(width-15);
			Actions.setMaxWidth(width-15);
			Cancel.getStyleClass().add("CancelInList");
			
			
		}

		private void addEvent() {
			Cancel.setOnAction(event->{
				window.close();
			});
			
		}

		private void addProprety() {
			
			clientName.setCellValueFactory(new PropertyValueFactory<LabelVent, String>("NameCli"));
			dateVente.setCellValueFactory(new PropertyValueFactory<LabelVent, String>("DateVente"));
			totalVente.setCellValueFactory(new PropertyValueFactory<LabelVent, String>("TotalVente"));
			etatVente.setCellValueFactory(new PropertyValueFactory<LabelVent, String>("etatVente"));
			Actions.setCellValueFactory(new PropertyValueFactory<LabelVent,String>("buttons"));
				
		}

		private void addNodesToWindow() {
			root.getChildren().add(WindowTitle);
			tableVentes.getColumns().addAll(clientName, dateVente, totalVente, etatVente, Actions);
			root.getChildren().add(tableVentes);
			root.getChildren().add(Cancel);
		}
		
		private void remplirList(int codeClient) {
		ConnectToBD connect = new ConnectToBD();
		ArrayList<Vente> listOfVentes = connect.ListOfVentes(codeClient);
		LabelVent labVent;
		for(Vente v : listOfVentes) {
			labVent = new LabelVent(v.getCodeVente(), v.getDateVente(), v.getTotal(), String.valueOf(v.isEtatPay()));
			tableVentes.getItems().add(labVent);
		}
	}

}
