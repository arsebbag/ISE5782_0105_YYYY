package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // setup
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
        Vector xAxisPositiveDir = new Vector(1, 0, 0); // unit vector in the positive x axis for creating Rays.
        Vector xAxisNegativeDir = xAxisPositiveDir.scale(-1d); // unit vector in the negative x axis for
        // creating Rays.
        Point p0 = new Point(-1, 0, 0); // used many time in code so creating it once for optimize test.
        // p1 and p2 for EP TC02
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        Point p3 = new Point(0.2, 0.2, 0.2); // for EP TC03
        Point p4 = new Point(1.9591663046625438, 0.2, 0.2); // for EP TC03
        Point p5 = new Point(0.5, 0.5, 1 / Math.sqrt(2)); // for BVA TC11 and TC12 and EP TC04
        Point p6 = new Point(1.5, 0.5, 1 / Math.sqrt(2)); // for BVA TC11
        Point p7 = new Point(2, 0, 0);
        Point p8 = new Point(0.5, 0, 0);
        Point p9 = new Point(3, 0, 0); // optimize code create the point only once for BVA TC12 and TC18
        Point p10 = new Point(1, 0, 1); // optimize code create the point only once for BVA TC19, TC20 and
        // TC21
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p0, new Vector(1, 1, 1))),
                "TC01: Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        List<Point> result = sphere.findIntersections(new Ray(p0, new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "TC02: Wrong number of points");
        if (result.get(0).get_x() > result.get(1).get_x())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "TC02: Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(p3, xAxisPositiveDir));
        assertEquals(1, result.size(), "Number of Intersection Points should be 1.");
        assertEquals(List.of(p4), result, "Ray should cross the sphere only once");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 1),
                new Vector(0, 1, 1))), "Number of Intersection Points should be null.");
        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(p5, xAxisPositiveDir));
        assertEquals(1, result.size(), "TC11: Number of Intersection Points should be 1.");
        assertEquals(List.of(p6), result, "TC11: Ray should cross the sphere only once");

        // TC12: Ray starts at sphere and goes outside (0 points)
        // scale with -1 to make ray go in the direction of the negative X axis.
        assertNull(sphere.findIntersections(new Ray(p5, xAxisNegativeDir)),
                "TC12: Number of Intersection Points should be null.");
        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(p0, xAxisPositiveDir));
        assertEquals(2, result.size(), "TC13: Number of intersection points should be 2.");
        if (result.get(0).get_x() > result.get(1).get_x())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(Point.ZERO, p7), result, "TC13: Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(p7, xAxisNegativeDir));
        assertEquals(1, result.size(), "TC14: Number of Intersection Points should be 1.");
        assertEquals(List.of(Point.ZERO), result, "TC14: Ray should cross the sphere only once");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(p8, xAxisPositiveDir));
        assertEquals(1, result.size(), "TC15: Number of Intersection Points should be 1.");
        assertEquals(List.of(p7), result, "TC15: Ray crosses sphere");

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), xAxisPositiveDir));
        assertEquals(1, result.size(), "TC16: Number of Intersection Points should be 1.");
        assertEquals(List.of(p7), result, "TC16: Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p7, xAxisPositiveDir)),
                "TC17: Number of Intersection Points should be null.");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p9, xAxisPositiveDir)),
                "TC18: Number of Intersection Points should be null.");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(p10.add(xAxisNegativeDir), xAxisPositiveDir)),
                "TC19: Number of Intersection Points should be null.");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(p10, xAxisPositiveDir)),
                "TC20: Number of Intersection Points should be null.");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(p10.add(xAxisPositiveDir), xAxisPositiveDir)),
                "TC21: Number of Intersection Points should be null.");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull(sphere.findIntersections(new Ray(p10.add(new Vector(0, 0, 1)), xAxisPositiveDir)),
                "TC22: Number of Intersection Points should be null.");
    }

}
