package sample;

import backend.PhoneNumberManager;
import backend.exceptions.IllegalCountryCodeException;
import com.google.i18n.phonenumbers.NumberParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

  private PhoneNumberManager phoneNumberManager;

  @FXML
  Label lbl_telNr;

  @FXML
  Label lbl_countryCode;

  @FXML
  Label lbl_prefix;

  @FXML
  Label lbl_number;

  @FXML
  Label lbl_extension;

  @FXML
  Button btn_calculateTelNr;

  @FXML
  TextField txt_telNr;

  @FXML
  ComboBox cb_country;

  public void initialize() {
    phoneNumberManager = new PhoneNumberManager();
    txt_telNr.setId("input");
    lbl_telNr.setText("");
    lbl_telNr.setId("telNr");
    lbl_extension.setText("");
    lbl_extension.setId("extension");
    lbl_number.setText("");
    lbl_number.setId("number");
    lbl_prefix.setText("");
    lbl_prefix.setId("prefix");
    lbl_countryCode.setText("");
    lbl_countryCode.setId("countryCode");

    btn_calculateTelNr.setOnAction(actionEvent -> {
      try {
        var phoneNumber = phoneNumberManager.disassemblePhoneNumber(txt_telNr.getText());
        lbl_telNr
            .setText(phoneNumber.toString());
        lbl_countryCode.setText(phoneNumber.getCountry().getCountryPrefix());
        lbl_prefix.setText(phoneNumber.getPrefix()!=null?phoneNumber.getPrefix().toString():"");
        lbl_number.setText(String.valueOf(phoneNumber.getNumber()));
        lbl_extension.setText(String.valueOf(phoneNumber.getExtension()));
      } catch (NumberParseException | IllegalCountryCodeException e) {
        e.printStackTrace();
      }
    });
    btn_calculateTelNr.setId("submit");

    cb_country.valueProperty().addListener(
        (observableValue, o, t1) -> PhoneNumberManager.setDefaultCountryCode(t1.toString()));
    cb_country
        .setItems(FXCollections.observableList(Arrays.stream(Locale.getISOCountries()).collect(
            Collectors.toList())));
    cb_country.getSelectionModel().select("DE");
    cb_country.setId("country");
  }
}
