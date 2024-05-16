/**
 * 
 */
package geometries;

import primitives.Ray;

/**
 * 
 */
public class Tube extends RadialGeometry {
final protected Ray axis;
public Tube(Ray r, double rad) {super(rad); axis=new Ray(r.direction, r.head); }
public Vector getNormal(Point p) {return null; }
}
