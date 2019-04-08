package me.welkinbai.crawleralthing.pagemodel;

import me.welkinbai.crawleralthing.annotation.UnThreadSafe;
import me.welkinbai.crawleralthing.path.Path;

import java.util.HashSet;
import java.util.Set;

@UnThreadSafe
public class ListPage extends Page {
    private Set<Path> pathsInPage;

    public ListPage(Path path) {
        super(path);
    }

    public Set<Path> getPathsInPage() {
        return pathsInPage;
    }

    public void setPathsInPage(Set<Path> pathsInPage) {
        this.pathsInPage = pathsInPage;
    }

    public boolean addPath(Path path) {
        ensurePaths();
        return pathsInPage.add(path);
    }

    private void ensurePaths() {
        if (pathsInPage == null) {
            pathsInPage = new HashSet<>();
        }
    }
}
