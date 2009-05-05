package models;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import play.db.jpa.JPAModel;

@Entity
@Table(name = "share_sessions")
public class ShareSession extends JPAModel {

    @ManyToOne
    @JoinColumn(name = "department_id")
    public Department department;

    @ManyToMany
    @JoinTable(name = "share_session_contributor",
    joinColumns =
    @JoinColumn(name = "share_session_id", table = "share_sessions", referencedColumnName = "id"),
    inverseJoinColumns =
    @JoinColumn(name = "contributor_id", table = "users", referencedColumnName = "id"))
    public List<User> contributors;

    public String subject;

    public String audiences;

    public Date start;

    public Date end;

    public String address;

    @Column(length = 2048)
    public String description;
    
    public int audiences_limit;
}
