package renderer;

import primitives.*;

import scene.Scene;
/**
 * A class responsible for calculating the color of the pixel from the impact of a specific ray
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class RayTracerBase {
	/**
	 * The scene with which we will test the cut with the ray
	 */
	protected Scene scene;
	
	/**
	 * A constructor that updates the scene according to the received parameter
	 * @param s is the scene by which we will update
	 */
	public RayTracerBase(Scene s) {
		scene=s;
	}

	/**
	 * Calculating the color of the pixel by the ray hitting the object according to the closest point, 
	 * if the ray does not hit the background color will be returned
	 * @param ray is the ray that passes through the grid towards the objects
	 * @return If the beam hit the object - the color of the object at the closest point, 
	 * and if not - the color of the background
	 */
	public abstract Color traceRay(Ray ray);

}
