package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
		_radius = radius;
		_axisRay = ray;
	}
	
	// Get:
	public Ray get_axisRay() {
		return _axisRay;
	}

	/**
	 *
	 * @param point3d on the Tube.
	 * @return the Normal.
	 */
	@Override
	public Vector getNormal(Point point3d) {
		double t = this.get_axisRay().get_dir().dotProduct(point3d.subtract(this.get_axisRay().get_p0()));
		Point projection = this.get_axisRay().get_p0().add(this.get_axisRay().get_dir().scale(t));
		return point3d.subtract(projection).normalize();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("The axis is in: %s and the radius is: %.2f", _axisRay.toString(), _radius);
	}

	/**
	 *
	 * @param ray - a Ray that try to find intersection with the shape.
	 * @return List of intersection points with the shape
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		double nv = _axisRay.get_dir().dotProduct(ray.get_dir());
		if (isZero(nv) || ray.get_p0() == _axisRay.get_p0()) {
			return null;
		}
		Vector vec = _axisRay.get_p0().subtract(ray.get_p0());

		double nQMinusP0 = _axisRay.get_dir().dotProduct(vec);
		double t = alignZero(nQMinusP0 / nv);
		if (t > 0) {
			return List.of(ray.getPoint(t));
		}
		return null;

	}
}
