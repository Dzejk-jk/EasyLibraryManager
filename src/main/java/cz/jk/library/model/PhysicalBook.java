package cz.jk.library.model;

import java.time.LocalDate;

public class PhysicalBook extends Book {
    private String location;

    public PhysicalBook() {}

    public PhysicalBook(String name, String author, LocalDate releaseDate, String location) {
        super(name, author, releaseDate);
        this.location = location;
    }

    @Override
    public String getInfo() {
        return this.getName() + " - " + this.getAuthor() + " [Fyzická]" + " - " + this.getLocation() + " (" + this.getStatus()+")";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean matches(String query) {
        return name.equalsIgnoreCase(query) || author.equalsIgnoreCase(query);
    }
}
