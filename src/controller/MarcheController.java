package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Enchere;

public class MarcheController {
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

	public static void addEnchere(Enchere e) {
		list.add(e);
	}
	
	public static void updateEnchere(Enchere e) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(e)) {
				list.set(i, e);
			}
		}
	}
	
	public static void deleteEnchere(Enchere e) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(e)) {
				list.remove(i);
			}
		}
	}
	
	@FXML
	public void initialize() {
		tableView.setItems(list);
		EnchereCol.setCellValueFactory(cellData -> cellData.getValue().objetProperty());
		PrixCol.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
	}
}
