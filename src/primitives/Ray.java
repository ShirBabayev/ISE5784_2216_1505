package primitives;

public class Ray {
	private final Vector direction;
	private final Point head;
	public Ray(Vector dir, Point p) {head=p; direction=dir.normalize();}
		@override
	public boolean equals (Object o){
		if (this == obj) return true;
	return (obj instanceof Ray other)
			&&direction.equals(other.direction)
			&&head.equals(other.head); 
	}
	@override public String toString(){
		return head.toString()+direction.toString();
	}
}
