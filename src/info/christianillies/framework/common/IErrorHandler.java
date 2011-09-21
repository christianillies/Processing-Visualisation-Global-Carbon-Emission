/**
 * 
 */
package info.christianillies.framework.common;

/**
 * error handling class the handle with errors like exceptions, input errors or
 * anything else.
 * 
 * @author christian illies
 * 
 */
public interface IErrorHandler
{
	/**
	 * simply prints an errormessage to the console for debugging<br>
	 * method example:<br>
	 * {@code System.err.println(this.getClass().getSimpleName() + "." + pMethodName + "(): " + pErrorMessage);}
	 * 
	 * @param errorMessage
	 *            to be sent to the console output
	 * @param methodName
	 *            the methodName
	 */
	void printErrorMessage(String pErrorMessage, String pMethodName);
}
