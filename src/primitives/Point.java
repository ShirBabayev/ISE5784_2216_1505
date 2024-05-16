package primitives;

public class Point {

	protected final Double3 xyz;
	public final Point ZERO= new Point(0.0,0.0,0.0);
	public Point (Double d1,Double d2,Double d3){
		xyz= new Double3(d1,d2,d3);
	}
	public Point (Double3 d){
		xyz = new Double3(d.d1,d.d2,d.d3);
	}
	/*@Override
	public boolean equals(Object obj) { 
		if (this == obj) return true;
		return (obj instanceof Point other)
				&&this.xyz.equals(obj.xyz);
		}*/
	@Override
	public boolean equals(Object obj) { 
		if (this == obj) return true;
		return (obj instanceof Point other)
				&&this.xyz.equals(other.xyz);
	}
	@Override
	public String toString() { 
		return xyz.toString();
	}
	public Point add(Vector v) {
		return new Point(xyz.add(v.xyz));
	}
	public Vector subtract(Point p) {
		return new Vector(xyz.subtract((p.xyz)));
	}
	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}
	public double distanceSquared(Point p) {
		return Math.pow(xyz.d1-p.xyz.d1,2) + Math.pow(xyz.d2-p.xyz.d2,2) + Math.pow(xyz.d3-p.xyz.d3,2);
	}
	
