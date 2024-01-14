package agh.ics.oop;

import agh.ics.oop.model.Globe;

public class simulationBuilder {
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

    public simulationBuilder setMap(Globe map) {
        this.map = map;
        return this;
    }

    public simulationBuilder setHowLong(int howLong) {
        this.howLong = howLong;
        return this;
    }

    public simulationBuilder setStaringAnimals(int staringAnimals) {
        this.staringAnimals = staringAnimals;
        return this;
    }

    public simulationBuilder setStartingGrass(int startingGrass) {
        this.startingGrass = startingGrass;
        return this;
    }

    public simulationBuilder setNewGrass(int newGrass) {
        this.newGrass = newGrass;
        return this;
    }

    public simulationBuilder setStartEnergy(int startEnergy) {
        this.startEnergy = startEnergy;
        return this;
    }

    public simulationBuilder setEnergyFromGrass(int energyFromGrass) {
        this.energyFromGrass = energyFromGrass;
        return this;
    }

    public simulationBuilder setFullEnergy(int fullEnergy) {
        this.fullEnergy = fullEnergy;
        return this;
    }

    public simulationBuilder setProcreationEnergy(int procreationEnergy) {
        this.procreationEnergy = procreationEnergy;
        return this;
    }

    public simulationBuilder setNumberOfMutations(int numberOfMutations) {
        this.numberOfMutations = numberOfMutations;
        return this;
    }

    public simulationBuilder setGenomeLength(int genomeLength) {
        GenomeLength = genomeLength;
        return this;
    }

    public Simulation build() {
        return new Simulation(this);
    }
}
