package controller;

import java.io.IOException;

import agents.PreneurAgent;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	
	private static ObservableList<Enchere> list = FXCollections.observableArrayList();
	
	private String auto = "Mode Automatique";
	
	private String manuel = "Mode manuel";
	
	private ObservableList<String> modes = FXCollections.observableArrayList(auto, manuel);
	
	private PreneurAgent agent;
	
	public ObservableList<Enchere> getList() {
		return list;
	}

	public static void setList(ObservableList<Enchere> inputList) {
		list = inputList;
	}
	
	public static void addEnchere(Enchere e) {
		list.add(e);
	}
	
	public void setAgent(PreneurAgent agent) {
		this.agent = agent;
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
		    	String choice = mode.getValue();
		    	System.out.println("j'ai choisi: " + choice);
		    	mode.getScene().getWindow().hide();
		    	if(choice.contains("Mode manuel")) {
		    		agent.setMode("manuel");
		    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../agents/agentInterfaces/PreneurManuel.fxml"));
					Parent root;
					try {
						root = fxmlloader.load();
						agent.setManuelController(fxmlloader.getController());
						agent.getManuelController().setAgent(agent);
						agent.getStage().setScene(new Scene(root));
						agent.getStage().show();
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    	else if(choice.contains("Mode Automatique")) {
		    		agent.setMode("auto");
		    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../agents/agentInterfaces/PreneurAuto.fxml"));
					Parent root;
					try {
						root = fxmlloader.load();
						agent.setAutoController(fxmlloader.getController());
						agent.getAutoController().setAgent(agent);
						agent.getStage().setScene(new Scene(root));
						agent.getStage().show();
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    }
		});
	}
}
