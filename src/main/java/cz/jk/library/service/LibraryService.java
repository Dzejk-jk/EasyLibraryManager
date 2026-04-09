package cz.jk.library.service;

import cz.jk.library.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryService {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        boolean exists = books.stream()
                .anyMatch(b -> b.getName().equalsIgnoreCase(book.getName())
                        && b.getClass().equals(book.getClass()));

        if (exists) {
            System.out.println("Kniha s názvem " + book.getName() + " a stejným typem už v knihovně je.");
            return;
        }

        System.out.println("Vytvořena knížka: " + book.getInfo());
        books.add(book);
    }

    public void getAllBooks() {
        for(Book book : books) {
            System.out.println(book.getInfo());
        }
    }

    public void getAvailableBooks() {
        System.out.println("Dostupné knihy: ");
        for (Book book : books) {
            if (book.getStatus() == BookStatus.AVAILABLE)
                System.out.println(book.getInfo());
        }
    }

    public void borrowBook(Book book) {
        if(book.getStatus() != BookStatus.BORROWED)
            book.setStatus(BookStatus.BORROWED);
        else
            System.out.println("Kniha " + book.getInfo() + " nejde půjčit.");
    }

    public void returnBook(Book book) {
        if (book.getStatus() != BookStatus.AVAILABLE)
            book.setStatus(BookStatus.AVAILABLE);
    }

    public List<Book> findBooksByName(String bookName) {
        return books.stream()
                .filter(b -> b.name.equalsIgnoreCase(bookName))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(b -> b.author.equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Searchable> search(String query, List<Book> items) {
        return items.stream()
                .filter(item -> item.matches(query))
                .collect(Collectors.toList());
    }
    public void findAvailableBooksByName(List<Book> booksByName, List<Book> booksToBorrow) {
        for (int i = 0; i < booksByName.size(); i++) {
            if (!booksByName.get(i).getStatus().equals(BookStatus.BORROWED))
                booksToBorrow.add(booksByName.get(i));
        }
    }
}
