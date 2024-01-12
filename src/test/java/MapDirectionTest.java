import agh.ics.oop.model.MapDirection;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


public class MapDirectionTest {
    @Test
    public void nextMethodTest(){
        Assert.assertEquals(MapDirection.NORTH.next(), MapDirection.EAST);
        Assert.assertEquals(MapDirection.EAST.next(), MapDirection.SOUTH);
        Assert.assertEquals(MapDirection.SOUTH.next(), MapDirection.WEST);
        Assert.assertEquals(MapDirection.WEST.next(), MapDirection.NORTH);
    }
    @Test
    public void previousMethodTest(){
        Assert.assertEquals(MapDirection.NORTH.previous(), MapDirection.WEST);
        Assert.assertEquals(MapDirection.EAST.previous(), MapDirection.NORTH);
        Assert.assertEquals(MapDirection.SOUTH.previous(), MapDirection.EAST);
        Assert.assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTH);
    }
}
