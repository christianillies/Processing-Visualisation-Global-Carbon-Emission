package info.christianillies.visualisation.view;

import processing.core.PApplet;
import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.view.AbstractView;

/**
 * shows an half transparent information panel about this application
 * @author christian illies
 *
 */
public class InfoView extends AbstractView{

	/**
	 * papplet instance to draw text
	 */
	private PApplet _papplet;

	
	/**
	 * constuctor assigns papplet from framework
	 */
	public InfoView() {
		_papplet = FrameworkConstants.getPappletInstance();
	}
	
	@Override
	public void draw() {
		final int width = 600;
		final int height = 350;
		final int x = _papplet.width/2 - width/2;
		final int y = _papplet.height/2 - height/2;
		final int backgroundColor = _papplet.color(0,200);
		final int textColor = _papplet.color(164,166,106);
		final int lineHeight = 20;
		int line = 1;
		
		_papplet.noStroke();
		_papplet.fill(backgroundColor);
		_papplet.rect(x,y,width,height);
		_papplet.fill(textColor);
		_papplet.textAlign(PApplet.CENTER);
		
		_papplet.text(("global carbon emission visualisation by christian illies").toUpperCase(),x + width/2, y + lineHeight*line++);
		_papplet.text("programmed with java and processing v1.5.1",x + width/2, y + lineHeight*line++);
		line++;
		_papplet.text("This application is developed for showing the worldwide carbon emission.",x + width/2, y + lineHeight*line++);
		_papplet.text("You are able to select two different views to show the emission either ",x + width/2, y + lineHeight*line++);
		_papplet.text("per country or per capita, which shows a lot of differences.",x + width/2, y + lineHeight*line++);
		_papplet.text("Enable the debugmode by pressing 'd'. SavePower-mode is avaible,",x + width/2, y + lineHeight*line++);
		_papplet.text("if you press 's'.",x + width/2, y + lineHeight*line++);
		line++;
		_papplet.text("Used processing libraries: controlp5, shapetween and xlsreader.",x + width/2, y + lineHeight*line++);
		line++;
		_papplet.text("A production of the 'Hochschule Harz (FH)' ",x + width/2, y + lineHeight*line++);
		_papplet.text("University of Applied Sciences in Saxony-Anhalt in Wernigerode.",x + width/2, y + lineHeight*line++);
		line++;
		line++;
		_papplet.text("Copyright 2011. All rights reserved.",x + width/2, y + lineHeight*line++);
		
		_papplet.textAlign(PApplet.LEFT);
	}
	
	@Override
	public boolean equals(Object pObj) {
		return this.getClass() == pObj.getClass();
	}
	
	@Override
	public int hashCode() {
		return (this.getClass().getCanonicalName()).hashCode();
	}
}
