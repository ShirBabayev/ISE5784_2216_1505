package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource {
	private Point position;
	private double kC=1;
	private double kL=0;
	private double kQ=0;

	public PointLight(Point position, Color intensity) {
		super(intensity);
		this.position = position;
	}

	public PointLight setKC(double kC) {
		this.kC = kC;
		return this;
	}

	public PointLight setKL(double kL) {
		this.kL = kL;
		return this;
	}

	public PointLight setKQ(double kQ) {
		this.kQ = kQ;
		return this;
	}
	
	public Color getIntensity(Point p) {
		double distance = position.distance(p);
		//Calculating the intensity of the color at a point by dividing
		//the color at the point by the distance of the light from the point,
		//the greater the distance the intensity will be smaller.
		return getIntensity().scale(1/(kC + kL*distance + kQ*distance*distance));
    }
	public Vector getL(Point p) {return p.subtract(position).normalize();}//ממקור האור לנקודה המוארת

}
