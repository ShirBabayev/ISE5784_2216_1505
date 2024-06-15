package renderer;

import primitives.*;

import scene.Scene;

public abstract class RayTracerBase {
	protected Scene scene;
	
	public RayTracerBase(Scene s) {
		scene=s;
	}

	public abstract Color traceRay(Ray ray);

}
