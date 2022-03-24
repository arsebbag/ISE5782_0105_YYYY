package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

class PointTest {

    @Test
    void testAdd() {
    }

    @Test
    void testSubtract() {
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testDistance() {
        // Setup:
        Point p0 = new Point(0, 0, 1);
        Point p1 = new Point(0, 0, 2);
        Point n0 = new Point(0, 0, -1);
        Point n1 = new Point(0, 0, -2);
        // ##################### Equivalence Partitions #####################
        // TC01: calculate distance between positive numbers points.
        double distance = alignZero(p0.distance(p1));
        assertTrue(isZero(1 - distance), "TC01: Should have distance of 1.");
        // TC02: calculate distance between negative and positive point.
        distance = alignZero(p0.distance(n1));
        assertTrue(isZero(3 - distance), "TC02: Should have distance of 3.");
        // TC03: calculate distance between negative numbers points.
        distance = alignZero(n0.distance(n1));
        assertTrue(isZero(1 - distance), "TC03: Should have distance of 1.");
        // ##################### Boundary Values #####################
        // TC04: distance with ZERO.
        distance = alignZero(p0.distance(Point.ZERO));
        assertTrue(isZero(1 - distance), "TC04: Should have distance of 1.");
        // TC05: distance with equal point.
        distance = alignZero(p0.distance(p0));
        assertTrue(isZero(distance), "TC05: Should have distance of 0.");
    }
}