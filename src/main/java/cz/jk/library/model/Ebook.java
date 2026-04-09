package cz.jk.library.model;

import java.time.LocalDate;

public class Ebook extends Book {
    private float fileSize;
    private EbookFormat format;

    public Ebook() {}

    public Ebook(String name, String author, LocalDate releaseDate, float fileSize, EbookFormat format) {
        super(name, author, releaseDate);
        this.fileSize = fileSize;
        this.format = format;
    }

    @Override
    public String getInfo() {
        return this.getName() + " - " + this.getAuthor() + " [Ebook]" + " - " + this.getFormat() + ", " +
                this.getFileSize() + " MB" + " (" + this.getStatus()+")";
    }

    public float getFileSize() {
        return fileSize;
    }

    public void setFileSize(float fileSize) {
        this.fileSize = fileSize;
    }

    public EbookFormat getFormat() {
        return format;
    }

    public void setFormat(EbookFormat format) {
        this.format = format;
    }

    @Override
    public boolean matches(String query) {
        return author.equalsIgnoreCase(query) || name.equalsIgnoreCase(query);
    }
}
