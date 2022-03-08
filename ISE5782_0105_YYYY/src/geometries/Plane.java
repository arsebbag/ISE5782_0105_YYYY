package geometries;

import primitives.Point;
import primitives.Double3;
import primitives.Vector;

/**
 * Plane class represents two-dimensional plane in 3D Cartesian coordinate system.
 * using a {@link Point} 3D point and a {@link Vector} unit vector as the normal to the plane in the point.
 *
 * @author Ariel Sebbag
 *
 */
public class Plane implements Geometry {

    // Private members:
    Point _p;
    Vector _normal;

    // ctors:

    /**
     *1
     * @param point3d in Plane.
     * @param point3d2 in Plane.
     * @param point3d3 in Plane.
     */
    public Plane(Point point3d, Point point3d2, Point point3d3) {
        _p = point3d;
        Vector vec1 = _p.subtract(point3d2);
        Vector vec2 = _p.subtract(point3d3);
        if ((vec1.crossProduct(vec2)).dotProduct(new Vector(_p)) == 0)//.normalize() -> ??
        { _normal = (vec1.crossProduct(vec2).normalize());} // *T*O*D*O*
    }

    /**
     *2
     * @param point3d point in the plane
     * @param n Normal
     */

    public Plane (Point point3d, Vector n)
    {
        this._p = point3d;
        this._normal = n;
    }


    // Getters:
    public Point get_p() {
        return _p;
    }
    public Vector get_normal() {
        return _normal;
    }

    /**
     * @param point in the plane
     * @return ZERO vector
     */
    @Override
    public Vector getNormal(Point point) { // *T*O*D*O*
        if ((new Vector(point).dotProduct(this._normal)) != 0)
        {throw new IllegalArgumentException("The point is wrong");}
        return _normal;
    }

    /**
     *
     * @return _p Normal of Plane.
     */
    public Vector getNormal() {
        return getNormal(_p);
    }


    @Override
    public String toString() {

        Point headPoint = _normal .getHead();

        /*double a = headPoint;
        double b = headPoint.getCoordinateY().get();
        double c = headPoint.getCoordinateZ().get();
        double d = new Vector(_p).dotProduct(_normal);*/
        double d = new Vector(_p).dotProduct(_normal);
        return String.format("the Plane Equsion is: %.2fx + %.2fb", headPoint.toString(), d);
        //return String.format("the Plane Equasion is: %.2fx + %.2fb + $.2fc = %.2f", a, b, c, d);

    }

}
