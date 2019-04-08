package me.welkinbai.crawleralthing.pagemodel;

import me.welkinbai.crawleralthing.path.Path;

import java.util.Objects;

public abstract class Page {
    private Path path;

    public Page(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(path, page.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(path);
    }
}
