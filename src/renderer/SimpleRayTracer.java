package renderer;

import primitives.*;
import lighting.LightSource;
import scene.Scene;
import static primitives.Util.*;

import geometries.Intersectable.GeoPoint;

/**
 * A class representing the path of a simple ray
 * 
 * @author Shir Babayev and Hodaya Avidan
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * The initial attenuation factor for color calculations.
	 */
	private static final Double3 INITIAL_K = Double3.ONE;

	/**
	 * max parameter for the level of the recursion
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;

	/**
	 * a number used to moving the head of the ray towards the light source
	 */
	private static final double DELTA = 0.1;

	/**
	 * The minimum threshold value for color calculation attenuation.
	 */
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

	/**
	 * A recursive function that calculates the color of the body at a point of
	 * intersection and its global effects
	 * 
	 * @param gp    The point for which the color is calculated
	 * @param ray   The ray that hits the point on the body
	 * @param level The stop level in the recursion, as level=1
	 * @param k     is A parameter according to which an additional stopping
	 *              condition of the recursion is considered
	 * @return the color of the point on the body
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray, k);
		return 1 == level ? color//
				: color.add(calcGlobalEffects(gp, ray, level, k));
	}

	/**
	 * Calculates the local lighting effects at a given intersection point on a
	 * geometry.
	 *
	 * @param intersection The intersection point and the geometry it intersects.
	 * @param CameraRay    The ray from the camera to the intersection point.
	 * @param k            The attenuation factor for the color.
	 * @return A Color value representing the resulting color at the intersection
	 *         point.
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray CameraRay, Double3 k) {
		// the ray from the camera to the point
		Vector direction = CameraRay.getDirection();
		// the normal from the intersection point on the body
		Vector normal = intersection.geometry.getNormal(intersection.point);
		// if the ray is orthogonal to the normal of the geometry
		double ndir = alignZero(normal.dotProduct(direction));
		// The ray is parallel to the tangent to the body at a point
		if (ndir == 0)
			// return the background color
			return Color.BLACK;

		// the material of the body
		Material material = intersection.geometry.getMaterial();
		// the color of the geometry
		Color color = intersection.geometry.getEmission();

		for (LightSource lightSource : scene.lights) {
			// the vector between the light to the point we want to color
			Vector lightRay = lightSource.getL(intersection.point);
			// The angle between the lighting vector to the normal
			double nlight = alignZero(normal.dotProduct(lightRay));
			// The effect of the lighting reach the camera because of the angles
			if (nlight * ndir > 0) { // sign(nlight) == sign(ndir)
				// if it is not shaded - the light hits it
				Double3 ktr = transparency(intersection, lightSource, lightRay, normal, ndir);
				if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
					// the intensity of the light at the intersection point
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					// add the diffusive and specular components to the color
					color = color.add(lightIntensity.scale(
							// The lighting that returns from the body equally in all directions
							calcDiffusive(material.kD, nlight).add(
									// The ray that returns as a reflection of the ray that hit the body from the
									// lighting
									calcSpecular(material.kS, lightRay, normal, nlight, direction,
											material.nShininess))));
				}
			}
		}
		return color;
	}

	/**
	 * Calculates the environmental effects of transparency and reflectance at a
	 * particular intersection point.
	 * 
	 * @param gPoint intersection point on body
	 * @param ray    is a ray that intersect the body at the point
	 * @param level  is The level of the recursion, when it equals 1 we will stop
	 * @param k      is A parameter according to which an additional stopping
	 *               condition of the recursion is considered
	 * @return The color at the intersection point considering global effects.
	 * 
	 */
	private Color calcGlobalEffects(GeoPoint gPoint, Ray ray, int level, Double3 k) {

		Material material = gPoint.geometry.getMaterial();
		Point point = gPoint.point;
		Vector direction = ray.getDirection();
		Vector normal = gPoint.geometry.getNormal(point);

		// Examination of the environmental effects of the point in the direction of
		// transparency and reflection
		return calcGlobalEffect(constructRefractedRay(point, direction, normal), material.kT, level, k)//
				.add(calcGlobalEffect(constructReflectedRay(point, direction, normal), //
						material.kR, level, k));

	}

	/**
	 * The function checks if the reflected ray intersects with another point and
	 * colors it with the appropriate color using a calculation with the attenuation
	 * coefficients, if there is no intersection point returns the background color
	 * 
	 * @param ray       is A ray emanating from a point painted in a reflected
	 *                  direction
	 * @param k         is the attenuation coefficient
	 * @param level     is The level of the recursion, when it equals 1 we will stop
	 * @param condition is A parameter according to which an additional stopping
	 *                  condition of the recursion is considered
	 * @return Returns the color in which the point where the beam intersected
	 *         should be painted or the background color if there was no
	 *         intersection
	 */
	private Color calcGlobalEffect(Ray ray, Double3 k, int level, Double3 condition) {

		Double3 kkx = condition.product(k);

		// Checking whether the number is so small that the color is imperceptible
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;

		// find the closet point
		GeoPoint gPoint = findClosestIntersection(ray);
		return gPoint == null ? scene.background
				// calculate the color at gPoint and scale in an attenuation coefficient
				: calcColor(gPoint, ray, level - 1, kkx).scale(k);
	}

	/**
	 * Calculates the transparency factor at a given point on a geometry towards a
	 * light source.
	 *
	 * @param gp                The intersection point and the geometry it
	 *                          intersects.
	 * @param lightSource       The light source being evaluated.
	 * @param directionLightRay The direction vector from the point to the light
	 *                          source.
	 * @param normal            The normal vector at the point.
	 * @param nv                The dot product of the normal vector and the view
	 *                          direction vector.
	 * @return A Double3 value representing the transparency factor.
	 */
	private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector directionLightRay, Vector normal,
			double nv) {

		// from point to light source, the opposite light direction from l
		Vector lightDirection = directionLightRay.scale(-1);
		// move this point with the scaled normal
		Point point = gp.point.add(normal.scale(nv < 0 ? DELTA : -DELTA));
		// create the ray with the new point
		Ray lightRay = new Ray(point, lightDirection);
		// calculate the intersections
		var intersections = scene.geometries.findGeoIntersections(lightRay);
		// Return 1 if the list of intersections is empty
		if (intersections == null)
			// All light reaches a point
			return Double3.ONE;

		Double3 ktr = Double3.ONE;

		// the distance from the point to the light source
		double lightDistance = lightSource.getDistance(point);// or gp.point

		// if there are points in the intersections list that are closer to the point
		// than light source – product ktr with the kT of the geometry
		for (var item : intersections) {
			if (point.distance(item.point) < lightDistance) {
				// The transparency factor (ktr) is multiplied by the transparency factor (kT)
				// of the geometry at the point of intersection
				ktr = ktr.product(item.geometry.getMaterial().kT);
				// if ktr is zero - stop
				if (ktr.lowerThan(MIN_CALC_COLOR_K))
					return Double3.ZERO;
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

	/**
	 * The function builds a ray that is reflected in the opposite direction on the
	 * normal axis
	 * 
	 * @param point     is the intersection point on the body
	 * @param direction is the direction of the ray that intersects the body at a
	 *                  point
	 * @param normal    is the normal to the body at a point
	 * @return The opposite ray is the ray of the direction starting from the point
	 *         of intersection in the opposite direction reflected on the normal
	 *         axis
	 */
	private Ray constructRefractedRay(Point point, Vector direction, Vector normal) {
		return new Ray(point, direction, normal);
	}

	/**
	 * A function that calculates the intersection point closest to the top of the
	 * ray
	 * 
	 * @param ray is a ray for which its closet intersection point are checked
	 * @return The intersection point closest to the top of the ray
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * A function that constructs a ray that is reflected like a mirror on the
	 * normal axis according to the direction of the original ray, the normal and a
	 * point
	 * 
	 * @param point     is the intersection point on the body
	 * @param direction is the direction of the ray that intersects the body at a
	 *                  point
	 * @param normal    is the normal to the body at a point
	 * @return A ray that is reflected like a mirror on the normal axis
	 */
	private Ray constructReflectedRay(Point point, Vector direction, Vector normal) {

		double nv = normal.dotProduct(direction);
		return nv == 0 ? null : new Ray(point, constructReflectedVector(normal, direction, nv), normal);
	}

	/**
	 * A function that constructs a vector that is reflected like a mirror on the
	 * normal axis according to the direction, normal and the angle between them
	 * 
	 * @param normal    is the normal to the body at a point
	 * @param direction is the direction of the ray that intersects the body at a
	 *                  point
	 * @param nv        the angle between direction to normal
	 * @return A vector that is reflected like a mirror on the normal axis
	 */
	private Vector constructReflectedVector(Vector normal, Vector direction, double nv) {
		return direction.add(normal.scale(-2 * nv));
	}

}