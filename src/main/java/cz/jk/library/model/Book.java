package cz.jk.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import cz.jk.library.interfaces.Searchable;

import java.time.LocalDate;

import static cz.jk.library.model.BookStatus.AVAILABLE;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AudioBook.class, name = "audio"),
        @JsonSubTypes.Type(value = PhysicalBook.class, name = "physical"),
        @JsonSubTypes.Type(value = Ebook.class, name = "ebook")
})
public abstract class Book implements Searchable {
    public String name;
    public String author;
    public LocalDate releaseDate;
    public BookStatus status;

    public Book() {}

    public Book(String name, String author, LocalDate releaseDate) {
        this.name = name;
        this.author = author;
        this.releaseDate = releaseDate;
        this.status = AVAILABLE;
    }

    @JsonIgnore
    public abstract String getInfo();

    @Override
    public String toString() {
        return this.getName() + " - " + this.getAuthor() + " - " + this.getReleaseDate() + ": " + this.getStatus();
    }

    public String getName() {
        return name;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
