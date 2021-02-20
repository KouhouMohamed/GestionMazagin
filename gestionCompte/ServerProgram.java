package gestionCompte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ServerProgram extends Thread{

	private static final int port = 3333;
	private int nbrClient;	
	public static void main(String[] args) {
		ServerProgram server = new ServerProgram();
		server.start();
	}
	
	public void run() {
		try {
			//Demarrage du serveur
				ServerSocket serverSocket = new ServerSocket(port);
				System.out.println("Démarage de server");
				while(true) {
					//Acceptation de la connexion des clients
					Socket socketEnd1 = serverSocket.accept();
					System.out.println("connextion accepté");
					++nbrClient;
					//Demarrer un conversation avec le cleint(demarrer un thread)
					new Conversation(socketEnd1, nbrClient).start();
				}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	class Conversation extends Thread{
		Socket socket;
		int numClient;
		public Conversation(Socket s ,int numero) {
			this.socket = s;
			this.numClient = numero;
		}
		private void sendInfoCompte() {
			ResultSet rs;
			try {

				InputStream is = this.socket.getInputStream();
				OutputStream os = this.socket.getOutputStream();
				String numCompte;
				double total;
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				DataInputStream di = new DataInputStream(is);
				//Lire le numero du compte 
				System.out.println("Attent de numCompte");
				numCompte = di.readUTF();
				//ce connecter à la DB pour véréfier le compte
				ConnectToBase connect = new ConnectToBase();
				rs = connect.getInfoCompte(numCompte);
				try {
					ObjectInputStream oi;
					DataOutputStream dos;
					dos = new DataOutputStream(os);
					if(rs.next()) {
						/*Envoeyr la repense(true ou false) 
						dependament de la véréfocation du compte*/
						dos.writeBoolean(true);
						
						//Lire le total de la vente à payer
						oi = new ObjectInputStream(is);
						total = oi.readDouble();
						
						//Comparer le total avec le solde du compte
						if(total <= rs.getDouble("solde")) {
							synchronized (this) {
								connect.addOpertaion( numCompte,"Retr", total);
							}
							//repend le client
							dos.writeBoolean(true);
						}
						else {dos.writeBoolean(false);}
					}
					else {
						dos.writeBoolean(false);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		@Override
		public void run() {
			try {
				sendInfoCompte();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
