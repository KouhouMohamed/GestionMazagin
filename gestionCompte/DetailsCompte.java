package gestionCompte;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Stack;


import com.sun.glass.ui.Window;

import Classes.Ligne;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailsCompte {

	ConnectToBase connect = new ConnectToBase();
	Stage window = new Stage();
	VBox root = new VBox();
	Scene scene = new Scene(root);
	
	
	HBox compteBox = new HBox();
	Label compte = new Label("Compte: ");
	TextField codeCompte = new TextField();
	Button viewDetail = new Button("Afficher le detail de compte");
	
	HBox nameBox = new HBox();
	Label nom = new Label("Nom: ");
	Label nameClient  = new Label();
	
	HBox soldebBox = new HBox();
	Label solde = new Label("Solde: ");
	Label montantSold = new Label();
	
	TableView<Operation> tableOp = new TableView<Operation>();
	 TableColumn<Operation, Date> DateOp = new TableColumn<Operation, Date>("Date");
	 TableColumn<Operation, String> Opera = new TableColumn<Operation, String>("Operation");
	 TableColumn<Operation, Double> Montant = new TableColumn<Operation, Double>("Montant");
	 
	 public DetailsCompte() {
		 initWindow();
	 }
	 public void initWindow(){
		 window.setScene(scene);
		 window.setMaxHeight(600);
		 window.setMinHeight(500);
		 window.setMaxWidth(700);
		 window.setMinWidth(200);
		 addNodes();
		 addPropreties();
		 addevent();
		 window.show();
		 
	 }
	 public void addNodes() {
		 compteBox.getChildren().addAll(compte, codeCompte, viewDetail);
		 nameBox.getChildren().addAll(nom, nameClient);
		 soldebBox.getChildren().addAll(solde,montantSold);
		 tableOp.getColumns().addAll(DateOp,Opera,Montant);
		 root.getChildren().addAll(compteBox, nameBox, soldebBox);
	 }
	 public void addPropreties() {
		 	scene.getStylesheets().add("css/styleExam.css");
			DateOp.setCellValueFactory(new PropertyValueFactory<Operation, Date>("date"));
			Opera.setCellValueFactory(new PropertyValueFactory<Operation, String>("type"));
			Montant.setCellValueFactory(new PropertyValueFactory<Operation, Double>("montant"));

			viewDetail.setDisable(true);
			nom.setVisible(false);
			nameClient.setVisible(false);
			solde.setVisible(false);
			montantSold.setVisible(false);
			
			viewDetail.getStyleClass().add("ViewBtn");
	 }
	 
	 public void addevent() {
		 codeCompte.setOnKeyTyped(event->{
			 if(!codeCompte.getText().matches("[0-9a-zA-Z]+") || codeCompte.getText().length()>15) {
				 viewDetail.setDisable(true);
				}
				else {
					viewDetail.setDisable(false);
				}
					 
		 });
		 viewDetail.setOnAction(event->{
			 ResultSet rs = connect.getInfoCompte(codeCompte.getText());
			 try {
				if(rs.next()) {
					nameClient.setText((rs.getString("nom")+" " +rs.getString("prenom")));

					nom.setVisible(true);
					nameClient.setVisible(true);
					remplirTable(codeCompte.getText());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
		 });
	 }
	 public void remplirTable(String codeCompte) {
		 ObservableList<Operation> listOps = connect.listOps(codeCompte);
		 tableOp.setItems(listOps);
		 double sold = 0;
		 for(Operation op : listOps) {
			 if(op.getType().equals("Vers")) {
				 sold += op.getMontant();
			 }
			 else if(op.getType().equals("Retr")) {
				 sold -= op.getMontant();
			 }
		 }			
		montantSold.setText(String.valueOf(sold));
		this.solde.setVisible(true);
		this.montantSold.setVisible(true);
		this.root.getChildren().add(tableOp);
		 

	 }
	 
	 
}
