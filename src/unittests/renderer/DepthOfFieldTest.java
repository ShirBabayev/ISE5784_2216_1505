/*
 * package unittests.renderer;
 * 
 * import org.junit.jupiter.api.Test;
 * 
 * import geometries.*; import lighting.AmbientLight; import primitives.*;
 * import renderer.*; import scene.Scene; import static java.awt.Color.*;
 * 
 * /** Test rendering a basic image with Depth of Field effect
 * 
 */
/*
 * public class DepthOfFieldTest { /** Scene of the tests
 */
// private final Scene scene = new Scene("Depth of Field Test");
/** Camera builder of the tests */
/*
 * private final Camera.Builder camera = Camera.getBuilder().setRayTracer(new
 * SimpleRayTracer(scene)) .setLocation(Point.ZERO).setDirection(new Vector(0,
 * 0, -1), new Vector(0, 1, 0)).setVpDistance(100) .setVpSize(500, 500);
 */
/**
 * Produce a scene with basic 3D model and render it into a png image without
 * Depth of Field effect
 */
/*
 * @Test public void renderWithoutDepthOfField() { scene.geometries.add( new
 * Sphere(new Point(0, 0, -100), 50d), new Triangle(new Point(-100, 0, -100),
 * new Point(0, 100, -100), new Point(-100, 100, -100)), new Triangle(new
 * Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)),
 * new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
 * Point(100, -100, -100)) ); scene.setAmbientLight(new AmbientLight(new
 * Color(255, 191, 191), Double3.ONE)) .setBackground(new Color(75, 127, 90));
 * 
 * Camera camera1 = camera.setImageWriter(new
 * ImageWriter("without_depth_of_field", 1000, 1000)).build();
 * camera1.renderImage(); camera1.printGrid(100, new Color(YELLOW));
 * camera1.writeToImage(); }
 * 
 * /** Produce a scene with basic 3D model and render it into a png image with
 * Depth of Field effect
 */
/*
 * @Test public void renderWithDepthOfField() { scene.geometries.add( new
 * Sphere(new Point(0, 0, -100), 50d), new Triangle(new Point(-100, 0, -100),
 * new Point(0, 100, -100), new Point(-100, 100, -100)), new Triangle(new
 * Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)),
 * new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
 * Point(100, -100, -100)) ); scene.setAmbientLight(new AmbientLight(new
 * Color(255, 191, 191), Double3.ONE)) .setBackground(new Color(75, 127, 90));
 * 
 * Camera camera1 = camera.setImageWriter(new ImageWriter("with_depth_of_field",
 * 1000, 1000)).build(); ((SimpleRayTracer)
 * camera1.getRayTracer()).setDepthOfField(true, 100, 20, 100);
 * camera1.renderImage(); camera1.printGrid(100, new Color(YELLOW));
 * camera1.writeToImage(); } }
 */