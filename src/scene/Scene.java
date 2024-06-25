package scene;

import lighting.AmbientLight;
import lighting.LightSource;

import java.util.LinkedList;
import java.util.List;

import geometries.*;

import primitives.Color;

/**
 * A class representing a graphical scene consisting of geometric bodies,
 * ambient lighting, background color, and the name of the scene
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Scene {
	/**
	 * the name of the scene
	 */
	public final String name;

	/**
	 * the background color of the scene
	 */
	public Color background = Color.BLACK;

	/**
	 * the ambient lighting of the scene
	 */
	public AmbientLight ambientLight = AmbientLight.NONE;

	/**
	 * the geometries bodies of the scene
	 */
	public Geometries geometries = new Geometries();

	/**
	 * list of lights sources in the scene
	 */
	public List<LightSource> lights = new LinkedList<>();

	/**
	 * a constructor for rename the scene
	 * 
	 * @param n is the name of the scene
	 */
	public Scene(String n) {
		name = n;
	}

	/**
	 * setter function for setting the background of the scene
	 * 
	 * @param color is the color according to which we will update the background
	 *              color
	 * @return the updated scene
	 */
	public Scene setBackground(Color color) {
		this.background = color;
		return this;
	}

	/**
	 * setter function for setting the ambient lighting of the scene
	 * 
	 * @param ambientLight is the ambient lighting according to which we will update
	 *                     the ambient lighting
	 * @return the updated scene
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * setter function for setting the geometries bodies of the scene
	 * 
	 * @param geometries are the geometries bodies according to which we will update
	 *                   the geometries is the scene
	 * @return the updated scene
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

	/**
	 * setter function for setting the source lights of the scene
	 * 
	 * @param list is a list of lights sources
	 * @return the updated scene
	 */
	public Scene setLights(List<LightSource> list) {
		// TODO:ADDALL OT =
		this.lights = (list);
		return this;
	}

}
