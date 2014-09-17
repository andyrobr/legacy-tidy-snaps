import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class TestLocalPhotoRepository {

    private static List<Photo> emptyList = new ArrayList<>(0);
    private static List<Photo> photoList = new ArrayList<>(5);

    @BeforeClass
    public static void setUp() {
        // This method is called before the tests are run. It's called once
        // per class and can be used to initialize items that are shared among
        // tests but not modified. If you need to have something that is shared
        // but modified, you'll need to use the @Before annotation which
        // calls the function before each test is executed.
        photoList.add(new Photo("a", null));
        photoList.add(new Photo("b", null));
        photoList.add(new Photo("c", null));
        photoList.add(new Photo("d", null));
        photoList.add(new Photo("e", null));
    }

    // The following two methods are tests that are called by the JUnit test
    // runner. JUnit knows about them because of the @Test annotation.
    // Notice also the names, they are meant to be descriptive about what they
    // test in the method name (make it verbose if you need to, nobody really
    // cares as long as the intent of the test is clear).

    @Test
    public void testIteratorOnEmptyPhotoList() {
        // Create an iterator that is backed by an empty list. There are no
        // items to iterate through, so hasNext should return false immediately.
        LocalPhotoRepository.PhotoIterator photoIterator =
                new LocalPhotoRepository.PhotoIterator(emptyList);

        while (photoIterator.hasNext())
        {
            // If hasNext does not return false, then our implementation is
            // incorrect.
            fail("Iterator should not return true for hasNext on empty lists.");
        }
    }

    @Test
    public void testIteratorOnNonEmptyPhotoList() {
        int index = 0;

        // Create an iterator backed by a non-empty list. We will say that our
        // implementation is correct if the iterator traverses through our list
        // in an ordered fashion. That is, the first item returned by the
        // iterator should correspond to the first item in the list, same for
        // the second item and so on.
        LocalPhotoRepository.PhotoIterator photoIterator =
                new LocalPhotoRepository.PhotoIterator(photoList);

        while (photoIterator.hasNext()) {
            Photo photo = photoIterator.next();

            // We're keeping track of the index within the list that we're
            // currently on and comparing it with the result from the iterator.
            // We only populated the URL, so those fields should match since.
            assertEquals(photoList.get(index++).getUrl(), photo.getUrl());
        }
    }
}
