package controller;

import java.io.IOException;
import java.util.ArrayList;

import agents.PreneurAgent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Enchere;

public class PreneurManuelController {
	@FXML
    private TableView<Enchere> tableView;
	
	@FXML
	private TableColumn<Enchere, String> EnchereCol;
	
	@FXML
	private TableColumn<Enchere, String> PrixCol;
	
	@FXML
	private Button rencherir;
	
	private ObservableList<Enchere> list = FXCollections.observableArrayList();
	
	private PreneurAgent agent;

	public ObservableList<Enchere> getList() {
		return list;
	}

	public void setList(ArrayList<Enchere> inputList) {
		if(!list.containsAll(inputList)) {
			list = FXCollections.observableArrayList(inputList);
		}
	}
	
	public void addEnchere(Enchere e) {
		list.add(e);
	}
	
	public void updateEnchere(Enchere e) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(e)) {
				if(list.get(i).getPrix() != e.getPrix()) {
					list.set(i, e);
				}
			}
		}
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
		
		rencherir.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	ObservableList<Enchere> selectedEncheres = tableView.getSelectionModel().getSelectedItems();
		        System.out.println(agent.getMyName() + ": i have (surencherit) sur: " + selectedEncheres);
		        if(selectedEncheres.size() > 1) {
		        	System.out.println(agent.getMyName() + ": the functionality to handle multiple offers isn't implement yet.");
		        }
		        else{
		        	System.out.println(agent.getMyName() + ": sending a to_bid manually.");
		        	agent.setTo_bid(new ACLMessage(ACLMessage.PROPOSE));
		        	agent.setOutbidBid(selectedEncheres.get(0));
			        agent.getTo_bid().addReceiver(new AID(selectedEncheres.get(0).getVendeur(), AID.ISLOCALNAME));
			        agent.send(agent.getTo_bid());
			        //agent.setBiding(false);
		        }
		    }
		});
	}
}
