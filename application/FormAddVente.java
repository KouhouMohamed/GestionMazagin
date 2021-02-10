package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Statement;

import Classes.Ligne;
import ConnectionDB.ConnectToBD;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class FormAddVente {
	int id;
	double total=0;
	static List<Ligne> tabLignes = new ArrayList<Ligne>();
	VBox root = new VBox();
	VBox rootv = new VBox();
	Scene scene = new Scene(rootv);
	Stage window = new Stage();
	Label titleLabel = new Label("Nouvelle Vente");
	
	Label NomClientLabel = new Label("Nom Client : ");
	TextField NomClientText = new TextField();
	Label nominfos = new Label();
	HBox Nombox = new HBox();
	
	Label PrenomClientLabel = new Label("Prénom Client : ");
	TextField PrenomClientText = new TextField();
	Label prenominfos = new Label();
	HBox Prenombox = new HBox();
	
	Label DateDAjoutLabel = new Label("Date Vente : ");
	DatePicker DatedAjoutPicker = new DatePicker(LocalDate.now());
	HBox Datebox = new HBox();
	
	Button Addligne = new Button("Ajouter Lignes");
	Label ligneinfos = new Label();
	
	
	
	HBox buttonsBox = new HBox();
	Button addBtn = new Button("Ajouter");
	Button cancelBtn = new Button("Annuler");
	Button viewBtn = new Button("AFficher");
	
	Alert message = new Alert(Alert.AlertType.CONFIRMATION);
	

	private void addNodesToWindow() {
		
		Nombox.getChildren().addAll(NomClientLabel,NomClientText);
		Prenombox.getChildren().addAll(PrenomClientLabel,PrenomClientText);
		Datebox.getChildren().addAll(DateDAjoutLabel,DatedAjoutPicker);
		buttonsBox.getChildren().addAll(addBtn, viewBtn, cancelBtn);

		rootv.getChildren().add(titleLabel);
		root.getChildren().add(Nombox);
		root.getChildren().addAll(nominfos);
		root.getChildren().add(Prenombox);
		root.getChildren().addAll(prenominfos);
		root.getChildren().add(Datebox);
		root.getChildren().addAll(Addligne,ligneinfos);
		rootv.getChildren().add(root);
		rootv.getChildren().add(buttonsBox);
		
	}
	
	private void initWindow() {
		window.setScene(scene);
		window.setWidth(600);
		window.setHeight(450);
		window.setTitle("Ajouter une nouvelle categorie");
		window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		viewBtn.setDisable(true);
		addNodesToWindow();
		addStyleToNodes();
		addEvent();
	}
	private void addEvent() {
		
		window.setOnCloseRequest(event ->{
			event.consume();
		});
		
		//Ajouter des events au btns
		addBtn.setOnAction(event ->{
			
			ConnectToBD addVente = new ConnectToBD();
			if(tabLignes.size()==0) {
				ligneinfos.getStyleClass().setAll("labelinfosError");
				ligneinfos.setText("ajouter des lignes de commandes à la vente");
			}
			else {
				ligneinfos.getStyleClass().setAll("labelinfosValid");
				ligneinfos.setText("la commande est remplie");
			}
			if(NomClientText.getText().isEmpty()) {NomClientText.getStyleClass().add("labelError");}
			if(PrenomClientText.getText().isEmpty()) {PrenomClientText.getStyleClass().add("labelError");}
			if(!NomClientText.getStyleClass().contains("labelError") && !PrenomClientText.getStyleClass().contains("labelError") && !ligneinfos.getStyleClass().contains("labelinfosError") ) {

				String nom = NomClientText.getText().toUpperCase();
				String prenom = PrenomClientText.getText().toUpperCase();
				String date = DatedAjoutPicker.getValue().toString();
				long codeCli;
				String requet = "select * from Clients where `Nom` = '"+nom+"' and `Prenom`='"+prenom+"'";
				ResultSet result = addVente.queryExecute(requet);
				ResultSet r;
				//System.out.println();
				try {
					if(result.next()){
						requet = "select * from Ventes where CodeClient="+result.getInt("IdClient")+" and dateVente= '"+date+"'";
						r = addVente.queryExecute(requet);
						if(!r.next()) {

							for(Ligne ligne:tabLignes) {
								this.total += ligne.getTotal();
							}
							this.id = addVente.addVente(date,result.getInt("IdClient"),this.total);
								int exist = -1;
								if(this.id!=-1 && tabLignes.size()!=0) {
									
									for(Ligne ligne:tabLignes) {
										requet = "select * from Lignes where CodeProd= "+ligne.getProduit().getCode()+" and IdVente= "+this.id;
										ResultSet result1;
										try {
											addVente.addLigne(ligne.getProduit().getCode(), this.id,ligne.getQuantite());			
											
											/*Modifier la qte dans le produit*/
											String q = " select `Quantite` from produits where CodeProd="+ligne.getProduit().getCode();
											double qte=0;
											result1 =  addVente.queryExecute(q);
											if(result1.next()) {
												qte = result1.getInt("Quantite")-ligne.getQuantite();
											}
											String query =" UPDATE `produits` SET `Quantite`= "+qte+" WHERE CodeProd="+ligne.getProduit().getCode();
											Statement sqlConnection = addVente.getConnection().createStatement();
											sqlConnection.executeUpdate(query);
											
											
											addBtn.setDisable(true);
											NomClientText.getStyleClass().setAll("labelValid");
											PrenomClientText.getStyleClass().setAll("labelValid");
										 	nominfos.getStyleClass().setAll("labelinfosValid");
										 	prenominfos.getStyleClass().setAll("labelinfosValid");
										 	message.setAlertType(Alert.AlertType.INFORMATION);
											message.setTitle("Ajout avec succé ...");
										 	message.setHeaderText("La vente du client  "+nom+" "+prenom+ " est ajoutée avec succé");
										 	message.setContentText("Vous pouvez maintenant visualiser la vente");
										 	message.setResizable(false);
										 	message.showAndWait();
										 	viewBtn.setDisable(false);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								
						}
						else {
							/*
							 * la vente est deja passé :  afficher un message **/
							message.setAlertType(Alert.AlertType.ERROR);
							message.setTitle("Erreur d'inforamations ...");
						 	message.setHeaderText("Le client  "+nom+" "+prenom+ " a déja effectué une vente le : "+date);
						 	message.setContentText("Changer la date et essayer à nouveau");
						 	message.showAndWait();
							
						}
					}
					else {
						Optional<ButtonType> choice = message.showAndWait();
						if (choice.get() == ButtonType.OK){
							FormAddClient newCli = new FormAddClient(false,-1);
							newCli.ClientNomText.setText(NomClientText.getText());
							newCli.ClientPrenomText.setText(PrenomClientText.getText());
							newCli.addBtn.setOnAction(even->{
								String nomCli = newCli.ClientNomText.getText().toUpperCase();
								String prenomCli = newCli.ClientPrenomText.getText().toUpperCase();
								long numTel = Long.parseLong(newCli.ClientNumTelText.getText());
								String email = newCli.ClientEmailText.getText();
								String address = newCli.ClientAdressText.getText();
								addVente.addClient(nomCli, prenomCli, numTel, email, address);
								newCli.window.close();
							});
						} else {
							NomClientText.getStyleClass().setAll("labelError");
							PrenomClientText.getStyleClass().setAll("labelError");
							
						 	nominfos.getStyleClass().setAll("labelinfosError");
						 	nominfos.setText("*ce nom n'existe pas");
						 	prenominfos.getStyleClass().setAll("labelinfosError");
						 	prenominfos.setText("*ce prenom n'existe pas");
						}
							
							}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				if(ligneinfos.getStyleClass().contains("labelinfosError")) {
					ligneinfos.setText("ajouter des lignes de commandes à la vente");
				}
				else {
					NomClientText.getStyleClass().setAll("labelError");
					PrenomClientText.getStyleClass().setAll("labelError");
				}
				addBtn.setDisable(true);
			}
		});
		
		
		
		cancelBtn.setOnAction(event ->{
			window.close();
			
		});
		
		viewBtn.setOnAction(event ->{
			new FormViewVente(id);
		});
		Addligne.setOnAction(event->{
			addBtn.setDisable(false);
			new FormAddLigne(tabLignes,"");
			if(tabLignes.size() != 0) {
				viewBtn.setDisable(false);
			}
			
		});
		NomClientText.setOnKeyTyped(event ->{
			NomClientLabel.getStyleClass().setAll("labelForm");
			PrenomClientLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			if(!NomClientText.getText().matches("[a-zA-Z]{3,}")) {
				NomClientText.getStyleClass().setAll("labelError");
				nominfos.getStyleClass().setAll("labelinfosError");
				nominfos.setText("*nom incorrect");
				
			}
			else {
//				NomClientText.getStyleClass().setAll("labelTextVent");
				NomClientText.getStyleClass().setAll("labelTextVent");
				nominfos.getStyleClass().setAll("labelinfosValid");
				nominfos.setText("*nom valide");
			}
			
		});
		
		PrenomClientText.setOnKeyTyped(event ->{
			PrenomClientLabel.getStyleClass().setAll("labelForm");
			NomClientLabel.getStyleClass().setAll("labelForm");
			DateDAjoutLabel.getStyleClass().setAll("labelForm");
			if(!PrenomClientText.getText().matches("[a-zA-Z]{3,}")) {
				PrenomClientText.getStyleClass().setAll("labelError");
				prenominfos.getStyleClass().setAll("labelinfosError");
				prenominfos.setText("*prenom incorrect");
				
			}
			else {
				PrenomClientText.getStyleClass().setAll("labelTextVent");
				prenominfos.getStyleClass().setAll("labelinfosValid");
				prenominfos.setText("*prenom valide");
			}
			
		});
		
			
		
		
	}
	private void addStyleToNodes() {
		scene.getStylesheets().add("css/style.css");
		root.getStyleClass().add("ajoutWindow");
		rootv.setSpacing(10);
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		rootv.setMargin(buttonsBox, new Insets(0, 0, 0, 20));
		
		
		titleLabel.getStyleClass().add("labelTitle");
		titleLabel.setMaxWidth(window.getWidth());
		titleLabel.setMaxHeight(100);
		titleLabel.setMinHeight(100);
		titleLabel.setAlignment(Pos.CENTER);
		Nombox.setMaxWidth(window.getWidth()-30);
		Prenombox.setMaxWidth(window.getWidth()-30);
		Datebox.setMaxWidth(window.getWidth()-30);
		
		
		NomClientText.setPromptText("Entrer nom");
		NomClientText.setMaxWidth(Nombox.getMaxWidth()/3);
		NomClientText.setMinWidth(Nombox.getMaxWidth()/3);
		NomClientText.setFont(Font.font(15));
		PrenomClientText.setPromptText("Entrer Prenom");
		PrenomClientText.setMaxWidth(Prenombox.getMaxWidth()/3);
		PrenomClientText.setMinWidth(Prenombox.getMaxWidth()/3);
		PrenomClientText.setFont(Font.font(15));
		Addligne.setMaxWidth(570);
		Addligne.setFont(Font.font(15));
		
		NomClientText.getStyleClass().add("labelTextVent");
		PrenomClientText.getStyleClass().add("labelTextVent");
		
		NomClientLabel.setMaxWidth(2*Nombox.getMaxWidth()/3);
		NomClientLabel.getStyleClass().add("labelForm");
		PrenomClientLabel.setMaxWidth(2*Prenombox.getMaxWidth()/3);
		PrenomClientLabel.getStyleClass().add("labelForm");
		DateDAjoutLabel.getStyleClass().add("labelForm");
		
		
		nominfos.setMaxWidth(570);
		nominfos.setMaxHeight(5);
		prenominfos.setMaxWidth(570);
		prenominfos.setMaxHeight(5);
		
		Nombox.setSpacing(40);
		Prenombox.setSpacing(20);
		Datebox.setSpacing(40);
		
		buttonsBox.setMaxWidth(570);
		buttonsBox.setSpacing(50);
		addBtn.getStyleClass().add("addBtn");
		cancelBtn.getStyleClass().add("cancelBtn");
		viewBtn.getStyleClass().add("emptyBtn");
		
		message.setTitle("Erreur dans les informations");
		message.setHeaderText("le nom et le prénom du client entrant ne figure pas dans la liste des clients");
		message.setContentText("Voulez-vous ajouter ce client ?");
		
		
	}
	
	public FormAddVente() {
		initWindow();

		window.show();
	}
}
