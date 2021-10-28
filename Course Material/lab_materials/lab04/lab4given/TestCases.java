import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
    private static final Song[] songs = new Song[] {
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            new Song("Gerry Rafferty", "Baker Street", 1998),
            new Song("City and Colour", "Sleeping Sickness", 2007),
            new Song("Foo Fighters", "Baker Street", 1997),
            new Song("Queen", "Bohemian Rhapsody", 1975),
            new Song("Gerry Rafferty", "Baker Street", 1978)
        };

    @Test
    public void testArtistComparator()
    {
    }

    @Test
    public void testLambdaTitleComparator()
    {
    }

    @Test
    public void testYearExtractorComparator()
    {
        // For this test, please make a comparator that orders songs
        // by the year.  You can use any mechanism you like to "extract"
        // the year.  You might find that java.util.Comparator contains
        // convenience methods that arguably make it easier to compare
        // using a single field.  On the other hand, learning one, more
        // general way to create comparators is simpler in a different way.
        //
        // If you want, you can use this test function for the "Comparing
        // two fields" part of the assignment (in which case you wouldn't
        // just be extracting the year).  Or, if you prefer, you can make
        // a new test function for "Comparing Two Fields."
    }

    @Test
    public void testComposedComparator()
    {
    }

    @Test
    public void runSort()
    {
        List<Song> songList = new ArrayList<>(Arrays.asList(songs));
        List<Song> expectedList = Arrays.asList(
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            new Song("City and Colour", "Sleeping Sickness", 2007),
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            new Song("Foo Fighters", "Baker Street", 1997),
            new Song("Gerry Rafferty", "Baker Street", 1978),
            new Song("Gerry Rafferty", "Baker Street", 1998),
            new Song("Queen", "Bohemian Rhapsody", 1975),
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
            );

        songList.sort(
            // pass comparator here
        );

        assertEquals(songList, expectedList);
    }
}
