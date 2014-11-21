import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class LocalPhotoRepository implements PhotoRepository
{
    public String folder;
    public boolean recursive;
    public static Set<String> supportedExtensions = new HashSet();

    static {
        supportedExtensions.add("png");
        supportedExtensions.add("jpg");
    }

    public LocalPhotoRepository(String folder, boolean recursive)
    {
        this.folder = folder;
        this.recursive = recursive;
    }

    @Override
    public Stream<Photo> stream() throws IOException {
        Path root = Paths.get(this.folder);

        Stream<Path> files;

        if (this.recursive) {
            files = Files.walk(root);
        } else {
            files = Files.walk(root, 1);
        }

        return files
                .filter(path -> filterImageOnly(path))
                .map(path -> {
                    try {
                        if (!path.isAbsolute()) {
                            path = path.toAbsolutePath();
                        }
                        Image img = ImageIO.read(path.toFile());
                        return new Photo(path.toString(), img);
                    } catch (IOException e) {
                        System.err.println("BITCH LOOK HERE");
                        e.printStackTrace();
                    }

                    return null;
                });
    }
    private boolean filterImageOnly(Path path){
        String pathStr = path.toString();
        String extension = pathStr.substring(pathStr.lastIndexOf('.')+1);

        return supportedExtensions.contains(extension);
    }
}
