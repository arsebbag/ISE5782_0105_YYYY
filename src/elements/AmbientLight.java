package elements;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient Light for objects in 3D space
 * Ambient Light Color
 * Ambient light class represents a fixed-intensity and fixed color light source
 * that affects all objects in the scene equally.
 * the Ambient light intensity in point is Ip = Ka ∙ Ia
 */
public class AmbientLight {

    private final Color intensity;  //

    /**
     * Ambient Light constructor accepting the intensity's value and the color light source's value
     * creates the fixed ambient light's intensity
     *
     * @param Ia intensity color
     * @param Ka constant for intensity
     */
    public AmbientLight(Color Ia, Double3 Ka){
        intensity = Ia.scale(Ka);
    }

    /**
     * empty constructor - return neutral color
     */
    public AmbientLight() {
        intensity = Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }

    @Override
    public String toString() {
        return "AmbientLight{" +
                "_intensity=" + getIntensity() +
                '}';
    }
}
