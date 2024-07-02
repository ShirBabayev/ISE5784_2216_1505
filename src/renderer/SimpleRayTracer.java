package renderer;

import primitives.*;
import lighting.LightSource;
import scene.Scene;

import static primitives.Util.*;

import java.util.List;

import geometries.Intersectable.GeoPoint;

/**
 * A class representing the path of a simple ray
 * 
 * @author Shir Babayev and Hodaya Avidan
 */
public class SimpleRayTracer extends RayTracerBase {

	private static final Double3 INITIAL_K = Double3.ONE;

	private static final int MAX_CALC_COLOR_LEVEL = 10;

	/**
	 * a number used to moving the head of the ray towards the light source
	 */
	private static final double DELTA = 0.1;

	private static final double MIN_CALC_COLOR_K = 0.001;

	/**
	 * constructor that build the scene according to the parameter
	 * 
	 * @param scene is the scene for building
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		// find the closet intersection point of the ray with the geometry bodies
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background //
									: calcColor(closestPoint, ray);
	}

	/**
	 * The function receives the point closest to the head of the ray and calculates
	 * the color of the point on the geometric body for painting the intersected
	 * pixel
	 * 
	 * @param gp  - The point closest to the head of the ray
	 * @param ray is the ray from the camera to the point
	 * @return Body color at this point
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray, k);
		return 1 == level ? color//
						  : color.add(calcGlobalEffects(gp, ray, level, k));
	}

	/**
	 * The function calculates the effect of lighting on the color
	 * 
	 * @param intersection is a point on a geometric body
	 * @param ray          is a ray that passes through the view plane
	 * @return the color for painting the pixel
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
		// the ray from the camera to the point
		Vector v = ray.getDirection();
		// the normal from the intersection point on the body
		Vector n = intersection.geometry.getNormal(intersection.point);
		// if the ray is orthogonal to the normal of the geometry
		double nv = alignZero(n.dotProduct(v));
		// The ray is parallel to the tangent to the body at a point
		if (nv == 0)
			// return the background color
			return Color.BLACK;

		// the material of the body
		Material material = intersection.geometry.getMaterial();
		// the color of the geometry
		Color color = intersection.geometry.getEmission();

		for (LightSource lightSource : scene.lights) {
			// the vector between the light to the point we want to color
			Vector l = lightSource.getL(intersection.point);
			// The angle between the lighting vector to the normal
			double nl = alignZero(n.dotProduct(l));
			// The effect of the lighting reach the camera because of the angles
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				// if it is not shaded - the light hits it
				Double3 ktr = transparency(intersection, lightSource, l, n, nv);
				if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
					// the intensity of the light at the intersection point
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					// add the diffusive and specular components to the color
					color = color.add(lightIntensity.scale(
							// The lighting that returns from the body equally in all directions
							calcDiffusive(material.kD, nl).add(
									// The ray that returns as a reflection of the ray that hit the body from the
									// lighting
									calcSpecular(material.kS, l, n, nl, v, material.nShininess))));
				}
			}
		}
		return color;
	}

	/**
	 * A function that determines if a point is unshaded, meaning no objects block
	 * the light source.
	 * 
	 * @param gp          - the geometric point that is being checked
	 * @param lightSource - the source of the light
	 * @param l           - the vector from the point to the light source
	 * @param n           - the normal vector at the geometric point
	 * @param nv          - the dot product of the normal vector and the view vector
	 * @return - true if the point is unshaded, false if it is shaded
	 */

	private Color calcGlobalEffects(GeoPoint gPoint, Ray ray, int level, Double3 k) {

		Material material = gPoint.geometry.getMaterial();
		Point point = gPoint.point;
		Vector direction = ray.getDirection();
		Vector normal = gPoint.geometry.getNormal(point);

		return calcGlobalEffect(constructRefractedRay(point, direction, normal), material.kT, level, k)//
				.add(calcGlobalEffect(constructReflectedRay(point, direction, normal),//
						material.kR, level, k));

	}

	private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 stop) {
		
		//Color color = Color.BLACK;TODO: see that it is unnecessary
		
		Double3 kkx = stop.product(kx);

		if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
		
		GeoPoint gPoint = findClosestIntersection(ray);
		return gPoint == null ? Color.BLACK //
							  : calcColor(gPoint, ray, level - 1, kkx).scale(kx);
	}

	private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv) {

		// from point to light source, the opposite light direction from l
		Vector lightDirection = l.scale(-1);
		// move this point with the scaled normal
		Point point = gp.point.add(n.scale(nv < 0 ? DELTA : -DELTA));
		// create the ray with the new point
		Ray lightRay = new Ray(point, lightDirection);
		// calculate the intersections
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		// Return 1 if the list of intersections is empty
		if (intersections == null)
			return Double3.ONE;

		Double3 ktr = Double3.ONE;

		// the distance from the point to the light source
		double lightDistance = lightSource.getDistance(point);// or gp.point

		// if there are points in the intersections list that are closer to the point
		// than light source – product ktr with the kT of the geometry
		for (var item : intersections) {
			if (point.distance(item.point) < lightDistance) {
				ktr = ktr.product(item.geometry.getMaterial().kT);
				// if ktr is zero - stop
				if (ktr.equals(Double3.ZERO))
					break;
			}
		}
		return ktr;
	}

	/**
	 * The function calculates the amount of diffusive light that returns from the
	 * point equally in all directions according to the attenuation coefficient
	 * 
	 * @param kd is a diffusion attenuation factor
	 * @param nl is the angle between the lighting vector to the normal
	 * @return The color of the diffusive lighting
	 */
	private Double3 calcDiffusive(Double3 kd, double nl) {
		return kd.scale(Math.abs(nl));
	}

	/**
	 * The function calculates the color of the specular light returning from the
	 * point according to the attenuation coefficient
	 * 
	 * @param ks         is a specular attenuation factor
	 * @param l          is the vector between the light to the point we want to
	 *                   color
	 * @param n          is the normal to the body at the point
	 * @param nl         is the angle between the lighting vector to the normal
	 * @param v          is the ray from the camera to the point
	 * @param nShininess is the amount of the shininess that the material returns
	 * @return The color of the specular lighting at the point
	 */
	private Double3 calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess) {
		// Calculation of the vector that is reflected as a mirror of the vector that
		// comes out of the lighting to the point
		Vector r = constructReflectedVector(n, l, nl); // nl must not be zero!
		double minusVR = -alignZero(r.dotProduct(v));

		// The direction in which the light returns and the direction of
		// the beam from the camera are opposite or perpendicular
		return minusVR <= 0 ? Double3.ZERO //
							: ks.scale(Math.pow(minusVR, nShininess));
	}

	private Ray constructRefractedRay(Point point, Vector direction, Vector normal) {
		return new Ray(point, direction, normal);
	}

	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	private Ray constructReflectedRay(Point point, Vector direction, Vector normal) {

		double nv = normal.dotProduct(direction);
		return nv == 0 ? null : new Ray(point, constructReflectedVector(normal, direction, nv), normal);
	}

	private Vector constructReflectedVector(Vector normal, Vector v, double nv) {
		return v.add(normal.scale(-2 * nv));
	}

}