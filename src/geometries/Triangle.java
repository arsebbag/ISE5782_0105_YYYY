package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Triangle class represents two-dimensional triangle in 3D Cartesian coordinate system.
 * using 3 {@link Point} 3D point .
 * 
 * @author Ariel Sebbag
 *
 */
public class Triangle extends Polygon {

	
	public Triangle(Point point1, Point point2, Point point3) {
		super(point1, point2, point3);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {

		List<Point> tentativeIntersection = plane.findIntersections(ray);
		// if we do not intersect with plane we can not possibly intersect the triangle.
		if (tentativeIntersection == null) {
			return null;
		}

		// algorithm to test if given point P we got from plane findIntersections is
		// inside the triangle.
		Point p0 = ray.get_p0();
		Vector v = ray.get_dir();
		Vector v1 = _vertices.get(0).subtract(p0);
		Vector v2 = _vertices.get(1).subtract(p0);
		Vector v3 = _vertices.get(2).subtract(p0);

		Vector[] crossVectors = { v1.crossProduct(v2).normalize(), v2.crossProduct(v3).normalize(),
				v3.crossProduct(v1).normalize() };

		int numOfPositiveNumbers = 0;
		for (Vector vector : crossVectors) {
			double vn = v.dotProduct(vector);
			if (isZero(vn)) {
				return null;
			}
			if (vn > 0) {
				numOfPositiveNumbers++;
			}
		}
		// if numOfPositiveNumbers is not 0 or 3 it's mean there is at least 1 number
		// with odd sign.
		if (numOfPositiveNumbers != 0 && numOfPositiveNumbers != 3) {
			return null;
		}

		return tentativeIntersection;
	}
	
	@Override
	public String toString() {
		return String.format("The first vertice: %s to %s."
						+ "\nThe second vertice: %s to %s."
						+ "\nThe third vertice: %s to %s.",
						_vertices.get(0).toString(), _vertices.get(1).toString(),
						_vertices.get(1).toString(),_vertices.get(2).toString(),
						_vertices.get(2).toString(),_vertices.get(0).toString());
	}
}
