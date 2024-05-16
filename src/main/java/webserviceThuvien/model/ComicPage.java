package webserviceThuvien.model;

public class ComicPage {
    String PageImgUrl;
    int Page;
    int Width;
    int Height;

    public void setPageImgUrl(String pageImgUrl) {
        this.PageImgUrl = pageImgUrl;
    }

    public void setPage(int page) {
        this.Page = page;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public String getPageImgUrl() {
        return PageImgUrl;
    }

    public int getPage() {
        return Page;
    }

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }
}
