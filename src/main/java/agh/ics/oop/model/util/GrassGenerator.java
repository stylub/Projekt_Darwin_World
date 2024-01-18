package agh.ics.oop.model.util;

import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;

import java.util.*;

import static java.lang.Math.min;

public class GrassGenerator extends RandomPositionGenerator{
    private HashSet<Vector2d> preferredPositions = new HashSet<>();
    long seed = System.nanoTime();
    private final double ratio;
    public GrassGenerator(int maxWidth, int maxHeight, int grassCount, double ratio) {
        super(maxWidth, maxHeight);

        grassCount = min(grassCount,maxWidth * maxHeight);
        this.ratio = ratio;

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
        int middle = (maxHeight/2) - 1;
        int leftMost = middle - 1;
        int rightMost = middle + 1;
        int numberOfPreferredPositions =  (maxWidth * maxHeight) - (int) (ratio * maxWidth * maxHeight);

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

    private void addLine(int idx, int number){
        List<Vector2d> allPositions = new ArrayList<>();
        for(int i = 0; i < maxWidth; i++){
            allPositions.add(new Vector2d(i,idx));
        }
        Collections.shuffle(allPositions, new Random(seed));
        allPositions = allPositions.subList(0,number);
        preferredPositions.addAll(allPositions);
    }

    public void generateNewPositions(int grassCount, Map<Vector2d, Grass> currentGrass) {
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

        grassCount = min(grassCount,preferred.size() + notPreferred.size());

        Collections.shuffle(preferred, new Random(seed));
        Collections.shuffle(notPreferred, new Random(seed));
        int numberOfPreferredToGet = min(preferred.size(),(int) (grassCount * ratio));
        positions = preferred.subList(0,numberOfPreferredToGet);
        positions.addAll(notPreferred.subList(0,grassCount - numberOfPreferredToGet));
    }
    public List<Vector2d> getPositions(){
        return positions;
    }
}
