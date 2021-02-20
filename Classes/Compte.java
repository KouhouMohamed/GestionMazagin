package Classes;

public class Compte {

	private long id;
	private String numero;
	private String nom;
	private String prenom;
	public Compte(long id, String numero, String nom, String prenom) {
		super();
		this.id = id;
		this.numero = numero;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	
	public long getId() {
		return id;
	}
	public String getNumero() {
		return numero;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	
}
