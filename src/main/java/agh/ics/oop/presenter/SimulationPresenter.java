package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.simulationBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationPresenter implements MapChangeListener {
    private Globe worldMap;
    @FXML
    private TextField movesTextField;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label printMove;
    public void setWorldMap(Globe worldMap) {
        this.worldMap = worldMap;
    }


    @FXML
    public void drawMap(){
        clearGrid();
        int maxX = worldMap.getCurrentBounds().rightTop().getX();
        int maxY = worldMap.getCurrentBounds().rightTop().getY();
        int minX = worldMap.getCurrentBounds().leftBottom().getX();
        int minY = worldMap.getCurrentBounds().leftBottom().getY();
        int tileWidth = Math.min((int) 600/maxX-minX+1, (int) 600/maxY-minY+1);
        int tileHeight = tileWidth;



        mapGrid.addColumn(0);    //coordinates col
        mapGrid.getColumnConstraints().add(new ColumnConstraints(tileWidth));
        mapGrid.addRow(0);       //coordinates row
        mapGrid.getRowConstraints().add(new RowConstraints(tileHeight));
        Label zeroLabel = new Label("y\\x");
        mapGrid.add(zeroLabel,0,0);
        GridPane.setHalignment(zeroLabel, HPos.CENTER);
        for(int i = minX; i <= maxX; i++){
            mapGrid.addColumn(i-minX+1);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(tileWidth));
            Label zeroXLabel = new Label(Integer.toString(i));
            mapGrid.add(zeroXLabel,i-minX+1,0);
            GridPane.setHalignment(zeroXLabel, HPos.CENTER);
        }

        for(int i = maxY; i >= minY; i--){
            mapGrid.addRow(maxY-i+1);
            mapGrid.getRowConstraints().add(new RowConstraints(tileHeight));
            Label zeroYLabel = new Label(Integer.toString(i));
            mapGrid.add(zeroYLabel,0,maxY-i+1);
            GridPane.setHalignment(zeroYLabel, HPos.CENTER);
        }
        for(int i = minX; i <= maxX; i++){
            for(int j = maxY; j >= minY; j--){
                if(worldMap.isOccupied(new Vector2d(i,j))){
                    Label label = new Label(worldMap.objectAt(new Vector2d(i,j)).toString());
                    mapGrid.add(label,i-minX+1,maxY-j+1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }
    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            printMove.setText(message);
            drawMap();
        });
    }
    public void onSimulationStartClicked() throws InterruptedException {
//        String[] moves = movesTextField.getText().split(" ");
//        List<Integer> intMoves = Arrays.stream(moves)
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());

        this.worldMap = new Globe(10,10,5,5);
        worldMap.addListener(this);


        Simulation simulation = new Simulation(new simulationBuilder()
                .setMap(worldMap)
                .setHowLong(100)
                .setStaringAnimals(3)
                .setStartingGrass(5)
                .setNewGrass(5)
                .setStartEnergy(5)
                .setEnergyFromGrass(5)
                .setFullEnergy(5)
                .setProcreationEnergy(5)
                .setNumberOfMutations(5)
                .setGenomeLength(5)
        );

        List<Simulation> simulationList = new ArrayList<>();
        Thread thread = new Thread(simulation);
        thread.start();
    }
}
