package controllers.myspace;

import controllers.Application;
import java.io.File;
import java.io.IOException;
import models.Attachment;
import models.ShareSession;
import org.apache.commons.io.FileUtils;
import play.Play;
import play.data.validation.Required;
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



        //        response.contentType = "application/octet-stream";

//        response.setHeader("Content-disposition", "attachment; filename=" + attachment.file_name + "");
//        renderText("attachment; filename=" + attachment.file_name + "");
        renderBinary(dest, attachment.file_name);
    }

    private static String getRelativePath(File dest) {

        return dest.getAbsolutePath().replace(Play.applicationPath.getAbsolutePath(), "");
    }
}
