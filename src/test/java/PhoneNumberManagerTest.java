import backend.PhoneNumberManager;
import backend.datastructures.Country;
import backend.exceptions.IllegalCountryCodeException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneNumberManagerTest {

    private PhoneNumberManager phonenumberManager;

    @BeforeEach
    void initialize(){
        phonenumberManager = new PhoneNumberManager();
    }

    @ParameterizedTest(name= "[{index}] {0}")
    @CsvFileSource(resources = "assemble.csv")
    void assemblePhoneNumber(String telephonenumber, Country countryCode) {


    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "dissambleTelphonenumber.csv")
    void disassemblePhoneNumber(String telephonenumber, String countryCode, Integer prefix, int number, String extension) throws IllegalCountryCodeException, NumberParseException {
        var phoneNumber = phonenumberManager.disassemblePhoneNumber(telephonenumber);
        assertEquals(countryCode, phoneNumber.getCountry());
        assertEquals(prefix, phoneNumber.getPrefix());
    }

    @Test
    void setDefaultCountryCode() {
    }
}