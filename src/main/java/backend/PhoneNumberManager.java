package backend;

import backend.datastructures.Country;
import backend.datastructures.PhoneNumber;
import backend.exceptions.IllegalCountryCodeException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Arrays;
import java.util.Locale;

public class PhoneNumberManager
{
    private static String defaultCountryCode = "DE";

    /**
     * Assembles a phone number from individual fields
     *
     * @param countryCode country code either a prefix or a iso code as String
     * @param prefix country specific (city-/area-)prefix
     * @param number telephone number
     * @param extension number extension
     * @return PhoneNumberObject with respective fields and toString method
     * @throws IllegalCountryCodeException if the iso country code or the prefix for a country is invalid
     * @throws IllegalStateException if the phone number is too long (>15 chars as specified by telephone
     *         numbering plan)
     */
    public PhoneNumber assemblePhoneNumber(String countryCode, Integer prefix, int number, String extension)
            throws IllegalCountryCodeException, IllegalStateException
    {
        Country country = null;
        country = handleCountry(countryCode);

        PhoneNumber phoneNumber = new PhoneNumber(country, prefix, number, extension);

        if (phoneNumber.toString().length() > 15)
        {
            throw new IllegalStateException(
                    "Phone number is too long. A maximum of 15 characters is specified by the telephone numbering plan");
        }

        return phoneNumber;
    }

    /**
     * Disassembles a phone number given as string
     *
     * @param phoneNumber phoneNumber as String to disassemble
     * @return PhoneNumber Object with respective fields and toString method
     * @throws NumberParseException if the number could not be parsed correctly (thrown by PhoneNumberUtil)
     * @throws IllegalCountryCodeException if the given country code or country prefix is invalid
     */
    public PhoneNumber disassemblePhoneNumber(String phoneNumber)
            throws NumberParseException, IllegalCountryCodeException
    {
        Phonenumber.PhoneNumber parsedPhoneNumber = PhoneNumberUtil.getInstance()
                .parse(phoneNumber, defaultCountryCode);

        Country country = new Country();
        country.fromPrefixCode(String.format("+%d", parsedPhoneNumber.getCountryCode()));

        return new PhoneNumber(country, null, parsedPhoneNumber.getNationalNumber(), parsedPhoneNumber.getExtension());
    }

    /**
     * Sets the default ISO country code for phone numbers where no code is given
     *
     * @param countryCode
     * @throws IllegalArgumentException if countryCode is invalid
     */
    public static void setDefaultCountryCode(String countryCode) throws IllegalArgumentException
    {
        if (!Arrays.asList(Locale.getISOCountries()).contains(countryCode))
        {
            throw new IllegalArgumentException(String.format("%s is not a valid country code", countryCode));
        }

        defaultCountryCode = countryCode.toUpperCase();
    }

    /**
     * removes all spaces, dashes and brackets/braces from the phone number
     *
     * @param phoneNumber number to clean up
     * @return clean phone number as String
     */
    private String cleanPhoneNumber(String phoneNumber)
    {
        return phoneNumber.replaceAll("[()\\[\\]\\-\\s]", "");
    }

    /**
     * helper method to decide on the country prefix
     *
     * @param prefix prefix either as prefix code or as iso code
     * @return a respective Country Object with country name, iso code and country prefix
     * @throws IllegalCountryCodeException if the country code or prefix could not be matched to any country
     */
    private Country handleCountry(String prefix) throws IllegalCountryCodeException
    {

        Country country = new Country();
        if (prefix.startsWith("+") || prefix.startsWith("00"))
        {
            country.fromPrefixCode(prefix);
        }
        else if (!"".equals(prefix))
        {
            country.fromISOCode(prefix);
        }
        else
        {
            country.fromPrefixCode(defaultCountryCode);
        }

        return country;
    }
}
