package agents;
import java.io.IOException;

import agents.agentBehaviours.VendeurAnnounceBehaviour;
import controller.VendeurController;
import controller.VendeurInitController;
import jade.core.Agent;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Enchere;

public class VendeurAgent extends Agent {
	private String myName;
	private int step = 0;
	private boolean finish = false;
	private VendeurInitController controllerInit;
	private VendeurController controller;
	private Enchere enchere;
	
	public Enchere getEnchere() {
		return enchere;
	}

	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}

	public VendeurInitController getControllerInit() {
		return controllerInit;
	}

	public void setControllerInit(VendeurInitController controllerInit) {
		this.controllerInit = controllerInit;
	}

	public VendeurController getController() {
		return controller;
	}

	public void setController(VendeurController controller) {
		this.controller = controller;
	}
	
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");

		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			myName = (String) args[0];
			
			// Printout the name
		    System.out.println("My name is "+myName);
			VendeurAgent self = this;
		    
		    /*new Thread() {
	            @Override
	            public void run() {*/
            	
            		new JFXPanel();
            		Platform.runLater(new Runnable() {
            			@Override
            			public void run() {
    						Parent root;
							try {
								FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("agentInterfaces/VendeurInit.fxml"));
								root = fxmlloader.load();
								controllerInit = fxmlloader.getController();
								controllerInit.setAgent(self);
								System.out.println("VendeurAgent: self = " + self);
								Stage stage = new Stage();
								stage.setX(1200);
	    					    stage.setTitle(myName);
	    					    stage.setScene(new Scene(root));
	    					    stage.setResizable(false);
	    					    stage.show();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            			}
            		});
	           /* }
	        }.start();*/
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
	
	public void creerEnchere() {
		enchere = new Enchere(controllerInit.getNom().getText(), controllerInit.getPrix().getText(), this.myName);
		VendeurAgent self = this;
		new JFXPanel();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Parent root;
				try {
					/*root = FXMLLoader.load(getClass().getResource("agentInterfaces/VendeurInit.fxml"));
					Stage stage = new Stage();
				    stage.setTitle(myName);
				    stage.setScene(new Scene(root));
				    stage.show();*/
				    FXMLLoader fxmlloader2 = new FXMLLoader(getClass().getResource("agentInterfaces/Vendeur.fxml"));
					root = fxmlloader2.load();
					controller = fxmlloader2.getController();
					Stage stage2 = new Stage();
					stage2.setX(1000);
					stage2.setTitle(myName);
					stage2.setScene(new Scene(root));
					stage2.show();
					addBehaviour(new VendeurAnnounceBehaviour(
		    				controllerInit.getPrix().getText(),
		    				Integer.parseInt(controllerInit.getTimer().getText()),
		    				Integer.parseInt(controllerInit.getIncrement().getText()),
		    				Integer.parseInt(controllerInit.getDecrement().getText()),
		    				self
		    		));	
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
