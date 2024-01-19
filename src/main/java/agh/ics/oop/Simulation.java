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
    int howLong;
    int staringAnimals;
    AnimalBuilder animalBuilder;
    int timeBetweenFrames;
    public Simulation(simulationBuilder builder){
        this.map = builder.map;
        this.howLong = builder.howLong;
        this.staringAnimals = builder.staringAnimals;
        this.animalList = new ArrayList<>();
        this.toPlaceList = new ArrayList<>();
        this.timeBetweenFrames = 1000 / builder.framePerSecond;

        RandomPositionGenerator positionGenerator = new RandomPositionGenerator(map.getCurrentBounds(),staringAnimals);
        for(var pos : positionGenerator){
            toPlaceList.add(pos);
        }

        animalBuilder = new AnimalBuilder()
                .setEnergy(builder.startEnergy)
                .setGenomeLength(builder.GenomeLength)
                .setProcreationEnergy(builder.procreationEnergy)
                .setEnergyFromGrass(builder.energyFromGrass)
                .setFullEnergy(builder.fullEnergy)
                .setNumberOfMutations(builder.numberOfMutations);

        this.map.setAnimalConfiguration(animalBuilder);
    }
    @Override
    public void run(){
        initialize();
        for(int i =0;i<howLong;i++){
            map.update();
            try {
                Thread.sleep(timeBetweenFrames);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
            map.place(newAnimal);
        }
    }
}

