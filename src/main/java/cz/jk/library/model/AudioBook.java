package cz.jk.library.model;

import java.time.LocalDate;

public class AudioBook extends Book {
    private int duration;
    private String narrator;

    public AudioBook() {}

    public AudioBook(String name, String author, LocalDate releaseDate, int duration, String narrator) {
        super(name, author, releaseDate);
        this.duration = duration;
        this.narrator = narrator;
    }

    @Override
    public String getInfo() {
        int hours = duration / 60;
        int minutes = duration % 60;
        return this.getName() + " - " + this.getAuthor() + " [Audio]" + " - " + hours + "h "+minutes+"min" + " (" + this.getStatus()+")";
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNarrator() {
        return narrator;
    }

    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }

    @Override
    public boolean matches(String query) {
        return name.equalsIgnoreCase(query) || author.equalsIgnoreCase(query);
    }
}
