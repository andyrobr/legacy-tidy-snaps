import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

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
        return new PhotoIterator();
    }

    private class PhotoIterator implements Iterator<Photo>
    {
        int listIndex = 0;

        @Override
        public boolean hasNext()
        {
            if(listIndex == listOfPhotos.size())
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
                return listOfPhotos.get(listIndex++);
            }
            else
            {
                return null;
            }
        }
    }
}
