package app.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "History")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private java.util.Date date_chenge;

    @ManyToOne
    private Article article;
    @ManyToOne
    private User user;

    private String action;

    public History(Date date_chenge, Article article, User user, String action) {
        this.date_chenge = date_chenge;
        this.article = article;
        this.user = user;
        this.action = action;
    }

    public History() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate_chenge() {
        return date_chenge;
    }

    public void setDate_chenge(Date date_chenge) {
        this.date_chenge = date_chenge;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
