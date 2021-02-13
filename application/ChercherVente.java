package application;

import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;

import ConnectionDB.ConnectToBD;
import javafx.scene.control.Alert;

public class ChercherVente {
	FormAddVente form= new FormAddVente();
	Alert message = new Alert(Alert.AlertType.INFORMATION);
	private void setNodes() {
		form.Addligne.setVisible(false);
		form.viewBtn.setVisible(false);
		form.addBtn.setText("Chercher");
		form.titleLabel.setText("Chercher une vente");
	}
	private void SetEvent() {
		form.addBtn.setOnAction(event->{
			ConnectToBD connect = new ConnectToBD();
			String requet = "select * from Clients where Nom = '"+form.NomClientText.getText().toUpperCase()+"' and Prenom = '"+form.PrenomClientText.getText().toUpperCase()+"'";
			//PreparedStatement statement = new PreparedStatement(requet);
			ResultSet resultCli = connect.queryExecute(requet);
			ResultSet resultVent;
			try {
				if(resultCli.next()) {
					requet = "select * from Ventes where CodeClient= "+resultCli.getInt("IdClient")+" and dateVente= '"+form.DatedAjoutPicker.getValue().toString()+"'";
					resultVent = connect.queryExecute(requet);
					if(resultVent.next()) {
						new FormViewVente(resultVent.getInt("codeVente"));
					}
					else {
						message.setTitle("Informations !!!");
						message.setHeaderText("Le client "+form.NomClientText.getText().toUpperCase() + " "+form.PrenomClientText.getText().toUpperCase()+" n'a effectue aucune vente le : "+form.DatedAjoutPicker.getValue());
						message.showAndWait();
					}
				}
				else {
					message.setTitle("Attention !!!");
					message.setHeaderText("Ce client ne figure pas dans la liste des Clients");
					message.showAndWait();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		});
	}
public ChercherVente() {
	setNodes();
	SetEvent();
}
public ChercherVente(String Nom, String Preom) {
	setNodes();
	SetEvent();
	form.NomClientText.setText(Nom);
	form.PrenomClientText.setText(Preom);
	form.NomClientText.setEditable(false);
	form.PrenomClientText.setEditable(false);
	
}
}
