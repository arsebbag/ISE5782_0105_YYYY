package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is an interface all geometries shapes implement.
 * The methods in the interface:
 * - getNormal(Point): Vector
 * @author Ariel sebbag
 *
 */
public interface Geometry extends Intersectable{

    Vector getNormal(Point point);
}

