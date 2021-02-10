package Classes;

import ConnectionDB.ConnectToBD;
import application.FormAddClient;
import application.FormSetCategorie;
import application.ListCategories;
import application.ListClients;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Client {
	private int idCli;
	private String nomCli;
	private String prenomCli;
	private long numTelephoneCli;
	private String emailCli;
	private String adressCli;
	private  Button deletButt;
	private  Button setButt;
	private HBox buttons = new HBox();
	private ConnectToBD connect = new ConnectToBD();
	public Client(int id ,String nomCli, String prenomCli, long numTelephoneCli, String emailCli, String adressCli) {
		super();
		this.idCli = id;
		this.nomCli = nomCli;
		this.prenomCli = prenomCli;
		this.numTelephoneCli = numTelephoneCli;
		this.emailCli = emailCli;
		this.adressCli = adressCli;

		this.deletButt = new Button("Supp");
		this.setButt = new Button("Set");
		buttons.setSpacing(5);
		buttons.getChildren().addAll(deletButt,setButt);
		deletButt.getStyleClass().add("DeletButt");
		setButt.getStyleClass().add("SetButt");
		deletButt.setOnAction(event->{
			for(Client verificat : ListClients.tableCategorie.getItems()) {
				if(verificat.getDeletButt() == this.deletButt) {
					ListClients.tableCategorie.getSelectionModel().clearSelection();
					ListClients.tableCategorie.getItems().remove(verificat);
					connect.DeletQuery("Clients",verificat.getIdCli());
					break;
				}
			}
		});
		setButt.setOnAction(event->{
			new FormAddClient(true,this.idCli);
		});
	}
	public Button getDeletButt() {
		return deletButt;
	}
	public Button getSetButt() {
		return setButt;
	}
	public void setDeletButt(Button deletButt) {
		this.deletButt = deletButt;
	}
	public void setSetButt(Button setButt) {
		this.setButt = setButt;
	}
	public int getIdCli() {
		return this.idCli;
	}
	public String getNomCli() {
		return nomCli;
	}
	public String getPrenomCli() {
		return prenomCli;
	}
	public long getNumTelephoneCli() {
		return numTelephoneCli;
	}
	public String getEmailCli() {
		return emailCli;
	}
	public String getAdressCli() {
		return adressCli;
	}
	public void setNomCli(String nomCli) {
		this.nomCli = nomCli;
	}
	public void setPrenomCli(String prenomCli) {
		this.prenomCli = prenomCli;
	}
	public void setNumTelephoneCli(long numTelephoneCli) {
		this.numTelephoneCli = numTelephoneCli;
	}
	public void setEmailCli(String emailCli) {
		this.emailCli = emailCli;
	}
	public void setAdressCli(String adressCli) {
		this.adressCli = adressCli;
	}
	public HBox getButtons() {
		return buttons;
	}
	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}
	


}