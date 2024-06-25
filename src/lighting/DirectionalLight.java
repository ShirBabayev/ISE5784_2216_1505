package lighting;

import primitives.*;

/**
 * A class that represents directional lighting - with equal lighting intensity
 * in all directions, like solar lighting
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class DirectionalLight extends Light implements LightSource {

	/**
	 * The direction of the light
	 */
	private final Vector direction;

	/**
	 * A constructor that build the directional light by direction of the light and
	 * intensity
	 * 
	 * @param intensity is the color of the body
	 * @param direction is the direction of the light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return this.intensity;
	}

	@Override
	public Vector getL(Point p) {
		return this.direction;
	}
}
