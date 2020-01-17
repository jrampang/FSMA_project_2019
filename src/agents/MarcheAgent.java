package agents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import controller.MarcheController;
import javafx.scene.control.TableColumn;

import jade.core.Agent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
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
	
	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");

		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			myName = (String) args[0];
			
			// Printout the name
		    System.out.println("My name is "+myName);
		    
		    
		    new Thread() {
	            @Override
	            public void run() {
            	
            		new JFXPanel();
            		Platform.runLater(new Runnable() {
            			@Override
            			public void run() {
            				Dimension _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	                    double _width = _screenSize.getWidth();
    	                    double _height = _screenSize.getHeight();
    						Parent root;
							try {
								root = FXMLLoader.load(getClass().getResource("agentInterfaces/Marche.fxml"));
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
	            }
	        }.start();
	        Thread update = new Thread(new Runnable() {

	            @Override
	            public void run() {
	                Runnable updater = new Runnable() {

	                    @Override
	                    public void run() {
	                        System.out.println(myName + ": Ce message s'affiche en boucle.");
	                        MarcheController.addEnchere(new Enchere("enchere", "prix"));
	                    }
	                };

	                while (true) {
	                    try {
	                        Thread.sleep(1000);
	                    } catch (InterruptedException ex) {
	                    }

	                    // UI update is run on the Application thread
	                    Platform.runLater(updater);
	                }
	            }

	        });
	        // don't let thread prevent JVM shutdown
	        update.setDaemon(true);
	        update.start();
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
}
