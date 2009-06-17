package controllers.myspace;

import controllers.Application;
import java.io.File;
import java.io.IOException;
import models.Attachment;
import models.ShareSession;
import org.apache.commons.io.FileUtils;
import play.Play;
import play.data.validation.Required;

public class AttachmentController extends Application {

    public static void upload(@Required File attachment, @Required Long id) throws IOException {

        File attachment_root = Play.getFile(new File("public", "attachments").getPath());

        FileUtils.copyFileToDirectory(attachment, attachment_root);

        File dest = new File(attachment_root, attachment.getName());

        ShareSession host = ShareSession.findById(id);

        forbiddenIfNo(host);

        Attachment dest_attachment = new Attachment(host, getRelativePath(dest));
        dest_attachment.save();

        ShareSessionController.show(id);
    }

    private static String getRelativePath(File dest) {

        return dest.getAbsolutePath().replace(Play.applicationPath.getAbsolutePath(), "");
    }
}
