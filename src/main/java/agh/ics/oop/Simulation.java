package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable{
    private final List<Vector2d> toPlaceList;
    private final List<Animal> animalList;
    private final List<Integer> movesList;
    private final WorldMap map;
    public Simulation(List<Integer> movesList, List<Vector2d> vector2dList, WorldMap map){
        this.animalList = new ArrayList<>();
        this.toPlaceList = vector2dList;
        this.movesList = movesList;
        this.map = map;
    }
    @Override
    public void run(){
        for (int index = 0; index < toPlaceList.size(); index++) {
            animalList.add(new Animal(toPlaceList.get(index)));
            map.place(animalList.get(index));
        }

        if (!animalList.isEmpty()) {
            for (int index = 0; index < movesList.size(); index++) {
                map.rotate(animalList.get(index % animalList.size()), movesList.get(index));
                map.forward(animalList.get(index % animalList.size()));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<Animal> getAnimalList() {
        return Collections.unmodifiableList(animalList);
    }

}
