package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable{
    private List<Vector2d> toPlaceList;
    private List<Animal> animalList;
    private List<MoveDirection> movesList;
    private WorldMap map;
    public Simulation(List<MoveDirection> movesList, List<Vector2d> vector2dList, WorldMap map){
        this.animalList = new ArrayList<>();
        this.toPlaceList = vector2dList;
        this.movesList = movesList;
        this.map = map;
    }
    @Override
    public void run(){
        for (int index = 0; index < toPlaceList.size(); index++) {
            try {
                animalList.add(new Animal(toPlaceList.get(index)));
                map.place(animalList.get(index));
            }
            catch(PositionAlreadyOccupiedException e){
                e.printStackTrace();        //continue without this animal
            }
        }

        if (!animalList.isEmpty()) {
            for (int index = 0; index < movesList.size(); index++) {
                map.move(animalList.get(index % animalList.size()), movesList.get(index));
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

    public List<MoveDirection> getMovesList() {
        return Collections.unmodifiableList(movesList);
    }
}
