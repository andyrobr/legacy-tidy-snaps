import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button folderBrowseBtn;

    @FXML
    private Button startBtn;

    private String sourcePathStr;
    private String targetPathStr;
    private boolean recursive = true;

    private JFileChooser fileChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        folderBrowseBtn.addActionListener(e -> {
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                sourcePathStr = fileChooser.getSelectedFile().getPath();
            }
        });

        startBtn.addActionListener(e -> {
            try {
                copyDistinctPhotos(sourcePathStr, targetPathStr, recursive);
            } catch (IOException e1) {
                System.err.println("");
            }
        });
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
        System.out.printf("Found duplicate photo; '%s' and '%s'\n", a.getUrl(), b.getUrl());

        return a;
    }

    /***
     * Returns the path of a file or directory relative to some directory. The
     * relative path is produced iff path in a subdirectory of relativeTo;
     * otherwise, path is going to be returned.
     *
     * @param path
     * @param relativeTo
     * @return
     */
    private String getRelativePath(String path, String relativeTo) {
        int index = path.indexOf(relativeTo);

        if (index == 0)
            return path.substring(relativeTo.length());
        else
            return path;
    }

    /***
     * Copies the distinct photos in source to target. No files are harmed in
     * the process.
     *
     * @param source
     * @param target
     * @param recursive
     * @throws IOException
     */
    void copyDistinctPhotos(String source, String target, boolean recursive) throws IOException {

        Map<String, Photo> distinctPhotos = new HashMap<>();

        PhotoRepository photos = new LocalPhotoRepository(source, recursive);

        photos.stream()
                .forEach(photo -> {
                    String hash = computeHash(photo.getImage());

                    // If the hash is already associated to some photo in the
                    // dictinctPhotos map, then that means we've already
                    // encountered this photo (we have a duplicate). We need
                    // to handle the conflict by keeping the best image in
                    // the map.
                    if (distinctPhotos.containsKey(hash)) {
                        Photo other = distinctPhotos.get(hash);
                        distinctPhotos.put(hash, bestPhoto(photo, other));
                    }
                });

        Path targetPath;
        String absolutePathStr, relativePathStr;

        // distinctPhotos should now be populated with distinct photos from the
        // source folder specified. We'll proceed to copy those images into the
        // target folder (don't delete photos for now, let the user do that).
        for (Photo photo : distinctPhotos.values()) {
            absolutePathStr = photo.getUrl();

            // We're going to find the path of the photo relative to the source
            // directory. That way, we know where in the target directory to
            // place that photo.
            relativePathStr = getRelativePath(absolutePathStr, source);

            targetPath = Paths.get(target, relativePathStr);
            Files.createDirectories(targetPath.getParent());

            if(targetPath.toString().toLowerCase().endsWith("jpeg")) {
                ImageIO.write((java.awt.image.RenderedImage) photo.getImage(), "jpeg", targetPath.toFile());
            }
            else if (targetPath.toString().toLowerCase().endsWith("png")) {
                ImageIO.write((java.awt.image.RenderedImage) photo.getImage(), "png", targetPath.toFile());
            }
        }
    }
}
// add support for other extentions