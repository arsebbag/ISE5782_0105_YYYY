package primitives;

/**
 * Vector class represents 3D vector in 3D Cartesian coordinate system.
 * using {@link Point} 3D Point indicating vector head.
 *
 * @author Ariel Sebbag
 *
 */
public class Vector extends Point{
    // Private members:
    //Point _head;

    // Constructors:
    /**
     * Constructor for building non zero vector from 3 double variables.
     * @param xCoordValue = double parameter for the X Axis Coordinate.
     * @param yCoordValue = double parameter for the Y Axis Coordinate.
     * @param zCoordValue = double parameter for the Z Axis Coordinate.
     * @throws if the point is the origin (0,0,0)
     */
    public Vector(double xCoordValue, double yCoordValue, double zCoordValue) {
        super(xCoordValue, yCoordValue, zCoordValue);
        Point newHead = new Point(xCoordValue, yCoordValue, zCoordValue);
        if(this.coordinate.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The point is the origin point which can not be used to describe a vector.");
        }
        //_head = newHead;
    }

    /**
     * Constructor for building non zero vector from Point3D variable.
     * @param otherPoint = Point3D parameter.
     * @throws if the point is the origin (0,0,0)
     */
    public Vector(Point otherPoint) {
        super(otherPoint.coordinate._d1, otherPoint.coordinate._d2, otherPoint.coordinate._d3);
        if(otherPoint.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The point is the origin point which can not be used to describe a vector.");
        }
        //_head = otherPoint;
    }

    /**
     * Constructor for building non zero vector from Point3D variable.
     * @param otherPoint = Point3D parameter.
     * @throws if the point is the origin (0,0,0)
     */
    public Vector(Double3 otherPoint) {
        super(otherPoint);
        if(otherPoint.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The point is the origin point which can not be used to describe a vector.");
        }
        //_head = new Point(otherPoint.d1, otherPoint.d2, otherPoint.d3);
    }

    // Getters:
    /**
     * Getter for the Point3D private field.
     * @return Point3D head of the Vector.
     */
    //public Point getHead() {return _head;}

    public Point getHead() {return new Point(this.coordinate._d1, this.coordinate._d2, this.coordinate._d3);}
    //public Double3 getHeadCoord() {return _head.coordinate;}

    // Vector Class Methods:

    /**
     * Add one vector from vector invoking the method.
     * @param otherVector the vector to add from the invoking vector.
     * @return Vector, new Vector result of invoker + otherVector
     */
    public Vector add(Vector otherVector) {
        return new Vector(new Double3(this.coordinate._d1, this.coordinate._d2, this.coordinate._d3).add(otherVector.coordinate));
    }
    /**
     * Scaling a vector with a scalar.
     * @param scalar a num to mult the vector.
     * @return Vector result of invoker * scalar
     */

   /* //**
     * Subtract one vector from vector invoking the method.
     * @param otherVector = the vector to subtract from the invoking vector.
     * @return Vector,new Vector result of otherVector - invoker

    public Vector subtract(Vector otherVector) {
        return new Vector(this.coordinate.subtract(otherVector.coordinate));
    }*/

    public Vector scale(double scalar) {
        return new Vector(this.coordinate.scale(scalar));
    }
    /**
     * function to get the cartesian product of two vectors.
     * @param otherVector = is the second operand in the cartesian product
     * @return double result of cartesian product invoker * otherVector
     */
    public double dotProduct(Vector otherVector) {
        double u1 = coordinate._d1;
        double u2 = coordinate._d2;
        double u3 = coordinate._d3;
        Double3 u = new Double3(u1, u2, u3);

        double v1 = otherVector.coordinate._d1;
        double v2 = otherVector.coordinate._d2;
        double v3 = otherVector.coordinate._d3;
        Double3 v = new Double3(v1, v2, v3);

        //return (u1 * v1 + u2 * v2 + u3 * v3);
        return sumProd(u.product(v));

    }

    /**
     * function to get the cross product of two vectors.
     * @param otherVector = is the second operand in the cross product
     * @return Vector result of cross product invoker X otherVector
     */
    public Vector crossProduct(Vector otherVector) {
        double u1 = coordinate._d1;
        double u2 = coordinate._d2;
        double u3 = coordinate._d3;

        double v1 = otherVector.coordinate._d1;
        double v2 = otherVector.coordinate._d2;
        double v3 = otherVector.coordinate._d3;
        return new Vector((u2 * v3 - v2 * u3), -(u1 * v3 - v1 * u3), (u1 * v2 - v1 * u2));
    }
    /*public Vector crossProduct(Vector otherVector) {
        Point otherVecEdge = otherVector.getHead();
        double xCoordValue, yCoordValue, zCoordValue;

        xCoordValue = this.coordinate.d2 * otherVecEdge.coordinate.d3
                -this.coordinate.d3 * otherVecEdge.coordinate.d2;

        yCoordValue = this.coordinate.d3 * otherVecEdge.coordinate.d1
                -this.coordinate.d1 * otherVecEdge.coordinate.d3;

        zCoordValue = this.coordinate.d1 * otherVecEdge.coordinate.d2
                -this.coordinate.d2 * otherVecEdge.coordinate.d1;

        return new Vector(xCoordValue, yCoordValue, zCoordValue);
    }*/
    /**
     * calculate the length ^ 2 of the invoking vector
     * @return double result |invoker| ^ 2 (|v|^2)
     */
    public double lengthSquared() {
        return this.distanceSquared(Point.ZERO);
    }
    /**
     * calculate the length of the invoking vector
     * @return double result of |invoker| (|v|)
     */
    public double length() {
        return this.distance(Point.ZERO);
    }
    /**
     * normalizing the invoking vector to be a unit vector (length = 1).
     * @return Vector the invoker after normalizing it (this)
     */
    public Vector normalize() {
        double vectorLength = length();

        Vector unitVector = scale(1/ vectorLength);
        return new Vector(new Point(unitVector.coordinate._d1, unitVector.coordinate._d2, unitVector.coordinate._d3));
    }
    /**
     * normalizing the invoking vector to be a unit vector (length = 1).
     * @return Vector new vector of length = 1 (unit vector) in the same direction of invoking vector
     */
    public Vector normalized() {
        Vector OldVector = new Vector(this.coordinate._d1, this.coordinate._d2, this.coordinate._d3);
        Vector newVector = this.normalize();
        return newVector;
    }

    public double sumProd(Double3 do3){
        return do3._d1 + do3._d2 + do3._d3;
    }
    /*@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector otherVector = (Vector) obj;
        return this.equals(new Vector(otherVector.coordinate.d1, otherVector.coordinate.d2, otherVector.coordinate.d3));
    }

    @Override
    public String toString() {
        return String.format("Vector head: %s", this.toString());
    }
*/
}

