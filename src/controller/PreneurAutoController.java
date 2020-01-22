package controller;

import agents.PreneurAgent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Enchere;

public class PreneurAutoController {
	
	@FXML
	private TableView<Enchere> tableView;
	
	@FXML
	private TableColumn<Enchere, String> EnchereCol;
	
	@FXML
	private TableColumn<Enchere, String> PrixCol;
	
	private ObservableList<Enchere> list = FXCollections.observableArrayList();
	
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
	
	public void updateState(String name) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getVendeur().contains(name)) {
				list.get(i).setObjet("WIN");
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
	}
}
