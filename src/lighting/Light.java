package lighting;

import primitives.Color;

/**
 * A class representing light
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
abstract class Light {

	/**
	 * The color of the lighting intensity
	 */
	protected final Color intensity;

	/**
	 * A constructor that builds a light with intensity by the parameters
	 * 
	 * @param intensity is the color of the lighting intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * getter function to the intensity color
	 * 
	 * @return the intensity color
	 */
	public Color getIntensity() {
		return intensity;
	}

}
