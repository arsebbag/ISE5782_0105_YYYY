package geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing eometries.
 *
 * @author SHAI FALACH and RON HAIM HODADEDI
 */
public class GeometriesTests {
    /**
     * Test method for {@link geometries.Geometries} Constructor.
     */
    @Test
    public void testFindIntersections() {
        // setup
        Ray axisRay = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
        Vector unitVectorXAxis = new Vector(1, 0, 0);
        Tube tube = new Tube(new Ray(new Point(0, 0, 50), unitVectorXAxis), 5);
        Plane plane = new Plane(new Point(0, 0, 30), axisRay.get_dir());
        Cylinder cylinder = new Cylinder(axisRay, 5, 25);
        Triangle triangle = new Triangle(new Point(0, 20, 6), new Point(0, -20, 6), new Point(10, 0, 10));
        Sphere sphere = new Sphere(5, new Point(0, 0, 10));
        Polygon polygon = new Polygon(new Point(0, -5, 5), new Point(5, -5, 0), new Point(0, 5, 0),
                new Point(-5, 5, 5));
        Geometries geom = new Geometries(plane, polygon, sphere, triangle);
        Geometries empty = new Geometries();

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is some intersections (not all shapes intersected).
        Ray intersectingRay = new Ray(new Point(-5, -5, 10), new Vector(15, 5, -10));
        List<Point> actualValue = geom.findIntersections(intersectingRay);
        assertEquals(3, actualValue.size(), "There should be 3 intersections with our shapes.");

        // =============== Boundary Values Tests ==================
        // TC02: There is no intersections (no shapes).
        actualValue = empty.findIntersections(axisRay);
        assertNull(actualValue, "There should not be any intersections without shapes");

        // TC03: There is no intersections (with shapes).
        actualValue = geom.findIntersections(new Ray(new Point(0, 0, -1), unitVectorXAxis));
        assertNull(actualValue, "There should not be any intersections with our shapes.");

        // TC04: There is only one intersection.
        intersectingRay = new Ray(new Point(0, 0, 2), unitVectorXAxis);
        actualValue = geom.findIntersections(intersectingRay);
        assertEquals(1, actualValue.size(), "There should not be any intersections with our shapes.");

        // TC05: There is intersections with all shapes.
        intersectingRay = new Ray(new Point(2, 0, 0), axisRay.get_dir());
        actualValue = geom.findIntersections(intersectingRay);
        assertEquals(5, actualValue.size(), "There should be 5 intersections with our shapes.");
    }

}
