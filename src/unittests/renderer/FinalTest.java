package unittests.renderer;

import java.util.Random;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

public class FinalTest {


	//TODO: add java doc
	@Test
	public void createTestScene() {
	    Scene scene = new Scene("test_scene");
	    // Light sources
	    //scene.lights.add(new SpotLight(new Color(128, 128, 128), new Point(0, 6, -300), new Vector(1,1,10)));
	    scene.lights.add(new SpotLight(new Color(192, 192, 192), new Point(-1, 7, -360), new Vector(1,1,1)));
	    //scene.lights.add(new SpotLight(new Color(150, 69, 0), new Point(0, 6, -270), new Vector(0,0,-2)));
	    Material mat = new Material().setKd(0.3).setKr(0.4).setKs(0.7).setShininess(1).setKt(0.1);
	    Material mat2 = new Material().setKd(0.3).setKs(0.7).setShininess(1).setKt(0.25);
	    Material mat3 = new Material().setKd(0.3).setKs(0.7).setKr(0.8).setShininess(500).setKt(0.5);
	    Material mat4 = new Material().setKd(0.3).setKr(0.05).setKs(0.7).setShininess(1);



	    Camera.Builder cameraBuilder = Camera.getBuilder()
	            .setRayTracer(new SimpleRayTracer(scene))
	            .setImageWriter(new ImageWriter("test_scene", 600, 600))
	            .setLocation(new Point(0, 10, 50))
	            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
	            .setVpDistance(100)
	            .setVpSize(50, 50)
	            .setFocalDistance(62.06)//62.06 //351.4
	            .setApertureRadius(0.35)
	            .setSqrtGridSize(8);

	    scene.geometries.add(
	    		//the moon
	    		new Sphere(new Point(10, 40, -300), 20)
	            .setEmission(new Color(192, 192, 192))
	            .setMaterial(mat2),

	            
	            //the sea
	            new Plane(new Point(12, 0, 0),new Point(-6, -0.09, 0), new Point(0, 0, -3.06))
	            .setEmission(new Color(0, 30, 60)) 
	            .setMaterial(mat4))/*,
	            
	            
	            //the sky
	            new Plane(new Point(9.77, 0, -450), new Point(-10.91, 0, -450), new Point(0, -2.95, -450))
	            .setEmission(new Color(100,100,235)) 
	            .setMaterial(mat),
	            
	            //something to cover the sky - make it look more real
	            new Plane(new Point(9.77, 0, -450), new Point(-10.91, 0, -450), new Point(0, -2.95, -450))
	            .setEmission(new Color(255, 94, 77)) 
	            .setMaterial(mat3))*/;
	    
	    Random rand = new Random();
	    //clouds - right side
        for (int k = 0; k < 200; k++) {
            double x1 =-15+ 100 * rand.nextDouble();
            double y1 = 28 + 2 * rand.nextDouble();
            double z1 = -200 - 10 * rand.nextDouble();
            double radius = 0.01 + 0.6*rand.nextDouble();
	        scene.geometries.add(
	            new Sphere(new Point(x1, y1, z1), radius)
	            .setEmission(new Color(128, 128, 128)) // Cloud color
	            .setMaterial(mat)
	        );
        }
      //clouds - left side
        for (int k = 0; k < 200; k++) {
            double x1 = -85 + 140 * rand.nextDouble();
            double y1 = 35 + 2.5 * rand.nextDouble();
            double z1 = -200 - 10 * rand.nextDouble();
            double radius1 = 0.01 + 0.6*rand.nextDouble();
	        scene.geometries.add(
	            new Sphere(new Point(x1, y1, z1), radius1)
	            .setEmission(new Color(128, 128, 128)) // Cloud color
	            .setMaterial(mat)
	        );
        }
        
        //stars
        for (int i = 0; i < 80; i++) {
	        double x = -40 + 100 * rand.nextDouble();
	        double y = 20 + 20 * rand.nextDouble();
	        double z = -100 + 40 * rand.nextDouble();
	        double radius = 0.01 + 0.15*rand.nextDouble();
	        scene.geometries.add(
	            new Sphere(new Point(x, y, z), radius)
	            .setEmission(new Color(119, 136, 153)) // Cloud color
	            .setMaterial(mat));
        }
     
        // Boat body (hull)
        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -12),
                new Point(-8, 0, -12),
                new Point(-7, 1, -12),
                new Point(-13, 1, -12))
            .setEmission(new Color(139, 69, 19)) // Brown color for the hull
            .setMaterial(mat2)
        );

        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -10),
                new Point(-8, 0, -10),
                new Point(-7, 1, -10),
                new Point(-13, 1, -10))
            .setEmission(new Color(139, 69, 19)) // Brown color for the hull
            .setMaterial(mat2)
        );

        scene.geometries.add(
            new Polygon(
                new Point(-13, 1, -12),
                new Point(-7, 1, -12),
                new Point(-7, 1, -10),
                new Point(-13, 1, -10))
            .setEmission(new Color(139, 69, 19)) // Brown color for the hull
            .setMaterial(mat2)
        );

        // Boat front (bow)
        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -12),
                new Point(-8, 0, -12),
                new Point(-10, 2, -13))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the bow
            .setMaterial(mat2)
        );

        // Boat back (stern)
        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -10),
                new Point(-8, 0, -10),
                new Point(-10, 1.5, -9))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the stern
            .setMaterial(mat2)
        );

        // Boat deck
        scene.geometries.add(
            new Polygon(
                new Point(-12, 1, -12),
                new Point(-8, 1, -12),
                new Point(-8, 1, -10),
                new Point(-12, 1, -10))
            .setEmission(new Color(139, 69, 19)) // Brown color for the deck
            .setMaterial(mat2)
        );

        // Boat sides
        scene.geometries.add(
            new Polygon(
                new Point(-13, 0, -12),
                new Point(-12, 0, -12),
                new Point(-12, 1, -12),
                new Point(-13, 1, -12))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the side
            .setMaterial(mat2)
        );

        scene.geometries.add(
            new Polygon(
                new Point(-8, 0, -12),
                new Point(-7, 0, -12),
                new Point(-7, 1, -12),
                new Point(-8, 1, -12))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the side
            .setMaterial(mat2)
        );

        // Boat mast
        scene.geometries.add(
            new Cylinder(
                new Ray(new Point(-10, 1, -11), new Vector(0, 1, 0)), 0.1, 5)
            .setEmission(new Color(160, 82, 45)) // Darker brown for the mast
            .setMaterial(mat3)
        );

        // Boat sails
        scene.geometries.add(
            new Triangle(
                new Point(-10, 6, -11),
                new Point(-11, 1, -11),
                new Point(-9, 1, -11))
            .setEmission(new Color(255, 255, 255)) // White color for the sail
            .setMaterial(mat4)
        );

        scene.geometries.add(
            new Triangle(
                new Point(-10, 4, -11),
                new Point(-10.5, 1, -11),
                new Point(-9.5, 1, -11))
            .setEmission(new Color(255, 255, 255)) // White color for the smaller sail
            .setMaterial(mat4)
        );

        // Boat railing
        for (int i = 0; i < 5; i++) {
            scene.geometries.add(
                new Cylinder(
                    new Ray(new Point(-12.8 + i * 1.4, 1, -11), new Vector(0, 1, 0)), 0.05, 0.5)
                .setEmission(new Color(160, 82, 45)) // Darker brown for the railing posts
                .setMaterial(mat3)
            );
        }

        scene.geometries.add(
            new Polygon(
                new Point(-12.8, 1.5, -11),
                new Point(-7.2, 1.5, -11),
                new Point(-7.2, 1.4, -11),
                new Point(-12.8, 1.4, -11))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the railing
            .setMaterial(mat3)
        );
	    scene.geometries.setBVH();

	    Camera camera = cameraBuilder.build();
	    camera.renderImage();
	    camera.writeToImage();
	}
}
