package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * A class that represents the ambient lighting for the scene and provides the
 * option to calculate the intensity of the color according to the attenuation
 * coefficient and the source color
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class AmbientLight {

	/**
	 * fill light intensity
	 */
	private final Color intensity;

	/**
	 * Default ambient lighting = black background with zero attenuation coefficient
	 */
	public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0d);

	/**
	 * The constructor calculates the intensity of the fill according to the
	 * original fill color and the light attenuation coefficient
	 * 
	 * @param iA is original fill color
	 * @param kA is light attenuation coefficient
	 */
	public AmbientLight(Color iA, Double3 kA) {
		intensity = iA.scale(kA);
	}

	/**
	 * The constructor calculates the intensity of the fill according to the
	 * original fill color and the light attenuation coefficient
	 * 
	 * @param iA is original fill color
	 * @param kA is light attenuation coefficient
	 */
	public AmbientLight(Color iA, Double kA) {
		intensity = iA.scale(kA);
	}

	/**
	 * getter function that returns the value of the ambient lighting intensity
	 * 
	 * @return the value of the ambient lighting intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
}
