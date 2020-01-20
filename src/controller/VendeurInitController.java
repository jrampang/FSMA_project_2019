package controller;

import agents.VendeurAgent;
import agents.agentBehaviours.VendeurAnnounceBehaviour;
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
	
	private VendeurAgent agent;


	public void initialize() {
		creerEnchere.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {
		        //fermer cette fenetre et ouvrir la fenetre Vendeur
		    	creerEnchere.getScene().getWindow().hide();
		    	
		    	if(agent != null) {
		    		agent.addBehaviour(new VendeurAnnounceBehaviour(
		    				prix.getText(),
		    				Integer.parseInt(timer.getText()),
		    				Integer.parseInt(increment.getText()),
		    				Integer.parseInt(decrement.getText())
		    		));	
		    	}
		    	else {
		    		System.out.println("VendeurInitController: agent is null.");
		    	}
		    }
		});
	}


	public VendeurAgent getAgent() {
		return agent;
	}


	public void setAgent(VendeurAgent agent) {
		this.agent = agent;
	}
}
