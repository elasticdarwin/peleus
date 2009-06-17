package unit;

import java.util.List;
import models.Attachment;
import models.ShareSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class AttachmentTest extends UnitTest {

    @Before
    @After
    public void setup() {
        Fixtures.deleteAll();
        Fixtures.load("fixtures.yml");
    }

    @Test
    public void testSaveAttachment() {

        ShareSession session = ShareSession.findById(2L);

        assertNotNull(session);
        final String path1 = "/public/attachments/hello.rb";
        final String path2 = "/public/attachments/world.rb";
        Attachment attachment1 = new Attachment(session, path1);
        Attachment attachment2 = new Attachment(session, path2);

        attachment1.save();
        attachment2.save();

        Attachment attachment_loaded_1 = Attachment.findOneBy("path", path1);
        Attachment attachment_loaded_2 = Attachment.findOneBy("path", path2);

        assertNotNull(attachment_loaded_1);
        assertNotNull(attachment_loaded_2);

        session.refresh();

        List<Attachment> attachments_loaded = session.attachments;
        assertFalse(attachments_loaded.isEmpty());
        assertEquals(attachments_loaded.size(), 2);


        assertEquals(path1, session.attachments.get(0).path);
        assertEquals(path2, session.attachments.get(1).path);
        assertEquals("hello.rb", session.attachments.get(0).getFileName());
        assertEquals("world.rb", session.attachments.get(1).getFileName());

    }
}
