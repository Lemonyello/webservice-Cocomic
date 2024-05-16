package webserviceThuvien.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class ComicChapter {
    String id;
    String bookId;
    String name;
    Date publishDate;
    ArrayList<ComicPage> pages;
    int sortOrder;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishDate() {
        return publishDate == null ? Date.from(Instant.now()) : publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public ArrayList<ComicPage> getPages() {
        return pages;
    }

    public void setPages(ArrayList<ComicPage> pages) {
        this.pages = pages;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
