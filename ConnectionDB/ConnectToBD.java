package ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Categorie;
import Classes.Client;
import Classes.Ligne;
import Classes.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

public class ConnectToBD {
	private Connection connection;
	public Connection getConnection() {
		return this.connection;
	}
	public ConnectToBD() {
		String url = "jdbc:mysql://localhost:3306/myMagasin";
		try {
			connection = DriverManager.getConnection(url,"root","");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void DeletQuery(String tab,long code) {
		String query="";
		switch (tab) {
		case "produits":
			query = "delete from produits where CodeProd="+code;
			break;
		case "Categorie":
			query = "delete from Categorie where CodeCat="+code;
			break;
		case "Clients":
			query = "delete from Clients where IdClient="+code;
			break;
		case "Lignes":
			query = "delete from Lignes where IdLigne="+code;
			break;
			
		default:
			break;
		}
		
		
		try {
			Statement sqlConnection = connection.createStatement();
			sqlConnection.executeUpdate(query);
			if(tab == "Categorie") {
				query = " UPDATE `produits` SET `CodeCategorie`= '"+null+"' where CodeCategorie= "+code;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	public ResultSet queryExecute(String requet) {
		PreparedStatement sqlConnection;
		ResultSet result=null;
		try {
			sqlConnection = connection.prepareStatement(requet);
			result = sqlConnection.executeQuery(requet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	public void addProduct(long code, String designation, double prixach, double prixven, String dateAjout ,long CodeCategorie,int quantity) {
		String requet = "insert into `produits` (`CodeProd`, `Designation`, `PrixAchat`, `PrixVente`, `DateAjout`, `CodeCategorie`, `Quantite`) values ( '" + code+"' , '"+designation +"' , '"+prixach+"' , '"+prixven+"' , '"+dateAjout+"' , '"+CodeCategorie+"' , '"+quantity+"' )";
		
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			sqlConnection.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Produit getProduit(long codeprod) {
		Produit prod=new Produit();
		String requet = "select * from produits where CodeProd="+codeprod;
		ResultSet result;
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			result = sqlConnection.executeQuery();
			if(result.next()) {
				prod = new Produit(result.getLong("CodeProd"), result.getString("Designation"),result.getDouble("PrixVente"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prod;
	}

	public ObservableList<Produit> getListOfProducts(){
		ObservableList<Produit> listProducts= FXCollections.observableArrayList();
		ResultSet result;
		ResultSet resultCategorie;
		//Categorie cat = new Categorie();
		int CodeCat;
		String intitule;
		Produit prod;
		String requet = "select * from produits";
		
		
		
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			result = sqlConnection.executeQuery();
			String query;
			while(result.next()) {
				//resultCategorie = queryExecute("select * from Categorie where CodeCat="+result.getInt("CodeCategorie"));
				
				
				if(result.getBoolean("CodeCategorie")) {
					try {
						query = "select * from Categorie where CodeCat="+result.getInt("CodeCategorie");
						PreparedStatement catConnection = connection.prepareStatement(query);
						resultCategorie = catConnection.executeQuery();
						if(resultCategorie.next()) {
							CodeCat = resultCategorie.getInt("CodeCat");
							intitule = resultCategorie.getString("intitule");
							prod = new Produit(result.getLong("CodeProd"), result.getString("Designation"), result.getDouble("PrixAchat"), result.getDouble("PrixVente"),result.getInt("Quantite"),new Categorie(CodeCat,intitule));
							listProducts.add(prod);
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					prod = new Produit(result.getLong("CodeProd"), result.getString("Designation"), result.getDouble("PrixAchat"), result.getDouble("PrixVente"),result.getInt("Quantite"),new Categorie(-1,null));
					listProducts.add(prod);
				}
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listProducts;
	}
	public ObservableList<Categorie> getListOfCategories(){
		ObservableList<Categorie> listCats = FXCollections.observableArrayList();
		ResultSet result;
		Categorie cat;
		String requet = "select * from Categorie";
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			result = sqlConnection.executeQuery();
			while(result.next()) {
				cat = new Categorie(result.getLong("CodeCat"), result.getString("intitule"));
				listCats.add(cat);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
		return listCats;
	}
	
	
	public void addCategorie(long code, String intitule) {
		String requet = "insert into `Categorie` (`CodeCat`, `intitule`) values ( '" + code+"' , '"+intitule +"' )";
		
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			sqlConnection.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addClient(String nom, String prenom, long numero, String email, String Address) {
		String requet = "INSERT INTO `Clients`(`Nom`, `Prenom`, `NumTelephone`, `Email`, `Address`) VALUES ('"+nom+"','"+prenom+"',"+numero+",'"+email+"','"+Address+"')";
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			sqlConnection.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ObservableList<Client> getListOfClient() {
		ObservableList<Client> listClis = FXCollections.observableArrayList();
		ResultSet result;
		Client client;
		String requet = "select * from Clients";
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			result = sqlConnection.executeQuery();
			while(result.next()) {
				client = new Client(result.getInt("IdClient"), result.getString("Nom"),result.getString("Prenom"),result.getLong("NumTelephone"),result.getString("Email"),result.getString("Address"));
				listClis.add(client);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
		return listClis;
	}
	
	public int addVente(String date, int codeCli, double total) {
		String requet = "insert into `Ventes` (`dateVente`, `CodeClient`,`total`) values ( '" + date+"' , '"+codeCli +"' , '"+total+"' )";
		int id=-1;
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet,Statement.RETURN_GENERATED_KEYS);
			sqlConnection.executeUpdate();
			ResultSet generatedKeys = sqlConnection.getGeneratedKeys();
			if(generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public void addLigne(long codeProd,int idVente,int qte) {
		String requet = "insert into `Lignes` (`CodeProd`, `IdVente`, `QteVend`) values ( '" + codeProd+"' , '"+idVente +"' , '"+qte+"' )";
		
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			sqlConnection.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ObservableList<Ligne> getListOfLignes(int Idvente) {
		ObservableList<Ligne> listLignes = FXCollections.observableArrayList();
		String requet = "select * from Lignes where IdVente= "+Idvente;
		ResultSet resultLigne;
		Ligne ligne;
		Produit produit;
		try {
			
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			resultLigne = sqlConnection.executeQuery();
			while(resultLigne.next()) {
				produit = getProduit(resultLigne.getLong("CodeProd"));
				ligne = new Ligne(resultLigne.getInt("IdLigne"),produit,resultLigne.getInt("QteVend"));
				if(ligne.getQuantite()!=0)
					listLignes.add(ligne);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listLignes;
	}
	public void setLigne(int codeprod,int idvente,int newQte) {
		String qry,rq = "select * from Lignes where idVente= "+idvente +" and CodeProd= "+codeprod;
		int oldQte=0, olpQtePrd=0,idLigne,codeProd=codeprod;
		double total=0, prix=0;
		ResultSet result;
		qry = "select *  from produits where CodeProd= "+codeProd;
		try {
			result = queryExecute(qry);
			if(result.next()) {
				olpQtePrd = result.getInt("Quantite");
				prix = result.getDouble("PrixVente");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ResultSet rs = queryExecute(rq);
		
		try {
			if(rs.next()) {
				oldQte = rs.getInt("QteVend");
				idLigne = rs.getInt("IdLigne");
				qry = " UPDATE `Lignes` SET `QteVend`="+newQte+" WHERE IdLigne="+idLigne;
				try {
					Statement sqlConnection = getConnection().createStatement();
					sqlConnection.executeUpdate(qry);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else {
					addLigne(codeProd, idvente, newQte);
				
//				qry = " UPDATE `Ventes` SET `total`="+(prix*newQte)+" WHERE codeVente="+idvente;
//				try {
//					Statement sqlConnection = getConnection().createStatement();
//					sqlConnection.executeUpdate(qry);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
			}
			
			
			qry = " UPDATE `produits` SET `Quantite`="+(olpQtePrd+oldQte-newQte)+" WHERE CodeProd="+codeProd;
			try {
				Statement sqlConnection = getConnection().createStatement();
				sqlConnection.executeUpdate(qry);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			rq = "select *  from Ventes where codeVente= "+idvente;
			result = queryExecute(rq);
			if(result.next()) {
				total = result.getDouble("total");
				qry = " UPDATE `Ventes` SET `total`="+(total+prix*(newQte-oldQte))+" WHERE codeVente="+idvente;
				try {
					Statement sqlConnection = getConnection().createStatement();
					sqlConnection.executeUpdate(qry);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public Boolean checkLogin(String email, String passwd, char fct) {
		Boolean login = false;
		String qry = "";
		switch (fct) {
		case 'A':
			qry ="select * from Administration where Email= '"+email+"' and Password= '"+passwd+"'";
			break;
		case 'C':
			qry ="select * from Clients where Email= '"+email+"' and Password= '"+passwd+"'";
			break;

		default:
			break;
		}
		ResultSet rs = queryExecute(qry);
		try {
			if(rs.next()) {
				login = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return login;
	}
	
}
