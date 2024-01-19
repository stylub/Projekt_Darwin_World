package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;

/**
 * This class is responsible for creating new animals from two parents
 * It also checks if two animals can procreate
 */
public class Incubator {

    int mutationVariant;
    int genomeLength;
    int numberOfMutations;
    AnimalBuilder animalConfiguration;
    Random random = new Random();
    /**
     * @param mutationVariant
     * 0 - random mutations only;
     * 1 - mutation can cause the genes to swap places;
     */
    public Incubator(int mutationVariant,AnimalBuilder animalConfiguration){
        this.mutationVariant = mutationVariant;
        this.animalConfiguration = animalConfiguration;
        this.genomeLength = animalConfiguration.genomeLength;
        this.numberOfMutations = animalConfiguration.numberOfMutations;
    }

    public Boolean canProcreate(Animal animal1, Animal animal2){
        return animal1.isFull()  && animal2.isFull() &&
                animal1.getPosition().equals(animal2.getPosition()) &&
                animal1.getEnergy()>animalConfiguration.procreationEnergy &&
                animal2.getEnergy()>animalConfiguration.procreationEnergy;
    }
    public Animal BornNewAnimal(Animal animal1,Animal animal2){
        int startEnergy = animal1.procreate() + animal2.procreate();
        Vector2d position = animal1.getPosition();
        List<Integer> genome = getNewGenome(animal1,animal2);
        return animalConfiguration
                .setEnergy(startEnergy)
                .setGenome(genome)
                .setPosition(position)
                .build();
    }

    public List<Integer> getNewGenome(Animal animal1, Animal animal2){
        if(Math.random() > 0.5){
            Animal temp = animal1;
            animal1 = animal2;
            animal2 = temp;
        }

        List<Integer> firstGenome = animal1.getGenome();
        List<Integer> secondGenome = animal2.getGenome();
        List<Integer> newGenome =  new ArrayList<>();
        if(firstGenome.size() != secondGenome.size())
            throw new IllegalArgumentException("Genomes are not the same size");
        double energyRatio = ((double) animal1.getEnergy()) / (animal2.getEnergy() + animal1.getEnergy());


        int firstGenomeIndex = min((int) (genomeLength * energyRatio),genomeLength);

        for(int i = 0; i < firstGenomeIndex; i++){
            newGenome.add(firstGenome.get(i));
        }
        for(int i = firstGenomeIndex; i < genomeLength; i++){
            newGenome.add(secondGenome.get(i));
        }
        mutate(newGenome);
        return newGenome;
    }

    private void mutate(List<Integer> genome){
        if(mutationVariant == 0)
            randomMutate(genome,numberOfMutations);
        else
        {
            int randomMutations = random.nextInt(numberOfMutations);
            randomMutate(genome, randomMutations);
            swapMutate(genome,numberOfMutations - randomMutations);
        }
    }

    private void randomMutate(List<Integer> genome, int numberOfMutations){
        for(int i = 0; i < numberOfMutations; i++){
            int index = random.nextInt(genomeLength);
            genome.set(index, (random.nextInt(8)));
        }
    }
    private void swapMutate(List<Integer> genome, int numberOfMutations){

        for(int i = 0; i < numberOfMutations; i++){
            int index1 = random.nextInt(genomeLength);
            int index2 = random.nextInt(genomeLength);
            int temp = genome.get(index1);
            genome.set(index1,genome.get(index2));
            genome.set(index2,temp);
        }
    }


}
