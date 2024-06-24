
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * An interface representing source of light by original color and a point in space around
 */
public interface LightSource {
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Color getIntensity(Point p);
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Vector getL(Point p);
}
