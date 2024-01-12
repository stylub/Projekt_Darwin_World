import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;


import static org.testng.AssertJUnit.assertTrue;


public class GrassFieldTest {
    @Test
    public void test() throws PositionAlreadyOccupiedException {
        GrassField map = new GrassField(10);
        ConsoleMapDisplay listener = new ConsoleMapDisplay();
        map.addListener(listener);
        Animal a = new Animal();
        map.place(a);
        map.move(a, MoveDirection.RIGHT);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.LEFT);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        System.out.println();
        assertTrue(a.getPosition().equals(new Vector2d(3,11)));
    }
}
