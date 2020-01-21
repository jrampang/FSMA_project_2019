package model;

import javafx.beans.property.StringProperty;

public class Encherisseur {
	private StringProperty preneur;
	private StringProperty offre;
	private Enchere enchere;
	
	public Encherisseur(StringProperty preneur, StringProperty offre, Enchere enchere) {
		this.preneur = preneur;
		this.offre = offre;
		this.enchere = enchere;
	}

	public StringProperty getPreneur() {
		return preneur;
	}

	public void setPreneur(StringProperty preneur) {
		this.preneur = preneur;
	}

	public StringProperty getOffre() {
		return offre;
	}

	public void setOffre(StringProperty offre) {
		this.offre = offre;
	}

	public Enchere getEnchere() {
		return enchere;
	}

	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}
	
}
