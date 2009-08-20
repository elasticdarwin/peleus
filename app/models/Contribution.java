package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import play.db.jpa.Model;

@Entity
@Table(name = "share_session_contributor")
public class Contribution extends Model {

    @ManyToOne
    public ShareSession share_session;
    @OneToOne
    @JoinColumn(name = "user_id")
    public User contributor;
}
