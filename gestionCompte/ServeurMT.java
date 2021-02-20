package gestionCompte;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class ServeurMT  extends Thread{

	private int nbrClient;
	private int port = 1234;
	
	@Override
	public void run() {
		try {
			synchronized (this)  {
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
		@Override
		public void run() {
			try {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while (true) {
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
