package geometries;

import geometries.Sphere;
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
            //TC01:
            Sphere sp = new Sphere(1,new Point(0,0,1));
            assertNotEquals(new Vector(0,0,1),sp.getNormal(new Point(0,1,1)));
            System.out.println(sp.getNormal(new Point(0,1,1)));

            //TC02:

            Point o = new Point (1.0,1.0,0.0); // o = center point of sphere
            Sphere sphere1  = new Sphere (1, o);

            Point p = new Point(1.0, 0.0,1.0);

            Vector normal = (o.subtract(p)).normalize();
        }


}
