package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * A class representing the path of a simple ray
 * @author Hodaya Avidan and Shir Babayev
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * A constructor that updates the scene according to the scene received as a parameter
	 * @param s is the scene by which we will update
	 */
	public SimpleRayTracer(Scene s) {
		super(s);
	}
	
	
	public Color traceRay(Ray ray) {
		Point point = ray.findClosestPoint(scene.geometries.findIntersections(ray));
        if (point == null)
            return scene.background;
        return scene.ambientLight.getIntensity();	
     }
}
