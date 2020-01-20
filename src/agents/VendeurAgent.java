package agents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import agents.agentInterfaces.VendeurFX;
import controller.VendeurInitController;
import jade.core.Agent;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VendeurAgent extends Agent {
	private String myName;
	private int step = 0;
	private boolean finish = false;
	VendeurInitController controller;
	
	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");

		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			myName = (String) args[0];
			
			// Printout the name
		    System.out.println("My name is "+myName);
		    
		    /*new Thread() {
	            @Override
	            public void run() {*/
            	
            		new JFXPanel();
            		Platform.runLater(new Runnable() {
            			@Override
            			public void run() {
            				Dimension _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	                    double _width = _screenSize.getWidth();
    	                    double _height = _screenSize.getHeight();
    						Parent root;
							try {
								root = FXMLLoader.load(getClass().getResource("agentInterfaces/VendeurInit.fxml"));
								Stage stage = new Stage();
	    					    stage.setTitle(myName);
	    					    stage.setScene(new Scene(root));
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
		new JFXPanel();
	VendeurAgent self = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Dimension _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double _width = _screenSize.getWidth();
                double _height = _screenSize.getHeight();
				Parent root;
				try {
					FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("agentInterfaces/VendeurInit.fxml"));
					root = fxmlloader.load();
					controller = fxmlloader.getController();
					controller.setAgent(self);
					Stage stage = new Stage();
				    stage.setTitle(myName);
				    stage.setScene(new Scene(root));
				    stage.show();
				    FXMLLoader fxmlloader2 = new FXMLLoader(getClass().getResource("agentInterfaces/Vendeur.fxml"));
					root = fxmlloader2.load();
					controller = fxmlloader2.getController();
					Stage stage2 = new Stage();
					stage2.setTitle(myName);
					stage2.setScene(new Scene(root));
					stage2.show();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
