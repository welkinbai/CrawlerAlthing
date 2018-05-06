package me.welkinbai.crawleralthing.path;

public class ListPath implements Path {
    private String url;

    public ListPath(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
