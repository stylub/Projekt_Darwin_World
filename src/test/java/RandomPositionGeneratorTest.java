import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.util.RandomPositionGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomPositionGeneratorTest {

    @Test
    void testIterator() {
        RandomPositionGenerator generator = new RandomPositionGenerator(5, 5, 10);

        Set<Vector2d> generatedPositions = new HashSet<>();
        for (Vector2d position : generator) {
            generatedPositions.add(position);
        }

        // Check if all generated positions are unique
        assertEquals(10, generatedPositions.size());
        assertThrows(NoSuchElementException.class, generator::next);
    }

    @Test
    void testHasNextAndNext() {
        RandomPositionGenerator generator = new RandomPositionGenerator(5, 5, 10);

        for (int i = 0; i < 10; i++) {
            assertTrue(generator.hasNext());
            assertNotNull(generator.next());
        }

        assertFalse(generator.hasNext());
    }

    @Test
    void testEmptyGenerator() {
        RandomPositionGenerator generator = new RandomPositionGenerator(5, 5, 0);

        assertFalse(generator.hasNext());
        assertThrows(NoSuchElementException.class, generator::next);
    }
}
