package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

/**
 * A class representing the path of a simple ray
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * A constructor that updates the scene according to the scene received as a
	 * parameter
	 * 
	 * @param scene is the scene by which we will update
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		GeoPoint point = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
		return point == null ? scene.background : calcColor(point);
	}

	/**
	 * The function receives the point closest to the ray and calculates the color
	 * of the point on the geometric body *
	 * 
	 * @param point The point closest to the top of the ray
	 * @return Body color at this point
	 */
	private Color calcColor(GeoPoint gpoint) {
		return scene.ambientLight.getIntensity().add(gpoint.geometry.getEmission());
	}
}
