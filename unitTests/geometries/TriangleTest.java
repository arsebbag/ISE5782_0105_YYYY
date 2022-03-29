package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {


    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3),
                triangle.getNormal(new Point(0, 0, 1)), "Bad normal to triangle");
    }

    /**
     * Test method for
     * {@link geometries.Triangle#findIntersections(primitives.Ray)}. This test does
     * not try to check for rays that does not cross the Plane of the triangle those
     * cases are covered by the plane tests.
     */
    @Test
    public void testFindIntersections() {
        // setup
        Point p0 = new Point(2, 0, 2);
        Point t1 = new Point(-5, 2, 1);
        Point t2 = new Point(5, 2, 1);
        Point t3 = new Point(0, 6, 6);
        Triangle triangle = new Triangle(t1, t2, t3);

        // ============ Equivalence Partitions Tests ==============
        // **** Group: Ray intersect the plane of the triangle:
        // TC01: Ray intersect the plane of the triangle inside the triangle
        Ray r1 = new Ray(p0, new Vector(0, 1, 0));

        List<Point> actualValue = triangle.findIntersections(r1);
        assertEquals(1, actualValue.size(), "TC01: The ray should intersected the triangle once.");

        assertEquals(List.of(new Point(2, 2.8, 2)),
                actualValue, "TC01: Wrong values in the intersection point.");

        // TC02: Ray intersect the plane of the triangle outside the triangle(against
        // edge)
        assertNull(triangle.findIntersections(new Ray(p0, new Vector(1, 1, 0))),
                "TC02: triangle should not intersected by Ray.");

        // TC03: Ray intersect the plane of the triangle outside the triangle(against
        // vertex)
        assertNull(triangle.findIntersections(new Ray(p0, new Vector(6, 1.6, 0.5))) ,
                "TC02: triangle should not intersected by Ray.");
        // =============== Boundary Values Tests ==================
        // TC04: Ray intersect Triangle on edge
        Ray r2 = new Ray(p0, new Vector(0.5, 4, 1.5));
        assertNull(triangle.findIntersections(r2), "TC04: The ray should intersected the triangle once.");

        // TC05: Ray intersect Triangle on vertex
        assertNull(triangle.findIntersections(new Ray(p0, new Vector(3, 2, -1))),
                "TC05: triangle should not intersected by Ray.");

        // TC06: Ray intersect on plane on line continuation of the Triangle edge
        assertNull(triangle.findIntersections(new Ray(p0, new Vector(4, 2, -1))),
                "TC06: triangle should not intersected by Ray.");

    }
}