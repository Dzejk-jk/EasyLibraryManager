package cz.jk.library.model;

import java.util.ArrayList;
import java.util.List;

public class User implements Searchable {
    private String firstName;
    private String lastName;
    private List<Book> bookList;

    public User() {}

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookList = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public boolean matches(String query) {
        return firstName.equalsIgnoreCase(query) || lastName.equalsIgnoreCase(query);
    }
}
