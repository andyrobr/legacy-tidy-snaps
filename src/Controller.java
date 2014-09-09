import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// We need to implemented Initializable, that way we can
// register to event handler on the appropriate controls.
// The Initializable interface adds a method called
// initialize for our controller.
public class Controller implements Initializable
{

    // The control ID must be used as the property name
    // within the controller. The annotation above the
    // field says that the property refers to the FXML.
    @FXML
    private Button clickableButton;

    // Notice the left-hand side we declared a list of
    // strings (List<String>) but we left the class info
    // empty on the right side (ArrayList<>). This is
    // called the diamond operator, it reduces the code
    // verbosity a bit. It was introduced in Java 7.
    private List<String> buttonLabels = new ArrayList<>();
    // Equivalent pre-Java 7:
//    private List<String> buttonLabels = new ArrayList<String>();

    private int currentLabelIndex = 1;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Add some button labels. We'll be scrolling through these
        // as the button is clicked.
        buttonLabels.add("I'm no longer clickable.");
        buttonLabels.add("I'm clickable.");
        buttonLabels.add("Yep, go ahead, keep going at it.");
        buttonLabels.add("Alright, you've had enough.");
        buttonLabels.add("Okay, you can stop now.");
        buttonLabels.add("Hey, stop it!");
        buttonLabels.add("I'll disable myself next time.");

        // This is a lambda expression, introduced in Java 8.
        // Like the diamond operator, it reduces the verbosity quite a bit.
        EventHandler<ActionEvent> onClickBrowseBtn = (ActionEvent e) -> {
            // This is my event handler code. It gets triggered when the
            // control gets activated. In our case, that occurs through
            // the click of a button.
            currentLabelIndex = (currentLabelIndex + 1) % buttonLabels.size();

            // ActionEvent.getSource() refers to the control that triggered
            // the event to be raised. In this case, it was our button that
            // was clicked. Notice that since we know the button that will
            // raise this event, we could have just as well used the member
            // variable "clickableButton".
            Button sourceButton = (Button) e.getSource();

            if (currentLabelIndex == 0)
                sourceButton.setDisable(true);

            sourceButton.setText(buttonLabels.get(currentLabelIndex));
        };
        // Equivalent pre-Java 8
//        EventHandler<ActionEvent> onClickBrowseBtn = new EventHandler<ActionEvent>()
//        {
//            @Override
//            public void handle(ActionEvent event)
//            {
//                // Your handler code goes here...
//            }
//        };

        // Here we attach our event handler to our button.
        clickableButton.setOnAction(onClickBrowseBtn);

        // Initialize the button label with some text. Notice that we start
        // with index 1 and not 0! (currentLabelIndex = 1 initially).
        clickableButton.setText(buttonLabels.get(currentLabelIndex));
    }
}
