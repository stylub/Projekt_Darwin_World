package agh.ics.oop;

import agh.ics.oop.model.AnimalBuilder;
import agh.ics.oop.model.Globe;

public class simulationBuilder {
    public AnimalBuilder animalBuilder;
    Globe map;
    int staringAnimals;
    int startingGrass;
    int newGrass;
    int startEnergy;
    int GenomeLength;
    int framePerSecond = 2;

    public simulationBuilder setMap(Globe map) {
        this.map = map;
        return this;
    }

    public simulationBuilder setStaringAnimals(int staringAnimals) {
        this.staringAnimals = staringAnimals;
        return this;
    }

    public simulationBuilder setStartEnergy(int startEnergy) {
        this.startEnergy = startEnergy;
        return this;
    }

    public simulationBuilder setGenomeLength(int genomeLength) {
        GenomeLength = genomeLength;
        return this;
    }
    public simulationBuilder setStartingGrass(int startingGrass){
        this.startingGrass = startingGrass;
        return this;
    }
    public simulationBuilder setNewGrass(int newGrass){
        this.newGrass = newGrass;
        return this;
    }
    public simulationBuilder setFramePerSecond(int framePerSecond){
        this.framePerSecond = framePerSecond;
        return this;
    }
    public simulationBuilder setAnimalBuilder(AnimalBuilder animalBuilder){
        this.animalBuilder = animalBuilder;
        return this;
    }

    public Simulation build() {
        return new Simulation(this);
    }
}
