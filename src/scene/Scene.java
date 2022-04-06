package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

public class Scene {

    public final String name;
    public final Color background;
    public final AmbientLight ambientLight;
    public final Geometries geometries;// final

    private Scene(SceneBuilder builder){
        name = builder._name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;

    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public static class SceneBuilder {

        private final String _name;
        private Color background;
        private AmbientLight ambientLight;
        private Geometries geometries = new Geometries();

        public SceneBuilder(String name){
            _name = name;
        }

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }
        public Scene build(){
            return new Scene(this);
        }
    }
}
