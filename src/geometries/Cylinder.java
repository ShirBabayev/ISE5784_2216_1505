/**
 * 
 */
package geometries;

/**
 * 
 */
public class Cylinder extends Tube {
final private double height;
public Cylinder(Ray r, double rad, double h) {super(r, rad); height=h;}
public Vector getNormal(Point p) {return null; }
}
