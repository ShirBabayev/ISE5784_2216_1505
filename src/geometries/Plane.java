package geometries;

import primitives.Vector;

public class Plane implements Geometry {
final private Point q;
final private Vector normal;
public Plane(Point p1,Point p2,Point p3) {normal=null; q=new Point(p1.xyz); }
public Plane(Point p, Vector v) {q=new Point(p.xyz); normal=new Vector(v.normalize());}
private Vector getNormal() {return normal;}/*check*/
private Vector getNormal(Point p) {return normal;}/*check*/


}
