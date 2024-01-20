import agh.ics.oop.model.Globe;
import agh.ics.oop.model.GrassVariant;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.util.GrassGenerator;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// TO DO: Add tests for other methods
class GrassGeneratorTest {
    @Test
    void testIterator() {
        int maxWidth = 5;
        int maxHeight = 5;
        int grassCount = 5;
        double ratio = 0.3;
        GrassVariant grassVariant = GrassVariant.EQUATORIAL;
        int newGrass = 1;

        GrassGenerator grassGenerator = new GrassGenerator(maxWidth, maxHeight, grassCount, ratio, grassVariant, newGrass,new Globe(maxWidth,maxHeight,grassCount,newGrass,grassVariant,1,null));
        Set<Vector2d> generatedPositions = new HashSet<>();
        for (Vector2d position : grassGenerator) {
            generatedPositions.add(position);
        }

        assertEquals(grassCount, generatedPositions.size());
        assertThrows(NoSuchElementException.class, grassGenerator::next);
    }


}
