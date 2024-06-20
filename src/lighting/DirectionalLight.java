/**
 * 
 */
package lighting;

import primitives.*;

/**
 * 
 */
public class DirectionalLight extends Light implements LightSource {
	private Vector direction;
	DirectionalLight(Color intensity, Vector direction){
		super(intensity);
		this.direction=direction;
	}
	
	public Color getIntensity(Point p) {return getIntensity();}
	public Vector getL(Point p) {return this.direction.normalize();}
}
