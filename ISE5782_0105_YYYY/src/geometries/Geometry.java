package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is an interface all geometries shapes implement.
 * The methods in the interface:
 * - getNormal(Point): Vector
 * @author ariel sebbag
 *
 */
public interface Geometry {

    Vector getNormal(Point point);
}

