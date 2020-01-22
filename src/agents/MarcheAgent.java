package agents;

import java.io.IOException;
import java.util.HashMap;

import agents.agentBehaviours.MarcheReceiveBehaviour;
import jade.core.Agent;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Enchere;

public class MarcheAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String myName;
	protected int step = 0;
	protected boolean finish = false;
	
	// Maximum 2 vendeurs en theorie
	private HashMap<String, Enchere> vendeurAgentList;
	
	private Parent root;
	private Stage stage;
	
	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");
		
		vendeurAgentList = new HashMap<>();
		
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			myName = (String) args[0];
			
			// Printout the name
		    System.out.println("My name is "+myName);
		    
		    addBehaviour(new MarcheReceiveBehaviour(this));
		    
		    new Thread() {
	            @Override
	            public void run() {
            	
            		new JFXPanel();
            		Platform.runLater(new Runnable() {
            			@Override
            			public void run() {
							try {
								root = FXMLLoader.load(getClass().getResource("agentInterfaces/Marche.fxml"));
								stage = new Stage();
								stage.setX(500);
	    					    stage.setTitle(myName);
	    					    stage.setScene(new Scene(root));
	    					    stage.setResizable(false);
	    					    stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
            			}
            		});
	            }
	        }.start();
		}
		else {
			// Make the agent terminate
			System.out.println("No name specified");
			//doDelete();
		}
	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}
	
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public HashMap<String, Enchere> getVendeurs (){
		return this.vendeurAgentList;
	}
	
	public void addVendeur(String agent, Enchere enchere) {
		this.vendeurAgentList.put(agent, enchere);
	}
	
	public void updateVendeur(String agent, Enchere enchere) {
		this.vendeurAgentList.put(agent, enchere);
	}
	
	public void deleteVendeur(String agent) {
		this.vendeurAgentList.remove(agent);
	}
}