package info.christianillies.visualisation.view;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.visualisation.model.XlsWorldDatabankModel;
import info.christianillies.visualisation.view.circle.EmissionCircle;

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * draws EmissionCircles randomly and caches the position for drawing.
 * @author Christian Illies
 *
 */
public class Visualisation_RandomCirclePosition_View extends AbstractVisualisationView {

	private PApplet _papplet;
	private ArrayList<EmissionCircle> _circleList;

	/**
	 * constructor assigns papplet instance from framework
	 */
	public Visualisation_RandomCirclePosition_View() {
		_papplet = FrameworkConstants.getPappletInstance();
	}
	
	@Override
	public void draw() {
		XlsWorldDatabankModel xlsmodel = (XlsWorldDatabankModel) getModel();
		
		xlsmodel.setYear(_currentYear);
		xlsmodel.setIndicator(_indicatorIndex);
		String indicatorName = xlsmodel.getIndicatorNames()[_indicatorIndex];
		
		final String[] countries = xlsmodel.getCountryNames();
		/*
		 * sum of all emissions worldwide for computing percentages
		 */
		final double sumOfAllEmission = xlsmodel.getSumOfAllValues();
		
		/*
		 * the maximum in this year by a country
		 */
		final double maxEmissionValue = xlsmodel.getMaxEmissionValue();
		
		/*
		 * max size of the circles
		 */
		final int maxSize = 50;
		
		_papplet.noStroke();
		
		/*
		 * short information text
		 */
		_papplet.fill(_papplet.color(164,166,106));
		_papplet.text(indicatorName + " in " + _currentYear, 30, 200);
		
		/*
		 * if no country have any data, a short message will be displayed
		 */
		if(sumOfAllEmission == 0d) {
			_papplet.fill(_papplet.color(164,166,106));
			_papplet.textAlign(PApplet.CENTER);
			_papplet.text("no data for the year " + _currentYear + " found.", _papplet.width/2, _papplet.height/2);
			_papplet.textAlign(PApplet.LEFT);
			return;
		} 
		
		if(_circleList != null) {
			for (int i = 0; i < _circleList.size(); i++) {
				_circleList.get(i).draw();
			}
			
			for (int i = 0; i < _circleList.size(); i++) {
				_circleList.get(i).drawText();
			}
			return;
		}
		
		int circleX;
		int circleY;
		int appreciableCountries = 0;
		
		ArrayList<EmissionCircle> circleList = new ArrayList<EmissionCircle>();
		
		for (int i = 0; i < countries.length; i++) {
			
			final float emission = xlsmodel.setCountry(i).value();
			
			/*
			 * countries with no emission (or no data) wont be displayed
			 */
			if(emission > 0.9f) {
				final float size = PApplet.map(emission, 0, (float) maxEmissionValue, 2, maxSize);
				
//				if(size > 2.1f) { // hides not notable countries from screen
					final float percentage = (float) (emission/sumOfAllEmission*100);

					EmissionCircle circle;
					boolean colliding = false;
					do {
						circleX = (int) (_papplet.random(_papplet.width - size*2) + size);
						circleY = (int) _papplet.random(_papplet.height - 380) + 250;
						
						circle = new EmissionCircle(countries[i], emission, percentage, circleX, circleY, size);
						
						for (int j = 0; j < circleList.size(); j++) {
							if(circleList.get(j).collide(circle)) {
								colliding = true;
								break;
							} else {
								colliding = false;
							}
						}
					}while(colliding);
					
					circle.draw();
					circleList.add(circle);
					
					appreciableCountries++;
//				}
			}
		}
		this._circleList = circleList;
		System.out.println("appreciable Countries: "+appreciableCountries);
	}
	
	@Override
	public void setCurrentYear(String pYear) {
		super.setCurrentYear(pYear);
		_circleList = null;
	}
	
	@Override
	public void setIndicatorToUse(int pIndicatorIndex) {
		int tmpIndex = _indicatorIndex;
		super.setIndicatorToUse(pIndicatorIndex);
		if(tmpIndex != _indicatorIndex) {
			_circleList = null;
		}
	}
}
