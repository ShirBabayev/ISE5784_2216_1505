/**
 * 
 */
package scene;

import java.awt.Color;

import lighting.AmbientLight;
import geometries.*;

/**
 * 
 */
public class Scene {
public String name;
public Color background=Color.black;
public AmbientLight ambientLight=AmbientLight.NONE;
public Geometries geometries= new Geometries();
public Scene(String n) {name=n;}
public Scene setBackground(primitives.Color color) {
    this.background = color;
    return this;
}
public Scene setAmbientLight(AmbientLight ambientLight) {
    this.ambientLight = ambientLight;
    return this;
}
public Scene setGeometries(Geometries geometries) {
    this.geometries = geometries;
    return this;
}


}
