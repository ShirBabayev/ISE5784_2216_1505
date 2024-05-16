package geometries;

public class Sphere extends RadialGeometry {
final private Point center;
public Sphere(Point p, double rad) {super(rad); center=new Point(p.xyz);}
public Vector getNormal(Point p) {return null;}

}
