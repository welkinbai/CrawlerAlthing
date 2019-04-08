package me.welkinbai.crawleralthing.path;

public class RawPath extends AbstractPath implements Path {
    private String title;

    public RawPath(String url) {
        super(url);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public PathType getPathType() {
        return PathType.RAW_PATH;
    }
}
