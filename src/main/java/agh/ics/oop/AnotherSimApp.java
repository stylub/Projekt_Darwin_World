package agh.ics.oop;

import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
//Tymczasowa klasa do odpalania GUI
public class AnotherSimApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("launcher.fxml"));
        VBox viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureStage(primaryStage, viewRoot);
//        primaryStage.setScene(new Scene(viewRoot));
        primaryStage.show();
    }
    private void configureStage(Stage primaryStage, VBox viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation launcher");
        primaryStage.minWidthProperty().bind(viewRoot.prefWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.prefHeightProperty());
    }
}
