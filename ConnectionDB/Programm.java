package ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

import Classes.Ligne;
import javafx.collections.ObservableList;

class Programm{
	public static void main(String[] args) {
		ConnectToBD connect = new ConnectToBD();
		Connection connection = (Connection) connect.getConnection();
		String nom="kouhou";
		String prenom="medko";
		String q = " select Quantite from produits where CodeProd="+1666;
		double qte=0;
		
		
		try {
			ResultSet result1 =  connect.queryExecute(q);
			if(result1.next()) {
				qte = result1.getInt("Quantite")+110;
			}
			String query =" UPDATE `produits` SET `Quantite`=  "+qte+" WHERE CodeProd="+1666;

			Statement sqlConnection = connect.getConnection().createStatement();
			sqlConnection.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}