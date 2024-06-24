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
		//find the closet intersection point of the ray with the geometry bodies
		GeoPoint point = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
		//return the color that we need to color the pixel that the ray intersect
		return point == null ? scene.background : calcColor(point, ray);
	}

	
	 
	 /**
	 * The function receives the point closest to the head of the ray and calculates the color
	 * of the point on the geometric body for painting the intersected pixel
	 * 
	 * @param gpoint - The point closest to the head of the ray
	 * @param ray 
	 * @return Body color at this point
	 */
	private Color calcColor(GeoPoint gpoint, Ray ray) {
		//Adding the lighting effect on the color to the intensity
		return scene.ambientLight.getIntensity().add(calcLocalEffects(gpoint, ray));
	}

	/**
	 * The function calculates the effect of lighting on the color
	 * @param intersection is a point on a geometric body
	 * @param ray
	 * @return
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Vector v = ray.getDirection();
		Vector n = intersection.geometry.getNormal(intersection.point);
		//if the ray is orthogonal to the normal of the geometry - the ray is parallel to the geometry
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			// return the background color
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
