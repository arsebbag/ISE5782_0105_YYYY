/**
 *
 */
package geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 *
 */
public class PolygonTest {

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     * This test does not try to check for rays that does not cross the Plane of the
     * polygon those cases are covered by the plane tests.
     */
    @Test
    public void testFindIntersections() {
        // setup
        Polygon pl = new Polygon(new Point(0, 0, 4), new Point(4, 0, 0), new Point(0, 4, 0),
                new Point(-4, 4, 4));
        Point p0 = new Point(-1, 0, 1);

        // ============ Equivalence Partitions Tests ==============
        // **** Group: Ray intersect the plane of the polygon:
        // TC01: Ray intersect the plane of the polygon inside the polygon
        Ray r1 = new Ray(p0, new Vector(1, 1, 0));

        List<Point> actualValue = pl.findIntersections(r1);
        assertEquals(1, actualValue.size(), "TC01: The ray should intersected the polygon once.");

        assertEquals(List.of(new Point(1, 2, 1)), actualValue, "TC01: Wrong values in the intersection point.");

        // TC02: Ray intersect the plane of the polygon outside the polygon(against
        // edge)
        assertNull(
                pl.findIntersections(new Ray(p0, new Vector(1, 1, 10))), "TC02: polygon should not intersected by Ray.");

        // TC03: Ray intersect the plane of the polygon outside the polygon(against
        // vertex)
        assertNull(pl.findIntersections(new Ray(p0, new Vector(1, -1, 4))),
                "TC02: polygon should not intersected by Ray.");
        // =============== Boundary Values Tests ==================
        // TC04: Ray intersect Polygon on edge
        Ray r2 = new Ray(p0, new Vector(-0.5, 1.5, 3));
        assertNull(pl.findIntersections(r2), "TC04: The ray should intersected the polygon once.");

        // TC05: Ray intersect Polygon on vertex
        assertNull(pl.findIntersections(new Ray(p0, new Vector(1, 0, 3))),
                "TC05: polygon should not intersected by Ray.");

        // TC06: Ray intersect on plane on line continuation of the Polygon edge
        assertNull(pl.findIntersections(new Ray(p0, new Vector(3, -2, 3))),
                "TC06: polygon should not intersected by Ray.");

    }
}
