package geometries;


import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Class SphereTests is a JUnit class that test {@link geometries.Sphere} Sphere class in geometries for correct implementation.
     * @author Ariel sebbag
     *
     */

        /**
         * Test method for {@link geometries.Sphere# Sphere(double, Point)}.
         */
        @Test
        public void testSphere() {
            fail("Not yet implemented");
        }

        /**
         * Test method for {@link geometries.Sphere#get_center()}.
         */
        @Test
        public void testGet_center() {
            fail("Not yet implemented");
        }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sphere = new Sphere(5, new Point(0, 0, 1));
        Point p = new Point(0, 0, 6);

        Vector expected = new Vector(0, 0, 1);
        Vector actualValue = p.subtract(sphere.get_center()).normalize();

        assertEquals(expected, actualValue, "Bad normal to Sphere");
    }


}
