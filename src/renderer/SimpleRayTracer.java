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
		GeoPoint point = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
		// return the color that we need to color the pixel that the ray intersect
		return point == null ? scene.background : calcColor(point, ray);
	}

	/**
	 * The function receives the point closest to the head of the ray and calculates
	 * the color of the point on the geometric body for painting the intersected
	 * pixel
	 * 
	 * @param gpoint - The point closest to the head of the ray
	 * @param ray    is the ray from the camera to the point
	 * @return Body color at this point
	 */
	private Color calcColor(GeoPoint gpoint, Ray ray) {
		// Adding the lighting effect on the color to the intensity
		return scene.ambientLight.getIntensity().add(calcLocalEffects(gpoint, ray));
	}

	/**
	 * The function calculates the effect of lighting on the color
	 * 
	 * @param intersection is a point on a geometric body
	 * @param ray          is a ray that passes through the view plane
	 * @return the color for painting the pixel
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
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
		// the shininess that the material returns
		int nShininess = material.nShininess;
		// the color of the geometry
		Color color = intersection.geometry.getEmission();

		for (LightSource lightSource : scene.lights) {
			// the vector between the light to the point we want to color
			Vector l = lightSource.getL(intersection.point);
			// The angle between the lighting vector to the normal
			double nl = alignZero(n.dotProduct(l));
			// The effect of the lighting reach the camera because of the angles
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(lightIntensity.scale(
						// The lighting that returns from the body equally in all directions
						calcDiffusive(material.kD, nl).add(
								// The ray that returns as a reflection of the ray that hit the body from the
								// lighting
								calcSpecular(material.kS, l, n, nl, v, nShininess))));
			}
		}
		return color;
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
		Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
		double minusVR = -alignZero(r.dotProduct(v));

		// The direction in which the light returns and the direction of
		// the beam from the camera are opposite or perpendicular
		return minusVR <= 0 ? Double3.ZERO //
				: ks.scale(Math.pow(minusVR, nShininess));
	}
}
