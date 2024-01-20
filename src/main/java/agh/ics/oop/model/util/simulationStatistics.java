package agh.ics.oop.model.util;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Globe;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;
import java.util.List;

public class simulationStatistics {
    Globe globe;
    public simulationStatistics(Globe globe){
        this.globe = globe;
    }
    public Integer getNumberOfAnimals(){
        return globe.getAllAnimals().size();
    }
    public Integer getNumberOfGrass(){
        return globe.getGrass().size();
    }
    public Integer getNumberOfFreePositions(){
        int freePositions = 0;
        Boundary boundary = globe.getCurrentBounds();
        int width = boundary.getWidth();
        int height = boundary.getHeight();
        for(int i = 0;i<=width;i++){
            for(int j = 0;j<=height;j++){
                Vector2d position = new Vector2d(i,j);
                if(!globe.isOccupied(position)){
                    freePositions += 1;
                }
            }
        }
        return freePositions;
    }
    public Double getAverageEnergy(){
        List<Animal> allAnimals = globe.getAllAnimals();
        if(allAnimals.isEmpty()){
            return 0.0;
        }
        int sum = 0;
        for (Animal animal : allAnimals) {
            sum += animal.getEnergy();
        }
        return (double) sum / allAnimals.size();
    }

    public Double getAverageNumberOfChildren(){
        List<Animal> allAnimals = globe.getAllAnimals();
        if(allAnimals.isEmpty()){
            return 0.0;
        }
        int sum = 0;
        for (Animal animal : allAnimals) {
            sum += animal.getNumberOfChildren();
        }
        return (double)sum / allAnimals.size();
    }
    public HashMap<String,String> getMapStatistics(){
        HashMap<String,String> mapStatistics = new HashMap<>();
        mapStatistics.put("numberOfAnimals",getNumberOfAnimals().toString());
        mapStatistics.put("numberOfGrass",getNumberOfGrass().toString());
        mapStatistics.put("numberOfFreePositions",getNumberOfFreePositions().toString());
        mapStatistics.put("averageEnergy",getAverageEnergy().toString());
        mapStatistics.put("averageNumberOfChildren",getAverageNumberOfChildren().toString());
        mapStatistics.put("averageLivedDays", globe.getAverageLifeSpan().toString());
        return mapStatistics;
    }
}
