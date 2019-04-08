package me.welkinbai.crawleralthing.path;

public class ListPath extends AbstractPath implements Path {
    public ListPath(String url) {
        super(url);
    }

    @Override
    public String toString() {
        return "ListPath{} " + super.toString();
    }

    @Override
    public PathType getPathType() {
        return PathType.LIST_PATH;
    }
}
