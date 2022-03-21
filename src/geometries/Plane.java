package geometries;

import primitives.Point;
import primitives.Double3;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
     * @param p1 in Plane.
     * @param p2 in Plane.
     * @param p3 in Plane.
     */
    Plane(Point p1, Point p2, Point p3) {
        _p = p1;
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        _normal = v1.crossProduct(v2).normalize();
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
    public Vector getNormal(Point point) {
        return _normal;
    }
    /*@Override
    public Vector getNormal(Point point) { // *T*O*D*O*
        if ((new Vector(point).dotProduct(_normal)) != 0)
        {throw new IllegalArgumentException("The point is wrong");}
        return _normal;
    }*/

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        double nv = this._normal.dotProduct(ray.get_dir());
        if (isZero(nv) || ray.get_p0() == this._p) {
            return null;
        }
        Vector vec = _p.subtract(ray.get_p0());

        double nQMinusP0 = _normal.dotProduct(vec);
        double t = alignZero(nQMinusP0 / nv);
        if (t > 0) {
            return List.of(ray.getPoint(t));
        }
        return null;

    }
}
