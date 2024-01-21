package agh.ics.oop.model;

import agh.ics.oop.model.util.GrassGenerator;

import java.math.BigInteger;
import java.util.*;

public class Globe implements WorldMap{
    private final Boundary boundary;
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d,Grass> grass = new HashMap<>();
    private final UUID id = UUID.randomUUID();
    private final Set<Vector2d> grassToEat = new HashSet<>();
    private final Set<Vector2d> animalsToProcreate = new HashSet<>();
    GrassGenerator grassGenerator;
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final HashMap<List<Integer>,Integer> countGenome = new HashMap<>();
    private final  Queue<Animal> recentlyDeadAnimals = new ArrayDeque<Animal>();
    Incubator incubator;
    int whichDay = 1;

    private BigInteger numberOfDeadAnimals = BigInteger.ZERO;
    private BigInteger deadAnimalsSumOfLivedDays = BigInteger.ZERO;

    public void notifyObservers(String description) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this,description);
        }
    }
    @Override
    public void addListener(MapChangeListener listener) {
        this.observers.add(listener);
    }

    public Globe(int width,int height,int startingGrass, int newGrass,GrassVariant grassVariant,int mutationVariant,AnimalBuilder animalConfiguration){
        incubator = new Incubator(mutationVariant,animalConfiguration);
        boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
        this.grassGenerator = new GrassGenerator(width + 1,height + 1,startingGrass,0.8,grassVariant,newGrass,this);
        for(var pos : grassGenerator){
            grass.put(pos,new Grass(pos));
        }
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
        Vector2d oldPosition = animal.getPosition();
        Vector2d newPosition = animal.forward();
        if(!insideMap(newPosition)){
            newPosition = wrap(newPosition);

            if(newPosition.equals(oldPosition))
                rotate(animal,4);

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
        grassGenerator.generateNewPositions();
        for(var pos : grassGenerator){
            grass.put(pos,new Grass(pos));
        }
    }

    public void update(){
        List<Animal> animalsToUpdate = getAllAnimals();
        List<Animal> aliveAnimals = new ArrayList<>();
        for (Animal animal : animalsToUpdate) {
            if (animal.isDead()) {
                deleteAnimal(animal);
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
        whichDay += 1;
    }

    public void addAnimal(Animal animal){
        place(animal);
        addGenome(animal.getGenome());
    }
    private void deleteAnimal(Animal animal){
        remove(animal);
        recentlyDeadAnimals.offer(animal);
        removeGenome(animal.getGenome());
        numberOfDeadAnimals = numberOfDeadAnimals.add(BigInteger.ONE);
        deadAnimalsSumOfLivedDays = deadAnimalsSumOfLivedDays.add(BigInteger.valueOf(animal.getAge()));
    }

    public Animal getAnimal(Vector2d position){
        if(!animals.containsKey(position)){
            return null;
        }
        return getStrongestAnimalsAtPosition(position).get(0);
    }
    public List<Animal> getAllAnimals(){
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

    public Map<Vector2d,Grass> getGrass(){
        return grass;
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

        for(var pos : animalsToProcreate){
            List<Animal> strongestAnimals = getStrongestAnimalsAtPosition(pos);
            if (strongestAnimals.size() < 2) {
                continue;
            }
            Animal animal1 = strongestAnimals.get(0);
            Animal animal2 = strongestAnimals.get(1);
            if(incubator.canProcreate(animal1,animal2)){
                Animal newAnimal = incubator.BornNewAnimal(animal1,animal2,whichDay);
                addAnimal(newAnimal);
            }
        }
        animalsToProcreate.clear();
    }
    public Double getAverageLifeSpan(){
        if(numberOfDeadAnimals.equals(BigInteger.ZERO)){
            return 0.0;
        }
        return deadAnimalsSumOfLivedDays.doubleValue() / numberOfDeadAnimals.doubleValue();
    }
    private void addGenome(List<Integer> genome){
        if(countGenome.containsKey(genome)){
            countGenome.put(genome,countGenome.get(genome) + 1);
        }
        else{
            countGenome.put(genome,1);
        }
    }
    private void removeGenome(List<Integer> genome){
        if(countGenome.containsKey(genome)){
            countGenome.put(genome,countGenome.get(genome) - 1);
        }
        if (countGenome.get(genome) == 0) {
            countGenome.remove(genome);
        }
    }
    public List<Integer> getMostPopularGenome() {
        if (countGenome.isEmpty()) {
            return null;
        }

        List<Integer> mostPopularGenome = null;
        int maxOccurrence = 0;

        for (Map.Entry<List<Integer>, Integer> entry : countGenome.entrySet()) {
            List<Integer> currentGenome = entry.getKey();
            int currentOccurrence = entry.getValue();

            if (currentOccurrence > maxOccurrence) {
                mostPopularGenome = currentGenome;
                maxOccurrence = currentOccurrence;
            }
        }

        return mostPopularGenome;
    }
    public List<Animal> getAnimalsWithDominatingGenome(){
        List<Animal> animalsWithDominatingGenome = new ArrayList<>();
        List<Integer> dominatingGenome = getMostPopularGenome();
        if(dominatingGenome == null){
            return animalsWithDominatingGenome;
        }
        for(Animal animal : getAllAnimals()){
            if(animal.getGenome().equals(dominatingGenome)){
                animalsWithDominatingGenome.add(animal);
            }
        }
        return animalsWithDominatingGenome;
    }
    public List<Vector2d> getPreferredPositions(){
        return grassGenerator.getPreferredPositions();
    }
    public List<Vector2d> getRecentlyDeadAnimalsPositions(){
        List<Vector2d> positions = new ArrayList<>();
        while (!recentlyDeadAnimals.isEmpty()){
            positions.add(recentlyDeadAnimals.poll().getPosition());
        }
        return positions;
    }

}
