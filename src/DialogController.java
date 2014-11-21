import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable
{
    @FXML
    private Rectangle viewArea1;

    @FXML
    private Label labelPixel1;

    @FXML
    private Label labelRes1;

    @FXML
    private Label labelDate1;

    @FXML
    private Rectangle viewArea2;

    @FXML
    private Label labelPixel2;

    @FXML
    private Label labelRes2;

    @FXML
    private Label labelDate2;

    private Photo a, b;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
//        resLabel1.setText("This is one resolution");
//        resLabel2.setText("This is another resolution");
        Image imageA = a.getImage();
        Image imageB = b.getImage();

        labelRes1.setText(imageA.getWidth(null) + "x" + imageA.getHeight(null));
        labelRes2.setText(imageB.getWidth(null) + "x" + imageB.getHeight(null));

        
    }

    public void setPhotos(Photo a, Photo b)
    {
        this.a = a;
        this.b = b;
    }
}
