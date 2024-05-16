package primitives;

public class Vector extends Point {
	
	public Vector(double d1,double d2,double d3){
		super(d1,d2,d3);
		if(ZERO.equals(xyz))
			throw new IllegalArgumentException("Zero vector is illegal");
	}
	public Vector(Double3 d){
		super(d);
		if(ZERO.equals(xyz))
			throw new IllegalArgumentException("Zero vector is illegal");
	}
	
	@override
	public boolean equals (Object obj){
		if (this == obj) return true;
		return (obj instanceof Vector other)
				&&this.xyz.equals(other.xyz);	
	}
	@override
	public String toString(){
		return super.toString();
	}
	public double lengthSquared(){
		return this.dotProduct(this);
	}
	public double length(){ Math.sqrt(this.lengthSquared()); }
	public Vector add(Vector vector) { return new Vector(this.xyz.add(vector.xyz)); }
	public Vector scale(double d){ return new Vector(this.xyz.scale(d)); }
	public double dotProduct(Vector v) { return this.xyz.d1*v.xyz.d1+this.xyz.d2*v.xyz.d2+this.xyz.d3*v.xyz.d3; }
	public Vector crossProduct(Vector v) {
		return new Vector(this.xyz.d2*v.xyz.d3-this.xyz.d3*v.xyz.d2,this.xyz.d3*v.xyz.d1-this.xyz.d1*v.xyz.d3,this.xyz.d1*v.xyz.d2-this.xyz.d2*v.xyz.d1);
	}
	public Vector normalize() { return new Vector(this.xyz.d1/this.length(),this.xyz.d2/this.length(),this.xyz.d3/this.length()); }
}
