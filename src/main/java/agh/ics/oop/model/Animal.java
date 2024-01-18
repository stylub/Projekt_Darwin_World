package agh.ics.oop.model;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class Animal implements  WorldElement, Comparable<Animal>{
    private final UUID id = UUID.randomUUID();
    private MapDirection direction = MapDirection.NORTH;
    public Vector2d position;
    public List<Integer> genome;
    public int procreationEnergy;
    public int energyFromGrass;
    public int fullEnergy;
    public int numberOfMutations;
    private int energy;
    private int numberOfChildren = 0;
    private int numberOfDescendants = 0;
    private int age = 0;
    private int activeGene;
    public Animal(AnimalBuilder builder){
        this.position = builder.position;
        this.energy = builder.energy;
        this.fullEnergy = builder.fullEnergy;
        this.procreationEnergy = builder.procreationEnergy;
        this.energyFromGrass = builder.energyFromGrass;
        this.numberOfMutations = builder.numberOfMutations;

        if (builder.genome == null) {
            Random random = new Random();
            this.genome = new ArrayList<>();
            for (int i = 0; i < builder.genomeLength; i++) {
                this.genome.add(random.nextInt(8));
            }
        } else {
            this.genome = builder.genome;
        }
    }
    @Override
    public String toString() {
        return switch (direction) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }

    public boolean isAt(Vector2d position) {
        return  this.position.equals(position);
    }

    public void rotate(int directionChange){
        for(int i =0;i<directionChange;i++)
            direction = direction.next();
    }
    public void rotate(){
        activeGene = activeGene % genome.size();
        rotate(genome.get(activeGene));
        activeGene++;
    }
    public Vector2d forward(){
        Vector2d newPosition = position.add(direction.toUnitVector());
        this.position = newPosition;
        energy -= 1;
        age += 1;
        return newPosition;
    }

    public void moveTo(Vector2d position){
        this.position = position;
    }
    public MapDirection getDirection(){
        return direction;
    }
    public int getEnergy(){
        return energy;
    }
    public int getAge(){
        return age;
    }
    public int getNumberOfChildren(){
        return numberOfChildren;
    }
    public Vector2d getPosition() {
        return position;
    }

    public Boolean isDead(){
        return energy <= 0;
    }
    public void eatGrass(){
        this.energy += this.energyFromGrass;
    }
    public UUID getId(){
        return id;
    }

    @Override
    public int compareTo(@NotNull Animal o) {
        if(Integer.compare(this.energy,o.energy) == 0){
            if(Integer.compare(this.age,o.age) == 0){
                if (Integer.compare(this.numberOfChildren,o.numberOfChildren) == 0){
                    return this.id.compareTo(o.id);
                }
                else
                    return Integer.compare(this.numberOfChildren,o.numberOfChildren);
            }
            else
                return Integer.compare(this.age,o.age);

        }
        else
            return Integer.compare(this.energy,o.energy);
    }
    public Boolean isFull(){
        return this.energy >= this.fullEnergy;
    }
    public int procreate(){
        this.energy -= procreationEnergy;
        this.numberOfChildren += 1;
        return procreationEnergy;
    }

    public void addDescendant(){
        this.numberOfDescendants += 1;
    }
    public int getNumberOfDescendants(){
        return numberOfDescendants;
    }
    public List<Integer> getGenome(){
        return genome;
    }

}

