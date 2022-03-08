package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere class represents two-dimensional triangle in 3D Cartesian coordinate system.
 * using a {@link Point} 3D point as the center of sphere and {@link Double} radius.
 * 
 * @author Ariel sebbag
 *
 */
public class Sphere implements Geometry { //extends RadialGeometry

	// Private members:
	private Point _center;
	protected double _radius;
	
	/**
	 * @param radius of the Sphere.
	 */
	public Sphere(double radius) {
		//super(radius);
		_radius = radius;

	}

	/**
	 * @param radius of the Sphere.
	 */
	public Sphere(double radius, Point center) {
		_radius = radius;
		this._center = center;
		// TODO Auto-generated constructor stub
	}

	// Getters:
	public Point get_center() {
		return _center;
	}

	/**
			*
			* @param point3d on the Sphere.
			* @return the Normal
			*/
	@Override
	public Vector getNormal(Point point3d) {
		return point3d.subtract(_center).normalize();
	}
	
	@Override
	public String toString() {
		return String.format("The center is: %s and the radius is: $.2f", _center.toString(), _radius);
	}
}
