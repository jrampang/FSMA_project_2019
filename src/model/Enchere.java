package model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Enchere implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3965384656646431112L;
	private String objet;
	private String prix;
	private String vendeur;
	
	public Enchere(String objet, String prix, String vendeur) {
		this.objet = objet;
		this.vendeur = vendeur;
		if(isInt(prix)) {
			this.prix = prix;
		}
		else {
			this.prix = "-1";
		}
	}
	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	
	public StringProperty objetProperty() {
		return new SimpleStringProperty(objet);
	}
	
	public String getPrix() {
		return prix;
	}
	
	public boolean isInt (String string) {
		boolean valeur = true;
		char[] tab = string.toCharArray();

		for(char carac : tab){
			if(!Character.isDigit(carac) && valeur){
				valeur = false;
			}
		}

		return valeur;
	}
	
	public void setPrix(String prix) {
		if(isInt(prix)) {
			this.prix = prix;
		}
		else {
			this.prix = "-1";
		}
	}
	
	public StringProperty prixProperty() {
		return new SimpleStringProperty(prix);
	}
	
	public String getVendeur() {
		return vendeur;
	}
	
	public void setVendeur(String vendeur) {
		this.vendeur = vendeur;
	}
	
	public StringProperty vendeurProperty() {
		return new SimpleStringProperty(vendeur);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
		Enchere e = (Enchere) obj;
		return (this.vendeur.contains(e.getVendeur()));
	}
	
	@Override
	public String toString() {
		return this.objet + "|" + this.prix + "|" + this.vendeur;
	}
}