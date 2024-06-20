package lighting;

import primitives.*;

/**
 * A class that represents directional lighting - with equal lighting intensity in all directions, like solar lighting
 */
public class DirectionalLight extends Light implements LightSource {
	
	private Vector direction;
	
	public DirectionalLight(Color intensity, Vector direction){
		super(intensity);
		this.direction=direction;
	}
	
	public Color getIntensity(Point p) {
		return getIntensity();
	}
	
	public Vector getL(Point p) {
		return this.direction.normalize();
	}
}
