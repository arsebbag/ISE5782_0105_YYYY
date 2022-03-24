package primitives;

import static primitives.Util.isZero;

/**
 * Ray  class represents 3D ray in 3D Cartesian coordinate system.
 * using a {@link Double3} point as starting point and direction using {@link Vector} unit vector .
 *
 * @author Ariel Sebbag
 *
 */
public class Ray {

    // Private members:
    private Point _p0;
    private Vector _dir;


    // Constructors:
    /**
     * Constructor to build Ray using Point3D and Vector.
     * @param otherPoint = starting point of ray.
     * @param unitVector = unit vector which is direction of the ray.
     */
    public Ray(Point otherPoint, Vector unitVector) {
        _dir = unitVector.normalized();
        _p0 = otherPoint;
    }
 /*   *//**
     * Constructor to build Ray using another ray (copy constructor).
     * @param otherRay = other Ray to copy data from.
     *//*
    public Ray(Ray otherRay) {
        _p0 = otherRay.get_p0();
        _dir = otherRay.get_dir();
    }*/

    // Getters:
    /**
     * Getter for the Point3D private field representing the start of the ray.
     * @return Point3D the start of the ray
     */
    public Point get_p0() {
        return _p0;
    }
    /**
     * Getter for the vector private field representing the direction of the ray.
     * @return vector direction of the ray
     */
    public Vector get_dir() {
        return _dir;
    }


    /**
     * get the Point3D on the Ray that is in distance of t from Ray starting Point3D
     * (p0).
     *
     * @param t distance to reach new point
     * @return Point - point on Ray axis with distance of t from Ray origin
     *         point(p0).
     */
    public Point getPoint(double t) {
        if(isZero(t)){
            throw new IllegalArgumentException("if t = 0 that cause a new zero vector");
        }
        return _p0.add(_dir.scale(t));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray otherRay = (Ray) obj;
        if (! _p0.equals(otherRay.get_p0())) return false;
        if (! _dir.equals(otherRay.get_dir())) return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("The starting point is: %s with the direction of: %s", _p0,_dir);
    }

}
