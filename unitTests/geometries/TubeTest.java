package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import primitives.*;

/**
        * Class TubeTests is a JUnit class that test {@link geometries.Tube} Tube class in geometries for correct implementation.
        * @author Ariel sebbag
        */
public class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test getNormal of Tube.
        // setup
        Point p = new Point(-5, 0, 4.32);
        Point p0 = new Point(0, 0, 1);
        Vector dirVector = new Vector(0, 0, 1);
        Ray axisRay = new Ray(p0, dirVector);
        Tube tube = new Tube(axisRay, 5);

        // calculate the o point.
        Vector vector = p.subtract(tube.get_axisRay().get_p0());
        double t = tube.get_axisRay().get_dir().dotProduct(vector);
        Point o = tube.get_axisRay().get_p0().add(tube.get_axisRay().get_dir().scale(t));

        // test for normal
        Vector actual = p.subtract(o).normalize();

        Vector expected = new Vector(-1, 0, 0);
        assertEquals(expected, actual, "normal was not correct.");
    }


}