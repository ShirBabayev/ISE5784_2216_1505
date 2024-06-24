package primitives;

/**
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Material {

	/**
	 * Diffusion attenuation factor
	 */
	public Double3 kD= Double3.ZERO;
	/**
	 * Specular attenuation factor
	 */
	public Double3 kS=Double3.ZERO;
	/**
	 * the amount of shininess that the material return
	 */
	public int nShininess=0;

	/**
	 * setter function for the 
	 * @param kD
	 * @return 
	 */
	public Material setKd(Double3 kD) {
		this.kD=kD;
		return this;
	}
	public Material setKd(double kD) {
		this.kD=new Double3(kD);
		return this;
	}
	public Material setKs(Double3 kS) {
		this.kS=kS;
		return this;
	}
	public Material setKs(double kS) {
		this.kS=new Double3(kS);
		return this;
	}
	public Material setShininess(int nShininess) {
		this.nShininess=nShininess;
		return this;
	}
	

	
	
}
