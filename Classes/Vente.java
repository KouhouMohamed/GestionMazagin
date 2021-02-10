package Classes;

import java.util.ArrayList;

public class Vente {
	private int codeVente;
	private String dateVente;
	private ArrayList<Ligne> lignesComd = new ArrayList<Ligne>();
	public Vente(int codeVente, String dateVente) {
		super();
		this.codeVente = codeVente;
		this.dateVente = dateVente;
	}
	
	public void addLigne(Ligne ligne) {
		this.lignesComd.add(ligne);
	}
	public void addLignes(ArrayList<Ligne> lignes) {
		this.lignesComd.addAll(lignes);
	}
}
