package lighting;

import primitives.*;

/**
 * A class that represents point lighting - lighting that comes from a point and
 * spreads around
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class PointLight extends Light implements LightSource {
	/**
	 * The point of origin of the light
	 */
	private Point position;
	/**
	 * constant attenuation coefficient
	 */
	private double kC = 1;
	/**
	 * Linear attenuation coefficient
	 */
	private double kL = 0;
	/**
	 * quadratic attenuation coefficient
	 */
	private double kQ = 0;

	/**
	 * A builder who builds our point lighting according to the parameters of the
	 * point of origin of the light and its intensity
	 * 
	 * @param position  is the point where the lighting comes from
	 * @param intensity is the intensity of the lighting
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * A function that allows editing of the constant attenuation coefficient
	 * 
	 * @param kC is constant attenuation coefficient
	 * @return the current lighting object
	 */
	public PointLight setKc(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * A function that allows editing of the Linear attenuation coefficient
	 * 
	 * @param kL is a linear attenuation coefficient
	 * @return the current lighting object
	 */
	public PointLight setKl(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * A function that allows editing of the quadratic attenuation coefficient
	 * 
	 * @param kQ is a quadratic attenuation coefficient
	 * @return the current lighting object
	 */
	public PointLight setKq(double kQ) {
		this.kQ = kQ;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double distance = position.distance(p);
		// Calculating the intensity of the color at a point by dividing
		// the color at the point by the distance of the light from the point,
		// the greater the distance the intensity will be smaller.
		return this.intensity.scale(1 / (kC + kL * distance + kQ * distance * distance));
	}

	@Override
	public Vector getL(Point p) {
		return p.subtract(position).normalize();
	}

}
