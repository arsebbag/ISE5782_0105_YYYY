package geometries;

import primitives.Point;
import primitives.Vector;

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
	public String toString() {
		return String.format("The first vertice: %s to %s."
						+ "\nThe second vertice: %s to %s."
						+ "\nThe third vertice: %s to %s.",
						_vertices.get(0).toString(), _vertices.get(1).toString(),
						_vertices.get(1).toString(),_vertices.get(2).toString(),
						_vertices.get(2).toString(),_vertices.get(0).toString());
	}
}
