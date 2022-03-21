package geometries;

import org.junit.jupiter.api.Test;
import geometries.Plane;
//import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
        * Class PlaneTests is a JUnit class that test {@link geometries.Plane} Plane class in geometries for correct implementation.
        * @author Ariel sebbag
        *
        */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane# Plane(primitives.Double3, primitives.Point3D, primitives.Point3D)}.
     * cross product and subtract already tested in VectorTest.
     */
    @Test
    public void testConstructors()
    {
        //TC01: test a CTOR of Plane that receive 3 points 3D.
        try {
            Plane instance = new Plane(new Point(0.0, 1.0, 1.0), new Point(0.0, 0.0, 1.0),
                    new Point(0.0, 1.0, 0.0));

        }catch (IllegalArgumentException e){
            System.out.println(  "TC01: " + e.getMessage());
            fail("TC01: Failed constructing a correct Plane");
        }

        //TC02: wrong can not have origin point as one of the points in the plane - must throw an exception.

        try {
            Plane instance = new Plane(new Point(0, 0, 0), new Point(0, 0, 0),
                    new Point(0, 0, 0));
            fail("TC02: Constructed a Plane with wrong order of vertices");
        }catch (IllegalArgumentException e){
            System.out.println(  "TC02: " + e.getMessage());
        }

        //TC03: test a CTOR of Plane that receive one Point3D and a Normal.

        try { //
            Plane instance2 = new Plane(new Point(0, 0, 1), new Vector(1,1,0) );

        }catch (IllegalArgumentException e){
            e.getMessage();
            fail("TC03: Failed constructing a correct Plane");
        }

        //TC 04: test a CTOR of Plane that receive one Point3D and a Normal

        Plane instance3 = new Plane(new Point(0, 0, 1), new Vector(1,1,0) );

        if (instance3.getNormal().dotProduct(new Vector(0,0,1)) != 0 )
            fail("TC04: The normal or the point are Wrong ");
    }

    /**
     * Test method for {@link geometries.Plane#get_p()}.
     */
    @Test
    public void testGet_p() {
        Plane instance = new Plane(new Point(0, 0, 1), new Point(1, 0, 0),
                new Point(0, 1, 0));
        Point expected = new Point(0,0,1);
        Point actual = instance.get_p();
        assertEquals(expected, actual, "Expected for P0:(0,0,1) from the constructor to be plane P0.");
    }

    /**
     * Test method for {@link geometries.Plane# getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormalPoint3D() {

        //TC01: test getNormal with param.
        Plane p_instance = new Plane(new Point(0.0, 1.0, 1.0), new Point(0.0, 0.0, 1.0),
                new Point(0.0, 1.0, 0.0));
        Point point_test = new Point(0.0,0.0,1.0);

        Vector expected = new Vector(1.0,0.0,0.0);// normal
        assertEquals(expected, p_instance.getNormal(point_test), "The Normal is False ");
        assertTrue(((p_instance.getNormal(point_test)).dotProduct(new Vector(0.0,1.0,1.0))) == 0, "The dotProduct with the normal didn't return true ");//,expected.normalize(), p_instance.getNormal(point_test));

        //System.out.println(p_instance.getNormal());
    }

    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    public void testGetNormal() {

        //TC01: test getNormal without param.
        Plane p_instance = new Plane(new Point(0.0, 1.0, 1.0), new Point(0.0, 0.0, 1.0),
                new Point(0.0, 1.0, 0.0));
        //Point3D point_test = new Point3D()
        Vector expected = new Vector(1.0,0.0,0.0);
        assertEquals(expected ,(p_instance.getNormal()), "TC01: ");//((p_instance.getNormal()).dotProduct(expected)
        //assertTrue("TC01: ", ((p_instance.getNormal()).dotProduct(expected)) == 0);
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersection() {
        // setup
        Point q0 = new Point(1, 0, 1);
        Vector dir = new Vector(q0);
        Plane plane = new Plane(q0, dir);
        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(1.5, 0, 0.5);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is before and do not cross the plane (0 points)
        assertNull(plane.findIntersections(new Ray(p1, dir.scale(-1d))), "TC01: Plane should not intersected by Ray.");

        // TC02: Ray starts before and crosses the plane (1 point)
        Ray r1 = new Ray(p1, dir);

        List<Point> actualValue = plane.findIntersections(r1);
        assertEquals(1, actualValue.size(), "TC02: The ray should intersected the Plane once.");

        assertEquals(List.of(p2), actualValue, "TC02: Wrong values in the intersection point.");

        // TC03: Ray starts on the plane (0 points)
        assertNull(plane.findIntersections(new Ray(p2, dir)), "TC03: Plane should not intersected by Ray.");

        // TC04: Ray starts after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(2, 0, 1), dir)),
                "TC04: Plane should not intersected by Ray.");

        // =============== Boundary Values Tests ==================

        // TC05: ray starts before Plane and go through the plane at the q0 point.
        Point p3 = new Point(-2, 0, -2);
        Ray r2 = new Ray(p3, dir);
        actualValue = plane.findIntersections(r2);
        assertEquals(1, actualValue.size(),"TC05: The ray should intersected the Plane once.");

        assertEquals(List.of(q0), actualValue, "TC05: Wrong values in the intersection point.");

        // TC06: ray starts at the Plane at the q0 point.
        assertNull(plane.findIntersections(new Ray(q0, dir)), "TC06: Plane should not intersected by Ray.");

        // **** Group: Ray's line is orthogonal to the plane Normal
        Vector v1 = new Vector(0, 1, 0);
        // TC07: Ray start before the Plane (0 points)
        assertNull(plane.findIntersections(new Ray(p3, v1)), "TC07: Plane should not intersected by Ray.");

        // TC08: Ray start on the Plane (0 points)
        assertNull(plane.findIntersections(new Ray(p2, v1)), "TC08: Plane should not intersected by Ray.");

        // **** Group: Special cases
    }
}