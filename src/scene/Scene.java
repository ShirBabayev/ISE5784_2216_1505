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
public Color getBackground() {return background;}
public AmbientLight getAmbientLight() {return ambientLight;}
public Geometries getGeometries() {return geometries;}


}
