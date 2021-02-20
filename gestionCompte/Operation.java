package gestionCompte;

import java.sql.Date;

public class Operation {

	private long id;
	private Date date;
	private double montant;
	private String type;
	
	
	public Operation(Date date, double montant, String type) {
		super();
		this.date = date;
		this.montant = montant;
		this.type = type;
	}
	public Operation(long id, Date date, double montant, String type) {
		super();
		this.id = id;
		this.date = date;
		this.montant = montant;
		this.type = type;
	}
	
	
	public long getId() {
		return id;
	}
	public Date getDate() {
		return date;
	}
	public double getMontant() {
		return montant;
	}
	public String getType() {
		return type;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
