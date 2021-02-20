package Classes;

import java.sql.ResultSet;

import ConnectionDB.ConnectToBD;
import application.FormViewVente;
import application.Paiement;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class LabelVent{
	private int idvent;
	private String NameCli;
	private String DateVente;
	private double TotalVente;
	private String etatVente;
	private Button payeBtn;
	private Button viewBtn;
	private HBox buttons;
	
	public LabelVent(int idvent,String dateVente, double totalVente, String etatVente) {
		super();
		this.idvent = idvent;
		setNameCli(getNom(idvent));
		DateVente = dateVente;
		TotalVente = totalVente;
		this.etatVente = etatVente;
		this.payeBtn = new Button("payé");
		this.viewBtn =  new Button("view");
		this.payeBtn.getStyleClass().add("SetButt");
		this.viewBtn.getStyleClass().add("ViewButt");
		if(etatVente=="true") {this.payeBtn.setDisable(true);}
		buttons = new HBox();
		buttons.getChildren().addAll(payeBtn,viewBtn);
		
		this.viewBtn.setOnAction(evnt->{
			new FormViewVente(this.idvent);
		});
		this.payeBtn.setOnAction(evnet->{
			new Paiement(this.TotalVente, this.idvent,this);
		});
	}
	public String getNom(int id) {
		ConnectToBD connect = new ConnectToBD();
		ResultSet rs = connect.queryExecute("select * from Ventes where codeVente = "+id);
		String nom="";
		try {
			if(rs.next()) {
				int codeCli = rs.getInt("CodeClient");
				rs = connect.queryExecute("select * from Clients where IdClient = "+codeCli);
				if(rs.next()) {
					nom = rs.getString("Nom")+" "+rs.getString("Prenom");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return nom;
	}
	public int getIdvent() {
		return idvent;
	}
	public String getNameCli() {
		return NameCli;
	}
	public String getDateVente() {
		return DateVente;
	}
	public double getTotalVente() {
		return TotalVente;
	}
	public String getEtatVente() {
		return etatVente;
	}
	public Button getPayeBtn() {
		return payeBtn;
	}
	public Button getViewBtn() {
		return viewBtn;
	}
	public HBox getButtons() {
		return buttons;
	}
	public void setIdvent(int idvent) {
		this.idvent = idvent;
		setNameCli(getNom(idvent));
	}
	public void setNameCli(String nameCli) {
		NameCli = nameCli;
	}
	public void setDateVente(String dateVente) {
		DateVente = dateVente;
	}
	public void setTotalVente(double totalVente) {
		TotalVente = totalVente;
	}
	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}
	public void setPayeBtn(Button payeBtn) {
		this.payeBtn = payeBtn;
	}
	public void setViewBtn(Button viewBtn) {
		this.viewBtn = viewBtn;
	}
	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}
	
	
}
