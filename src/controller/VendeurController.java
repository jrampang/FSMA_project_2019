package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Encherisseur;

public class VendeurController {
	@FXML
	private TableView<Encherisseur> tableView;
	
	@FXML
	private TableColumn<Encherisseur, String> preneur;
	
	@FXML
	private TableColumn<Encherisseur, String> montant;
	
	private static ObservableList<Encherisseur> list = FXCollections.observableArrayList();

	public static ObservableList<Encherisseur> getList() {
		return list;
	}

	public static void setList(ObservableList<Encherisseur> list) {
		VendeurController.list = list;
	}
	
	public void addEncherisseur(Encherisseur e) {
		list.add(e);
	}
	
	public void removeEncherisseur(Encherisseur e) {
		list.remove(e);
	}
	
	public void removeAllEncherisseurs() {
		list.clear();
	}
	
	@FXML
	public void initialize() {
		tableView.setItems(list);
		preneur.setCellValueFactory(cellData -> cellData.getValue().getPreneur());
		montant.setCellValueFactory(cellData -> cellData.getValue().getOffre());
	}

	
}
