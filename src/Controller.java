import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Controller implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @FXML
    private Button folderBrowseBtn;

    @FXML
    private Button startBtn;

    private String sourcePathStr;
    private boolean recursive = true;

    private DirectoryChooser directoryChooser;

    private ImagePHash imageHasher = new ImagePHash();
    private boolean isDialogVisible = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryChooser = new DirectoryChooser();

        EventHandler<ActionEvent> onFolderBrowseBtn = (ActionEvent e) -> {
            if (!isDialogVisible)
            {
                isDialogVisible = true;
                File directory = directoryChooser.showDialog(folderBrowseBtn.getScene().getWindow());
                isDialogVisible = false;
                if (directory != null)
                {
                    sourcePathStr = directory.getAbsolutePath();
                    LOGGER.info("Selected folder on Folder Browser Button Source Path: '" + sourcePathStr + "'");
                }
            }
        };

        EventHandler<ActionEvent> onStartBtn = (ActionEvent e) -> {
            try {
                LOGGER.info("Selected folder on Start Button Source Path: '" + sourcePathStr +  "'");
                copyDistinctPhotos(sourcePathStr, recursive);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };

        folderBrowseBtn.setOnAction(onFolderBrowseBtn);
        startBtn.setOnAction(onStartBtn);
    }

    /***
     * Computes the perceptual hash for the image specified.
     *
     * @param image
     * @return
     */
    String computeHash(Image image) {
        // TODO: Implement pHash algorithm.
        return "";
    }

    /***
     * Given two photos, returns the best out of the two. The best photo can be
     * chosen using some heuristic (i.e. keep whichever has higher resolution)
     * or by prompting the user which should stay.
     *
     * @param a
     * @param b
     * @return
     */
    Photo bestPhoto(Photo a, Photo b) {
        // TODO: Just output a message for the time being. We'll allow the user
        // to pick which photo to keep later on.
//        imageHasher.getHash(a.getImage().g)
        System.out.printf("Found duplicate photo; '%s' and '%s'\n", a.getUrl(), b.getUrl());

        return a;
    }

    /***
     * Copies the distinct photos in source to target. No files are harmed in
     * the process.
     *
     * @param source
     * @param recursive
     * @throws IOException
     */
    void copyDistinctPhotos(String source, boolean recursive) throws IOException {
        Map<String, Photo> distinctPhotos = new HashMap<>();

        PhotoRepository photos = new LocalPhotoRepository(source, recursive);

        photos.stream()
                .forEach(photo -> {
                    LOGGER.info("Selected folder: Source Path: '" + source + "'" );
                    String hash = computeHash(photo.getImage());
                    // If the hash is already associated to some photo in the
                    // dictinctPhotos map, then that means we've already
                    // encountered this photo (we have a duplicate). We need
                    // to handle the conflict by keeping the best image in
                    // the map.
                    if (distinctPhotos.containsKey(hash)) {
                        LOGGER.info("Distinct Photo '" + distinctPhotos.values() + "' with Hash: '" + hash);
                        Photo other = distinctPhotos.get(hash);
                        distinctPhotos.put(hash, bestPhoto(photo, other));
                    }
                });

        // distinctPhotos should now be populated with distinct photos from the
        // source folder specified. We'll proceed to copy those images into the
        // target folder (don't delete photos for now, let the user do that).

    }
}