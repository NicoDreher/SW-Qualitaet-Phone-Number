package backend.datastructures;

import backend.exceptions.IllegalCountryCodeException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * Data structure for a Country
 */
public class Country
{
    private String country;
    private String isoShort;
    private String countryPrefix;

    /**
     * generates this data structure from an iso code
     *
     * @param iso code to find country specs for
     * @throws IllegalCountryCodeException if the iso could not be matched to any country
     */
    public void fromISOCode(String iso) throws IllegalCountryCodeException
    {

        if (!PhoneNumberUtil.getInstance().getSupportedRegions().contains(iso))
        {
            throw new IllegalCountryCodeException("No valid iso code given");
        }

        isoShort = iso;
        countryPrefix = getPrefixFromCountryCode(iso);
        country = new Locale("de", iso).getDisplayCountry();
        ;
    }

    /**
     * generates this data structure from a prefix code
     *
     * @param prefix prefix to find country specs for
     * @throws IllegalCountryCodeException if the prefix could not be matched to a country
     */
    public void fromPrefixCode(String prefix) throws IllegalCountryCodeException
    {
        int prefixCode;

        if (StringUtils.isNumeric(prefix) && prefix.startsWith("00"))
        {
            prefixCode = Integer.parseInt(prefix.substring(2));
        }
        else if (prefix.startsWith("+") && StringUtils.isNumeric(prefix.substring(1)))
        {
            prefixCode = Integer.parseInt(prefix.substring(1));
        }
        else
        {
            throw new IllegalCountryCodeException("No valid prefix code given");
        }

        for (String region : PhoneNumberUtil.getInstance().getSupportedRegions())
        {
            if (PhoneNumberUtil.getInstance().getCountryCodeForRegion(region) == prefixCode)
            {
                this.countryPrefix = String.format("+%d", prefixCode);
                isoShort = region;
                country = new Locale("de", region).getDisplayCountry();
                return;
            }
        }

        throw new IllegalArgumentException("No valid country prefix given");
    }

    /**
     * @return Country name as string
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @return Country iso code as String
     */
    public String getIsoShort()
    {
        return isoShort;
    }

    /**
     * @return country prefix code as String
     */
    public String getCountryPrefix()
    {
        return countryPrefix;
    }

    /**
     * Maps a iso country code to a prefix
     *
     * @param iso country code to match prefix to
     * @return prefix code as string
     */
    public static String getPrefixFromCountryCode(String iso)
    {
        return String.format("+%d", PhoneNumberUtil.getInstance().getCountryCodeForRegion(iso));
    }
}
