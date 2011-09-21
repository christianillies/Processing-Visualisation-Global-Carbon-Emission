package info.christianillies.framework.common;

import processing.core.PApplet;
import info.christianillies.framework.events.EventDispatcher;
import info.christianillies.framework.events.IEventDispatcher;

/**
 * bounding circles are similar to bounding boxes but use circles instead of rectangles
 * @author christian illies
 *
 */
public class BoundingCircle extends EventDispatcher implements IEventDispatcher {
	
	protected int x;
	protected int y;
	protected float radius;
	
	/**
	 * constructor that assings x and y coordinates as the point in the middle and the radius of the circle
	 * @param x 
	 * @param y
	 * @param radius
	 */
	public BoundingCircle(int x, int y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	/**
	 * proves a collision between this object and the given.
	 * @param anotherCircle which will be proved for collision
	 * @return true if they're colliding, false otherwise
	 */
	public boolean collide(BoundingCircle anotherCircle) {
		final float radii = this.radius + anotherCircle.radius;
		final double xPow = Math.pow(Math.abs(anotherCircle.x - this.x), 2);
		final double yPow = Math.pow(Math.abs(anotherCircle.y - this.y), 2);
		final double shortestDistance = Math.sqrt(xPow+yPow);
		
		if(shortestDistance <= radii) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * proves a collision between this object and the given and the window edges.
	 * @param anotherCircle which will be proved for collision
	 * @return true if they're colliding, false otherwise
	 */
	public boolean collide(BoundingCircle anotherCircle, boolean careScreenEdges) {
		if(!careScreenEdges) {
			return collide(anotherCircle);
		}
		
		final int maxX = (int) (this.getX() + this.getRadius());
		final int maxY = (int) (this.getY() + this.getRadius());
		final int minX = (int) (this.getX() - this.getRadius());
		final int minY = (int) (this.getY() - this.getRadius());
		final PApplet p = FrameworkConstants.getPappletInstance();
		
		if(maxX > p.width || minX < 0) {
			return true;
		} else if(maxY > p.height || minY < 0) {
			return true;
		}
		
		return collide(anotherCircle);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param pX the x to set
	 */
	public void setX(int pX) {
		x = pX;
	}

	/**
	 * @param pY the y to set
	 */
	public void setY(int pY) {
		y = pY;
	}

	/**
	 * @param pRadius the radius to set
	 */
	public void setRadius(float pRadius) {
		radius = pRadius;
	}
}
