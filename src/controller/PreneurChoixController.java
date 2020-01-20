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
		        //fermer cette fenetre et ouvrir la fenetre Preneur avec le bon mode
		    	System.out.println("j'ai choisi: " + mode.getValue());
		    	mode.getScene().getWindow().hide();
		    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../agents/agentInterfaces/PreneurManuel.fxml"));
				Parent root;
				try {
					root = fxmlloader.load();
					agent.setManuelContoller(fxmlloader.getController());
					agent.getManuelContoller().setAgent(agent);
					agent.getStage().setTitle(agent.getName());
					agent.getStage().setScene(new Scene(root));
					agent.getStage().show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*choixController = fxmlloader.getController();
				choixController.setAgent(self);*/
				
				
		    }
		});
	}
}
