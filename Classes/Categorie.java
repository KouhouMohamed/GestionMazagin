package Classes;

import ConnectionDB.ConnectToBD;
import application.FormAddCategorie;
import application.FormSetCategorie;
import application.ListCategories;
import application.ListProducts;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Categorie {
	private long codeCat;
	private String intitule;
	private  Button deletButt;
	private  Button setButt;
	private  Button viewButt;
	private  Button viewButtClient;
	private HBox buttons = new HBox();
	private ConnectToBD connect = new ConnectToBD();

	public Categorie(){}
	public Categorie(long codeCat, String intitule) {
		super();
		this.codeCat = codeCat;
		this.intitule = intitule;
		
		this.deletButt = new Button("Supp");
		this.setButt = new Button("Set");
		this.viewButt = new Button("View");
		this.viewButtClient = new Button("View");
		buttons.setSpacing(5);
		buttons.getChildren().addAll(deletButt,setButt,viewButt);
		deletButt.getStyleClass().add("DeletButt");
		setButt.getStyleClass().add("SetButt");
		viewButt.getStyleClass().add("ViewButt");
		viewButtClient.getStyleClass().add("ViewButt");
		deletButt.setOnAction(event->{
			for(Categorie verificat : ListCategories.tableCategorie.getItems()) {
				if(verificat.getDeletButt() == this.deletButt) {
					ListCategories.tableCategorie.getSelectionModel().clearSelection();
					ListCategories.tableCategorie.getItems().remove(verificat);
					connect.DeletQuery("Categorie",verificat.getCodeCat());
					break;
				}
			}
		});
		setButt.setOnAction(event->{
			new FormSetCategorie(this.codeCat);
		});
		viewButt.setOnAction(event->{
			new ListProducts(getIntitule());
		});
		viewButtClient.setOnAction(event->{
			new ListProducts(getIntitule(),true);
		});
	}
	public long getCodeCat() {
		return codeCat;
	}
	public void setCodeCat(long codeCat) {
		this.codeCat = codeCat;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	
	public Button getDeletButt() {
		return deletButt;
	}
	public Button getSetButt() {
		return setButt;
	}
	public Button getViewButt() {
		return viewButt;
	}
	public Button getViewButtClient() {
		return viewButtClient;
	}
	
	public HBox getButtons() {
		return buttons;
	}
	public void setDeletButt(Button deletButt) {
		this.deletButt = deletButt;
	}
	public void setSetButt(Button setButt) {
		this.setButt = setButt;
	}
	public void setViewButt(Button viewButt) {
		this.viewButt = viewButt;
	}
	public void setViewButtClient(Button viewButtClient) {
		this.viewButtClient = viewButtClient;
	}
	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}
	@Override
	public String toString() {
		if(intitule != null)
			return intitule.toUpperCase();
		else
			return null;
	}
	
	
	

}
