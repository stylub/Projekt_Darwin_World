package agh.ics.oop.GUI;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.FollowAnimal;
import agh.ics.oop.model.util.SimulationStatistics;
import agh.ics.oop.simulationBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Label numbOfAnimals;
    @FXML
    private Label followGenome;
    @FXML
    private Label followEnergy;
    @FXML
    private Label eatenGrass;
    @FXML
    private Label followChildren;
    @FXML
    private Label followDescendants;
    @FXML
    private Label followLifetime;
    @FXML
    private Label followDeath;
    @FXML
    private Button showStrongest;
    @FXML
    private Button showPrefferedFields;
    @FXML
    private Label avgKids;
    @FXML
    private Label avgLifetime;
    @FXML
    private Label avgEnergy;
    @FXML
    private Label bestGen;
    @FXML
    private Label freeSpace;
    @FXML
    private Label numbOfGrass;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button stopButton;
    private Globe worldMap;
    private Simulation simulation;
    private SimulationStatistics simulationStatistics;
    private FollowAnimal followAnimal;
    private Boolean stopFlag = false;
    private int height;
    private int gridWidth = 600;
    private Boolean isPrefFieldsShown = false;
    private Boolean isShowStrongest = false;
    private File simulationDataFile;
    private FileWriter csvWriter;
    private Boolean saveData;
    private Boolean outcomeFilesInitialized = false;

    public void initializeSimulation(HashMap<String, Integer> options) {
        this.height = options.get("mapHeight");
        this.saveData = options.get("saveOutcome").toString().equals("1");
        AnimalBuilder animalBuilder = new AnimalBuilder()
                .setEnergy(options.get("animalStartingEnergy"))
                .setGenomeLength(options.get("genomeLength"))
                .setProcreationEnergy(options.get("animalBreedEnergy"))
                .setEnergyFromGrass(options.get("grassEnergy"))
                .setFullEnergy(options.get("animalFullEnergy"))
                .setNumberOfMutations(options.get("minMutations"));
        this.worldMap = new Globe(options.get("mapWidth"),
                options.get("mapHeight"),
                options.get("grassStartingNumber"),
                options.get("grassRegeneration"),
                GrassVariant.getVariantByValue(options.get("grassGrowthVariant")),
                options.get("mutationVariant"),
                animalBuilder);

        worldMap.addListener(this);
        this.simulationStatistics = new SimulationStatistics(worldMap);
        this.followAnimal = new FollowAnimal(worldMap);

        Simulation simulation = new Simulation(new simulationBuilder()
                .setMap(worldMap)
                .setStaringAnimals(options.get("animalStartingNumber"))
                .setStartingGrass(options.get("grassStartingNumber"))
                .setNewGrass(options.get("grassRegeneration"))
                .setStartEnergy(options.get("animalStartingEnergy"))
                .setGenomeLength(options.get("genomeLength"))
                .setFramePerSecond(options.get("framesPerSecond"))
                .setAnimalBuilder(animalBuilder)
        );
        this.simulation = simulation;
        Thread thread = new Thread(simulation);
        thread.start();
    }
    @FXML
    public void drawMap() {
        clearGrid();
        int tileWidth = (int) this.gridWidth / (this.height + 1);
        int tileHeight = tileWidth;
        mapGrid.addColumn(0);    //coordinates col
        mapGrid.getColumnConstraints().add(new ColumnConstraints(tileWidth));
        mapGrid.addRow(0);       //coordinates row
        mapGrid.getRowConstraints().add(new RowConstraints(tileHeight));
        Label zeroLabel = new Label("y\\x");
        mapGrid.add(zeroLabel, 0, 0);
        GridPane.setHalignment(zeroLabel, HPos.CENTER);
        for (int i = 0; i <= height; i++) {
            mapGrid.addColumn(i + 1);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(tileWidth));
            Label zeroXLabel = new Label(Integer.toString(i));
            mapGrid.add(zeroXLabel, i + 1, 0);
            GridPane.setHalignment(zeroXLabel, HPos.CENTER);
        }

        for (int i = height; i >= 0; i--) {
            mapGrid.addRow(height - i + 1);
            mapGrid.getRowConstraints().add(new RowConstraints(tileHeight));
            Label zeroYLabel = new Label(Integer.toString(i));
            mapGrid.add(zeroYLabel, 0, this.height - i + 1);
            GridPane.setHalignment(zeroYLabel, HPos.CENTER);
        }
        for (int i = 0; i <= this.height; i++) {
            for (int j = this.height; j >= 0; j--) {
                if (worldMap.isOccupied(new Vector2d(i, j))) {
                    Circle monster = worldMap.objectAt(new Vector2d(i, j)).toCircle();
                    monster.setRadius(monster.getRadius()*tileWidth/3);
                    mapGrid.add(monster,i + 1, this.height - j + 1);
                    GridPane.setHalignment(monster, HPos.CENTER);

//                    Label label = new Label(worldMap.objectAt(new Vector2d(i, j)).toString());
//                    mapGrid.add(label, i - minX + 1, maxY - j + 1);
//                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }
    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    @FXML
    private void updateUIData(){
        HashMap<String, String> data = simulationStatistics.getMapStatistics();
        avgKids.setText(data.get("averageNumberOfChildren"));
        avgLifetime.setText(data.get("averageLivedDays"));
        avgEnergy.setText(data.get("averageEnergy"));
        bestGen.setText(data.get("bestGenome"));
        freeSpace.setText(data.get("numberOfFreePositions"));
        numbOfGrass.setText(data.get("numberOfGrass"));
        numbOfAnimals.setText(data.get("numberOfAnimals"));
    }
    @FXML
    private void updateFollowData(){
        HashMap<String, String> data = followAnimal.getAnimalInfo();
        followGenome.setText(data.get("genome"));
        followEnergy.setText(data.get("energy"));
        eatenGrass.setText(data.get("grassEaten"));
        followChildren.setText(data.get("children"));
        followDescendants.setText(data.get("descendants"));
        followLifetime.setText(data.get("age"));
        followDeath.setText(data.get("dayOfDeath"));
    }
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
        Platform.runLater(this::updateUIData);
        if(followAnimal.isFollowing()){
            Platform.runLater(this::updateFollowData);
        }
        if(this.saveData){
            saveDayToData();
        }
    }
    @FXML
    private void stop(){
        if(!stopFlag){
            stopFlag = true;
            this.simulation.pause();
            stopButton.setText("Wznow");
//            setToTrack();
        }
        else{
            stopFlag = false;
            this.simulation.resume();
            stopButton.setText("Zatrzymaj");
        }
    }
    @FXML
    private void clickGrid(javafx.scene.input.MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (stopFlag && clickedNode != mapGrid) {
            // click on descendant node
            int colIndex = GridPane.getColumnIndex(clickedNode) - 1;
            int rowIndex = worldMap.getCurrentBounds().rightTop().getX() - GridPane.getRowIndex(clickedNode) + 1;
            followAnimal.startFollowing(colIndex,rowIndex);
            Platform.runLater(this::updateFollowData);
        }
    }
    @FXML
    private void showFields(){
        if(!isPrefFieldsShown) {
            this.isPrefFieldsShown = true;
            List<Vector2d> prefferedPositions = worldMap.getPreferredPositions();
            for (Vector2d field:prefferedPositions) {
                showPrefferedFields.setText("Pokaz");
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight((double) this.gridWidth/(this.height+1));
                rectangle.setWidth((double) this.gridWidth/(this.height+1));
                rectangle.setOpacity(0.5);
                rectangle.setFill(Color.LIGHTBLUE);
                mapGrid.add(rectangle,field.getX() + 1, this.height - field.getY() + 1);
            }
        }
        else{
            this.isPrefFieldsShown = false;
            showPrefferedFields.setText("Ukryj");
            drawMap();
        }
    }
    @FXML
    private void showStrongestAnimals(){
        if(!this.isShowStrongest){
            this.isShowStrongest = true;
            showStrongest.setText("Pokaz");
            List<Animal> strongestAnimals = worldMap.getAnimalsWithDominatingGenome();
            for(Animal animal:strongestAnimals){
                Circle circle = new Circle(this.gridWidth/(2*(this.height+1)));
                circle.setOpacity(0.5);
                circle.setFill(Color.BLUE);
                mapGrid.add(circle,animal.getPosition().getX() + 1, this.height - animal.getPosition().getY() + 1);
            }
        }
        else{
            this.isShowStrongest = false;
            showStrongest.setText("Ukryj");
            drawMap();
        }
    }
    public void setSimulationDataFile(String name){
        this.simulationDataFile = new File("simulation data", name +".csv");
        setCsvWriter(this.simulationDataFile);
    }
    public void setCsvWriter(File file){
        try {
            this.csvWriter = new FileWriter(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void saveDayToData(){
        try {
            if(!outcomeFilesInitialized){
                addSaveOutcomeFileColumns();
            }
            HashMap<String, String> data = simulationStatistics.getMapStatistics();
            for (String key : data.keySet()) {
                this.csvWriter.append(data.get(key)).append(" ");
            }
            this.csvWriter.append("\n");
            this.csvWriter.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void addSaveOutcomeFileColumns() throws IOException{
        try {
            for (String key : this.simulationStatistics.getMapStatistics().keySet()) {
                this.csvWriter.append(key).append(" ");
            }
            this.csvWriter.append("\n");
            this.outcomeFilesInitialized = true;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
