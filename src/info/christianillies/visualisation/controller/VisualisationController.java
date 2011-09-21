package info.christianillies.visualisation.controller;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.controller.IController;
import info.christianillies.framework.events.IEvent;
import info.christianillies.framework.events.IEventDispatcher;
import info.christianillies.framework.model.IDataModel;
import info.christianillies.framework.view.AbstractView;
import info.christianillies.visualisation.main.MainViewClass;
import info.christianillies.visualisation.model.XlsWorldDatabankModel;
import info.christianillies.visualisation.view.AbstractVisualisationView;
import info.christianillies.visualisation.view.DebugView;
import info.christianillies.visualisation.view.GraphicalUserInterfaceView;
import info.christianillies.visualisation.view.InfoView;
import info.christianillies.visualisation.view.LegendView;
import info.christianillies.visualisation.view.TopTenView;
import info.christianillies.visualisation.view.Visualisation_RandomCirclePosition_View;
import processing.core.PApplet;

/**
 * controls the view object (gui) and its subviews.
 * @author christian illies
 *
 */
public class VisualisationController implements IController {

	private IDataModel _model;
	private AbstractView _view;
	private AbstractVisualisationView _visualisationView;
	private PApplet _papplet;
	
	public VisualisationController() {
		_papplet = FrameworkConstants.getPappletInstance();
	}
	
	@Override
	public void onEvent(IEvent pEvent, IEventDispatcher pDispatcher) {
		if(pDispatcher instanceof IDataModel) {
			/*
			 * handle model events
			 */
			if(pEvent.equals(IDataModel.MODEL_HAS_CHANGED)) {
				handleModelHasChanged();
			}
		} else if(pDispatcher instanceof AbstractView) {
			/*
			 * handle view events
			 */
			if(pEvent.equals(AbstractView.CLICK_EVENT)) {
				mousePressed();
				return;
			}
			
			if(pEvent.equals(AbstractView.KEY_PRESSED_EVENT)) {
				keyPressed();
				return;
			}
			
			if(pEvent.equals(GraphicalUserInterfaceView.BUTTON_BY_LAND_CLICK_EVENT)) {
				changeVisualisationViewToPerLand();
				return;
			}
			
			if(pEvent.equals(GraphicalUserInterfaceView.BUTTON_PER_CAPITA_CLICK_EVENT)) {
				changeVisualisationViewToPerCapita();
				return;
			}
			
			if(pEvent.equals(GraphicalUserInterfaceView.BUTTON_INFO_CLICK_EVENT)) {
				toggleInformationPanel();
				return;
			}
			
			if(pEvent.equals(GraphicalUserInterfaceView.BUTTON_TOPTEN_CLICK_EVENT)) {
				toggleTopTenPanel();
				return;
			}
			
			/*
			 * if no other event dispatched it has to be a slider event with the current year information
			 */
			handleSliderEvent(pEvent.toString());
		}
	}

	/**
	 * toggles the top ten view panel
	 */
	private void toggleTopTenPanel() {
		TopTenView topTenView = TopTenView.getInstance();
		if(_view.getViewComponents().contains(topTenView)) {
			_view.removeViewComponent(topTenView);
		} else {
			topTenView.setController(this);
			topTenView.setModel(getModel());
			_view.addViewComponent(topTenView);
		}
	}

	/**
	 * toggles an informationpanel about the program
	 */
	private void toggleInformationPanel() {
		InfoView infoView = new InfoView();
		if(_view.getViewComponents().contains(infoView)) {
			_view.removeViewComponent(infoView);
		} else {
			_view.addViewComponent(infoView);
		}
	}

	/**
	 * handles a slider event and sets the year
	 * @param year as String 
	 */
	private void handleSliderEvent(String year) {
		_visualisationView.setCurrentYear(year.substring(0, 4));
	}

	/**
	 * changes the visualisation view to per-capita view
	 */
	private void changeVisualisationViewToPerCapita() {
		_visualisationView.setIndicatorToUse(1);
	}

	/**
	 * changes the visualisation view to per-land view
	 */
	private void changeVisualisationViewToPerLand() {
		_visualisationView.setIndicatorToUse(0);
	}

	/**
	 * handles a key event from the view
	 */
	private void keyPressed() {
//		System.out.println("key: " + _papplet.key);
		if(_papplet.key == 'd') {
			MainViewClass.DEBUG_MODE = !MainViewClass.DEBUG_MODE;
			/*
			 * if debug mode is enabled
			 */
			if(MainViewClass.DEBUG_MODE) {
				AbstractView debugView = new DebugView();
				debugView.setController(this);
				debugView.setModel(_model);
				_view.addViewComponent(debugView);
			} else {
				_view.removeViewComponent(new DebugView());
			}
			
			return;
		}
		if(_papplet.key == 's') {
			MainViewClass.SAVE_POWER = !MainViewClass.SAVE_POWER;
			return;
		}
		
	}

	/**
	 * handles a mouse click event from the view
	 */
	private void mousePressed() {
//		System.out.println("mousebutton: " + _papplet.mouseButton);
	}

	/**
	 * reacts on changes of the model
	 */
	private void handleModelHasChanged() {
		/*
		 * model do not change in this application
		 */
	}

	@Override
	public void setModel(IDataModel pModel) {
		if(_model != null) {
			_model.removeEventListener(this);
		}
		_model = pModel;
		_model.addEventListener(this);
	}

	@Override
	public void setView(AbstractView pView) {
		if(_view != null) {
			_view.removeEventListener(this);
			_view.removeAllViewComponents();
		}
		_view = pView;
		_view.addEventListener(this);
		
		appendViewComponents();
	}

	/**
	 * appends more sub-views to the main-view. controller takes control for all views.
	 */
	private void appendViewComponents() {
		/*
		 * adding sub views (i.e. legends)
		 */
		
		AbstractView legendView = new LegendView();
		legendView.setController(this);
		legendView.setModel(_model);
		_view.addViewComponent(legendView);
		
//		_visualisationView = new Visualisation_CirclesInALine_View();
		_visualisationView = new Visualisation_RandomCirclePosition_View();
		_visualisationView.setController(this);
		_visualisationView.setModel(_model);
		_visualisationView.setCurrentYear(((XlsWorldDatabankModel) _model).getYears()[0]);
		_view.addViewComponent(_visualisationView);
	}

	@Override
	public IDataModel getModel() {
		return _model;
	}

	@Override
	public AbstractView getView() {
		return _view;
	}
}
