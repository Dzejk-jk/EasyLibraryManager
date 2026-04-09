package cz.jk.library.service;

import cz.jk.library.model.Book;
import cz.jk.library.model.Searchable;
import cz.jk.library.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private List<User> users = new ArrayList<>();

    public void createUser(String firstName, String lastName){
        User newUser = new User(firstName, lastName);
        users.add(newUser);
    }

    public void getAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Žádní uživatelé.");
            return;
        }
        for (User user : users) {
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
    }

    public void addBookToUser(Book book, User user) {
        List<Book> userList = user.getBookList();
        userList.add(book);
        System.out.println("Kniha: " + book.getInfo() + " zapůjčena.");
    }

    public User findUserByNames(String firstName, String lastName) {
        return users.stream()
                .filter(u -> u.getFirstName().equalsIgnoreCase(firstName) &&
                        u.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    public void removeBookFromUser(Book book, User user) {
        List<Book> userBookList = user.getBookList();
        userBookList.remove(book);
        System.out.println("Knížka: " + book.getInfo() + " byla vrácena.");
    }

    public List<Searchable> search(String query, List<User> items) {
        return items.stream()
                .filter(item -> item.matches(query))
                .collect(Collectors.toList());
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
