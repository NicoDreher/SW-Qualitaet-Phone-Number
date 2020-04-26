package backend.exceptions;

/**
 * Exception class for illegal country codes
 */
public class IllegalCountryCodeException extends Exception
{
    /**
     * @param message error message to display for illegal country code
     */
    public IllegalCountryCodeException(String message){
        super(message);
    }

}
