package info.christianillies.visualisation.model;

import java.util.ArrayList;

/**
 * specific xls data model for xls files downloaded from world databank. you do
 * not have to change anything to the xls file.
 * 
 * @author christian illies
 * 
 */
public class XlsWorldDatabankModel extends XlsDataModel {

	/**
	 * all country names as a string array
	 */
	private String[] countryNames;
	/**
	 * all indicator names
	 */
	private String[] indicatorNames;
	/**
	 * all available years
	 */
	private String[] years;
	
	/**
	 * reveals the num of different indicators. alternativ usage: {@code indicatorNames.length}
	 */
	private int differentDataCounter;
	
	/**
	 * current country index for the method {@code value()}
	 */
	private int _currentCountryIndex;
	/**
	 * current year index for the method {@code value()}
	 */
	private int _currentYearIndex;
	
	/**
	 * current indicator index for the method {@code value()}
	 */
	private int _currentIndicatorIndex;
	
	/**
	 * data values stored as a 3d array
	 */
	private Float[][][] data;

	/**
	 * constructor assigns xls file name
	 * @param pXlsFileName file name as string
	 */
	public XlsWorldDatabankModel(String pXlsFileName) {
		super(pXlsFileName);
	}

	@Override
	protected void loadModelData() {
		differentDataCounter = _getDifferentDataCounter();
		countryNames = _getCountryNames();
		indicatorNames = _getIndicatorNames();
		years = _getYears();
		data = _getData();
		dispatchEvent(MODEL_DATA_LOADED);
	}

	@Override
	public Object[] toArray() {
		return data.clone();
	}
	
	/**
	 * returns the data of the model as a 3d array of float values. empty cells will be stored as 0.0f values.
	 * @return
	 */
	protected Float[][][] _getData() {
		/*
		 * 3 dimensions for all of the data. <br>
		 * first index is the country <br>
		 * second the year <br>
		 * third is the indicator
		 */
		Float[][][] data = new Float[getCountryNames().length][getYears().length][getDifferentDataCounter()];
		
		final int startColumnIndex = 5;
		final int startRowIndex = 2;
		
		final int steps = getDifferentDataCounter();
		
		for (int j = 0; j < getYears().length; j++) {
			
			for (int k = 0; k < steps; k++) {
				for (int i = 0; i < getCountryNames().length * steps; i += steps) {
				
					final int currentRow = startRowIndex-1+i+k;
					final int currentCol = startColumnIndex-1+j;

					try {
						data[i/steps][j][k] = _xlsReader.getFloat(currentRow, currentCol);
					} catch(NullPointerException ex) {
						/*
						 * if cell is empty an exception will be thrown, so we apply a value of 0.0f.
						 */
						data[i/steps][j][k] = 0.0f;
					}
				} 
			}
		} // end for 3x
		
		return data;
	}

	/**
	 * search all country names
	 * 
	 * @return String array with all countries
	 */
	protected String[] _getCountryNames() {
		int maxRows = _xlsReader.getLastRowNum();
		int numDataTypes = this.getDifferentDataCounter();
		String[] allCountries = new String[maxRows/numDataTypes];
		int index = 0;

		for (int i = 1; i <= maxRows; i += numDataTypes) {
			String countryName = _xlsReader.getString(i, 0);
			allCountries[index++] = countryName;
		}

		return allCountries;
	}

	/**
	 * returns all available years
	 * @return String array within all years
	 */
	protected String[] _getYears() {
		int startColumnIndex = 4;
		String[] years;
		/*
		 * current size is 51 (1960 to 2010) maybe in the future there are more
		 * elements
		 */
		ArrayList<String> yearsArrayList = new ArrayList<String>();
		/*
		 * jump to first row
		 */
		_xlsReader.firstRow();
		/*
		 * skip the first columns without year information
		 */
		for (int i = 0; i < startColumnIndex - 1; i++) {
			_xlsReader.nextCell();
		}

		do {
			_xlsReader.nextCell();

			yearsArrayList.add(_xlsReader.getString());

		} while (_xlsReader.hasMoreCells());

		years = new String[yearsArrayList.size()];
		yearsArrayList.toArray(years);

		return years;
	}

	/**
	 * gets the different indicator names
	 * 
	 * @return String array with all indicators
	 */
	protected String[] _getIndicatorNames() {
		int numDataTypes = this.getDifferentDataCounter();
		String[] allIndicators = new String[numDataTypes];

		for (int i = 1; i <= numDataTypes; i++) {
			String indicatorName = _xlsReader.getString(i, 2);
			allIndicators[(i - 1)] = indicatorName;
		}

		return allIndicators;
	}

	/**
	 * returns the num of different datatypes stored in the xls file.
	 * 
	 * @return integer num of different data
	 */
	protected int _getDifferentDataCounter() {
		/*
		 * jump to first row
		 */
		_xlsReader.firstRow();
		/*
		 * skip the first row and go directly to the next row where the country
		 * names start
		 */
		_xlsReader.nextRow();

		String countryName = "";
		int dataCounter = 0;
		do {
			countryName = _xlsReader.getString();
			_xlsReader.nextRow();
			dataCounter++;
		} while (countryName.equals(_xlsReader.getString()));

		return dataCounter;
	}

	/**
	 * search all country names
	 * 
	 * @return the countryNames
	 */
	public String[] getCountryNames() {
		if (countryNames == null && _xlsReader == null) {
			return null;
		} else if (countryNames == null) {
			countryNames = _getCountryNames();
		}
		return countryNames;
	}

	/**
	 * gets the different indicator names
	 * 
	 * @return the indicatorNames
	 */
	public String[] getIndicatorNames() {
		if (indicatorNames == null && _xlsReader == null) {
			return null;
		} else if (indicatorNames == null) {
			indicatorNames = _getIndicatorNames();
		}
		return indicatorNames;
	}

	/**
	 * returns the num of diffrent data as a integer number
	 * 
	 * @return the differentDataCounter
	 */
	public int getDifferentDataCounter() {
		if (differentDataCounter <= 0 && _xlsReader == null) {
			return -1;
		} else if (differentDataCounter <= 0) {
			differentDataCounter = _getDifferentDataCounter();
		}
		return differentDataCounter;
	}

	/**
	 * returns the years stored in the xls file or NULL when xls file is not
	 * loaded
	 * 
	 * @return the years
	 */
	public String[] getYears() {
		if (years == null && _xlsReader == null) {
			return null;
		} else if (years == null) {
			years = _getYears();
		}
		return years;
	}

	/**
	 * returns the index of the given index if the method found the name
	 * @param country name of the country as string
	 * @return integer index of the country name or -1 when nothing found
	 */
	public int getCountryIndex(String country) {
		int ret = -1;
		for (int i = 0; i < countryNames.length; i++) {
			if(countryNames[i].equals(country)) {
				ret = i;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * returns the index of the overgiven year
	 * @param year as string
	 * @return integer index of the year or -1
	 */
	public int getYearIndex(String year) {
		int ret = -1;
		for (int i = 0; i < years.length; i++) {
			if(years[i].equals(year)) {
				ret = i;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * returns the index of the given indicatorname
	 * @param indicator name string
	 * @return integer index of the indicator name
	 */
	public int getIndicatorIndex(String indicator) {
		int ret = -1;
		for (int i = 0; i < indicatorNames.length; i++) {
			if(indicatorNames[i].equals(indicator)) {
				ret = i;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * sets the country index for the value()-method to the overgiven country.
	 * @param country name as string
	 * @return this object to chain the method to the other set()-methods for value
	 */
	public XlsWorldDatabankModel setCountry(String country) {
		_currentCountryIndex = getCountryIndex(country);
		return this;
	}
	
	/**
	 * sets the country index for the value()-method to the overgiven country.
	 * @param countryIndex as integer value 
	 * @return this object to chain the method to the other set()-methods for value
	 */
	public XlsWorldDatabankModel setCountry(int countryIndex) {
		if(countryIndex >= 0 && countryIndex < countryNames.length)
		{
			_currentCountryIndex = countryIndex;
		} else {
			_currentCountryIndex = -1;
		}
		return this;
	}
	
	/**
	 * sets the year for the value()-method to the overgiven country.
	 * @param year as string value 
	 * @return this object to chain the method to the other set()-methods for value
	 */
	public XlsWorldDatabankModel setYear(String year) {
		_currentYearIndex = getYearIndex(year);
		return this;
	}
	
	/**
	 * sets the year index for the value()-method to the overgiven country.
	 * @param year as integer value 
	 * @return this object to chain the method to the other set()-methods for value
	 */
	public XlsWorldDatabankModel setYear(int yearIndex) {
		if(yearIndex >= 0 && yearIndex < years.length)
		{
			_currentYearIndex = yearIndex;
		} else {
			_currentYearIndex = -1;
		}
		return this;
	}
	
	/**
	 * sets the indicator for the value()-method to the overgiven country.
	 * @param indicator as string value 
	 * @return this object to chain the method to the other set()-methods for value
	 */
	public XlsWorldDatabankModel setIndicator(String indicator) {
		_currentIndicatorIndex = getIndicatorIndex(indicator);
		return this;
	}
	
	/**
	 * sets the indicator index for the value()-method to the overgiven country.
	 * @param indicator as integer value 
	 * @return this object to chain the method to the other set()-methods for value
	 */
	public XlsWorldDatabankModel setIndicator(int indicatorIndex) {
		if(indicatorIndex >= 0 && indicatorIndex < indicatorNames.length)
		{
			_currentIndicatorIndex = indicatorIndex;
		} else {
			_currentIndicatorIndex = -1;
		}
		return this;
	}
	
	/**
	 * returns the value of the current field or -1.0f if no data was found.<br>
	 * before you can use this method, you have to set country-, year- and indicator-index 
	 * @return float value of the cell otherwise -1.0f
	 */
	public float value() {
		if(_currentCountryIndex == -1 || _currentYearIndex == -1 || _currentIndicatorIndex == -1) {
			return -1.0f;
		}
		
		return (Float) data[_currentCountryIndex][_currentYearIndex][_currentIndicatorIndex];
	}
	
	/**
	 * returns the maximum of all countries in this year and for the specific indicator
	 * @return the maximum value of emission
	 */
	public double getMaxEmissionValue() {
		if(_currentYearIndex == -1 || _currentIndicatorIndex == -1) {
			return -1.0d;
		}
		
		double maxValue = 0.0d;
		
		for (int i = 0; i < countryNames.length; i++) {
			final double currentValue = data[i][_currentYearIndex][_currentIndicatorIndex];
			if(currentValue > maxValue) {
				maxValue = currentValue;
			}
		}
		
		return maxValue;
	}
	
	/**
	 * returns the sum of all countries in this year and for the specific indicator
	 * @return the maximum value of emission
	 */
	public double getSumOfAllValues() {
		if(_currentYearIndex == -1 || _currentIndicatorIndex == -1) {
			return -1.0d;
		}
		
		double sumValue = 0.0d;
		
		for (int i = 0; i < countryNames.length; i++) {
			final double currentValue = data[i][_currentYearIndex][_currentIndicatorIndex];
			sumValue += currentValue;
		}
		
		return sumValue;
	}
	
	
	
	/**
	 * returns the value of the current field or -1.0f if no data was found.<br>
	 * searches internal for the correct indices.
	 * @param country name as string
	 * @param year as string
	 * @param indicator as string
	 * @return float value of the cell otherwise -1.0f
	 */
	public float value(String country, String year, String indicator) {
		return setCountry(country).setYear(year).setIndicator(indicator).value();
	}
	
	/**
	 * returns the value of the current field or -1.0f if no data was found.
	 * @param country name as int
	 * @param year as int
	 * @param indicator as int
	 * @return float value of the cell otherwise -1.0f
	 */
	public float value(int countryIndex, int yearIndex, int indicatorIndex) {
		return setCountry(countryIndex).setYear(yearIndex).setIndicator(indicatorIndex).value();
	}

	/**
	 * @return the currentCountryIndex
	 */
	public int getCurrentCountryIndex() {
		return _currentCountryIndex;
	}

	/**
	 * @return the currentYearIndex
	 */
	public int getCurrentYearIndex() {
		return _currentYearIndex;
	}

	/**
	 * @return the currentIndicatorIndex
	 */
	public int getCurrentIndicatorIndex() {
		return _currentIndicatorIndex;
	}
}
