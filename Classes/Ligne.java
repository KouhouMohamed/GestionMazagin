package Classes;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import ConnectionDB.ConnectToBD;
import application.FormAddLigne;
import application.FormSetLigne;
import application.FormSetProduit;
import application.FormViewVente;
import application.ListProducts;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Ligne {
	private int idLigne;
	private Produit produit;
	private double total;
	private int quantite;
	private int codeVente;
	
	private  Button deletButt;
	private  Button setButt;
	private HBox buttons = new HBox();
	
	private String  designation;
	private double prix;
	
	public Ligne(long CodeProd, int qte, double prix) {
		this.quantite = qte;
		Produit prod  = new Produit();
		prod.setCode(CodeProd);
		prod.setPrixVen(prix);
		setProduit(prod);
	}
	
	public Ligne(long CodeProd, int qte) {
		this.quantite = qte;
		this.produit = new Produit();
		this.produit.setCode(CodeProd);
	}
	public Ligne(int id,Produit produit,int qte) {
		super();
		this.idLigne =id;
		this.produit = produit;
		this.quantite = qte;
		this.designation = produit.getDesignation();
		this.prix = produit.getPrixVen();
		setTotal();
		
		this.deletButt = new Button("Supp");
		this.setButt = new Button("Set");
		buttons.setSpacing(5);
		buttons.getChildren().addAll(deletButt,setButt);
		deletButt.getStyleClass().add("DeletButt");
		setButt.getStyleClass().add("SetButt");
		ConnectToBD connect = new ConnectToBD();
		
		deletButt.setOnAction(event->{
			
			for(Ligne verificat : FormViewVente.tableLigne.getItems()) {
				if(verificat.getDesignation().equals(this.getDesignation())) {
					FormViewVente.tableLigne.getSelectionModel().clearSelection();
					FormViewVente.tableLigne.getItems().remove(verificat);
					/*Modifier le total de la vente + la qte du produit*/
					
					/**/

					connect.setLigne((int)verificat.getProduit().getCode(), FormViewVente.idVent, 0);
					connect.DeletQuery("Lignes",verificat.getIdLigne());
					FormViewVente.tableLigne.getItems().get(0).setQuantite(qte);
					FormViewVente.Remplirtable(FormViewVente.idVent);
					break;
				}
			}
		});
		setButt.setOnAction(event->{
			new FormSetLigne(String.valueOf(this.produit.getCode()),String.valueOf(getQuantite()),FormViewVente.idVent);
		});
		
	}
	public int getIdLigne() {
		return this.idLigne;
	}
	public void setIdLigne(int id) {
		this.idLigne = id;
	}
	public int getQuantite() {
		return quantite;
	}
	public int getCodeVente() {
		return codeVente;
	}
	public void setCodeVente(int codeVente) {
		this.codeVente = codeVente;
	}
	public Produit getProduit() {
		return this.produit;
	}
	public double getTotal() {
		setTotal();
		return total;
	}
	
	public void setQuantite(int qte) {
		
		this.quantite = qte;
		setTotal();
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
		setTotal();
	}
	public void setTotal() {
		this.total = this.produit.getPrixVen()*this.quantite;
	}
	
	public double getPrix() {
		return this.prix;
	}
	public String getDesignation() {
		return this.designation;
	}
	public HBox getButtons() {
		return buttons;
	}
	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}
	public  Button getDeletButt() {
		return deletButt;
	}
	public void setDeletButt(Button deletButt) {
		this.deletButt = deletButt;
	}
	public  Button getSetButt() {
		return setButt;
	}
	public  void setSetButt(Button setButt) {
		this.setButt = setButt;
	}
}
