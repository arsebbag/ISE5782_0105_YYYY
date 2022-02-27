package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class represents tube in 3D Cartesian coordinate system.
 * using {@link Double} radius and a {@link Ray} ray for the axis.
 * 
 * @author Ariel Sebbag
 *
 */
public class Tube implements Geometry { // extends RadialGeometry

	// Private members:
	protected Ray _axisRay;
	protected double _radius;
	/**
	 * 
	 * @param ray on Tube.
	 * @param radius on Tube.
	 */
	public Tube(Ray ray ,double radius) {
		//super(radius);
		_axisRay = ray;
	}
	
	// Get:
	public Ray get_axisRay() {
		return _axisRay;
	}

	/**
	 *
	 * @param point on the Tube.
	 * @return NULL.
	 */
	@Override
	public Vector getNormal(Point point) {
		return null; // TODO in stage 2 implement 3 points normal calculation.
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("The axis is in: %s and the radius is: %.2f", _axisRay.toString(), _radius);
	}
}
