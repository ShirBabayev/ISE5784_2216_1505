/**
 * 
 */
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.ImageWriter;
//import java.util.*;

/**
 * Unit tests for Image Writer Test
 * 
 * @author Shir Babayev and Hodaya Avidan
 */

class ImageWriterTest {
	/**
	 * an imageWriter board for the test
	 */
	private final ImageWriter imageWriter=new ImageWriter("image",800,500);
	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}
	 */
	@Test
	void writeToImage() {
		// Loop through all columns of the image
		for (int i=0;i<imageWriter.getNx();i++) {//i<800
			// Check if the column index is a multiple of 50
			if ((i % 50)==0)//isZero(i % 50) is not working
				// If true, color the entire column in red
				for (int j=0;j<imageWriter.getNy();j++) //i<500
					imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
			else
				// Otherwise, loop through all rows of the current column
				for (int j=0;j<imageWriter.getNy();j++) 
					// Check if the row index is a multiple of 50
					if ((j % 50)==0) 
						// If true, color the pixel in black
						imageWriter.writePixel(i, j, new Color(java.awt.Color.BLACK));
					else {
						// Otherwise, color the pixel in light-blue
						imageWriter.writePixel(i, j, new Color(java.awt.Color.CYAN));
					}	
		}
			imageWriter.writeToImage();//write the image to the file
		}

}
