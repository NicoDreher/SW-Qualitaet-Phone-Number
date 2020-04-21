package sample;

import backend.PhoneNumberManager;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

  @FXML
  Label lbl_telNr;

  @FXML
  Button btn_calculateTelNr;

  @FXML
  TextField txt_telNr;

  @FXML
  ComboBox cb_country;

  public void initialize() {
    lbl_telNr.setText("");
    cb_country
        .setItems(FXCollections.observableList(Arrays.stream(Locale.getISOCountries()).collect(
            Collectors.toList())));
    cb_country.getSelectionModel().select("DE");
    btn_calculateTelNr.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        lbl_telNr.setText(PhoneNumberManager.getPrefixFromCountryCode(txt_telNr.getText()));
      }
    });
    cb_country.valueProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observableValue, Object o, Object t1) {
        PhoneNumberManager.setDefaultCountryCode(t1.toString());
      }
    });
  }


}
