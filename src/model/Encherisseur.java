package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Encherisseur {
	private String preneur;
	private String offre;
	private Enchere enchere;
	
	public Encherisseur(String preneur, String offre, Enchere enchere) {
		super();
		this.preneur = preneur;
		this.offre = offre;
		this.enchere = enchere;
	}


	public StringProperty getPreneur() {
		return new SimpleStringProperty(preneur);
	}

	public void setPreneur(String preneur) {
		this.preneur = preneur;
	}

	public StringProperty getOffre() {
		return new SimpleStringProperty(offre);
	}

	public void setOffre(String offre) {
		this.offre = offre;
	}

	public Enchere getEnchere() {
		return enchere;
	}

	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}
	
}
