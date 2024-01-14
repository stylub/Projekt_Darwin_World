package agh.ics.oop.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class Animal implements  WorldElement{
    private MapDirection direction = MapDirection.NORTH;
    public Vector2d position;
    public List<Integer> genome;
    public int procreationEnergy;
    public int energyFromGrass;
    public int fullEnergy;
    public int numberOfMutations;
    private int energy;
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
        return newPosition;
    }

    public void moveTo(Vector2d position){
        this.position = position;
    }
    public MapDirection getDirection(){
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }
}

