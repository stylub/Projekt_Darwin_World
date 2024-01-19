package agh.ics.oop.model;

import agh.ics.oop.model.util.GrassGenerator;

import java.math.BigInteger;
import java.util.*;

public class Globe implements WorldMap{
    private final Boundary boundary;
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d,Grass> grass = new HashMap<>();
    private final UUID id = UUID.randomUUID();
    private final int newGrass;
    private AnimalBuilder animalConfiguration;
    private final Set<Vector2d> grassToEat = new HashSet<>();
    private final Set<Vector2d> animalsToProcreate = new HashSet<>();
    GrassGenerator grassGenerator;
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final int grassVariant;
    private final int mutationVariant;

    private BigInteger numberOfDeadAnimals = BigInteger.ZERO;
    private BigInteger deadAnimalsSumOfLivedDays = BigInteger.ZERO;

    public void notifyObservers(String description) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this,description);
        }
    }

    public Globe(int width,int height,int startingGrass, int newGrass,int grassVariant,int mutationVariant){
        this.newGrass = newGrass;
        this.grassVariant = grassVariant;
        this.mutationVariant = mutationVariant;
        boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
        this.grassGenerator = new GrassGenerator(width + 1,height + 1,startingGrass,0.8);
        for(var pos : grassGenerator){
            grass.put(pos,new Grass(pos));
        }
    }
    public void setAnimalConfiguration(AnimalBuilder animalConfiguration){
        this.animalConfiguration = animalConfiguration;
    }

    @Override
    public void place(Animal animal){
        if(!insideMap(animal.getPosition())){
            throw new IllegalArgumentException("Animal is out of map");
        }
        if(animals.containsKey(animal.getPosition())){
            animals.get(animal.getPosition()).add(animal);
        }
        else{
            List<Animal> list = new ArrayList<>();
            list.add(animal);
            animals.put(animal.getPosition(),list);
        }
    }
    public void remove(Animal animal){
        List<Animal> animalsAtPosition = animals.get(animal.getPosition());
        if(animalsAtPosition == null){
            throw new IllegalArgumentException("There is no such animal on map");
        }
        animals.get(animal.getPosition()).remove(animal);

        if (animalsAtPosition.isEmpty()) {
            animals.remove(animal.getPosition());
        }
    }

    /**
     * rotates animal by multiple of 45 degrees angle.
     * @param directionChange specify how much animal will be rotated
     */
    public void rotate(Animal animal,int directionChange) {
        animal.rotate(directionChange);
    }

    public void rotate(Animal animal){
        animal.rotate();
    }

    /**
     * Moves animal forward in current direction of given animal
     * @param animal The animal to move forward
     */

    public void forward(Animal animal) {
        this.remove(animal);
        //Vector2d oldPosition = animal.getPosition();
        Vector2d newPosition = animal.forward();
        if(!insideMap(newPosition)){
            newPosition = wrap(newPosition);
//            if(newPosition.equals(oldPosition))
//                rotate(animal,4);
//            else
                animal.moveTo(newPosition);

        }
        this.place(animal);
    }

    public Vector2d wrap(Vector2d position){
        int x = position.getX();
        int y = position.getY();
        if(x < 0){
            x = boundary.rightTop().getX();
        }
        if(x > boundary.rightTop().getX()){
            x = 0;
        }
        if(y < 0){
            y +=1;
        }
        if(y > boundary.rightTop().getY()){
            y -=1;
        }
        return new Vector2d(x,y);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grass.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position){
        if(animals.containsKey(position)) {
            return getStrongestAnimalsAtPosition(position).get(0);
        }
        else return grass.getOrDefault(position, null);
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }

    @Override
    public void addListener(MapChangeListener listener) {
        this.observers.add(listener);
    }

    @Override
    public void removeListener(MapChangeListener listener) {
        this.observers.remove(listener);
    }

    @Override
    public UUID getId() {
        return id;
    }

    private boolean insideMap(Vector2d position){
        return boundary.rightTop().follows(position) && boundary.leftBottom().precedes(position);
    }

    public void growGrass(){
        grassGenerator.generateNewPositions(this.newGrass,grass);
        for(var pos : grassGenerator){
            grass.put(pos,new Grass(pos));
        }
    }

    public void update(){
        List<Animal> animalsToUpdate = getAllAnimals();
        List<Animal> aliveAnimals = new ArrayList<>();
        for (Animal animal : animalsToUpdate) {
            if (animal.isDead()) {
                remove(animal);
                numberOfDeadAnimals = numberOfDeadAnimals.add(BigInteger.ONE);
                deadAnimalsSumOfLivedDays = deadAnimalsSumOfLivedDays.add(BigInteger.valueOf(animal.getAge()));
            }
            else{
                aliveAnimals.add(animal);
            }
        }

        for (Animal animal : aliveAnimals) {
            rotate(animal);
            forward(animal);
            if(grass.containsKey(animal.getPosition())){
                grassToEat.add(animal.getPosition());
            }
            if(animals.containsKey(animal.getPosition()) && animals.get(animal.getPosition()).size() > 1){
                animalsToProcreate.add(animal.getPosition());
            }
        }
        eatGrass();
        procreate();
        growGrass();
        notifyObservers("Number of Animals now: " + getAllAnimals().size());
    }

    private List<Animal> getAllAnimals(){
        List<Animal> allAnimals = new ArrayList<>();
        for(List<Animal> list : animals.values()){
            allAnimals.addAll(list);
        }
        return allAnimals;
    }
    private List<Animal> getStrongestAnimalsAtPosition(Vector2d position){
        List<Animal> strongestAnimals = animals.get(position);
        strongestAnimals.sort(Collections.reverseOrder());
        return strongestAnimals;
    }

    private void eatGrass(){
        for(var pos : grassToEat){
            Animal strongestAnimal = getStrongestAnimalsAtPosition(pos).get(0);
            strongestAnimal.eatGrass();
            grass.remove(pos);
        }
        grassToEat.clear();
    }

    private void procreate(){
        if (animalsToProcreate.isEmpty()) {
            return;
        }
        NewAnimalCreator newAnimalCreator = new NewAnimalCreator(0,animalConfiguration);
        for(var pos : animalsToProcreate){
            List<Animal> strongestAnimals = getStrongestAnimalsAtPosition(pos);
            if (strongestAnimals.size() < 2) {
                continue;
            }
            Animal animal1 = strongestAnimals.get(0);
            Animal animal2 = strongestAnimals.get(1);
            if(newAnimalCreator.canProcreate(animal1,animal2)){
                Animal newAnimal = newAnimalCreator.BornNewAnimal(animal1,animal2);
                place(newAnimal);
            }
        }
        animalsToProcreate.clear();
    }
    public Integer getNumberOfAnimals(){
        return getAllAnimals().size();
    }
    public Integer getNumberOfGrass(){
        return grass.values().size();
    }
    public Double getAverageEnergy(){
        List<Animal> allAnimals = getAllAnimals();
        if(allAnimals.isEmpty()){
            return 0.0;
        }
        int sum = 0;
        for (Animal animal : allAnimals) {
            sum += animal.getEnergy();
        }
        return (double) sum / allAnimals.size();
    }
    public Integer getNumberOfFreePositions(){
        int freePositions = 0;
        int width = boundary.rightTop().getX();
        int height = boundary.rightTop().getY();
        for(int i = 0;i<=width;i++){
            for(int j = 0;j<=height;j++){
                Vector2d position = new Vector2d(i,j);
                if(!isOccupied(position)){
                    freePositions += 1;
                }
            }
        }
        return freePositions;
    }
    public Double getAverageNumberOfChildren(){
        List<Animal> allAnimals = getAllAnimals();
        if(allAnimals.isEmpty()){
            return 0.0;
        }
        int sum = 0;
        for (Animal animal : allAnimals) {
            sum += animal.getNumberOfChildren();
        }
        return (double)sum / allAnimals.size();
    }
    public HashMap<String,String> getMapStatistics(){
        HashMap<String,String> mapStatistics = new HashMap<>();
        mapStatistics.put("numberOfAnimals",getNumberOfAnimals().toString());
        mapStatistics.put("numberOfGrass",getNumberOfGrass().toString());
        mapStatistics.put("numberOfFreePositions",getNumberOfFreePositions().toString());
        mapStatistics.put("averageEnergy",getAverageEnergy().toString());
        mapStatistics.put("averageNumberOfChildren",getAverageNumberOfChildren().toString());
        Double averageLivedDays = 0.0;
        if(numberOfDeadAnimals.compareTo(BigInteger.ZERO) != 0){
            averageLivedDays = deadAnimalsSumOfLivedDays.divide(numberOfDeadAnimals).doubleValue();
        }
        mapStatistics.put("averageLivedDays",averageLivedDays.toString());
        return mapStatistics;
    }
}
