package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import play.db.jpa.JPAModel;

@Entity
@Table(name = "virtual_teams")
public class VirtualTeam extends JPAModel {
}
