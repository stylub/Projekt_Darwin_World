import agh.ics.oop.model.Grass;
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

        GrassGenerator grassGenerator = new GrassGenerator(maxWidth, maxHeight, grassCount, ratio);
        Set<Vector2d> generatedPositions = new HashSet<>();
        for (Vector2d position : grassGenerator) {
            generatedPositions.add(position);
        }

        // Check if all generated positions are unique
        assertEquals(grassCount, generatedPositions.size());
        assertThrows(NoSuchElementException.class, grassGenerator::next);
    }


}
