/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * 
 */
public class SpotLight extends PointLight {
	private Vector direction;
	//TODO: בגלל שהם אמרו לדרוס את הסטרים אני תוהה אם צריך אובריד
	public SpotLight(Point position, Color intensity, Vector direction) {
		super(position, intensity);
		this.direction = direction;
	}
	public SpotLight setKC(double kC) {
		return (SpotLight)super.setKC(kC);
	}
	public SpotLight setKL(double kL) {
		return (SpotLight)super.setKC(kL);

	}
	public SpotLight setKQ(double kQ) {
		return (SpotLight)super.setKC(kQ);
	}
	public Color getIntensity(Point p) {
		//the getL of the father is working
		return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
	}
	

}
