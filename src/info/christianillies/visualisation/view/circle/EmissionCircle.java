/**
 * 
 */
package info.christianillies.visualisation.view.circle;

import info.christianillies.framework.common.BoundingCircle;
import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.visualisation.main.MainViewClass;
import megamu.shapetween.Tween;
import processing.core.PApplet;

/**
 * circle objects for every country. represents carbon emission by land or per capita.
 * @author christian illies
 *
 */
public class EmissionCircle extends BoundingCircle {

	private PApplet _papplet;
	private final String _country;
	private final float _emission;
	private final float _percentage;
	private Tween _animation;
	private Tween _colorInAnimation;
	private Tween _colorOutAnimation;

	/**
	 * constructor assigns parameters
	 * @param pEmission emission data
	 * @param pCountry country name
	 * @param pPercentage total percentage
	 * @param pX x-coordinate
	 * @param pY y-coordinate
	 * @param pRadius radius of the circle
	 */
	public EmissionCircle(String pCountry, float pEmission, float pPercentage, int pX, int pY, float pRadius) {
		super(pX, pY, pRadius);
		_country = pCountry;
		_emission = pEmission;
		_percentage = pPercentage;
		_papplet = FrameworkConstants.getPappletInstance();
		_animation = null;
	}
	
	/**
	 * draws the circle only.
	 */
	public void draw() {
		
		if(_animation == null) {
			_animation = new Tween(_papplet, .25f, Tween.SECONDS);
			if(MainViewClass.SAVE_POWER)
			{
				_animation.pre();
			}
		}
		
		_papplet.ellipseMode(PApplet.RADIUS);
		
		final BoundingCircle mousePositionCircle = new BoundingCircle(_papplet.mouseX, _papplet.mouseY, 0);
		if(this.collide(mousePositionCircle)) {
			/*
			 * color animation 
			 */
			_colorOutAnimation = null;
			if(_colorInAnimation == null) {
				_colorInAnimation = new Tween(_papplet, 0.5f, Tween.SECONDS);
				if(MainViewClass.SAVE_POWER)
				{
					_colorInAnimation.pre();
				}
			}
			final float lerpRColor = PApplet.lerp(95 , 200, _colorInAnimation.position());
			final float lerpGColor = PApplet.lerp(103, 222, _colorInAnimation.position());
			final float lerpBColor = PApplet.lerp(107,  89, _colorInAnimation.position());
			/*
			 * ellipse style
			 */
			_papplet.fill(lerpRColor,lerpGColor,lerpBColor);
		} else {
			/*
			 * ellipse style
			 */
			_colorInAnimation = null;
			if(_colorOutAnimation == null) {
				_colorOutAnimation = new Tween(_papplet, 0.5f, Tween.SECONDS);
				if(MainViewClass.SAVE_POWER)
				{
					_colorOutAnimation.pre();
				}
			}
			final float lerpRColor = PApplet.lerp(200,  95, _colorOutAnimation.position());
			final float lerpGColor = PApplet.lerp(222, 103, _colorOutAnimation.position());
			final float lerpBColor = PApplet.lerp(89,  107, _colorOutAnimation.position());
			
			_papplet.fill(lerpRColor,lerpGColor,lerpBColor);
		}
		
		final float animationStep = PApplet.lerp(0, radius, _animation.position());
		
		/*
		 * draw the ellipse
		 */
		_papplet.ellipse(x, y, animationStep, animationStep);
		
		/*
		 * reset ellipseMode to default
		 */
		_papplet.ellipseMode(PApplet.CENTER);
	}
	
	/**
	 * special method to draw text only if mouse hovers the circle. need to avoid circles covering the text.
	 */
	public void drawText() {
		final BoundingCircle mousePositionCircle = new BoundingCircle(_papplet.mouseX, _papplet.mouseY, 0);
		
		/*
		 * text will be displayed if mouse hovers the ellipse
		 */
		if(this.collide(mousePositionCircle)) {
			float textX = getX();
			/*
			 * standard text height : above the circle
			 */
			float textY = getY() - getRadius() - 10;
			final String textInfo = Math.round(_emission) + " by " + _country + " ("+Math.round(_percentage)+"%)";
			
			_papplet.fill(_papplet.color(230,245,154));
			
			/*
			 * align the text in the middle of the ellipse if the textwidth fits into it
			 */
			if(_papplet.textWidth(textInfo) <= getRadius()*2 - 5) {
				textY = getY();
				_papplet.fill(_papplet.color(95,103,107));
			} else if(_papplet.textWidth(textInfo)/2 + textX > _papplet.width) {
				/*
				 * if textX plus textwidth is above the screen edges
				 */
				textX = _papplet.width - _papplet.textWidth(textInfo)/2 - 5;
			} else if(textX - _papplet.textWidth(textInfo)/2 <= 0) {
				/*
				 * if textX minus textwidth is under 0
				 */
				textX = _papplet.textWidth(textInfo)/2 + 5;
			}
			
			_papplet.textAlign(PApplet.CENTER);
			_papplet.text(textInfo, textX, textY);
			_papplet.textAlign(PApplet.LEFT);
		}
	}
}
