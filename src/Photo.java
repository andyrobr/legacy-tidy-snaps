import java.awt.*;

/**
 * Created by Fateh on 9/11/2014.
 */
public class Photo {

    private String url;
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Photo(String url, Image image) {
        this.url = url;
        this.image = image;
    }
}
