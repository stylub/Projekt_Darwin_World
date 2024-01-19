package agh.ics.oop.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Prezenter launchera - TODO: zrobić go
public class SimulationLauncher {
    @FXML
    private HashMap<String, Integer> options = new HashMap<>();
    @FXML
    private ComboBox mutationVariant;
    @FXML
    private ComboBox grassGrowthVariant;
    @FXML
    private Spinner<Integer> mapHeight;
    @FXML
    private Spinner<Integer> mapWidth;
    @FXML
    private Spinner<Integer> grassStartingNumber;
    @FXML
    private Spinner<Integer> grassEnergy;
    @FXML
    private Spinner<Integer> grassRegeneration;
    @FXML
    private Spinner<Integer> genomeLength;
    @FXML
    private Spinner<Integer> minMutations;
    @FXML
    private Spinner<Integer> maxMutations;
    @FXML
    private Spinner<Integer> animalStartingNumber;
    @FXML
    private Spinner<Integer> animalStartingEnergy;
    @FXML
    private Spinner<Integer> animalFullEnergy;
    @FXML
    private Spinner<Integer> animalBreedEnergy;
    @FXML
    private Spinner<Integer> framesPerSecond;
    @FXML
    public void launchSimulation() throws IOException {
//        Update options to pass them into presenter
        updateOptions();

//        Set up new window, with new simulation and new presenter
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        presenter.initializeSimulation(options);
        configureStage(stage, viewRoot);
        stage.show();
    }
    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
    private void updateOptions(){
        this.options.clear();
        options.put("mapHeight", mapHeight.getValue());
        options.put("mapWidth", mapWidth.getValue());
        options.put("grassStartingNumber", grassStartingNumber.getValue());
        options.put("grassEnergy", grassEnergy.getValue());
        options.put("grassRegeneration", grassRegeneration.getValue());
        options.put("genomeLength", genomeLength.getValue());
        options.put("minMutations", minMutations.getValue());
        options.put("maxMutations", maxMutations.getValue());
        options.put("animalStartingNumber", animalStartingNumber.getValue());
        options.put("animalStartingEnergy", animalStartingEnergy.getValue());
        options.put("animalFullEnergy", animalFullEnergy.getValue());
        options.put("animalBreedEnergy", animalBreedEnergy.getValue());
        options.put("framesPerSecond", framesPerSecond.getValue());
        options.put("mutationVariant", mutationVariant.getValue().equals("Domyślny") ? 0 : 1);
        options.put("grassGrowthVariant", grassGrowthVariant.getValue().equals("Domyślny") ? 0 : 1);
    }
    @FXML
    private void loadConfig(){

    }
    @FXML
    private void saveConfig(){
        
    }
}
