package renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import renderer.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Integration Test of the Camera Ray constructs function
 * {@link renderer.Camera#constructRay(int, int, int, int)} with
 * intersection finding capabilities of intersectable geometries
 * {@link geometries.Intersectable#findIntersections(Ray)}.
 * Camera Intersection integration test.
 */
public class CIIntegrationTests {

    /**
     * integration test of
     * {@link renderer.Camera#constructRay(int, int, int, int)} and
     * {@link geometries.Sphere#findIntersections(Ray)}
     */
    @Test
    public void testCameraAndSphere() {
        // setup constants
        double distance = 1d;
        double vpWidth = 3d;
        double vpHeight = 3d;

        // TC01: only center ray (pixel #5 intersect the sphere).
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        Vector camVTo = new Vector(0, 0, -1);
        Vector camVUp = new Vector(0, 1, 0);
        Camera camera = new Camera(Point.ZERO, camVTo, camVUp);
        camera.setVPDistance(distance);
        camera.setVPSize(vpHeight, vpWidth);

        int actualValue = findAllIntersections(camera, sphere);
        assertEquals(2, actualValue, "TC01: The Center Ray should intersect the sphere 2 times.");

        // TC02: all ray from camera through View Panel intersect twice.
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        camera = new Camera(new Point(0, 0, 0.5), camVTo, camVUp);
        camera.setVPDistance(distance);
        camera.setVPSize(vpHeight, vpWidth);

        actualValue = findAllIntersections(camera, sphere);
        assertEquals(18, actualValue, "TC02: All Rays should intersect the sphere 2 times.");

        // TC03: all Ray but Corner Rays intersect the Sphere.
        sphere = new Sphere(2, new Point(0, 0, -2));

        actualValue = findAllIntersections(camera, sphere);
        assertEquals(10, actualValue, "TC03: The non corner Rays should intersect the sphere 2 times.");

        // TC04: camera inside Sphere so only 9 intersection (1 for each pixel).
        sphere = new Sphere(4, new Point(0, 0, -2));

        actualValue = findAllIntersections(camera, sphere);
        assertEquals(9, actualValue, "TC04: All Rays should intersect the sphere one time only.");

        // TC05: no intersections because camera is after the Sphere.
        sphere = new Sphere(0.5, new Point(0, 0, 1));

        actualValue = findAllIntersections(camera, sphere);
        assertEquals(0, actualValue, "TC05: All rays should not intersect the sphere.");

    }

    /**
     * integration test of
     * {@link renderer.Camera#constructRay(int, int, int, int)} and
     * {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    public void testCameraAndPlane() {
        // setup constants
        double distance = 1d;
        double vpWidth = 3d;
        double vpHeight = 3d;
        Vector camVTo = new Vector(0, 0, -1);
        Vector camVUp = new Vector(0, 1, 0);
        Camera camera = new Camera(Point.ZERO, camVTo, camVUp);
        camera.setVPDistance(distance);
        camera.setVPSize(vpHeight, vpWidth);

        // TC01: plane is perpendicular to the Camera Vto Ray.
        Plane plane = new Plane(new Point(0, 0, -1), camVTo);
        int actualValue = findAllIntersections(camera, plane);
        assertEquals(9, actualValue, "TC01: All Rays should intersect the Plane one time only.");

        // TC02: Plane is diagonally aligned with camera but still all 9 pixels
        // intersects it.
        plane = new Plane(new Point(0, 0, -1), new Vector(0, 1, 5));

        actualValue = findAllIntersections(camera, plane);
        assertEquals(9, actualValue, "TC02: All Rays should intersect the Plane one time only.");

        // TC03: Plane is intersected by Rays in the first 2 rows (last row does not
        // intersect).
        plane = new Plane(new Point(0, 0, -2), new Vector(0, -1, 1));

        actualValue = findAllIntersections(camera, plane);
        assertEquals(6, actualValue, "TC03: Rays in first and second row should intersect the plane one time only.");
    }

    /**
     * integration test of
     * {@link renderer.Camera#constructRay(int, int, int, int)} and
     * {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    public void testCameraAndTriangle() {
        // setup constants
        double distance = 1d;
        double vpWidth = 3d;
        double vpHeight = 3d;
        Vector camVTo = new Vector(0, 0, -1);
        Vector camVUp = new Vector(0, 1, 0);
        Camera camera = new Camera(Point.ZERO, camVTo, camVUp);
        camera.setVPDistance(distance);
        camera.setVPSize(vpHeight, vpWidth);

        // TC01: Triangle should be intersected only by the center pixel.
        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        int actualValue = findAllIntersections(camera, triangle);
        assertEquals(1, actualValue, "TC01: Only the Center Ray should intersect the triangle once.");

        // TC02: long triangle but narrow base so only center and upper rays intersect
        // with it.
        triangle = new Triangle(
                new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));

        actualValue = findAllIntersections(camera, triangle);
        assertEquals(2, actualValue, "TC02: The center/upper Ray should intersect the triangle once.");
    }

    /**
     * A helper function to find all intersection point of camera view panel rays
     * and an intersectable shape
     *
     * @param camera Camera - a camera we want to find intersection with.
     * @param shape  Intersectable - a Geometry that has implement the Intersectable
     *               interface.
     * @return Int - total number of intersection points.
     */
    private int findAllIntersections(Camera camera, Intersectable shape) {
        int pixelNumberOfCols = 3;
        int pixelNumberOfRows = 3;
        double vpWidth = 3d;
        double vpHeight = 3d;

        int sumOfIntersection = 0;
        for (int i = 0; i < vpHeight; i++) {
            for (int j = 0; j < vpWidth; j++) {
                Ray cameraRay = camera.constructRay(pixelNumberOfCols, pixelNumberOfRows, j, i);
                List<Point> actualValue = shape.findIntersections(cameraRay);
                sumOfIntersection += actualValue == null ? 0 : actualValue.size();
            }
        }
        return sumOfIntersection;
    }
}
