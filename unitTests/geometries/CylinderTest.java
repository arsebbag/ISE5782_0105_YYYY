package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Class CylinderTests is a JUnit class that test {@link geometries.Cylinder} Cylinder class in geometries for correct implementation.
 * @author Ariel sebbag
 *
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder# getNormal(primitives.Double3)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test getNormal of Cylinder (not in bases).
        // setup
        Point p = new Point(-5, 0, 4.32);
        Point p0 = new Point(0, 0, 1);
        Vector dirVector = new Vector(0, 0, 1);
        Ray axisRay = new Ray(p0, dirVector);
        Cylinder cylinder = new Cylinder(axisRay, 5, 25);

        // calculate the o point.
        Vector vector = p.subtract(cylinder.get_axisRay().get_p0());
        double t = cylinder.get_axisRay().get_dir().dotProduct(vector);
        Point o = cylinder.get_axisRay().get_p0().add(cylinder.get_axisRay().get_dir().scale(t));

        // test for normal
        Vector normal = p.subtract(o).normalize(); // new Vector (x,y,z)

        Vector expected = new Vector(-1, 0, 0);
        assertEquals(expected, normal, "normal was not correct.");

        // TC02: Test getNormal of Cylinder in first base. - not sure is ok
        // two point that are on the same plane of the "circle" base in on.
        Point firstPoint = new Point(p0.get_x(), p0.get_y() + cylinder._radius, p0.get_z());
        Point secondPoint = new Point(p0.get_x() + cylinder._radius, p0.get_y(), p0.get_z());

        Vector v1 = firstPoint.subtract(p0);
        Vector v2 = secondPoint.subtract(p0);

        Vector crossVector = v1.crossProduct(v2).normalize();

        boolean normalIsOk = crossVector.equals(cylinder.get_axisRay().get_dir())
                || crossVector.equals(cylinder.get_axisRay().get_dir().scale(-1d));

        assertTrue(normalIsOk, "bad normal to first base of Cylinder");

        // TC03: Test getNormal of Cylinder in second base.
        // two point that are on the same plane of the "circle" base in on.
        firstPoint = new Point(p0.get_x(), p0.get_y() + cylinder._radius, p0.get_z() + cylinder._height);
        secondPoint = new Point(p0.get_x() + cylinder._radius, p0.get_y(), p0.get_z() + cylinder._height);

        // p1 is the center of the second circular "base".
        Point p1 = p0.add(cylinder.get_axisRay().get_dir().scale(cylinder._height));
        v1 = firstPoint.subtract(p1);
        v2 = secondPoint.subtract(p1);

        crossVector = v1.crossProduct(v2).normalize();

        normalIsOk = crossVector.equals(cylinder.get_axisRay().get_dir())
                || crossVector.equals(cylinder.get_axisRay().get_dir().scale(-1d));

        assertTrue(normalIsOk, "bad normal to second base of Cylinder");

        // =============== Boundary Values Tests ==================
        // TC04: the "corner" of the first base and the side of the cylinder

        // TC05: the "corner" of the second base and the side of the cylinder

        // TC04: the center of the first base and the side of the cylinder

        // TC05: the center of the second base and the side of the cylinder
    }
}