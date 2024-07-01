
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * An interface representing source of light by original color and a point in
 * space around
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public interface LightSource {

	/**
	 * A function that returns the intensity of the color at a certain point
	 * 
	 * @param p is the point we consider its strength
	 * @return the color after calculating the intensity according to the lighting
	 */
	public Color getIntensity(Point p);

	/**
	 * A function that returns the direction normalized vector between the starting
	 * point of the light and a certain point
	 * 
	 * @param p is a point within the scene
	 * @return normalized vector between the point of origin of the light and the
	 *         point
	 */
	public Vector getL(Point p);

	/**
	 * A function that calculates the distance from the current point to the given
	 * point.
	 *
	 * @param p the point to calculate the distance
	 * @return the distance from the current point to the given point
	 */
	double getDistance(Point p);
}
