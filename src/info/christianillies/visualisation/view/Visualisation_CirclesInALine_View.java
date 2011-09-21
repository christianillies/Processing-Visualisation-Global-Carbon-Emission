package info.christianillies.visualisation.view;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.visualisation.model.XlsWorldDatabankModel;
import info.christianillies.visualisation.view.circle.EmissionCircle;

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * draws emission circles in a line alphabetically sorted.
 * @author Christian illies
 *
 */
public class Visualisation_CirclesInALine_View extends AbstractVisualisationView {

	private PApplet _papplet;

	public Visualisation_CirclesInALine_View() {
		_papplet = FrameworkConstants.getPappletInstance();
	}
	
	@Override
	public void draw() {
		XlsWorldDatabankModel xlsmodel = (XlsWorldDatabankModel) getModel();
		
		System.out.println(_currentYear +"/"+_indicatorIndex);
		
		xlsmodel.setYear(_currentYear);
		xlsmodel.setIndicator(_indicatorIndex);
		String indicatorName = xlsmodel.getIndicatorNames()[_indicatorIndex];
		
		final String[] countries = xlsmodel.getCountryNames();
		/*
		 * sum of all emissions worldwide for computing percentages
		 */
		final double sumOfAllEmissions = xlsmodel.getSumOfAllValues();
		
		/*
		 * the maximum in this year by a country
		 */
		final double maxEmissionValue = xlsmodel.getMaxEmissionValue();
		
		/*
		 * max size of the circles
		 */
		final int maxSize = 75;
		
		_papplet.noStroke();
		
		/*
		 * short information text
		 */
		_papplet.fill(_papplet.color(164,166,106));
		_papplet.text(indicatorName + " in " + _currentYear, 30, 200);
		
		/*
		 * if any country have no data, a short message will be displayed
		 */
		if(sumOfAllEmissions == 0d) {
			_papplet.fill(_papplet.color(164,166,106));
			_papplet.textAlign(PApplet.CENTER);
			_papplet.text("no data for the year " + _currentYear + " found.", _papplet.width/2, _papplet.height/2);
			_papplet.textAlign(PApplet.LEFT);
			return;
		} 
		
		int circleX = 50;
		int circleY = _papplet.height/2;
		
		int appreciableCountries = 0;
		
		/*
		 * TODO debug
		 */
		ArrayList<EmissionCircle> circleList = new ArrayList<EmissionCircle>();
		
		for (int i = 0; i < countries.length; i++) {
			
			final float emission = xlsmodel.setCountry(i).value();
			
			/*
			 * countries with no emission (or no data) wont be displayed
			 */
			if(emission > 0) {

				final float size = PApplet.map(emission, 0, (float) maxEmissionValue, 1, maxSize);
				if(size > 0.9f) { // hides not notable countries from screen

					final float percentage = (float) (emission/sumOfAllEmissions*100);

					EmissionCircle circle;
					boolean colliding = false;
					_papplet.pushMatrix();
					
					do {
						circle = new EmissionCircle(countries[i], emission, percentage, circleX, circleY, size);
						
						circleX = circleX+1;
						circleY = circleY+0;
						
						for (int j = 0; j < circleList.size(); j++) {
							if(circleList.get(j).collide(circle, false)) {
								colliding = true;
								break;
							} else {
								colliding = false;
							}
						}
					}while(colliding);
					
					
					circle.draw();
					circleList.add(circle);
					
					_papplet.popMatrix();
					
					appreciableCountries++;
				}
			}
		}
		
		System.out.println("appreciable Countries: "+appreciableCountries);
	}

	
}
