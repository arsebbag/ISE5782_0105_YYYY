package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class represents a Cylinder in 3D Cartesian coordinate system.
 * using {@link Double} radius and height and a {@link Ray} ray for the axis.
 * 
 * @author Ariel Sebbag
 *
 */
public class Cylinder extends Tube {

	//  members:
	 double _height;
	
	// Constructors:
	/**
	 * 
	 * @param radius on the Cylinder.
	 */
	public Cylinder(Ray ray, double radius, double height) {
		super(ray, radius);
		_height = height;
	}

	/**
	 *
	 * @param point on the Cylinder.
	 * @return NULL.
	 */
	@Override
	public Vector getNormal(Point point) {
		return null; // TODO in stage 2 implement 3 points normal calculation.
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format(" and height of: %.2f", _height);
	}
}
