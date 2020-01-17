package agents.agentInterfaces;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MarcheFX extends Application {
	private Dimension _screenSize;
    private double _width;
    private double _height;
	private BorderPane root;
	private Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        _width = _screenSize.getWidth();
        _height = _screenSize.getHeight();
		root = FXMLLoader.load(getClass().getResource("Marche.fxml"));
		scene = new Scene(root, _width / 5, _height / 5);
		primaryStage.setTitle("test");
		
		Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("MarcheFX:Ce message s'affiche en boucle.");
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
