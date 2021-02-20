package threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Classes.Client;

public class ServerProgram {
	private static final int port = 3333;
	private ServerSocket serverSocket = null;
	private Socket socketEnd1;
	
	private InputStream inputStream=null;
	private OutputStream outputStream=null;
	
	public ServerProgram() {
		// TODO Auto-generated constructor stub
		try {
			System.out.println("server .....");
			serverSocket = new ServerSocket(port);
			System.out.println("server started.....");
			accepterConnexion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void accepterConnexion() {
		try {
			System.out.println("attente de demande .....");
			socketEnd1 = serverSocket.accept();

			inputStream = socketEnd1.getInputStream();
			outputStream = socketEnd1.getOutputStream();
			System.out.println("connexion accepter .....");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendInfoCompte() {
		try {
			/*Pour les entiers*/
			int numCli = inputStream.read();
//			System.out.println("numero compte recu est : "+numCli); 
			
			/*Pour les String*/
			PrintWriter pw  = new PrintWriter(outputStream);
//			pw.write("Bonjour Le client numero : "+numCli);
//			pw.flush();
//			pw.close();
			
			/*Pour les Objet*/
			/*La class a envoyer doit implimenter la class Serializable*/
			Client obj=new Client();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(obj);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
public static void main(String[] args) {
	
	ServerProgram server = new ServerProgram();
	server.sendInfoCompte();
}
}
