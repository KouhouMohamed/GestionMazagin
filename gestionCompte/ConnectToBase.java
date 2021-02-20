package gestionCompte;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConnectToBase {

	private Connection connection;
	public Connection getConnection() {
		return this.connection;
	}
	public ConnectToBase() {
		String url = "jdbc:mysql://localhost:3306/compte_exam";
		try {
			connection = DriverManager.getConnection(url,"root","");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Boolean addCompte(long code,String nom, String prenom) {
		Boolean add = true;
		String requet = "INSERT INTO `compte`(`numero`, `nom`, `prenom`) VALUES ("+code+",'"+nom+"','"+prenom+"')";
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(requet);
			sqlConnection.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
			add = false;
			e.printStackTrace();
		}
		return add;
	}
	public ResultSet getInfoCompte(String code) {
		ResultSet rs=null;
		String rq = "select *  from compte where numero = '"+code+"'";
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(rq);
			rs = sqlConnection.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	public ObservableList<Operation> listOps(String codeCompte){
		String rq = "select * from compte where numero = '"+codeCompte+"'";
		ObservableList<Operation> listOfOpts = FXCollections.observableArrayList();
		Operation op;
		try {
			PreparedStatement sqlConnection = connection.prepareStatement(rq);
			ResultSet rs = sqlConnection.executeQuery();
			if(rs.next()) {
				String id = rs.getString("id");
				rq = "select * from operation where id_compte = '"+id+"'";
				sqlConnection = connection.prepareStatement(rq);
				rs = sqlConnection.executeQuery();
				while(rs.next()) {
					op = new Operation(rs.getDate("date"), rs.getDouble("montant"), rs.getString("type"));
					listOfOpts.add(op);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOfOpts;
	}
	public void addOpertaion(String numCompte, String type,double total) {
		
		ResultSet rs = getInfoCompte(numCompte);
		double totalCompte=0;
		String qry;
		try {
			if(rs.next()){
				totalCompte = rs.getDouble("solde");
			}
			if(type =="Vers") {
				totalCompte += total;
			}
			else if(type == "Retr") {
				totalCompte -= total;
			}
			 qry = " UPDATE `compte` SET `solde`="+totalCompte+" WHERE numero='"+numCompte+"'";
			 try {
					Statement sqlConnection = getConnection().createStatement();
					sqlConnection.executeUpdate(qry);
				} 
			 catch (Exception e) {e.printStackTrace();}
			 
		} catch (Exception e) {e.printStackTrace();}
	}
}
