package de.dhbw.phonenumber.test.frontend;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import sample.Main;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class UITest {

    @Start
    public void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    public void test(FxRobot robot) throws InterruptedException {
        robot.clickOn("#input").write("015153515852");
        robot.clickOn("#country");
        //robot.type(KeyCode.DOWN);
        //robot.type(KeyCode.ENTER);
        robot.clickOn("#submit");
        FxAssert.verifyThat("#countryCode", LabeledMatchers.hasText("+49"));
    }
}
