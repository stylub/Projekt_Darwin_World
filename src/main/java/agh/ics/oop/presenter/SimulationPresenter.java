package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
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
    private WorldMap worldMap;
    @FXML
    private TextField movesTextField;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label printMove;
    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
    @FXML
    public void drawMap(){
        clearGrid();
        int maxX = worldMap.getCurrentBounds().rightTop().getX();
        int maxY = worldMap.getCurrentBounds().rightTop().getY();
        int minX = worldMap.getCurrentBounds().leftBottom().getX();
        int minY = worldMap.getCurrentBounds().leftBottom().getY();
        int tileWidth = Math.min((int) 300/maxX-minX+1, (int) 300/maxY-minY+1);
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
        String[] moves = movesTextField.getText().split(" ");
        List<Integer> intMoves = Arrays.stream(moves)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        this.worldMap = new Globe(10,10);
        worldMap.addListener(this);

        Simulation simulation = new Simulation(intMoves, List.of(new Vector2d(2,2),new Vector2d(4,4)), worldMap);
        List<Simulation> simulationList = new ArrayList<>();
        Thread thread = new Thread(simulation);
        thread.start();
    }
}
