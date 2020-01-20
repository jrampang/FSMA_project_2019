package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class VendeurInitController {
	@FXML
	private TextField prix;
	
	@FXML
	private TextField timer;
	
	@FXML
	private TextField increment;
	
	@FXML
	private TextField decrement;
	
	@FXML
	private Button creerEnchere;
	
	public void initialize() {
		creerEnchere.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {
		        //fermer cette fenetre et ouvrir la fenetre Vendeur
		    }
		});
	}
}
