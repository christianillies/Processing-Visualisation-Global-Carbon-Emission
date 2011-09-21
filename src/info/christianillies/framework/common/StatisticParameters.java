package info.christianillies.framework.common;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Stellt statistische Kenngroessen für verschiedene Datensaetze zur Verfuegung.
 * 
 * @author Christian Illies
 *
 * @param <T> Sollte vergleichbar(Comparable) und wenn möglich auch eine Zahl(Number) sein.
 */
public class StatisticParameters<T extends Comparable<T>> implements IErrorHandler {
	
	private ArrayList<T> elementArrayList;
	private ArrayList<T> sortedElements;
	
	
	public StatisticParameters() {
		elementArrayList = new ArrayList<T>();
	}
	
	public void addValue(T value) {
		elementArrayList.add(value);
	}
	
	public boolean removeValue(T value) {
		return elementArrayList.remove(value);
	}
	
	public void removeAllValues() {
		elementArrayList.clear();
	}

	/**
	 * Gibt den Maximalwert aus dem Array zurück.
	 * @return 
	 */
	public T getMaximum() {
		
		/**
		 * Variable speichert den größten Wert im Elements-Array
		 */
		T max     = elementArrayList.get(0);
		for (int i = 0; i < elementArrayList.size(); i++) {
			if(max.compareTo(elementArrayList.get(i)) < 0) {
				max   = elementArrayList.get(i);
			}
		}
		return max;
	}
	
	/**
	 * Gibt den Minimalwert aus dem Array zurück.
	 * @return 
	 */
	public T getMinimum() {
		
		/**
		 * Variable speichert den kleinsten Wert im Elements-Array
		 */
		T min     = elementArrayList.get(0);
		for (int i = 0; i < elementArrayList.size(); i++) {
			if(min.compareTo(elementArrayList.get(i)) > 0) {
				min   = elementArrayList.get(i);
			}
		}
		return min;
	}
	
	/**
	 * Berechnet das arithmetische Mittel anhand des Double-Wertes!
	 * @return double-Wert des arithmetischen Mittels
	 */
	public double getArithmeticMean() {
		
		double average = 0.0;
		try {
			for (int i = 0; i < elementArrayList.size(); i++) {
				average += ((Number)elementArrayList.get(i)).doubleValue();
			}
		} catch(Exception ex) {
			printErrorMessage("could not convert to Number", "getArithmeticMean");
			average = 0.0;
		}
		
		return average/elementArrayList.size();
	}
	
	/**
	 * Ermittelt das Quadratische Mittel
	 * @return
	 */
	public double getQuadraticMean() {
		
		double average = 0.0;
		
		try {
			for (int i = 0; i < elementArrayList.size(); i++) {
				/**
				 * pow (x, 2); => x²
				 */
				average += Math.pow((((Number)elementArrayList.get(i)).doubleValue()), 2);
			}
		} catch(Exception ex) {
			printErrorMessage("could not convert to Number", "getQuadraticMean");
			average = 0.0;
		}

		/**
		 * Wurzel(1/n * SUM(x2))
		 */
		average = Math.sqrt(((double)1/elementArrayList.size() * average));
		
		return average;
	}
	
	/**
	 * ermittelt das geometrische Mittel.
	 * @return
	 */
	public double getGeometricMean() {
		
		double average = 1.0;
		
		try {
			for (int i = 0; i < elementArrayList.size(); i++) {
				average *= ((Number)elementArrayList.get(i)).doubleValue();
			}
		} catch(Exception ex) {
			printErrorMessage("could not convert to Number", "getGeometricMean");
			average = 0.0;
		}	
		average = Math.pow(average, (double)1/elementArrayList.size());
		
		return average;
		
	}
	
	/**
	 * Ermittelt das harmonische Mittel
	 * @return
	 */
	public double getHarmonicMean() {
		double average = 0.0;
		try {
			for (int i = 0; i < elementArrayList.size(); i++) {
			
				double tmp = ((Number)elementArrayList.get(i)).doubleValue();
				
				if(tmp != 0) {
					average += 1/tmp;
				}
			}
		} catch(Exception ex) {
			printErrorMessage("could not convert to Number", "getHarmonicMean");
			average = 1.0;
		}
		return (double)elementArrayList.size()/average;
	}
	
	/**
	 * Berechnet den Median der Reihe
	 * @return double
	 */
	public double getMedian() {
		/**
		 * x n/2 + x n/2+1 ) / 2
		 */
		
		double median = 0;
		
		ArrayList<T> sortedElements = getSortedElements();
		
		if(sortedElements.size() % 2 == 0) {
				
			T x1 = sortedElements.get((int)(sortedElements.size()/2 - 1));
			T x2 = sortedElements.get((int)(sortedElements.size()/2));
			
			median = (((Number)x1).doubleValue() + ((Number)x2).doubleValue()) / 2;
			
		} else {
			/**
			 * M = x ((n+1)/2)
			 */
			median = ((Number)(elementArrayList.get((elementArrayList.size())/2))).doubleValue();
		}
		
		return median;
	}
	
	/**
	 * Gibt das Quantil zurück.
	 * @param percentile 
	 * @return
	 */
	public double getQuantil(float percentile) {
		ArrayList<T> sortedElements = getSortedElements();
		
		double quantile = 0;
		
		if(sortedElements.size() % 2 == 0) {
			
			T x1 = sortedElements.get((int)(sortedElements.size()*percentile - 1));
			T x2 = sortedElements.get((int)(sortedElements.size()*percentile));
			
			quantile = (((Number)x1).doubleValue() + ((Number)x2).doubleValue()) / 2;
			
		} else {
			quantile = ((Number)(elementArrayList.get((int) ((elementArrayList.size())*percentile)))).doubleValue();
		}		
		
		return quantile;
	}

	/**
	 * Gibt die Elemente sortiert als ArrayList zurück
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> getSortedElements() {
		
		if(sortedElements == null) {
			sortedElements = (ArrayList<T>) elementArrayList.clone();
			Collections.sort(sortedElements);
		}
		
		return sortedElements;
	}

	@Override
	public void printErrorMessage(String pErrorMessage, String pMethodName) {
		System.err.println(this.getClass().getSimpleName() + "." + pMethodName + "(): " + pErrorMessage);
	}
	
}
