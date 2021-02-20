package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Classes.Client;

public class ClientProgram {

	private Socket socketEnd2 = null;
	private static final int port = 3333;
	
	private InputStream inputStream=null;
	private OutputStream outputStream=null;
	
	public ClientProgram() {
		// TODO Auto-generated constructor stub
		try {
			socketEnd2 = new Socket("127.0.0.1",port);
			inputStream = socketEnd2.getInputStream();
			outputStream = socketEnd2.getOutputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getInfoCompte() {
		int numCli = 2;
		try {
			/*Pour envoyer un entier*/
			outputStream.write(numCli);
			
			/*Pour lire une Stirng*/
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String messageRecu = br.readLine();
			System.out.println(messageRecu);
			
			/*Pour lire les objets*/
			
			ObjectInputStream oi = new ObjectInputStream(inputStream);
			//Client client = (Client)oi.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

public static void main(String[] args) {
	ClientProgram client = new ClientProgram();
	client.getInfoCompte();
}
}
