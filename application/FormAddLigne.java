package application;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Ligne;
import ConnectionDB.ConnectToBD;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class FormAddLigne {
	List<Ligne> tabLignes;
	double totalVente=0;
	public FormSetProduit form = new FormSetProduit("");
	Label ProdQteVentLabel = new Label("Quantité à acheter : ");
	 TextField ProdQteVentText = new TextField();
	Label qteventinfos = new Label();
	public FormAddLigne(ObservableList<Ligne> tabLigne) {
		SetNodes();
		SetEvents();
		form.getRoot(form.ProdCodeText.getText());
		form.ProdCodeText.setDisable(true);
		form.ShowWindow();
		this.tabLignes = tabLigne;
	}
	public FormAddLigne(ObservableList<Ligne> tabLigne, String codeprod) {
		SetNodes();
		SetEvents();
		form.getRoot(form.ProdCodeText.getText());
		form.ProdCodeText.setDisable(true);
		form.ProdCodeText.setText(codeprod);
		form.ShowWindow();
		this.tabLignes = tabLigne;
		if(codeprod!="") {
			RemplirForm();
			ProdQteVentText.setDisable(false);
		}
	
	}
	private void RemplirForm() {
		ConnectToBD connection = new ConnectToBD();
		String query = "select * from produits where CodeProd="+Long.parseLong(form.ProdCodeText.getText());
		ResultSet result = connection.queryExecute(query);
		ResultSet resultCategorie;
		
		
		try {
			if(result.next()) {
				form.ProdDesgnText.setText(result.getString("Designation"));
				form.ProdPrixAchText.setText(result.getString("PrixAchat"));
				form.ProdPrixVenText.setText(result.getString("PrixVente"));
				form.ProdQteText.setText(result.getString("Quantite"));
				try {
					query = "select * from Categorie where CodeCat="+result.getInt("CodeCategorie");
					resultCategorie = connection.queryExecute(query);
					if(resultCategorie.next()) {
						form.Category.getSelectionModel().select(resultCategorie.getString("intitule"));
					}
				}
				catch (Exception e) {}
				form.codeinfos.getStyleClass().setAll("labelinfosValid");
				form.codeinfos.setText("*code valid");
				ProdQteVentText.setDisable(false);
			}
			
		}

		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void SetNodes() {
		form.titleLabel.setText("Slectionner un produits");

		form.setBtn.setDisable(true);
		form.ProdDesgnText.setDisable(true);
		form.ProdPrixVenText.setDisable(true);
		form.ProdPrixAchText.setDisable(true);
		form.ProdQteText.setDisable(true);
		form.Category.setDisable(true);
		ProdQteVentText.setDisable(true);
		
		int width = (int) form.window.getWidth() - 220;
		int font = 13;
		ProdQteVentText.setMaxWidth(width);
		ProdQteVentText.setFont(Font.font(font));
		ProdQteVentLabel.setMaxWidth(width);
		ProdQteVentLabel.getStyleClass().add("labelFormSet");
		qteventinfos.setMaxHeight(5);
		//category
		form.setBtn.setText("Selectionner");
		form.addCat.setVisible(false);
		form.root.getChildren().removeAll(form.ProdPrixAchLabel,form.ProdPrixAchText,form.prixachinfos,form.buttonsBox);
		form.root.getChildren().addAll(ProdQteVentLabel,ProdQteVentText,qteventinfos);
		form.root.getChildren().add(form.buttonsBox);
	}
	private void SetEvents() {
		ConnectToBD connection = new ConnectToBD();
		form.ProdCodeText.setOnKeyTyped(event->{
			if(!form.ProdCodeText.getText().matches("\\d{4,}")) {
				form.ProdCodeText.getStyleClass().add("labelError");
				form.codeinfos.getStyleClass().setAll("labelinfosError");
				form.codeinfos.setText("*code incorrect");
				
				form.ProdDesgnText.setText("");
				form.ProdPrixAchText.setText("");
				form.ProdPrixVenText.setText("");
				form.ProdQteText.setText("");
				form.Category.getSelectionModel().selectFirst();
				
			}
			else {
				form.ProdCodeText.getStyleClass().removeAll("labelError");
				String query = "select * from produits where CodeProd="+Long.parseLong(form.ProdCodeText.getText());
				ResultSet result = connection.queryExecute(query);
				ResultSet resultCategorie;
				
				
				try {
					if(result.next()) {
						form.ProdDesgnText.setText(result.getString("Designation"));
						form.ProdPrixAchText.setText(result.getString("PrixAchat"));
						form.ProdPrixVenText.setText(result.getString("PrixVente"));
						form.ProdQteText.setText(result.getString("Quantite"));
						try {
							query = "select * from Categorie where CodeCat="+result.getInt("CodeCategorie");
							resultCategorie = connection.queryExecute(query);
							if(resultCategorie.next()) {
								form.Category.getSelectionModel().select(resultCategorie.getString("intitule"));
							}
						}
						catch (Exception e) {}
						form.codeinfos.getStyleClass().setAll("labelinfosValid");
						form.codeinfos.setText("*code valid");
						ProdQteVentText.setDisable(false);
					}
					else {
						form.ProdCodeText.getStyleClass().add("labelError");
						form.codeinfos.getStyleClass().setAll("labelinfosError");
						form.codeinfos.setText("*Ce code ne fégure pas dans la liste");
					}
				} catch (Exception e) {}
			}	
		});
		ProdQteVentText.setOnKeyTyped(event ->{
			if(!ProdQteVentText.getText().matches("\\d+")) {
				ProdQteVentText.getStyleClass().add("labelError");
				qteventinfos.getStyleClass().setAll("labelinfosError");
				qteventinfos.setText("*quantite incorrecte");
				form.setBtn.setDisable(true);
			}
			else if(Integer.valueOf(ProdQteVentText.getText())>Integer.valueOf(form.ProdQteText.getText())) {
				ProdQteVentText.getStyleClass().add("labelError");
				qteventinfos.getStyleClass().setAll("labelinfosError");
				qteventinfos.setText("*quantite doit etre inférieur à "+form.ProdQteText.getText());
				form.setBtn.setDisable(true);
			}
			else {
				ProdQteVentText.getStyleClass().removeAll("labelError");
				qteventinfos.getStyleClass().setAll("labelinfosValid");
				qteventinfos.setText("*quantite valide");
				form.setBtn.setDisable(false);
			}
		});
		
		form.setBtn.setOnAction(event->{
			Ligne ligne = new Ligne(Long.parseLong(form.ProdCodeText.getText()), Integer.valueOf(ProdQteVentText.getText()),Double.valueOf(form.ProdPrixVenText.getText())) ;
			int exist = -1;
			for(Ligne l:tabLignes) {
				if(l.getProduit().getCode()==ligne.getProduit().getCode()) {
					l.setQuantite(ligne.getQuantite());
					exist = 1;
					break;
				}
				
			}
			if(exist == -1) {
				tabLignes.add(ligne);
			}
			form.ProdCodeText.setText("");
			form.ProdDesgnText.setText("");
			form.ProdPrixAchText.setText("");
			form.ProdPrixVenText.setText("");
			form.ProdQteText.setText("");
			ProdQteVentText.setText("");
			ProdQteVentText.setDisable(true);
			form.setBtn.setDisable(true);
			qteventinfos.setText("");
			form.Category.getSelectionModel().selectFirst();
		});
	}


}
