package renderer;

import elements.AmbientLight;
import primitives.*;

import scene.*;

import java.util.List;

import static primitives.Util.*;

/**
 * class used to trace rays for the rendering engine
 */
public class RayTracerBasic extends RayTracer {

    /**
     * how many reflections we want to calculate - The maximum value of the tree height (number of intersections) for reflection/refraction calculations for a geometry the camera sees.
     * Stop condition for transparency:
     * how far we consider to calculate the ray's intersections with
     * other geometries which are behind original geometry.
     * Stop condition for refraction:
     * how far we consider to calculate the intersections of the rays
     * which created by refractions in the original geometric body.
     * <p>
     * USE WITH CAUTION! higher values leads to performance decreasing rapidly
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10; // 10

    /**
     * the minimum value of transparency/refraction to be considered if the calculation
     * for reflection/refraction is necessary in the current geometry.
     */
    private static final double MIN_CALC_COLOR_K = 0.001; // 0.001


    //
    /**
     * 0 leads to no reflections at all, but no significant difference with any other value
     */
    private static final double INITIAL_K = 1; // 1

    /**
     * boolean value to determine use of bounding box improvement
     */
    private boolean _bb; // bounding box

    /**
     * setter for bounding box flag
     *
     * @param _bb to set the bb factor?
     * @return this instance
     */
    public RayTracerBasic set_bb(boolean _bb) {
        this._bb = _bb;
        return this;
    }

    /**
     * constructor
     *
     * @param scene - the scene the rays are sent to
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
//        scene.getGeometries().BuildTree();
    }

    /**
     * abstract function to determine the color of a pixel
     *
     * @param ray - the ray sent from the light source to the scene
     * @return the color of the pixel intersects the given ray
     */
    @Override
    public Color traceRay(Ray ray) {

        Point closestPoint = findClosestIntersection(ray);

        if (closestPoint == null) {
            return scene.getBackground();
        }

        return calcColor(closestPoint);
    }

    /**
     * function to find geo point closest to starting point of the ray
     * from all of the intersections with an object
     *
     * @param ray - the tested ray
     * @return the point closest to the ray's starting point
     */
    private Point findClosestIntersection(Ray ray) {

        List<Point> intersections;

        if (!_bb) {
            intersections = scene.getGeometries().findIntersections(ray);
        } else {
            intersections = scene.getGeometries().findIntersections(ray);
        }

        if (intersections == null || intersections.size() == 0) {
            return null;
        } else {
            return ray.findClosestPoint(intersections);
        }
    }

    /**
     * function which returns the color of the object the ray is intersecting
     * if no intersection was found, returns the ambient light's color
     *
     * @param point - the point on the 3D model
     * @return the color in the point
     */
    private Color calcColor(Point point) {
        return scene.getAmbientLight().getIntensity();
    }

}