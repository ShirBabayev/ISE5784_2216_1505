/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A class representing spot lighting - lighting emanating from a point in a specific direction
 */
public class SpotLight extends PointLight {
	/**
	 * The direction in which the lighting goes out
	 */
	private Vector direction;
	
	/**
	 * A builder who builds our spot lighting according to the parameters of the 
	 * point of origin of the light, the direction of the light and its intensity
	 * @param position is the point where the lighting comes from
	 * @param intensity is the intensity of the lighting
	 * @param direction is the direction in which the lighting shines
	 */
	public SpotLight(Color intensity,Point position,  Vector direction) {
		super(intensity,position);
		this.direction = direction;
	}
		
	/**
	 * A function that allows editing of the constant attenuation coefficient
	 * @return the current lighting object
	 */
	public SpotLight setKc(double kC) {
		return (SpotLight)super.setKc(kC);
	}
	
	/**
	 * A function that allows editing of the Linear attenuation coefficient
	 * @return the current lighting object
	 */
	public SpotLight setKl(double kL) {
		return (SpotLight)super.setKl(kL);

	}
	
	/**
	 * A function that allows editing of the quadratic attenuation coefficient
	 * @return the current lighting object
	 */
	public SpotLight setKq(double kQ) {
		return (SpotLight)super.setKq(kQ);
	}
	
	@Override
	public Color getIntensity(Point p) {
		//the getL of the father is working
		return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
	}
	

}
