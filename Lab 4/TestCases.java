import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
    public static final double DELTA = 0.00001;
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

    Comparator<Song> titleCompare = (Song o1, Song o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle());

    Comparator<Song> yearCompare = (Song o1, Song o2) -> {
        if (o1.getArtist().compareToIgnoreCase(o2.getArtist()) == 0) { ;
            return o1.getYear() - o2.getYear();
        }

        else {
            return o1.getArtist().compareToIgnoreCase(o2.getArtist());
        }
    };

    @Test
    public void testArtistComparator()
    {
        Song o1 = songs[0];
        Song o2 = songs[1];
        Song o3 = songs[3];
        Song o4 = songs[7];

        Comparator<Song> test = new ArtistComparator();

        assertEquals(-14, test.compare(o1, o2), DELTA);
        assertEquals(0, test.compare(o3, o4), DELTA);
        assertEquals(14, test.compare(o2, o1), DELTA);
    }

    @Test
    public void testLambdaTitleComparator()
    {
        Song s1 = songs[0];
        Song s2 = songs[1];
        Song s3 = songs[3];
        Song s4 = songs[7];

        assertEquals(-8, titleCompare.compare(s2, s1), DELTA);
        assertEquals(0, titleCompare.compare(s3, s4), DELTA);
        assertEquals(10, titleCompare.compare(s2, s3), DELTA);
    }

    @Test
    public void testYearExtractorComparator()
    {
        Song s1 = songs[0];
        Song s2 = songs[1];
        Song s3 = songs[3];
        Song s4 = songs[7];

        assertEquals(-20, yearCompare.compare(s4, s3), DELTA);
        assertEquals(-14, yearCompare.compare(s1, s2), DELTA);
        assertEquals(20, yearCompare.compare(s3, s4), DELTA);
    }

    @Test
    public void testComposedComparator()
    {
        Song s1 = songs[0];
        Song s2 = songs[1];
        Song s3 = songs[3];
        Song s4 = songs[7];

        ComposedComparator c = new ComposedComparator(titleCompare, yearCompare);

        assertEquals(-8, c.compare(s2, s1), DELTA);
        assertEquals(20, c.compare(s3, s4), DELTA);
        assertEquals(-20, c.compare(s4, s3), DELTA);
        assertEquals(10, c.compare(s2, s3), DELTA);

    }

    @Test
    public void testThenComparing()
    {

        Song s1 = songs[0];
        Song s2 = songs[1];
        Song s3 = songs[3];
        Song s4 = songs[7];

        ComposedComparator c = new ComposedComparator(new ArtistComparator(), titleCompare);
        Comparator<Song> newC = c.thenComparing(yearCompare);
        assertEquals(-14, newC.compare(s1, s2), DELTA);
        assertEquals(11, newC.compare(s2, s3), DELTA);
        assertEquals(20, newC.compare(s3, s4), DELTA);
        assertEquals(-20, newC.compare(s4, s3), DELTA);
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

        songList.sort(new ComposedComparator(new ArtistComparator(), titleCompare).thenComparing(yearCompare));

        assertEquals(songList, expectedList);
    }
}
