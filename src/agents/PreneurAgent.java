package agents;

import java.io.IOException;
import java.util.ArrayList;

import agents.agentBehaviours.PreneurAnnounceBehaviour;
import controller.PreneurAutoController;
import controller.PreneurChoixController;
import controller.PreneurManuelController;
import jade.core.Agent;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Enchere;

public class PreneurAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7728858608190050506L;
	private String myName;
	private boolean finish = false;
	private ArrayList<Enchere> enchereList;
	private int budget;
	private String mode;
	
	private Parent root;
	private Stage stage;
	
	private PreneurChoixController choixController;
	private PreneurAutoController autoController;
	private PreneurManuelController manuelController;
	
	private PreneurAgent self;
	
	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");
		
		enchereList = new ArrayList<>();
		
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			myName = (String) args[0];
			budget = Integer.parseInt((String) args[1]);
			
			self = this;
			
			// Printout the name
		    System.out.println("My name is " + myName);
		    System.out.println("My budget is " + budget);
		    
			addBehaviour(new PreneurAnnounceBehaviour(this));
		    
		    new Thread() {
	            @Override
	            public void run() {
            		new JFXPanel();
            		Platform.runLater(new Runnable() {
            			@Override
            			public void run() {
							try {
								FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("agentInterfaces/PreneurChoix.fxml"));
								root = fxmlloader.load();
								choixController = fxmlloader.getController();
								choixController.setAgent(self);
								stage = new Stage();
	    					    stage.setTitle(myName);
	    					    stage.setScene(new Scene(root));
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
			doDelete();
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

	public ArrayList<Enchere> getEnchereList() {
		return enchereList;
	}

	public void setEnchereList(ArrayList<Enchere> enchereList) {
		this.enchereList = enchereList;
	}

	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public PreneurChoixController getChoixController() {
		return choixController;
	}

	public void setChoixController(PreneurChoixController choixController) {
		this.choixController = choixController;
	}

	public PreneurAutoController getAutoController() {
		return autoController;
	}

	public void setAutoController(PreneurAutoController autoController) {
		this.autoController = autoController;
	}

	public PreneurManuelController getManuelContoller() {
		return manuelController;
	}

	public void setManuelContoller(PreneurManuelController manuelContoller) {
		this.manuelController = manuelContoller;
	}
}