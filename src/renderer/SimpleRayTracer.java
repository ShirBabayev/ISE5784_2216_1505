package renderer;

import primitives.*;
import lighting.LightSource;
import scene.Scene;

import static primitives.Util.*;
import static geometries.Intersectable.GeoPoint;

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
		return point == null ? scene.background : calcColor(point, ray);
	}

	/**
	 * The function receives the point closest to the ray and calculates the color
	 * of the point on the geometric body
	 * 
	 * @param point The point closest to the top of the ray
	 * @return Body color at this point
	 */
	private Color calcColor(GeoPoint gpoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(gpoint, ray));
	}

	/**
	 * 
	 * @param intersection
	 * @param ray
	 * @return
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Vector v = ray.getDirection();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;

		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;

		Color color = intersection.geometry.getEmission();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));

			if (nl * nv > 0) { // sign(nl) == sign(nv)
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(lightIntensity.scale( //
						calcDiffusive(material.kD, nl).add( //
						calcSpecular(material.kS, l, n, nl, v, nShininess))));
			}
		}
		return color;
	}

	/**
	 * 
	 * @param kd
	 * @param nl
	 * @return
	 */
	private Double3 calcDiffusive(Double3 kd, double nl) {
		return kd.scale(Math.abs(nl));
	}

	/**
	 * 
	 * @param ks
	 * @param l
	 * @param n
	 * @param nl
	 * @param v
	 * @param nShininess
	 * @param lightIntensity
	 * @return
	 */
	private Double3 calcSpecular(Double3 ks, //
			Vector l, Vector n, double nl, Vector v, //
			int nShininess) {
		Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
		double minusVR = -alignZero(r.dotProduct(v));

		// It may be that the view is from direction opposite to r vector
		return minusVR <= 0 ? Double3.ZERO //
				: ks.scale(Math.pow(minusVR, nShininess));
	}
}
