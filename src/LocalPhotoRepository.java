import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class LocalPhotoRepository implements PhotoRepository
{
    public String folder;
    public boolean recursive;
    private List<Photo> listOfPhotos = new ArrayList<>();


    public LocalPhotoRepository(String folder, boolean recursive)
    {
        this.folder = folder;
        this.recursive = recursive;
    }

    @Override
    public Iterator<Photo> iterator() {
        return new PhotoIterator(listOfPhotos);
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

    static class PhotoIterator implements Iterator<Photo>
    {
        int listIndex = 0;
        private List<Photo> items;

        public PhotoIterator(List<Photo> items) {
            this.items = items;
        }

        @Override
        public boolean hasNext()
        {
            if(listIndex == items.size())
            {
                return false;
            }   else {
                return true;
            }
            // Andy's way --> return listIndex < listOfPhotos.size(); // 1 line wtf? lol
        }


        @Override
        public Photo next()
        {
            if(this.hasNext() )
            {
                return items.get(listIndex++);
            }
            else
            {
                return null;
            }
        }
    }
}
