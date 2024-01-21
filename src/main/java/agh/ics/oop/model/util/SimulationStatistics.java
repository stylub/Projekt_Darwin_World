package agh.ics.oop.model.util;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Globe;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationStatistics {
    private Globe globe;
    public SimulationStatistics(Globe globe){
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
        mapStatistics.put("averageEnergy",String.format("%.2f",getAverageEnergy()));
        mapStatistics.put("averageNumberOfChildren",String.format("%.2f", getAverageNumberOfChildren()));
        mapStatistics.put("averageLivedDays", String.format("%.2f",globe.getAverageLifeSpan()));
        mapStatistics.put("bestGenome", globe.getMostPopularGenome().stream().map(gen -> gen.toString()).collect(Collectors.joining()));
        return mapStatistics;
    }
}
