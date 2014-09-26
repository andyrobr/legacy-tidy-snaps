import java.io.IOException;
import java.util.stream.Stream;

public interface PhotoRepository {

    /***
     * Returns a Stream where Photo instances are produced in a lazy fashion.
     *
     * @return
     * @throws IOException
     */
    Stream<Photo> stream() throws IOException;
}
