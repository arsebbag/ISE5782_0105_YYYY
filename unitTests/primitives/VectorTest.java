package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {

    /**
     * Test method for {@link primitives.Vector# Vector(primitives.Coordinate, primitive.Coordinate, primitives.Coordinate)}.
     */
    @Test
    public void testVectorCoordinateCoordinateCoordinate() {
        // =============== Boundary Values Tests ==================
        //TC01: CTOR that receive 3 coordinates
        try {
            Vector v = new Vector(0.0,0.0,0.0);
            fail("constructed a ZERO Vector");
        }catch(IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link primitives.Vector# Vector(primitives.Double3)}.
     */
    @Test
    public void testVectorPoint3D() {
        // =============== Boundary Values Tests ==================
        //TC01:
        try {
            Vector v1 = new Vector(Double3.ZERO);
            fail("constructed a ZERO Vector");
        }catch(IllegalArgumentException e) {}
    }

    /**
     * This test is for checking that the getter for head field working properly.
     */
    @Test
    public void testGetHead() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct head point3D of Vector is returned via Getter.
        Point expected = new Point(1.0, 1.0, 1.0);
        Vector vector = new Vector(1.0, 1.0, 1.0);
        Point actualValue = vector.getHead();

        assertEquals(expected, actualValue, "Should give back head: (1,1,1)");
    }

    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: correct test for add.
        Vector v1 = new Vector(1.0, 3.5, 4.0);
        Vector v2 = new Vector(1.0, 2.5, 3.0);
        Vector v_add1 = v2.add(v1);
        Vector temp = new Vector(2.0,6.0,7.0);

        assertEquals(temp , v_add1, "TC01: add() wrong result");////new Vector(3.0,7.0,8.0)
        // =============== Boundary Values Tests ==================
        //TC02: wrong add must throw an exception.
        try {

            Vector v3 = v2.add(new Vector(-1.0, -2.5, -3.0));
            fail("TC02: Didn't throw, Problem - zero vector");

        }catch(IllegalArgumentException e)
        {
            System.out.println("TC02: " + e.getMessage());
            assertTrue(true);//???
        }
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {

        //TC01: correct test for scale
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        double scalar = 3;
        Vector v_scale = v1.scale(scalar);
        //scale() wrong result
        assertEquals(v_scale, new Vector(3.0,6.0,9.0), "TC02: wrong - problem zero Vector");

        // =============== Boundary Values Tests ==================
        //
        try {
            v1 = v1.scale(0.0);
            fail("Didn't throw, Problem - zero vector");
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }

    }

    @Test
    public void testDotProduct() {

        Vector v1 = new Vector(4.0, 2.0, 5.0);
        Vector v2 = new Vector(2.5, 8.0, 3.0);
        double num_dot = v1.dotProduct(v2);
        double temp = 41;

        assertEquals(num_dot, temp, 0.00001, "dotProduct() wrong result");// resolution

        //fail("Not yet implemented");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)

        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
        double sqLengthExpected = 100.0;
        Vector v1 = new Vector(Math.sqrt(3d), 4.0, 9.0);
        // ============ Equivalence Partitions Tests ==============
        //TC01: test if the Squared distance of a Vector is correct.
        assertEquals(sqLengthExpected, v1.lengthSquared(), 0.0000001, "The Result is not the squared length of the Vector. ");
        //fail("Not yet implemented");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void testLength() {
        double LengthExpected = 10.0;
        Vector v1 = new Vector(Math.sqrt(3d), 4.0, 9.0);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(LengthExpected , v1.length(), 0.0001, "The Result is not the length of the Vector. ");

    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Testing Normalize
        Vector vector = new Vector(1, 2, 3);
        Vector vectorCopy = new Vector(vector.getHead());
        Vector normalizeVectorCopy = vectorCopy.normalize();

        //assertTrue(vectorCopy.equals(normalizeVectorCopy), "normalize() function should not create a new vector");

        boolean unitVectorLengthOK = isZero(normalizeVectorCopy.length() - 1);
        assertTrue(unitVectorLengthOK, "normalize() result is not a unit vector");

    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    public void testNormalized() {

        Vector v1 = new Vector(2.5, 4.75, 8.0);
        Vector unitVector = v1.normalized();
        assertTrue(unitVector.length() == 1 && v1.length() > 1, " The new Vector isn't normalized or old Vector is normalized. ");
    }

    /**
     * Test method for {@link primitives.Vector#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        Vector v1 = new Vector(2.5, 4.75, 8.0);
        Vector v2 = new Vector(2.5000, 4.75000, 8.0000);
        assertEquals(v1, v2, "The 2 Vectors are not equals.");
    }

    @Test
    void testSumProd() {
    }
}