package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * class used to trace rays for the rendering engine
 */
public abstract class RayTracer {
    /**
     * scene to be rendered
     */
    protected Scene scene;

    /**
     * constructor for the ray tracer
     * @param scene to be intersected
     */
    protected RayTracer(Scene scene){
        this.scene = scene;
    }

    /**
     * abstract function to determine the color of a pixel
     * @param ray - ray to intersect
     * @return the color of the pixel intersects the given ray
     */
    abstract Color traceRay(Ray ray);
}
