package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Enchere {
	private StringProperty objet;
	private StringProperty prix;
	
	public Enchere(String objet, String prix) {
		this.objet = new SimpleStringProperty(objet);
		this.prix = new SimpleStringProperty(prix);
	}
	public String getObjet() {
		return objet.get();
	}
	public void setObjet(String objet) {
		this.objet.set(objet);
	}
	
	public StringProperty objetProperty() {
		return objet;
	}
	
	public String getPrix() {
		return prix.get();
	}
	public void setPrix(String prix) {
		this.prix.set(prix);
	}
	
	public StringProperty prixProperty() {
		return prix;
	}
}
