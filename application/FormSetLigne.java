package application;

import java.sql.ResultSet;
import java.sql.Statement;

import ConnectionDB.ConnectToBD;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class FormSetLigne extends FormSetProduit{

	ConnectToBD connect = new ConnectToBD();
	Label ProdQteVentLabel = new Label("Quantité à acheter : ");
	TextField ProdQteVentText = new TextField();
	Label qteventinfos = new Label();
	String codeProd;
	String QteAchete;
	int idvente;
	public String getProdQteVentText() {
		return ProdQteVentText.getText();
	}
	public FormSetLigne(String codeText,String qte,int IdVente) {
		super(codeText);
		addProprieties();
		this.codeProd=codeText;
		this.QteAchete=qte;
		this.idvente=IdVente;
		ProdQteVentText.setText(qte);
		setEvents();
		ShowWindow();
	}

	public void addProprieties() {
		getRoot().getChildren().removeAll(getPrixachinfos(),getProdPrixAchText(),getProdPrixAchLabel(),getDateDAjoutLabel(),getDatedAjoutPicker());
		getCatBox().getChildren().remove(getAddCat());
		getTitleLabel().setText("Modifier une ligne ");
		getRoot().getChildren().remove(getButtonsBox());
		getRoot().getChildren().addAll(ProdQteVentLabel,ProdQteVentText,qteventinfos);
		getRoot().getChildren().add(getButtonsBox());
		
		ProdQteVentText.setMaxWidth(getWindow().getWidth()-220);
		ProdQteVentText.setFont(Font.font(13));
		ProdQteVentLabel.setMaxWidth(getWindow().getWidth()-220);
		ProdQteVentLabel.getStyleClass().add("labelFormSet");
		qteventinfos.setMaxHeight(5);
		
		getProdCodeText().setDisable(true);
		getProdPrixVenText().setDisable(true);
		getProdDesgnText().setDisable(true);
		getProdQteText().setDisable(true);
		getCategory().setDisable(true);
	}
	public void setEvents() {
		ProdCodeText.setOnKeyTyped(event ->{
			if(!ProdCodeText.getText().matches("\\d{4,}")) {
				ProdCodeText.getStyleClass().add("labelError");
				codeinfos.getStyleClass().setAll("labelinfosError");
				codeinfos.setText("*code incorrect");
				ProdDesgnText.setText("");
				ProdPrixAchText.setText("");
				ProdPrixVenText.setText("");
				ProdQteText.setText("");
				Category.getSelectionModel().selectFirst();
				setBtn.setDisable(true);
				ProdQteVentText.setDisable(true);
				
			}
			else {
				ProdCodeText.getStyleClass().removeAll("labelError");
				String query = "select * from produits where CodeProd="+Long.parseLong(ProdCodeText.getText());
				ResultSet result = connect.queryExecute(query);
				ResultSet resultCategorie;
				
				
				try {
					if(result.next()) {
						ProdDesgnText.setText(result.getString("Designation"));
						ProdPrixAchText.setText(result.getString("PrixAchat"));
						ProdPrixVenText.setText(result.getString("PrixVente"));
						ProdQteText.setText(result.getString("Quantite"));
						try {
							query = "select * from Categorie where CodeCat="+result.getInt("CodeCategorie");
							resultCategorie = connect.queryExecute(query);
							if(resultCategorie.next()) {
								Category.getSelectionModel().select(resultCategorie.getString("intitule"));
							}
						}
							catch (Exception e) {}
						codeinfos.getStyleClass().setAll("labelinfosValid");
						codeinfos.setText("*code valid");
						ProdQteVentText.setDisable(false);
					}
					else {
						ProdCodeText.getStyleClass().add("labelError");
						codeinfos.getStyleClass().setAll("labelinfosError");
						codeinfos.setText("*Ce code ne fégure pas dans la liste");
						ProdDesgnText.setText("");
						ProdPrixAchText.setText("");
						ProdPrixVenText.setText("");
						ProdQteText.setText("");
						Category.getSelectionModel().selectFirst();
						setBtn.setDisable(true);
						ProdQteVentText.setDisable(true);
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		ProdQteVentText.setOnKeyTyped(event->{
			if(!ProdQteVentText.getText().matches("[0-9]+")) {
				qteventinfos.getStyleClass().setAll("labelinfosError");
				qteventinfos.setText("*Errore dans la quantite");
				setBtn.setDisable(true);
			}
			else if(Integer.valueOf(ProdQteVentText.getText())>(Integer.valueOf(getProdQteText().getText())+Integer.valueOf(this.QteAchete))){

				qteventinfos.getStyleClass().setAll("labelinfosError");
				qteventinfos.setText("*quantite doit etre inférieur à " + (Integer.valueOf(getProdQteText().getText())+Integer.valueOf(this.QteAchete)));
				setBtn.setDisable(true);
			}
			else {
				qteventinfos.getStyleClass().setAll("labelinfosValid");
				qteventinfos.setText("*La quantite est Valide");
				setBtn.setDisable(false);
			}
		});
		setBtn.setOnAction(event->{
			int newQte = Integer.valueOf(ProdQteVentText.getText());
			if(newQte!=0) {
				connect.setLigne(Integer.valueOf(this.codeProd),Integer.valueOf(this.idvente), newQte);
				FormViewVente.Remplirtable(this.idvente);
			}
			getWindow().close();
		});
	}
}
