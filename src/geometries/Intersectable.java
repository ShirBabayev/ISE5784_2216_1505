package geometries;
import java.util.List;
import primitives.*;

/**
 *Interface for finding intersection points of a ray with the geometric body
 *
 * @author Hodaya Avidan and Shir Babayev
 */
public interface Intersectable {
	/**
	 * calculates the points where the ray intersects with the geometry
	 * @param ray - the ray that Intersects with the geometry
	 * @return a list of intersection point between the ray and the geometry
	 */
	public abstract List<Point> findIntersections(Ray ray);

}