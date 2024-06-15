/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * 
 */
public class AmbientLight {
	/**
	 * 
	 */
	private final Color intensity;
	/**
	 * 
	 */
	public static AmbientLight NONE=new AmbientLight(Color.BLACK,0d);

	/**
	 * 
	 * @param Ia
	 * @param Ka
	 */
	public AmbientLight(Color Ia, Double3 Ka) {
		intensity = Ia.scale(Ka);
	}//בנאי שמקבל פרמטרים עבור , ומחשב את העצמה הסופית של תאורת מילוי שנשמרת בשדה
	/**
	 * 
	 * @param Ia
	 * @param Ka
	 */
	public AmbientLight(Color Ia, Double Ka) {
		intensity = Ia.scale(Ka);

	}
	/**
	 * 
	 * @return
	 */
	public Color getIntensity() {return intensity;}
}
