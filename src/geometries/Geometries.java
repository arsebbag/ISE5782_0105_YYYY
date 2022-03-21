package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Geometries class represents compsition of Geometries in 3D Cartesian
 * coordinate system
 *
 * @author SHAI FALACH and RON HAIM HODADEDI
 */
public class Geometries implements Intersectable {
    private List<Intersectable> geometriesList;

    /**
     * A Constructor the sets the objevct to be with empty list of intersectable
     * shapes.
     */
    public Geometries() {
        geometriesList = new ArrayList<>();
    }

    /**
     * A Constructor the gets an Array of intersectable Geometries shapes. and
     * create a composite list of them.
     *
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        geometriesList = List.of(geometries);
    }

    /**
     * Add a new Shape/s to the List.
     *
     * @param geometries - array of Intersectable Geometries.
     */
    public void add(Intersectable... geometries) {
        geometriesList.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (geometriesList.isEmpty()) {
            return null;
        } else {
            List<Point> intersections = null;
            for (Intersectable intersectable : geometriesList) {
                List<Point> intersectableIntersections = intersectable.findIntersections(ray);
                if (intersectableIntersections != null) {
                    if (intersections != null) {
                        intersections.addAll(intersectableIntersections);
                    } else {
                        intersections = new ArrayList<>(intersectableIntersections);
                    }
                }
            }
            return intersections;
        }
    }

}
