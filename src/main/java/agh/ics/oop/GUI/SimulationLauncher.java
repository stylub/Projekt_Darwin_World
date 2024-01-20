package agh.ics.oop.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class SimulationLauncher {

    private HashMap<String, Integer> options = new HashMap<>();
    @FXML
    private ComboBox<String> mutationVariant;
    @FXML
    private ComboBox<String> grassGrowthVariant;
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
    private TextField configName;
    @FXML
    private ArrayList<Spinner<Integer>> spinners;
    @FXML
    private String defaultGrassGrowth;
    @FXML
    private String deadAnimalGrassGrowth;
    @FXML
    private String defaultMutation;
    @FXML
    private String substitutionMutation;


    @FXML
    public void launchSimulation() throws IOException {
//        Update options to pass them into presenter
        updateOptions();
        System.out.println(grassGrowthVariant.toString());
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
        if(Objects.isNull(spinners)){
            spinners = new ArrayList<>(List.of(mapHeight,mapWidth,grassStartingNumber,grassEnergy,grassRegeneration,genomeLength,minMutations,maxMutations,animalStartingNumber,animalStartingEnergy,animalFullEnergy,animalBreedEnergy,framesPerSecond));
        }
        this.options.clear();

        for(Spinner<Integer> spinner:spinners){
            options.put(spinner.getId(), spinner.getValue());
        }
        options.put(mutationVariant.getId(), mutationVariant.getValue().equals(defaultMutation) ? 0 : 1);
        options.put(grassGrowthVariant.getId(), grassGrowthVariant.getValue().equals(defaultGrassGrowth) ? 0 : 1);
    }
    private void updateGUI(HashMap<String, Integer> newOptions){
        for(Spinner<Integer> spinner: spinners){
            spinner.getValueFactory().setValue(newOptions.get(spinner.getId()));
        }
        mutationVariant.setValue(newOptions.get(mutationVariant.getId())  == 0 ? defaultMutation : substitutionMutation);
        grassGrowthVariant.setValue(newOptions.get(grassGrowthVariant.getId()) == 0 ? defaultGrassGrowth : deadAnimalGrassGrowth);
    }
    @FXML
    private void loadConfig() throws IOException {
        checkSpinners();
        Stream<String> lines = Files.lines(Paths.get("configs/" + configName.getCharacters().toString() +".csv"));
        HashMap<String, Integer> newOptions = new HashMap<>();
        lines.map(line -> line.split(" "))
                .filter(line -> !line[0].equals("Option"))
                .forEach(line -> newOptions.put(line[0], Integer.valueOf(line[1])));
        System.out.println(newOptions);
        lines.close();
        updateGUI(newOptions);
    }
    @FXML
    private void saveConfig(){
        //Get config name from TextField
        String cfgName = configName.getCharacters().toString();

        //Get fresh options
        updateOptions();

        //Save everything in new file
        try (FileWriter csvWriter = new FileWriter(new File("configs", cfgName +".csv"))) {
            csvWriter.append("Option value\n");
            for (String key:options.keySet()) {
                csvWriter.append(key).append(" ");
                csvWriter.append(options.get(key).toString()).append("\n");
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void checkSpinners(){
        if(Objects.isNull(spinners)){
            spinners = new ArrayList<>(List.of(mapHeight,mapWidth,grassStartingNumber,grassEnergy,grassRegeneration,genomeLength,minMutations,maxMutations,animalStartingNumber,animalStartingEnergy,animalFullEnergy,animalBreedEnergy,framesPerSecond));
        }
    }
}
