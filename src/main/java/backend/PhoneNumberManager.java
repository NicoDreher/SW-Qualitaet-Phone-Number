package backend;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Arrays;
import java.util.Locale;

public class PhoneNumberManager
{
    private static String defaultCountryCode = "+49";

    public static String getPrefixFromCountryCode(String phoneNumber)
    {

        boolean validCountryCode = false;

        for (String countryCode : Locale.getISOCountries())
        {
            if (phoneNumber.startsWith(countryCode))
            {
                int phonePrefix = PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode);

                phoneNumber = phoneNumber.replaceFirst(countryCode, String.format("+%d", phonePrefix));

                validCountryCode = true;
                break;
            }
        }

        if (!validCountryCode)
        {
            throw new IllegalArgumentException("Country Code is invalid");
        }

        return phoneNumber;
    }

    public static String replaceNonExistingCountryCodeWithDefault(String phoneNumber)
    {
        if (!(phoneNumber.startsWith("00") || phoneNumber.startsWith("+")))
        {
            phoneNumber = defaultCountryCode.concat(phoneNumber);
        }
        return phoneNumber;
    }

    public static void setDefaultCountryCode(String countryCode)
    {
        if (!Arrays.asList(Locale.getISOCountries()).contains(countryCode))
        {
            throw new IllegalArgumentException(String.format("%s is not a valid country code", countryCode));
        }

        defaultCountryCode = String.format("+ %d", PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode));
    }

    public static boolean validatePhoneNumber(String phoneNumber, String countryCode) throws NumberParseException
    {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();

        if (!Arrays.asList(Locale.getISOCountries()).contains(countryCode)){
            throw new IllegalArgumentException(String.format("%s is not a valid country code", countryCode));
        }

        return util.isValidNumber(util.parse(phoneNumber, countryCode));
    }
}
