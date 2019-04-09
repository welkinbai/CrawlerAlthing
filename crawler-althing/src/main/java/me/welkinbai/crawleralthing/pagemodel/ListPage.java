package me.welkinbai.crawleralthing.pagemodel;

import me.welkinbai.crawleralthing.annotation.UnThreadSafe;
import me.welkinbai.crawleralthing.path.Path;

import java.util.HashMap;
import java.util.Map;

@UnThreadSafe
public class ListPage extends Page {
    private Map<String, Path> pathsInPage;

    public ListPage(Path path) {
        super(path);
    }

    public Map<String, Path> getPathsInPage() {
        return pathsInPage;
    }

    public void setPathsInPage(Map<String, Path> pathsInPage) {
        this.pathsInPage = pathsInPage;
    }

    public void addPath(Path path) {
        ensurePaths();
        pathsInPage.put(path.getUrl(), path);
    }

    private void ensurePaths() {
        if (pathsInPage == null) {
            pathsInPage = new HashMap<>();
        }
    }

    public Path getSameUrlPathInPage(Path path) {
        ensurePaths();
        return pathsInPage.get(path.getUrl());
    }

    public boolean isEmpty() {
        return pathsInPage == null || pathsInPage.isEmpty();
    }

    @Override
    public String toString() {
        return "ListPage{" +
                "pathsInPage=" + pathsInPage +
                "} " + super.toString();
    }
}
