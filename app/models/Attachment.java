package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import play.db.jpa.JPAModel;

@Entity
@Table(name = "attachments",
uniqueConstraints = {@UniqueConstraint(columnNames = {"path"})})
public class Attachment extends JPAModel {
 
    public String path;
    @ManyToOne
    @JoinColumn(name = "host_id")
    public ShareSession share_session;
    public String file_name;

    public Attachment(ShareSession host, String dest_path, String file_name) {
        share_session = host;
        path = dest_path;
        this.file_name = file_name;
    }
//    public String getFileName() {
//
//        String separator = System.getProperty("file.separator");
//
//        return path.substring(path.lastIndexOf(separator) + 1);
//
//    }
}
