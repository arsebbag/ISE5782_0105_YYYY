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

    private Point p0;
    private Vector vTo, vUp, vRight;
    private double width, height, distance;
    private ImageWriter imageWriter;
    private RayTracer rayTracer;

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
     * @param p0 - Point3D A position for the camera.
     * @param vTo - Vector The forward vector direction.
     * @param vUp - Vector The upward vector direction.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("The Vectors supplied should be orthogonal to each other");
        }
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        vRight = this.vTo.crossProduct(this.vUp);
    }


    /**
     * Getter for private field Position
     *
     * @return {@link Point}
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for private field Vector vTo (forward).
     *
     * @return {@link Vector}
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * Getter for private field Vector vUp.
     *
     * @return {@link Vector}
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Getter for private field Vector vRight.
     *
     * @return {@link Vector}
     */
    public Vector getVRight() {
        return vRight;
    }

    public Camera setImageWriter(renderer.ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBasic rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * A Setter for View panel size. need to provide width and height.
     *
     * @param width  - double the view panel's width.
     * @param height - double the view panel's height.
     * @return - Camera (self)
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * A setter for distance between view panel and the camera.
     *
     * @param distance - double the distance between the camera and the view panel.
     * @return - Camera (self)
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
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

        double rX = alignZero(width / nX);
        double rY = alignZero(height / nY);
        Point pc = p0.add(vTo.scale(distance));

        double yI = alignZero((((nY - 1) / 2d) - i) * rY);
        double xJ = alignZero((j - ((nX - 1) / 2d)) * rX);

        Point pIJ = pc;
        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));

        return new Ray(p0, pIJ.subtract(p0));
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
        p0 = newPos;
        vTo = lookAtPoint.subtract(p0).normalize();
        vUp = vTo.crossProduct(new Vector(1, 0, 0)).normalize();
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    private Vector rotateVUpByVTo(double angleInDeg) {
        // Using this formula from wikipedia in order to rotate vector *V* around other
        // vector *K* by *t*
        // vRot = vCos(t) + (KxV)sin(t) + K(KV)(1 - cost)

        // vRot = v*(u * v) + costT(uXv)Xu+sinT(uXv)

        double cosT = Math.cos(Math.toRadians(angleInDeg));
        double sinT = Math.sin(Math.toRadians(angleInDeg));

        double kvOneMinusCosT = vTo.dotProduct(vUp) * (1 - cosT);
        Point rotatedVectorHead = Point.ZERO;
        if (!isZero(cosT)) {
            rotatedVectorHead = rotatedVectorHead.add(vUp.scale(cosT));
        }
        if (!isZero(sinT)) {
            rotatedVectorHead = rotatedVectorHead.add(vTo.crossProduct(vUp).scale(sinT));
        }
        if (!isZero(kvOneMinusCosT)) {
            rotatedVectorHead = rotatedVectorHead.add(vTo.scale(kvOneMinusCosT));
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
        vUp = rotateVUpByVTo(angleInDeg);
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * Rotate camera in angle given by user (using degrees) clockwise.
     *
     * @param angleInDeg - the angle to rotate (in degrees).
     * @return self return for more mutations.
     */
    public Camera rotateCameraClockWise(double angleInDeg) {
        vUp = rotateVUpByVTo(-angleInDeg);
        vRight = vTo.crossProduct(vUp).normalize();
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
        return p0.equals(camera.p0) && vTo.equals(camera.vTo) && vUp.equals(camera.vUp)
                && vRight.equals(camera.vRight) && width == camera.width && height == camera.height
                && distance == camera.distance;
    }

    public Ray constructRayThroughPixel(Point point) {
        return new Ray(p0, point.subtract(p0));
    }

    public List<Ray> createGridCameraRays(List<Point> points) {
        return points.stream().parallel().map(point -> new Ray(p0, point.subtract(p0)))
                .collect(Collectors.toList());
    }

    public List<Point> calculatePoints(int nx, int ny, int j, int i, int gridSize) {
        // pixel size
        double rX = alignZero(width / nx);
        double rY = alignZero(height / ny);

        int halfGrid = Math.floorDiv(gridSize, 2);
        // interval between 2 points in the sub grid
        double xInterval = rX / gridSize;
        double yInterval = rY / gridSize;

        Ray centerRay = constructRay(nx, ny, j, i);
        Point center = p0.add(centerRay.get_dir().scale(distance));
        List<Point> points = new LinkedList<>();

        for (int row = -halfGrid; row < gridSize; row++) {
            for (int col = -halfGrid; col < gridSize; col++) {
                Point gridPij = isZero(col * xInterval) ? center : center.add(vRight.scale(col * xInterval));
                gridPij = isZero(row * yInterval) ? gridPij : gridPij.add(vUp.scale(row * yInterval));
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
        double rX = alignZero((width / nx) / 2);
        double rY = alignZero((height / ny) / 2);

        return List.of(center.add(vUp.scale(-rY)).add(vRight.scale(-rX)),
                center.add(vUp.scale(-rY)).add(vRight.scale(rX)), center.add(vUp.scale(rY)).add(vRight.scale(-rX)),
                center.add(vUp.scale(rY)).add(vRight.scale(rX)));
    }

    public List<Point> getNewCenters(int nx, int ny, List<Point> pixelCorners) {
        // pixel size
        double rX = alignZero((width / nx) / 4);
        double rY = alignZero((height / ny) / 4);

        return List.of(pixelCorners.get(0).add(vUp.scale(rY)).add(vRight.scale(rX)),
                pixelCorners.get(1).add(vUp.scale(rY)).add(vRight.scale(-rX)),
                pixelCorners.get(2).add(vUp.scale(-rY)).add(vRight.scale(rX)),
                pixelCorners.get(3).add(vUp.scale(-rY)).add(vRight.scale(-rX)));
    }

    public Point getPixel(Ray centerRay) {
        return p0.add(centerRay.get_dir().scale(distance));
    }


    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (this == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (rayTracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);
        for (int row = 0; row < Ny; row++) {
            for (int col   = 0; col < Nx; col++) {
                castRay(Nx,Ny,row,col);
            }
        }
        throw new UnsupportedOperationException();
    }

    private void castRay(int nx, int ny, int row, int col) {
        Ray  ray = constructRay(nx,ny,row,col);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(col,row,color);
    }


    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * interval and color.
     *
     * @param interval  grid's step
     * @param color grid's color
     */
    public void printGrid(int interval, Color color) {

        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }

    }

    public void writeToImage() {
        imageWriter.writeToImage();
    }
}
