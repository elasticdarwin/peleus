package controllers.myspace;

import controllers.Application;
import java.io.File;
import java.io.IOException;
import models.Attachment;
import models.ShareSession;
import org.apache.commons.io.FileUtils;
import play.Play;
import play.libs.Codec;

public class AttachmentController extends Application {

    public static void upload(File attachment, Long id) throws IOException {

        forbiddenIfNo(id);

        if (attachment == null) {
            flash.put("attachment_error", "Attachment file can not be empty!");
            ShareSessionController.show(id);
        }

        ShareSession host = ShareSession.findById(id);
        forbiddenIfNo(host);

        File attachment_root = Play.getFile(new File("attachments").getPath());

        File dest = new File(attachment_root, Codec.UUID());

        Attachment dest_attachment = new Attachment(host, getRelativePath(dest), attachment.getName());
        dest_attachment.save();

        FileUtils.copyFile(attachment, dest);

        ShareSessionController.show(id);
    }

    public static void download(String path) {

        File attachment_root = Play.getFile(new File("attachments").getPath());

        File dest = new File(attachment_root, path);

        Attachment attachment = Attachment.findOneBy("path = ?", getRelativePath(dest));

        renderBinary(dest, attachment.file_name);
    }

    public static void delete(Long id) {
        forbiddenIfNo(id);
        Attachment attachment =
                Attachment.findById(id);
        forbiddenIfNo(attachment);

        String path = attachment.path;

        File attachment_file = new File(Play.applicationPath, path);
        attachment_file.delete();

        Long share_session_id = attachment.share_session.id;
        attachment.share_session.attachments.remove(attachment);

        attachment.delete();

        ShareSessionController.show(share_session_id);
    }

    private static String getRelativePath(File dest) {

        return dest.getAbsolutePath().replace(Play.applicationPath.getAbsolutePath(), "");
    }
}
