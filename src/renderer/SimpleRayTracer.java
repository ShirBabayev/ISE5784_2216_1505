package renderer;

import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;

import scene.Scene;

import static primitives.Util.alignZero;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

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
	 * of the point on the geometric body *
	 * 
	 * @param point The point closest to the top of the ray
	 * @return Body color at this point
	 */
	private Color calcColor(GeoPoint gpoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(gpoint, ray));
	}

	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
	    Vector v = ray.getDirection();
	    Vector n = intersection.geometry.getNormal(intersection.point);
	    double nv = alignZero(n.dotProduct(v));
	    if (nv == 0)
	        return Color.BLACK;

	    int nShininess = intersection.geometry.getMaterial().nShininess;

	    Double3 kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;

	    Color color = Color.BLACK;
	    for (LightSource lightSource : scene.lights) {
	    Vector l = lightSource.getL(intersection.point);
	    double nl = alignZero(n.dotProduct(l));

	    if (nl * nv > 0) { // sign(nl) == sign(nv)
	    Color lightIntensity = lightSource.getIntensity(intersection.point);
	    color = color.add(calcDiffusive(kd, nl, lightIntensity),
	    calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
	    }
	    }
	    return color;
	    }

	private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
	     return lightIntensity.scale(kd.scale(Math.abs(nl)));
	     }

	    
	    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess,
	                               Color lightIntensity) {
	        Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
	        double minusVR = -alignZero(r.dotProduct(v));
	        if (minusVR <= 0) {
	            return new primitives.Color(Color.BLACK.getColor()); // View from direction opposite to r vector
	        }
	        return lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
	    }
}
