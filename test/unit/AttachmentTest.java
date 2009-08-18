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

        ShareSession session = ShareSession.findOneBy("subject", "Gof Design Patterns with Java");

        assertNotNull(session);
        int original_attachment_count = session.attachments.size();

        final String path1 = "/attachments/hello.rb";
        final String path2 = "/attachments/world.rb";
        Attachment attachment1 = new Attachment(session, path1, "hello.rb");
        Attachment attachment2 = new Attachment(session, path2, "world.rb");

        attachment1.save();
        attachment2.save();

        Attachment attachment_loaded_1 = Attachment.findOneBy("path", path1);
        Attachment attachment_loaded_2 = Attachment.findOneBy("path", path2);

        assertNotNull(attachment_loaded_1);
        assertNotNull(attachment_loaded_2);

        session.refresh();

        List<Attachment> attachments_loaded = session.attachments;
        assertFalse(attachments_loaded.isEmpty());
        assertEquals(original_attachment_count + 2, attachments_loaded.size());


        assertEquals(path1, session.attachments.get(original_attachment_count + 0).path);
        assertEquals(path2, session.attachments.get(original_attachment_count + 1).path);
        assertEquals("hello.rb", session.attachments.get(original_attachment_count).file_name);
        assertEquals("world.rb", session.attachments.get(original_attachment_count + 1).file_name);

    }
}
