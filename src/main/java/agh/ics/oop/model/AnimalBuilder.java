package agh.ics.oop.model;

import agh.ics.oop.simulationBuilder;

import java.util.List;

public class AnimalBuilder {
    public MapDirection direction;
    public Vector2d position;
    public List<Integer> genome;
    public int energy;
    public int procreationEnergy;
    public int energyFromGrass;
    public int fullEnergy;
    public int numberOfMutations;
    public int genomeLength;

    public AnimalBuilder setPosition(Vector2d position) {
        this.position = position;
        return this;
    }
    public AnimalBuilder setGenome(List<Integer> genome) {
        this.genome = genome;
        return this;
    }

    public AnimalBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }
    public AnimalBuilder setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
        return this;
    }
    public AnimalBuilder setProcreationEnergy(int procreationEnergy) {
        this.procreationEnergy = procreationEnergy;
        return this;
    }
    public AnimalBuilder setEnergyFromGrass(int energyFromGrass) {
        this.energyFromGrass = energyFromGrass;
        return this;
    }
    public AnimalBuilder setFullEnergy(int fullEnergy) {
        this.fullEnergy = fullEnergy;
        return this;
    }
    public AnimalBuilder setNumberOfMutations(int numberOfMutations) {
        this.numberOfMutations = numberOfMutations;
        return this;
    }
    public Animal build() {
        return new Animal(this);
    }


}
