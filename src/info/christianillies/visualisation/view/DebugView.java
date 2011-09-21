package info.christianillies.visualisation.view;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.common.StatisticParameters;
import info.christianillies.framework.view.AbstractView;
import info.christianillies.visualisation.main.MainViewClass;
import processing.core.PApplet;

/**
 * display some debug infos (i.e. performace: fps / avg, ...)
 * @author chrischan
 *
 */
public class DebugView extends AbstractView{
	private PApplet _p;
	private StatisticParameters<Float> _statistics;

	public DebugView() {
		_p = FrameworkConstants.getPappletInstance();
		_statistics = new StatisticParameters<Float>();
	}
	
	@Override
	public void draw() {
		_p.pushStyle();
		
		/*
		 * refreshs average framerate every 60th frame
		 */
		if(_p.frameCount % 60 == 0) {
			_statistics.addValue(_p.frameRate);
		}
		
		/*
		 * gets the arithmetic mean of the framerate
		 */
		final double avgFrameRate = _statistics.getArithmeticMean();
		
		/*
		 * if current fps are under 30 it will be displayed red as a warning.
		 * a framerate under 30 fps could cause jerking
		 */
		if(_p.frameRate < 30) {
			_p.fill(255,0,0);
		} else {
			_p.fill(0,255,0);
		}

		_p.text(PApplet.round(_p.frameRate) + "fps/" + PApplet.round((float) avgFrameRate)+"avg", 10, _p.height-10);
		
		_p.fill(200);
		_p.text(" global emission visualisation - debug view mode");
		_p.text(" - frame count: " + _p.frameCount);
		_p.text(" - views in total: " + (((MainViewClass) _p).getView().getViewComponents().size()+1));
		_p.text(" - mouse moved " + (((MainViewClass) _p).getMouseMoved()) + " recognized pixels and clicked ");
		_p.text((((MainViewClass) _p).getMouseClicked()) + " times");
		_p.popStyle();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().getCanonicalName().equals(obj.getClass().getCanonicalName());
	}
	@Override
	public int hashCode() {
		return this.getClass().getCanonicalName().hashCode();
	}
}
