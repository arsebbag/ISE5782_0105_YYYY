package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

/**
 * Class point3D is the basic class representing a point in space of Euclidean
 * geometry in Cartesian 3-Dimensional coordinate system.
 *
 * @author Ariel Sebbag
 */
public class Camera {

    //fields

    private Point _p0;
    private Vector _vTo, _vUp, _vRight;
    private double _width, _height, _distance;
    private ImageWriter ImageWriter;
    private RayTracerBasic RayTracer;

    //ERROR messages
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    /**
     * Only Constructor for Camera. must given position and 
     * 2 directions(forward and
     * up).
     *
     * @param camPos - Point3D A position for the camera.
     * @param camVTo - Vector The forward vector direction.
     * @param camVUp - Vector The upward vector direction.
     */
    public Camera(Point camPos, Vector camVTo, Vector camVUp) {
        _p0 = camPos;
        if (!isZero(camVTo.dotProduct(camVUp))) {
            throw new IllegalArgumentException("The Vectors supplied should be orthogonal to each other");
        }
        _vTo = camVTo.normalized();
        _vUp = camVUp.normalized();
        _vRight = _vTo.crossProduct(_vUp).normalize();
    }


    /**
     * Getter for private field Position
     *
     * @return {@link Point}
     */
    public Point get_p0() {
        return _p0;
    }

    /**
     * Getter for private field Vector vTo (forward).
     *
     * @return {@link Vector}
     */
    public Vector getVTo() {
        return _vTo;
    }

    /**
     * Getter for private field Vector vUp.
     *
     * @return {@link Vector}
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * Getter for private field Vector vRight.
     *
     * @return {@link Vector}
     */
    public Vector getVRight() {
        return _vRight;
    }

    public Camera setImageWriter(renderer.ImageWriter imageWriter) {
        ImageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBasic rayTracer) {
        RayTracer = rayTracer;
        return this;
    }

    /**
     * @return the height
     */
    public double get_height() {
        return _height;
    }

    /**
     * @return the width
     */
    public double get_width() {
        return _width;
    }

    /**
     * A Setter for View panel size. need to provide width and height.
     *
     * @param width  - double the view panel's width.
     * @param height - double the view panel's height.
     * @return - Camera (self)
     */
    public Camera setVPSize(double width, double height) {
        this._width = width;
        this._height = height;
        return this;
    }

    /**
     * A setter for distance between view panel and the camera.
     *
     * @param distance - double the distance between the camera and the view panel.
     * @return - Camera (self)
     */
    public Camera setVPDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * gets Ray that align with 2 points (camera position and a viewPanel pixel).
     *
     * @param nX int - number of columns in the View panel.
     * @param nY int - number of rows in the View panel.
     * @param j  int - column index of the pixel in the View panel.
     * @param i  int - row index of the pixel in the View panel.
     * @return Ray - that start at the camera and goes through the pixel[i,j].
     */
    public Ray constructRay(int nX, int nY, int j, int i) { // name was: constructRayThroughPixel

        double rX = alignZero(_width / nX);
        double rY = alignZero(_height / nY);
        Point pc = _p0.add(_vTo.scale(_distance));

        double yI = alignZero((((nY - 1) / 2d) - i) * rY);
        double xJ = alignZero((j - ((nX - 1) / 2d)) * rX);

        Point pIJ = pc;
        if (xJ != 0)
            pIJ = pIJ.add(_vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(_vUp.scale(yI));

        return new Ray(_p0, pIJ.subtract(_p0));
    }

    /**
     * A function that moves the camera around using new point parameter while
     * keeping the camera direction at the point given in the lookAtPoint parameter.
     *
     * @param newPos      - A point to move to.
     * @param lookAtPoint - A point to fix camera Point of view at it.
     * @return self return for more mutations.
     */
    public Camera moveCamera(Point newPos, Point lookAtPoint) {
        _p0 = newPos;
        _vTo = lookAtPoint.subtract(_p0).normalize();
        _vUp = _vTo.crossProduct(new Vector(1, 0, 0)).normalize();
        _vRight = _vTo.crossProduct(_vUp).normalize();
        return this;
    }

    private Vector rotateVUpByVTo(double angleInDeg) {
        // Using this formula from wikipedia in order to rotate vector *V* around other
        // vector *K* by *t*
        // vRot = vCos(t) + (KxV)sin(t) + K(KV)(1 - cost)

        // vRot = v*(u * v) + costT(uXv)Xu+sinT(uXv)

        double cosT = Math.cos(Math.toRadians(angleInDeg));
        double sinT = Math.sin(Math.toRadians(angleInDeg));

        double kvOneMinusCosT = _vTo.dotProduct(_vUp) * (1 - cosT);
        Point rotatedVectorHead = Point.ZERO;
        if (!isZero(cosT)) {
            rotatedVectorHead = rotatedVectorHead.add(_vUp.scale(cosT));
        }
        if (!isZero(sinT)) {
            rotatedVectorHead = rotatedVectorHead.add(_vTo.crossProduct(_vUp).scale(sinT));
        }
        if (!isZero(kvOneMinusCosT)) {
            rotatedVectorHead = rotatedVectorHead.add(_vTo.scale(kvOneMinusCosT));
        }
        return new Vector(rotatedVectorHead).normalize();
    }

    /**
     * Rotate camera in angle given by user (using degrees) counter clockwise.
     *
     * @param angleInDeg - the angle to rotate (in degrees).
     * @return self return for more mutations.
     */
    public Camera rotateCameraCounterClockWise(double angleInDeg) {
        _vUp = rotateVUpByVTo(angleInDeg);
        _vRight = _vTo.crossProduct(_vUp).normalize();
        return this;
    }

    /**
     * Rotate camera in angle given by user (using degrees) clockwise.
     *
     * @param angleInDeg - the angle to rotate (in degrees).
     * @return self return for more mutations.
     */
    public Camera rotateCameraClockWise(double angleInDeg) {
        _vUp = rotateVUpByVTo(-angleInDeg);
        _vRight = _vTo.crossProduct(_vUp).normalize();
        return this;
    }

    /**
     * An override to check if two cameras positioned in the same location with the
     * same directions in vectors.
     *
     * @param obj - An object to compare to this camera.
     * @return True if: same camera or same fields. else, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Camera)) {
            return false;
        }
        Camera camera = (Camera) obj;
        return _p0.equals(camera._p0) && _vTo.equals(camera._vTo) && _vUp.equals(camera._vUp)
                && _vRight.equals(camera._vRight) && _width == camera._width && _height == camera._height
                && _distance == camera._distance;
    }

    public Ray constructRayThroughPixel(Point point) {
        return new Ray(_p0, point.subtract(_p0));
    }

    public List<Ray> createGridCameraRays(List<Point> points) {
        return points.stream().parallel().map(point -> new Ray(_p0, point.subtract(_p0)))
                .collect(Collectors.toList());
    }

    public List<Point> calculatePoints(int nx, int ny, int j, int i, int gridSize) {
        // pixel size
        double rX = alignZero(_width / nx);
        double rY = alignZero(_height / ny);

        int halfGrid = Math.floorDiv(gridSize, 2);
        // interval between 2 points in the sub grid
        double xInterval = rX / gridSize;
        double yInterval = rY / gridSize;

        Ray centerRay = constructRay(nx, ny, j, i);
        Point center = _p0.add(centerRay.get_dir().scale(_distance));
        List<Point> points = new LinkedList<>();

        for (int row = -halfGrid; row < gridSize; row++) {
            for (int col = -halfGrid; col < gridSize; col++) {
                Point gridPij = isZero(col * xInterval) ? center : center.add(_vRight.scale(col * xInterval));
                gridPij = isZero(row * yInterval) ? gridPij : gridPij.add(_vUp.scale(row * yInterval));
                points.add(gridPij);
            }
        }
        if (gridSize == 2) {
            points.add(center);
        }
        return points;
    }

    public List<Point> pixelCorners(int nx, int ny, Point center) {
        // pixel size
        double rX = alignZero((_width / nx) / 2);
        double rY = alignZero((_height / ny) / 2);

        return List.of(center.add(_vUp.scale(-rY)).add(_vRight.scale(-rX)),
                center.add(_vUp.scale(-rY)).add(_vRight.scale(rX)), center.add(_vUp.scale(rY)).add(_vRight.scale(-rX)),
                center.add(_vUp.scale(rY)).add(_vRight.scale(rX)));
    }

    public List<Point> getNewCenters(int nx, int ny, List<Point> pixelCorners) {
        // pixel size
        double rX = alignZero((_width / nx) / 4);
        double rY = alignZero((_height / ny) / 4);

        return List.of(pixelCorners.get(0).add(_vUp.scale(rY)).add(_vRight.scale(rX)),
                pixelCorners.get(1).add(_vUp.scale(rY)).add(_vRight.scale(-rX)),
                pixelCorners.get(2).add(_vUp.scale(-rY)).add(_vRight.scale(rX)),
                pixelCorners.get(3).add(_vUp.scale(-rY)).add(_vRight.scale(-rX)));
    }

    public Point getPixel(Ray centerRay) {
        return _p0.add(centerRay.get_dir().scale(_distance));
    }


    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (ImageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (this == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (RayTracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        throw new UnsupportedOperationException();
    }


    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * interval and color.
     *
     * @param interval  grid's step
     * @param color grid's color
     */
    public void printGrid(int interval, Color color) {

        for (int i = 0; i < ImageWriter.getNx(); i++) {
            for (int j = 0; j < ImageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    ImageWriter.writePixel(i, j, color);
                }
            }
        }

    }

    public void writeToImage() {
        ImageWriter.writeToImage();
    }
}
