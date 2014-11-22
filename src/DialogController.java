import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable
{
    @FXML
    private ImageView viewArea1;

    @FXML
    private Label labelPixel1;

    @FXML
    private Label labelRes1;

    @FXML
    private Label labelDate1;

    @FXML
    private ImageView viewArea2;

    @FXML
    private Label labelPixel2;

    @FXML
    private Label labelRes2;

    @FXML
    private Label labelDate2;

    private Photo a, b;

    private Photo best;

    private final AppController appController;
    private Photo bestPhoto;

    public DialogController(AppController appController)
    {
        this.appController = appController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Image imageA = a.getImage();
        Image imageB = b.getImage();

        labelPixel1.setText(a.getUrl());
        labelPixel2.setText(b.getUrl());

        labelRes1.setText(imageA.getWidth(null) + "x" + imageA.getHeight(null));
        labelRes2.setText(imageB.getWidth(null) + "x" + imageB.getHeight(null));

        ByteArrayOutputStream osA = new ByteArrayOutputStream();
        ByteArrayOutputStream osB = new ByteArrayOutputStream();

        try
        {
            ImageIO.write((RenderedImage) imageA, "png", osA);
            ImageIO.write((RenderedImage) imageB, "png", osB);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        InputStream fisA = new ByteArrayInputStream(osA.toByteArray());
        InputStream fisB = new ByteArrayInputStream(osB.toByteArray());

        viewArea1.setImage(new javafx.scene.image.Image(fisA));
        viewArea1.setPreserveRatio(false);
        viewArea2.setImage(new javafx.scene.image.Image(fisB));
        viewArea2.setPreserveRatio(false);

        viewArea1.setOnMouseClicked(s -> {
            best = a;
            // TODO: Determine how to properly close a window.
            labelDate1.getScene().getWindow().hide();
        });

        viewArea2.setOnMouseClicked(s -> {
            best = b;
            // TODO: Again, determine how to properly close a window.
            labelDate1.getScene().getWindow().hide();
        });
    }

    public void setPhotos(Photo a, Photo b)
    {
        this.a = a;
        this.b = b;
    }

    public Photo getBestPhoto()
    {
        return best;
    }
}
