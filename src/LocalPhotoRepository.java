import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LocalPhotoRepository implements PhotoRepository
{
    public String folder;
    public boolean recursive;

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
                .filter(path -> path.toString().toLowerCase().endsWith("png"))
                .filter(path -> path.toString().toLowerCase().endsWith("jpeg"))
                .map(path -> {
                    try {
                        if (!path.isAbsolute()) {
                            path = path.toAbsolutePath();
                        }
                        return new Photo(path.toString(), ImageIO.read(path.toFile()));
                    } catch (IOException e) {
                    }

                    return null;
                });
    }
}
