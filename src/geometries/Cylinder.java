package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.alignZero;

import java.util.ArrayList;
import java.util.List;

/**
 * Cylinder class represents a Cylinder in 3D Cartesian coordinate system.
 * using {@link Double} radius and height and a {@link Ray} ray for the axis.
 * 
 * @author Ariel Sebbag
 *
 */
public class Cylinder extends Tube {
	/**
	 * _height - the length of the cylinder from base to base
	 */
	final double _height;

	/**
	 * constructor for cylinder.
	 *
	 * @param axisRay - ray for base tube
	 * @param radius  = radius of base tube
	 * @param height  - length of cylinder
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		if (height <= 0) {
			throw new IllegalArgumentException("height can't be negative");
		}
		_height = height;
		//setBoundingBox();
	}

	/**
	 * getter
	 *
	 * @return height of cylinder
	 */
	public double get_height() {
		return _height;
	}

	@Override
	public String toString() {

		return "(" + _axisRay + "," + _radius + "," + _height + ")";
	}

	@Override
	public Vector getNormal(Point P) {

		Point P0 = _axisRay.get_p0(); //middle of starting base
		Vector v = _axisRay.get_dir();
		Point P1 = P0.add(v.scale(_height)); //middle of far base

		//if point is on the starting base - if distance from p0 is radius, and orthogonal to ray (so it is not on ray itself)
		if ((P.distance(P0) <= _radius) && (P.subtract(P0).dotProduct(v) == 0)) {
			return get_axisRay().get_dir().scale(-1);
		}
		//if point is on the far base - if distance from p1 is radius, and orthogonal to ray (so it is not on ray itself)
		else if ((P.distance(P1) <= _radius) && (P.subtract(P1).dotProduct(v) == 0)) {
			return get_axisRay().get_dir();
		}

		// if point is on round surfaces - not based:
		else {

			Vector PMinusP0 = P.subtract(P0);
			double t = v.dotProduct(PMinusP0);

			Point O = P0.add(v.scale(t));

			return (P.subtract(O)).normalized();
//            Vector sub = P.subtract(O);
//
//            sub.normalize();
//
//            return sub;
			//copy from tube
		}


	}

	/**
	 * this function finds the intersection points of a given ray with the cylinder
	 * @param ray         - which could intersect the cylinder
	 * @return list of intersection points
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// Step 1: find intersections between the ray and the tube which the cylinder is a part of (extends it)
		List<Point> intersectionsTube = super.findIntersections(ray);

		// Step 2: intersect is between caps
		Vector dir = _axisRay.get_dir();
		Point bottomCapCenter = _axisRay.get_p0();
		Point upperCupCenter = _axisRay.getPoint(_height);

		double loweBound;
		double upperBound;
		List<Point> intersectionsCylinder = new ArrayList<>();

		// validate each intersection (make sure it is in the cylinder itself and not on its continual)
		if (intersectionsTube != null) {
			for (Point geoPoint : intersectionsTube) {
				loweBound = dir.dotProduct(geoPoint.subtract(bottomCapCenter));
				upperBound = dir.dotProduct(geoPoint.subtract(upperCupCenter));
				if (loweBound > 0 && upperBound < 0) {
					// the check for distance, if the intersection point is beyond the distance
					if (alignZero(geoPoint.distance(ray.get_p0())) <= 0) {
						intersectionsCylinder.add(geoPoint);
					}
				}
			}
		}

		// Step 3: intersect with each plane which belongs to the caps
		Plane bottomCap = new Plane(bottomCapCenter, dir);
		Plane upperCap = new Plane(upperCupCenter, dir);
		List<Point> intersectionsBottomCup = bottomCap.findIntersections(ray);
		List<Point> intersectionsUpperCup = upperCap.findIntersections(ray);

		// if no intersections were found with the caps, return the ones we have already found
		if (intersectionsBottomCup == null && intersectionsUpperCup == null) {
			if (intersectionsCylinder.isEmpty()) {
				return null;
			}
			return intersectionsCylinder;
		}

		// Step 4: intersections inside caps
		Point bottomCapIntersectionPoint;
		Point upperCapIntersectionPoint;

		// bottom cup
		if (intersectionsBottomCup != null) {
			bottomCapIntersectionPoint = intersectionsBottomCup.get(0);
			if (bottomCapIntersectionPoint.subtract(bottomCapCenter).lengthSquared() < _radius * _radius) {
				intersectionsCylinder.add(intersectionsBottomCup.get(0));
			}
		}

		// upper cup
		if (intersectionsUpperCup != null) {
			upperCapIntersectionPoint = intersectionsUpperCup.get(0);
			if (upperCapIntersectionPoint.subtract(upperCupCenter).lengthSquared() < _radius * _radius) {
				intersectionsCylinder.add(intersectionsUpperCup.get(0));
			}
		}

		if (intersectionsCylinder.size() == 0) { // .isEmpty()
			return null;
		}

		return intersectionsCylinder;
	}

	/**
	 * method sets the values of the bounding box for the cylinder
	 */
	/*@Override
	public void setBoundingBox() {
		if (_boundingBox == null) {
			_boundingBox = new BoundingBox();
		}
*/
	/*Point bottomCapCenter = _axisRay.get_p0();

	Point upperCapCenter = getPoint(get_height());

	// get minimal & maximal x value for the containing box
	double minX = Math.min(bottomCapCenter.get_x(), upperCapCenter.get_x()) - _radius;
	double maxX = Math.max(bottomCapCenter.get_x(), upperCapCenter.get_x()) + _radius;

	// get minimal & maximal y value for the containing box
	double minY = Math.min(bottomCapCenter.get_y(), upperCapCenter.get_y()) - _radius;
	double maxY = Math.max(bottomCapCenter.get_y(), upperCapCenter.get_y()) + _radius;

	// get minimal & maximal z value for the containing box
	double minZ = Math.min(bottomCapCenter.get_z(), upperCapCenter.get_z()) - _radius;
	double maxZ = Math.max(bottomCapCenter.get_z(), upperCapCenter.get_z()) + _radius;

	// set the minimum and maximum values in 3 axes for this bounding region of the component
	//_boundingBox.setBoundingBox(minX, maxX, minY, maxY, minZ, maxZ);*/
}
