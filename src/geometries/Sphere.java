package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

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

	/**
	 * check for intersections points with a sphere and returned them
	 * @param ray - a Ray that try to find intersection with the shape.
	 * @return List of points intersection with the Sphere
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		double tM, d;
		try {
			Vector u = _center.subtract(ray.get_p0());
			tM = alignZero(ray.get_dir().dotProduct(u));
			d = alignZero(Math.sqrt(u.lengthSquared() - tM * tM));
		} catch (IllegalArgumentException e) {
			tM = 0;
			d = 0;
		}

		if (d >= _radius) {
			return null;
		}

		double tH = alignZero(Math.sqrt(_radius * _radius - d * d));
		double t1 = alignZero(tM + tH);
		double t2 = alignZero(tM - tH);

		if (t1 > 0) {
			List<Point> tentativeIntersections = new ArrayList<>();
			tentativeIntersections.add(ray.getPoint(t1));
			if (t2 > 0) {
				tentativeIntersections.add(ray.getPoint(t2));
			}
			return tentativeIntersections;

		} else {
			if (t2 > 0) {
				List<Point> tentativeIntersections = new ArrayList<>();
				tentativeIntersections.add(ray.getPoint(t2));
				return tentativeIntersections;
			}
		}
		return null;
	}
}
