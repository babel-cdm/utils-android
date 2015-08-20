package library.utils.rss.model;

import java.io.Serializable;

public class RssObject implements Serializable {

    private String title;
    private String link;
    private String comments;
    private String pubDate;
    private String description;

    public RssObject() {
    }

    public RssObject(RssObject object) {
        this.title = object.getTitle();
        this.link = object.getLink();
        this.comments = object.getComments();
        this.pubDate = object.getPubDate();
        this.description = object.getDescription();
    }

    public RssObject(String title, String link, String comments, String pubDate, String description) {
        this.title = title;
        this.link = link;
        this.comments = comments;
        this.pubDate = pubDate;
        this.description = description;
    }

    public void clear() {
        title = null;
        link = null;
        comments = null;
        pubDate = null;
        description = null;
    }

    public boolean isCompleted() {
        return this.description != null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}