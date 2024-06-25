package renderer;

import primitives.*;

import scene.Scene;

/**
 * A class responsible for calculating the color of the pixel from the impact of
 * a specific ray
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class RayTracerBase {
	/**
	 * The scene with which we will test the cut with the ray
	 */
	protected final Scene scene;

	/**
	 * A constructor that updates the scene according to the received parameter
	 * 
	 * @param scene is the scene by which we will update
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * A function that calculates the color that we need to paint the pixel that the
	 * ray intersect with geometric bodies
	 * 
	 * @param ray that intersect the bodies
	 * @return the color that we need to paint the pixel that the ray intersect
	 */
	public abstract Color traceRay(Ray ray);

}
