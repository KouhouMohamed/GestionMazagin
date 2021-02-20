package application;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Statement;

import Classes.LabelVent;
import ConnectionDB.ConnectToBD;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Paiement {
	
	private Socket socketEnd2 = null;
	private static final int port = 3333;
	private InputStream inputStream=null;
	private OutputStream outputStream=null;
	
	VBox root = new VBox();
	Scene scene = new Scene(root);
	Stage window = new Stage();
	Label titleLabel = new Label("Paiement");
	
	Button PaiementCatreBtn = new Button("Paiement par carte");
	Button AutrePaiementBtn = new Button("Paiement par autres");
	HBox paiementChoice = new HBox();
	TextField NumeroCompte = new TextField();
	Button valideBtn = new Button("Valider");
	Label ResultPaiement = new Label();
	Button cancelBtn = new Button("Quitter");
	
	private double total;
	private int idVente;
	private LabelVent Thisvente;
	ConnectToBD connect = new ConnectToBD();
	public Paiement(double total, int numVente, LabelVent LigneVenteTab) {
		this.total = total;
		this.idVente = numVente;
		this.Thisvente = LigneVenteTab;
		windowInit();
		window.show();
	}
	
	private String getResultPaiement(String NumCompte, double total) {
		try {
			//Etablir la connexion avec le serveur via le port : 3333
			socketEnd2 = new Socket("127.0.0.1",port);
			inputStream = socketEnd2.getInputStream();
			outputStream = socketEnd2.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectOutputStream os;
		boolean accept;
		DataInputStream dis;
		DataOutputStream dos;
		try {
			dos  = new DataOutputStream(outputStream);
			dis = new DataInputStream(inputStream);
			//Envoyer le numero du compte eu serveur
			dos.writeUTF(NumCompte);
			dos.flush();
			
			//Lire la repense du serveur
			accept = dis.readBoolean();
			
			
			if(accept) {
				//Si le numero du compte est correct envoyer le total de la vente
				os = new ObjectOutputStream(outputStream);
				os.writeDouble(total);
				os.flush();
				
				//Lire la repense de serveur depent au suffisance de solde et afficher un message
				accept = dis.readBoolean();
				if(accept) {
					//Si le paiement est fait avec succé
					ResultPaiement.setText("Paiement valid !! Votre achat est payé avec succé");
					ResultPaiement.getStyleClass().setAll("labelValidPaiem");
					valideBtn.setVisible(false);
					String qry = " UPDATE `Ventes` SET `payer`="+true+" WHERE codeVente="+this.idVente;
					try {
						Statement sqlConnection = connect.getConnection().createStatement();
						sqlConnection.executeUpdate(qry);
						
					} catch (Exception e) {e.printStackTrace();}
					
					this.Thisvente.getPayeBtn().setDisable(true);
				}
				else {
					//SI le solde du compte n'est pas suffisant
					ResultPaiement.setText("Votre solde est insuffesant pour réaliser cette operation !!");
					ResultPaiement.getStyleClass().setAll("labelErrorPaiem");
				}
			}
			else {
				//Si le numero du compte est incorrect
				ResultPaiement.setText("ce numero de compte est incorrect, essayez a nouveau !!");
				ResultPaiement.getStyleClass().setAll("labelErrorPaiem");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private void windowInit() {
		window.setScene(scene);
		window.setWidth(600);
		window.setHeight(450);
		window.setTitle("Paiement d'une vente");
		window.getIcons().add(new Image(getClass().getResourceAsStream("icone.jpg")));
		//window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		
		addNodes();
		addStyles();
		addEvents();
		
	}
	
	private void addNodes() {
		paiementChoice.getChildren().addAll(PaiementCatreBtn,AutrePaiementBtn);
		root.getChildren().add(titleLabel);
		root.getChildren().addAll(paiementChoice, NumeroCompte,valideBtn,ResultPaiement,cancelBtn);
		NumeroCompte.setVisible(false);
		valideBtn.setVisible(false);
	}
	
	private void addStyles() {
		scene.getStylesheets().add("css/style.css");
		
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		paiementChoice.setAlignment(Pos.CENTER);
		paiementChoice.setSpacing(10);
		
		titleLabel.getStyleClass().add("labelTitle");
		titleLabel.setMaxWidth( window.getWidth());
		titleLabel.setAlignment(Pos.CENTER);
		
		NumeroCompte.setMaxWidth(400);
		NumeroCompte.setMinWidth(400);
		ResultPaiement.setMaxWidth(400);
		ResultPaiement.setMinWidth(400);
		ResultPaiement.setMaxHeight(30);
		ResultPaiement.setMinHeight(30);
		NumeroCompte.getStyleClass().add("numeroCompte");
		cancelBtn.getStyleClass().add("cancelBtn");
		valideBtn.getStyleClass().add("createBtn");
		valideBtn.setAlignment(Pos.TOP_RIGHT);
		
		PaiementCatreBtn.getStyleClass().add("paiementChoice");
		AutrePaiementBtn.getStyleClass().add("paiementChoice");
		
	}
	
	private void addEvents() {
		PaiementCatreBtn.setOnAction(event->{
			NumeroCompte.setVisible(true);
			valideBtn.setVisible(true);
			NumeroCompte.setPromptText("Entrer le numero de votre carte bancaire");
			PaiementCatreBtn.setDisable(true);
		});
		
		cancelBtn.setOnAction(event->{
			window.close();
		});
		valideBtn.setOnAction(event->{
			getResultPaiement(NumeroCompte.getText(), this.total);
		});
	}

	
	
}
