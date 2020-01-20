package agents.agentInterfaces;

import java.awt.Dimension;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VendeurFX extends Application {
	private Dimension _screenSize;
    private double _width;
    private double _height;
	BorderPane root;
	Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new BorderPane();
		Parent root = FXMLLoader.load(getClass().getResource("VendeurInit.fxml"));
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setTitle("test");
		
		Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("VendeurFX:Ce message s'affiche en boucle.");
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
        thread.setDaemon(true);
        thread.start();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
