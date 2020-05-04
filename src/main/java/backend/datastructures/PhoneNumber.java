package backend.datastructures;

/**
 * Data structure for a PhoneNumber
 */
public class PhoneNumber
{
    private Country country;
    private Integer prefix;
    private long number;
    private String extension;

    /**
     * @param country Country object for this phone number
     * @param prefix prefix code within a country
     * @param number phone number
     * @param extension phone extension
     */
    public PhoneNumber(Country country, Integer prefix, long number, String extension){
        this.country = country;
        this.prefix = prefix;
        this.number = number;
        this.extension = extension;
    }

    /**
     * @return Country object for this phone number
     */
    public Country getCountry()
    {
        return country;
    }

    /**
     * @return this phone number as formatted String
     */
    @Override
    public String toString(){
        StringBuilder numberBuilder = new StringBuilder();

        numberBuilder.append(country.getCountryPrefix());

        if (prefix != null){
            numberBuilder.append(" ").append(prefix);
        }

        numberBuilder.append(" ").append(number);

        if (extension != null && !extension.isEmpty())
        {
            numberBuilder.append("-").append(extension);
        }

        return numberBuilder.toString();
    }
}
