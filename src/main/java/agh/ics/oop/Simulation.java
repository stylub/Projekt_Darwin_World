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
    int startingGrass;
    int newGrass;
    int startEnergy;
    int energyFromGrass;
    int fullEnergy;
    int procreationEnergy;
    int numberOfMutations;
    int GenomeLength;
    public Simulation(simulationBuilder builder){
        this.map = builder.map;
        this.howLong = builder.howLong;
        this.staringAnimals = builder.staringAnimals;
        this.startingGrass = builder.startingGrass;
        this.animalList = new ArrayList<>();
        this.toPlaceList = new ArrayList<>();
        this.newGrass = builder.newGrass;
        this.startEnergy = builder.startEnergy;
        this.energyFromGrass = builder.energyFromGrass;
        this.fullEnergy = builder.fullEnergy;
        this.procreationEnergy = builder.procreationEnergy;
        this.numberOfMutations = builder.numberOfMutations;
        this.GenomeLength = builder.GenomeLength;

        RandomPositionGenerator positionGenerator = new RandomPositionGenerator(map.getCurrentBounds(),staringAnimals);
        for(var pos : positionGenerator){
            toPlaceList.add(pos);
        }
    }
    @Override
    public void run(){
        for (int index = 0; index < toPlaceList.size(); index++) {
            Animal newAnimal = new AnimalBuilder()
                    .setEnergy(startEnergy)
                    .setGenomeLength(GenomeLength)
                    .setProcreationEnergy(procreationEnergy)
                    .setEnergyFromGrass(energyFromGrass)
                    .setFullEnergy(fullEnergy)
                    .setNumberOfMutations(numberOfMutations)
                    .setPosition(toPlaceList.get(index))
                    .build();

            Random random = new Random();
            newAnimal.rotate(random.nextInt(8));
            animalList.add(newAnimal);
            map.place(animalList.get(index));
        }

        for(int i =0;i<howLong;i++){
            map.update();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

