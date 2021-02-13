package Classes;

import java.util.List;

import ConnectionDB.ConnectToBD;
import application.ListProducts;
import application.MainWindows;
import application.FormAddLigne;
import application.FormSetProduit;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Produit {
	private long code;
	private String Designation;
	private double prixAch;
	private double prixVen;
	private int quantity;
	private  Button deletButt;
	private  Button setButt;
	private  Button selectBtn;
	private HBox buttons = new HBox();
	private ConnectToBD connect = new ConnectToBD();
	private Categorie categorie;
	
	public Produit() {};
	
	public Produit(long code, String designation, double prixVen) {
		super();
		this.code = code;
		Designation = designation;
		this.prixVen = prixVen;
		this.categorie = categorie;
	}

	public Produit(long code, String designation, double prixAch, double prixVen, int quantity, Categorie cat) {
		super();
		this.code = code;
		this.Designation = designation;
		this.prixAch = prixAch;
		this.prixVen = prixVen;
		this.quantity = quantity;
		this.categorie = cat;
		this.deletButt = new Button("Supp");
		this.setButt = new Button("Set");
		this.selectBtn = new Button("Select");
		buttons.setSpacing(5);
		buttons.getChildren().addAll(deletButt,setButt,selectBtn);
		deletButt.getStyleClass().add("DeletButt");
		setButt.getStyleClass().add("SetButt");
		selectBtn.getStyleClass().add("ViewButt");
		deletButt.setOnAction(event->{
			
			for(Produit verificat : ListProducts.tableProduct.getItems()) {
				if(verificat.getDesignation().equals(this.getDesignation())) {
					ListProducts.tableProduct.getSelectionModel().clearSelection();
					ListProducts.tableProduct.getItems().remove(verificat);
					connect.DeletQuery("produits",verificat.getCode());
					break;
				}
			}
		});
		setButt.setOnAction(event->{
			new FormSetProduit(String.valueOf(code)).ShowWindow();
		});
		selectBtn.setOnAction(event->{
			FormAddLigne formaddLigne = new FormAddLigne(MainWindows.listLignes, String.valueOf(getCode()));
			
		});
	}
	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}
	public String getDesignation() {
		return Designation;
	}
	public void setDesignation(String designation) {
		Designation = designation;
	}
	public double getPrixVen() {
		return prixVen;
	}
	public void setPrixVen(double prix) {
		this.prixVen = prix;
	}
	public double getPrixAch() {
		return prixAch;
	}
	public void setPrixAch(double prix) {
		this.prixAch = prix;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public  Button getSelectBtn() {
		return selectBtn;
	}
	public void setSelectBtn(Button selectBtn) {
		this.selectBtn = selectBtn;
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
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.Designation;
	}
	
	
}
