package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import play.test.UnitTest;

public class TimeTest extends UnitTest {

    @Test
    public void testTimeFormat() {
        final Date date = new Date();

        String date_text = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT).format(date);
        assertNotNull(date_text);


        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd E HH:mm");


        assertNotNull(format.format(date));
    }
}
