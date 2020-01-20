package agents;

import java.io.IOException;
import java.util.HashMap;

import agents.agentBehaviours.MarcheReceiveBehaviour;
import jade.core.Agent;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
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

	@FXML
    private TableView<Enchere> tableView;
	
	@FXML
	private TableColumn<Enchere, String> EnchereCol;
	
	@FXML
	private TableColumn<Enchere, String> PrixCol;
	
	private static ObservableList<Enchere> list = FXCollections.observableArrayList();

	public ObservableList<Enchere> getList() {
		return list;
	}

	public static void setList(ObservableList<Enchere> inputList) {
		list = inputList;
	}
	
	public static void addEnchere(Enchere e) {
		list.add(e);
	}
	
	@FXML
	public void initialize() {
		tableView.setItems(list);
		EnchereCol.setCellValueFactory(cellData -> cellData.getValue().objetProperty());
		PrixCol.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
	}
	
	// Max 2 vendeurs en theorie
	private HashMap<String, Enchere> vendeurAgentList;
	//private ArrayList<String> preneurAgentList;
	
	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");
		
		vendeurAgentList = new HashMap<>();
		//preneurAgentList = new ArrayList<>();
		
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
    						Parent root;
							try {
								root = FXMLLoader.load(getClass().getResource("agentInterfaces/Marche.fxml"));
								Stage stage = new Stage();
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
	        /*Thread update = new Thread(new Runnable() {

	            @Override
	            public void run() {
	                Runnable updater = new Runnable() {

	                    @Override
	                    public void run() {
<<<<<<< HEAD
	                        //System.out.println("from the marche" + getVendeurs());
=======
	                        System.out.println(myName + ": Ce message s'affiche en boucle.");
	                        addEnchere(new Enchere("enchere", "prix"));
>>>>>>> branch 'master' of https://github.com/jrampang/FSMA_project_2019.git
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
	        update.start();*/
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