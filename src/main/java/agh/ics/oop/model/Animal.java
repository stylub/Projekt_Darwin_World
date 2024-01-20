package agh.ics.oop.model;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class Animal implements  WorldElement, Comparable<Animal>{
    private final UUID id = UUID.randomUUID();
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private final List<Integer> genome;
    private final AnimalBuilder animalConfiguration;
    private int energy;
    private int numberOfChildren = 0;
    private int age = 0;
    private int numberOfGrassEaten = 0;
    private int bornDay = 0;
    private int activeGene;
    Parents parents = null;
    HashSet<Animal> descendants = new HashSet<>();
    public Animal(AnimalBuilder builder){
        this.animalConfiguration = builder;
        this.bornDay = builder.bornDay;
        this.position = builder.position;
        this.energy = builder.energy;
        this.parents = new Parents(builder.parent1,builder.parent2);
        parents.noticeOfChild(this,parents);
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

    public void rotate(){
        direction = direction.rotate(genome.get(getActiveGene()));
        activeGene++;
    }
    public int getActiveGene(){
        activeGene = activeGene % genome.size();
        return activeGene;
    }
    public void rotate(int rotation){
        direction = direction.rotate(rotation);
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
    public int getBornDay(){
        return bornDay;
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
        this.energy += this.animalConfiguration.energyFromGrass;
        this.numberOfGrassEaten += 1;
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
        return this.energy >= this.animalConfiguration.fullEnergy;
    }
    public int procreate(){
        this.energy -= this.animalConfiguration.procreationEnergy;
        this.numberOfChildren += 1;
        return this.animalConfiguration.procreationEnergy;
    }

    public void addDescendant(Animal descendant){
        descendants.add(descendant);
    }
    public int getNumberOfDescendants(){
        return descendants.size();
    }
    public List<Integer> getGenome(){
        return genome;
    }

    public int getNumberOfGrassEaten(){
        return numberOfGrassEaten;
    }

    private Parents parents(){
        return parents;
    }

   private class Parents {
       Animal parent1;
       Animal parent2;

       public Parents(Animal parent1, Animal parent2) {
           this.parent1 = parent1;
           this.parent2 = parent2;
       }

       public Animal getParent1() {
           return parent1;
       }

       public Animal getParent2() {
           return parent2;
       }

       public void noticeOfChild(Animal childrenInQuestion, Parents parents){
            Animal parent1 = parents.getParent1();
            Animal parent2 = parents.getParent2();

             if(parent1 != null && !parent1.isDead()){
                 parent1.addDescendant(childrenInQuestion);
                 noticeOfChild(childrenInQuestion,parent1.parents());
              }
              if(parent2 != null && !parent2.isDead()){
                parent2.addDescendant(childrenInQuestion);
                noticeOfChild(childrenInQuestion,parent2.parents());
              }
       }
   }
}




