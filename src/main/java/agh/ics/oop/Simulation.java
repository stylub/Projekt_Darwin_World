package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable{
    List<Animal> animalList;
    List<Vector2d> toPlaceList;
    Globe map;
    int staringAnimals;
    AnimalBuilder animalBuilder;
    int timeBetweenFrames;
    boolean isRunning = true;

    public Simulation(simulationBuilder builder){
        this.map = builder.map;
        this.staringAnimals = builder.staringAnimals;
        this.animalList = new ArrayList<>();
        this.toPlaceList = new ArrayList<>();
        this.timeBetweenFrames = 1000 / builder.framePerSecond;
        this.animalBuilder = builder.animalBuilder;

        RandomPositionGenerator positionGenerator = new RandomPositionGenerator(map.getCurrentBounds(),staringAnimals);
        for(var pos : positionGenerator){
            toPlaceList.add(pos);
        }
    }
    @Override
    public void run(){
        initialize();
        while (true){
            if(isRunning) {
                map.update();
                try {
                    Thread.sleep(timeBetweenFrames);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("Simulation paused...");
                try {
                    // Sleep to reduce CPU usage
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    private void initialize(){
        for (Vector2d vector2d : toPlaceList) {
            Animal newAnimal = this.animalBuilder
                    .setPosition(vector2d)
                    .build();

            Random random = new Random();
            newAnimal.rotate(random.nextInt(8));
            map.addAnimal(newAnimal);
        }
    }
    public void pause(){
        isRunning = false;
    }
    public void resume(){
        isRunning = true;
    }
}

