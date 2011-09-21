package info.christianillies.framework.common;

import info.christianillies.framework.events.Event;
import info.christianillies.framework.events.EventDispatcher;
import info.christianillies.framework.events.IEvent;

import java.util.ArrayList;

/**
 * bounding boxes to check for collisions between two or more other boxes in a two dimensions world.
 * @author christian illies
 *
 */
public class BoundingBox extends EventDispatcher {
	
	/**
	 * box collided event
	 */
	public static final IEvent BOX_COLLIDED = new Event("collidingboxIsColliding");

	/**
	 * collided box isnt colliding anymore event
	 */
	public static final IEvent BOX_IS_NOT_COLLIDING_ANYMORE = new Event("boxIsntCollidingAnymore");
	
	/**
	 * all boxes that will be checked for collision if 'collide' is called
	 */
	private ArrayList<BoundingBox> _otherBoundingBoxes;
	
	protected int x1, y1, x2, y2;
	
	/**
	 * current box that is colliding, or null if no box is colliding
	 */
	private BoundingBox _currentCollidingBox;
	
	/**
	 * constructor sets coordinates 
	 * @param x1 x position
	 * @param y1 y position
	 * @param x2 x2 position. if you comitted a width add x (i.e. x+width) to obtain x2 position
	 * @param y2 y2 position. if you comitted a height add y (i.e. y+height) to obtain y2 position
	 */
	public BoundingBox(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		_currentCollidingBox = null;
		_otherBoundingBoxes = new ArrayList<BoundingBox>();
	}
	
	/**
	 * proves a collision between this object and the given.
	 * @param anotherBox which will be proved for collision
	 * @return true if they're colliding, false otherwise
	 */
	public boolean collide(BoundingBox anotherBox) {
		if(		   this.x2 >= anotherBox.x1 
				&& this.y2 >= anotherBox.y1 
				&& anotherBox.x2 >= this.x1 
				&& anotherBox.y2 >= this.y1		
		) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * returns true if any box is colliding
	 * @return true on collide else false
	 */
	public boolean collide() {
		boolean ret = false;
		
		if(getCurrentCollidingBox() != null) 
		{
			if(collide(getCurrentCollidingBox())) {
				ret = true;
				dispatchEvent(BOX_COLLIDED);
			} else {
				ret = false;
				setCurrentCollidingBox(null);
				dispatchEvent(BOX_IS_NOT_COLLIDING_ANYMORE);
			}
		}
		if(!ret) 
		{
			for (BoundingBox box : _otherBoundingBoxes) {
				if(collide(box)) {
					ret = true;
					setCurrentCollidingBox(box);
					dispatchEvent(BOX_COLLIDED);
					break; /* one box is still colliding so we don't need to search for more boxes. */
				}
			}
		}
		return ret;
	}
	
	/**
	 * returns current colliding box
	 * @return returns the current colliding box
	 */
	public BoundingBox getCurrentCollidingBox() {
		return _currentCollidingBox;
	}
	
	/**
	 * sets the current colliding box
	 * @param box the new colliding box
	 */
	public void setCurrentCollidingBox(BoundingBox box) {
		_currentCollidingBox = box;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		if(obj.getClass() == this.getClass())
		{
			BoundingBox tmp = (BoundingBox) obj;
			if(	tmp.x1 == this.x1 &&
				tmp.x2 == this.x2 &&
				tmp.y1 == this.y1 &&
				tmp.y2 == this.y2) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * adds a new bounding box if not containing to the list that will be checked on method 'collide()'
	 * @param b the new bounding box
	 */
	public void addBoundingBox(BoundingBox b) {
		if(!_otherBoundingBoxes.contains(b)) {
			_otherBoundingBoxes.add(b);
		}
	}
	
	/**
	 * deletes the given boundingbox from the list
	 * @param b the box
	 * @return {@code true}  if this list contained the specified element
	 */
	public boolean removeBoundingBox(BoundingBox b) {
		return _otherBoundingBoxes.remove(b);
	}
	
	/**
	 * clears the bounding box list
	 */
	public void removeAllBoundingBoxes() {
		_otherBoundingBoxes.clear();
	}
	
	/**
	 * sets new coordinates for all attributes
	 * @param x1 x position
	 * @param y1 y position
	 * @param x2 x2 position. if you comitted a width add x (i.e. x+width) to obtain x2 position
	 * @param y2 y2 position. if you comitted a height add y (i.e. y+height) to obtain y2 position
	 */
	public void setBoundingBoxPosition(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[x1:"+x1+",y1:"+y1+",x2:"+x2+",y2:"+y2+"]";
	}
}
