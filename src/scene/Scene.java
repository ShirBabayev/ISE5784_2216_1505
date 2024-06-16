package scene;
import lighting.AmbientLight;
import geometries.*;

import primitives.Color;

/**
 * A class representing a graphical scene consisting of geometric bodies, 
 * ambient lighting, background color, and the name of the scene
 * @author Hodaya Avidan and Shir Babayev
 */
public class Scene {
/**
 * the name of the scene
 */
public String name;

/**
 * the background color of the scene
 */
public Color background=Color.BLACK;

/**
 * the ambient lighting of the scene
 */
public AmbientLight ambientLight=AmbientLight.NONE;

/**
 * the geometries bodies of the scene
 */
public Geometries geometries= new Geometries();

/**
 * a constructor for rename the scene
 * @param n is the name of the scene
 */
public Scene(String n) {
	name=n;
}

/**
 * setter function for setting the background of the scene 
 * @param color is the color according to which we will update the background color
 * @return the updated scene
 */
public Scene setBackground(Color color) {
	this.background = color;
    return this;
}

/**
 * setter function for setting the ambient lighting of the scene 
 * @param ambientLight is the ambient lighting according to which we will update the ambient lighting
 * @return the updated scene
 */
public Scene setAmbientLight(AmbientLight ambientLight) {
    this.ambientLight = ambientLight;
    return this;
}

/**
 * setter function for setting the geometries bodies of the scene 
 * @param geometries are the geometries bodies according to which we will update the geometries is the scene
 * @return the updated scene
 */
public Scene setGeometries(Geometries geometries) {
    this.geometries = geometries;
    return this;
}


}
