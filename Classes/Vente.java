package Classes;

import java.util.ArrayList;

import application.FormViewVente;
import application.ListVentes;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Vente {
	private int codeVente;
	private String dateVente;
	double total;
	private boolean etatPay;
	Button payeBtn;
	Button viewBtn;
	HBox buttons ;
	
	private ArrayList<Ligne> lignesComd = new ArrayList<Ligne>();

	public Vente(int codeVente, String dateVente,double total, boolean etat) {
		super();
		this.codeVente = codeVente;
		this.dateVente = dateVente;
		this.total = total;
		this.etatPay =etat;
		this.payeBtn = new Button("payé");
		this.viewBtn = new Button("view");
		this.buttons = new HBox();
		buttons.getChildren().addAll(viewBtn,payeBtn);
	}
	
	public void addLigne(Ligne ligne) {
		this.lignesComd.add(ligne);
	}
	public void addLignes(ArrayList<Ligne> lignes) {
		this.lignesComd.addAll(lignes);
	}
	public int getCodeVente() {
		return codeVente;
	}
	public String getDateVente() {
		return dateVente;
	}
	public boolean isEtatPay() {
		return etatPay;
	}
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
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
	public ArrayList<Ligne> getLignesComd() {
		return lignesComd;
	}
	public void setCodeVente(int codeVente) {
		this.codeVente = codeVente;
	}
	public void setDateVente(String dateVente) {
		this.dateVente = dateVente;
	}
	public void setEtatPay(boolean etatPay) {
		this.etatPay = etatPay;
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
	public void setLignesComd(ArrayList<Ligne> lignesComd) {
		this.lignesComd = lignesComd;
	}
	
	
}
