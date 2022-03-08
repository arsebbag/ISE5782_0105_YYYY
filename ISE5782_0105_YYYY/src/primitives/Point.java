package primitives;

/**
 * Point3D class represents 3D point in 3D Cartesian coordinate system.
 * using 3 {@link Double3 } coordinates variables.
 *
 * @author Ariel Sebbag
 *
 */
public class Point {
    /**
     * Static constant for the origin point of the 3-Dimensional coordinate system.
     */
    public static Point ZERO = new Point(0.0,0.0,0.0);

    // Private members:
    final Double3 coordinate; //final

    // Ctors:

    /**
     * Constructor for building Point3D from 3 double variables
     * @param xCoordValue = double parameter for the X Axis Coordinate.
     * @param yCoordValue = double parameter for the Y Axis Coordinate.
     * @param zCoordValue = double parameter for the Z Axis Coordinate.
     */
    public Point(double xCoordValue, double yCoordValue, double zCoordValue) {
        this.coordinate = new Double3(xCoordValue,yCoordValue,zCoordValue);
    }

    /**
     * Constructor for building Point3D.
     * @param coord = 3D coordinates
     */
    public Point(Double3 coord) {
        this.coordinate = coord;
    }

    // Point3D class Methods:

    /**
     * Add a vector to the invoking Point3D getting a new Point3D back
     * @param vector = the second operand in the Addition
     * @return Point3D result of invoker +  vector
     */
    public Point add(Vector vector) {
        return new Point(this.coordinate.add(vector.coordinate));
    }

    /**
     * Subtract a Point3D from the invoking Point3D getting a new vector back
     * @param OtherPoint = the first operand in the Subtraction
     * @return Vector result of OtherPoint - invoker
     */
    public Vector subtract(Point OtherPoint) {
        Double3 result = coordinate.subtract(OtherPoint.coordinate);

        if(result.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("resulting of subract: Vector(0,0,0) not allowed");
        }
        return new Vector(result);
    }

    /**
     * calculating the squared distance between two Point3D variables
     * @param otherPoint = the second operand.
     * @return double result of (x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2
     */
    public double distanceSquared(Point otherPoint) {
        double output, xDelta, yDelta, zDelta;
        xDelta = coordinate._d1 - otherPoint.coordinate._d1;
        yDelta = coordinate._d2 - otherPoint.coordinate._d2;
        zDelta = coordinate._d3 - otherPoint.coordinate._d3;
        output = xDelta * xDelta + yDelta * yDelta + zDelta * zDelta;
        return output;
    }
    /**
     * calculating the squared distance between two Point3D variables
     * @param otherPoint = the second operand.
     * @return double result of ((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)^0.5
     */
    public double distance(Point otherPoint) {
        // always positive value so no check for negative value before squaring it.
        double output = distanceSquared(otherPoint);
        return Math.sqrt(output);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point otherPoint = (Point) obj;
        return coordinate.equals(otherPoint.coordinate);
    }

    @Override
    public String toString () {
        return String.format(coordinate.toString());
    }
}



