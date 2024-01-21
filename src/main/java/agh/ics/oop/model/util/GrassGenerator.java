package agh.ics.oop.model.util;

import agh.ics.oop.model.Globe;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.GrassVariant;
import agh.ics.oop.model.Vector2d;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GrassGenerator extends RandomPositionGenerator{
    private final HashSet<Vector2d> preferredPositions = new HashSet<>();
    long seed = System.nanoTime();
    private final double ratio;
    private final GrassVariant grassVariant;
    private final int newGrass;
    private final Globe globe;
    private final int numberOfPreferredPositions;
    private final Queue<Vector2d> fertilizedPositions = new ArrayDeque<>();
    private final int maxNumberOfFertilizedPositions;
    public GrassGenerator(int maxWidth, int maxHeight, int grassCount, double ratio, GrassVariant grassVariant, int newGrass, Globe globe) {
        super(maxWidth, maxHeight);
        this.grassVariant = grassVariant;
        this.newGrass = newGrass;
        this.globe = globe;
        this.maxNumberOfFertilizedPositions = (int) (maxWidth * maxHeight * (1 - ratio)) + 2;
        grassCount = min(grassCount,maxWidth * maxHeight);
        this.ratio = ratio;
        numberOfPreferredPositions = (maxWidth * maxHeight) - (int) (ratio * maxWidth * maxHeight);

        initializePreferredPositions();

        List<Vector2d> preferred = new ArrayList<>();
        List<Vector2d> notPreferred = new ArrayList<>();
        for (int i = 0; i < maxWidth; i++) {
            for(int j = 0; j < maxHeight; j++){
                if(preferredPositions.contains(new Vector2d(i,j))) {
                    preferred.add(new Vector2d(i, j));
                }
                else{
                    notPreferred.add(new Vector2d(i, j));
                }
            }
        }

        Collections.shuffle(preferred, new Random(seed));
        Collections.shuffle(notPreferred, new Random(seed));
        int numberOfPreferredToGet = min(preferred.size(),(int) (grassCount * ratio));
        positions = preferred.subList(0,numberOfPreferredToGet);
        positions.addAll(notPreferred.subList(0,grassCount - numberOfPreferredToGet));
    }

    private void initializePreferredPositions(){
        switch (grassVariant) {
            case EQUATORIAL -> preferredEquatorialPositions();
            case NEAR_CORPSES -> preferredNearCorpsesPositions();
        }
    }

    private void preferredEquatorialPositions(){
        int middle = (maxHeight/2);
        int leftMost = middle - 1;
        int rightMost = middle + 1;

        int count = min(maxWidth,numberOfPreferredPositions);
        addLine(middle,count);
        while (count < numberOfPreferredPositions) {
            int numberOfNewPositions = min(maxWidth,numberOfPreferredPositions - count);
            if(middle - leftMost < rightMost - middle) {
                addLine(leftMost,numberOfNewPositions);
                leftMost -=1;
            }
            else{
                addLine(rightMost,numberOfNewPositions);
                rightMost +=1;
            }
            count += numberOfNewPositions;
        }
    }
    private void preferredNearCorpsesPositions(){
        addFertilizedPositions();
    }

    private void addFertilizedPositions(){
        List<Vector2d> newPositions = globe.getRecentlyDeadAnimalsPositions();
        for(Vector2d position : newPositions){
            fertilizedPositions.offer(position);
            fertilize(position);
        }

        while (fertilizedPositions.size() > maxNumberOfFertilizedPositions){
            unfertilize(Objects.requireNonNull(fertilizedPositions.poll()));
        }
    }

    private void fertilize(Vector2d position){
        int left = max(position.getX() - 1,0);
        int right = min(position.getX() + 1,maxWidth);
        int top = max(position.getY() + 1,maxHeight);
        int bottom = min(position.getY() - 1,0);

        for(int i = left; i <= right; i++){
            for(int j = bottom; j <= top; j++){
                preferredPositions.add(new Vector2d(i,j));
            }
        }
    }

    private void unfertilize(Vector2d position){
        int left = max(position.getX() - 1,0);
        int right = min(position.getX() + 1,maxWidth);
        int top = max(position.getY() + 1,maxHeight);
        int bottom = min(position.getY() - 1,0);

        for(int i = left; i <= right; i++){
            for(int j = bottom; j <= top; j++){
                preferredPositions.remove(new Vector2d(i,j));
            }
        }
    }

    private void addLine(int idx, int number){
        List<Vector2d> allPositions = new ArrayList<>();
        for(int i = 0; i < maxWidth; i++){
            allPositions.add(new Vector2d(i,idx));
        }
        Collections.shuffle(allPositions, new Random(seed));
        allPositions = allPositions.subList(0,number);
        preferredPositions.addAll(allPositions);
    }

    public void generateNewPositions() {
        if(grassVariant == GrassVariant.NEAR_CORPSES){
            addFertilizedPositions();
        }

        Map<Vector2d,Grass> currentGrass = globe.getGrass();
        currentIndex = 0;
        List<Vector2d> preferred = new ArrayList<>();
        List<Vector2d> notPreferred = new ArrayList<>();
        for (int i = 0; i < maxWidth; i++) {
            for(int j = 0; j < maxHeight; j++){
                if(!currentGrass.containsKey(new Vector2d(i,j))){
                    if (preferredPositions.contains(new Vector2d(i, j))) {
                        preferred.add(new Vector2d(i, j));
                    } else {
                        notPreferred.add(new Vector2d(i, j));
                    }
                }
            }
        }

        int grassCount = min(this.newGrass,preferred.size() + notPreferred.size());

        Collections.shuffle(preferred, new Random(seed));
        Collections.shuffle(notPreferred, new Random(seed));
        int numberOfPreferredToGet = min(preferred.size(),(int) (grassCount * ratio));
        positions = preferred.subList(0,numberOfPreferredToGet);
        positions.addAll(notPreferred.subList(0,grassCount - numberOfPreferredToGet));
    }

    public List<Vector2d> getPreferredPositions() {
        return new ArrayList<>(preferredPositions);
    }
}
