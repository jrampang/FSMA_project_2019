package controller;

import java.io.IOException;

import agents.PreneurAgent;
import agents.agentBehaviours.PreneurAutoBehaviour;
import agents.agentBehaviours.PreneurManuelBehaviour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Enchere;

public class PreneurChoixController {
	@FXML
    private TableView<Enchere> tableView;
	
	@FXML
	private TableColumn<Enchere, String> EnchereCol;
	
	@FXML
	private TableColumn<Enchere, String> PrixCol;
	
	@FXML
	private Button lancerEnchere;
	
	@FXML
	private ChoiceBox<String> mode;
	
	@FXML
	private TextField budget;
	
	@FXML
	private Label error;
	
	private ObservableList<Enchere> list = FXCollections.observableArrayList();
	
	private String auto = "Mode Automatique";
	
	private String manuel = "Mode manuel";
	
	private ObservableList<String> modes = FXCollections.observableArrayList(auto, manuel);
	
	private PreneurAgent agent;
	
	public ObservableList<Enchere> getList() {
		return list;
	}

	public void setList(ObservableList<Enchere> inputList) {
		list = inputList;
	}
	
	public void addEnchere(Enchere e) {
		list.add(e);
	}
	
	public void updateEnchere(Enchere e) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(e)) {
				list.set(i, e);
			}
		}
	}
	
	public PreneurAgent getAgent() {
		return this.agent;
	}
	
	public void setAgent(PreneurAgent agent) {
		this.agent = agent;
	}
	
	public Scene getScene() {
		return mode.getScene();
	}
	
	@FXML
	public void initialize() {
		tableView.setItems(list);
		EnchereCol.setCellValueFactory(cellData -> cellData.getValue().objetProperty());
		PrixCol.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		mode.setItems(modes);
		mode.setValue(auto);
		lancerEnchere.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {
		    	ObservableList<Enchere> selectedEncheres = tableView.getSelectionModel().getSelectedItems();
		    	if(selectedEncheres != null) {
		    		System.out.println(agent.getMyName() + ": Enchere clicked = " + selectedEncheres);
		    		String choice = mode.getValue();
			    	if(choice.contains("Mode manuel")) {
			    		System.out.println(agent.getMyName() + ": j'ai choisit " + choice);
			    		//mode.getScene().getWindow().hide();
			    		agent.getStage().hide();
			    		agent.setMode("manuel");
			    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../agents/agentInterfaces/PreneurManuel.fxml"));
						Parent root;
						try {
							root = fxmlloader.load();
							agent.setManuelController(fxmlloader.getController());
							agent.getManuelController().setAgent(agent);
							for(int i = 0; i < selectedEncheres.size(); i++) {
								agent.getEnchereList().add(selectedEncheres.get(i));
								agent.getManuelController().addEnchere(selectedEncheres.get(i));
							};
							agent.getStage().setX(10);
							agent.getStage().setScene(new Scene(root));
							agent.getStage().show();
						} catch (IOException error) {
							error.printStackTrace();
						}
						agent.addBehaviour(new PreneurManuelBehaviour(agent));
			    	}
			    	else if(choice.contains("Mode Automatique") && !budget.getText().isEmpty() && isInt(budget.getText())) {
			    		System.out.println(agent.getMyName() + ": j'ai choisit " + choice);
			    		agent.setMode("auto");
			    		//mode.getScene().getWindow().hide();
			    		agent.getStage().hide();
			    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../agents/agentInterfaces/PreneurAuto.fxml"));
						Parent root;
						try {
							root = fxmlloader.load();
							agent.setAutoController(fxmlloader.getController());
							agent.getAutoController().setAgent(agent);
							for(int i = 0; i < selectedEncheres.size(); i++) {
								agent.getEnchereList().add(selectedEncheres.get(i));
								agent.getAutoController().addEnchere(selectedEncheres.get(i));
							};
							agent.getStage().setX(10);
							agent.getStage().setScene(new Scene(root));
							agent.getStage().show();
						} catch (IOException error) {
							error.printStackTrace();
						}
						agent.addBehaviour(new PreneurAutoBehaviour(agent));
			    	}
			    	else {
			    		error.setVisible(true);
			    	}
		    	}
		    }

			private boolean isInt(String string) {
				boolean valeur = true;
				char[] tab = string.toCharArray();

				for(char carac : tab){
					if(!Character.isDigit(carac) && valeur){
						valeur = false;
					}
				}

				return valeur;
			} 
		});
	}
}
