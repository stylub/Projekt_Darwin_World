import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


public class Vector2dTest {
    private Vector2d vector1 = new Vector2d(1,2);
    private Vector2d vector2 = new Vector2d(1,2);
    private Vector2d vector3 = new Vector2d(3,-1);
    private Vector2d vector4 = new Vector2d(4,5);
    @Test
    public void equalsMethodTest(){
        System.out.println("Test metody equals");
        Assert.assertTrue(vector1.equals(vector2));
        Assert.assertTrue(vector2.equals(vector1));
        Assert.assertFalse(vector1.equals(vector3));
    }
    @Test
    public void toStringMethodTest(){
        Assert.assertEquals(vector1.toString(), "(1,2)");
        Assert.assertEquals(vector3.toString(), "(3,-1)");
    }
    @Test
    public void precedesMethodTest(){
        Assert.assertFalse(vector1.precedes(vector3));
        Assert.assertTrue(vector3.precedes(vector4));
    }
    @Test
    public void followsMethodTest(){

        Assert.assertFalse(vector1.follows(vector3));
        Assert.assertTrue(vector4.follows(vector3));
    }
    @Test
    public void upperRightMethodTest(){
        Assert.assertEquals(vector1.upperRight(vector3), new Vector2d(3,2));
        Assert.assertEquals(vector4.upperRight(vector3), new Vector2d(4,5));
    }
    @Test
    public void lowerLeftMethodTest(){
        Assert.assertEquals(vector1.lowerLeft(vector3), new Vector2d(1,-1));
        Assert.assertEquals(vector4.lowerLeft(vector3), new Vector2d(3,-1));
    }
    @Test
    public void addMethodTest(){
        Assert.assertEquals(vector1.add(vector3), new Vector2d(4,1));
        Assert.assertEquals(vector4.add(vector3), new Vector2d(7,4));
    }
    @Test
    public void subtractMethodTest(){
        Assert.assertEquals(vector1.subtract(vector3), new Vector2d(-2,3));
        Assert.assertEquals(vector4.subtract(vector3), new Vector2d(1,6));
    }
    @Test
    public void oppositeMethodTest(){
        Assert.assertEquals(vector1.opposite(), new Vector2d(-1,-2));
        Assert.assertEquals(vector3.opposite(), new Vector2d(-3,1));
    }
}
