package cz.jk.library.ui;

import cz.jk.library.interfaces.Searchable;
import cz.jk.library.model.*;
import cz.jk.library.service.JsonStorage;
import cz.jk.library.service.LibraryService;
import cz.jk.library.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.in;

public class ConsoleMenu {
    private final LibraryService libraryService;
    private final UserService userService;
    private final Scanner sc;

    public ConsoleMenu(LibraryService libraryService, UserService userService) {
        this.userService = userService;
        this.libraryService = libraryService;
        this.sc = new Scanner(in);
    }

    private void showMenu() {
        System.out.println(
                "1. Vytvořit uživatele\n" +
                "2. Vytvořit knihu\n" +
                "3. Zobrazit všechny uživatele\n"+
                "4. Zobrazit všechny knihy\n"+
                "5. Zobrazit nezapůjčené knihy\n"+
                "6. Půjčit si knihu\n"+
                "7. Vrátit knihu\n"+
                "8. Hledat knihu\n"+
                "9. Vyhledávání\n"+
                "0. Konec programu\n"+
                "\n"+
                "Volba: ");
    }

    private void showSearchMenu() {
        System.out.println(
                "1. Podle názvu\n" +
                "2. Podle autora\n" +
                "3. Zpět"
        );
    }

    public void showCreateBookMenu() {
        System.out.println(
                "Jakou knihu chceš vytvořit?\n" +
                "1. Fyzická kniha\n"+
                "2. Audio kniha\n" +
                "3. E-book\n" +
                "4. Zpět"
        );
    }

    public void showEbookFormat() {
        System.out.println(
                "1. PDF\n"+
                "2. EPUB\n"+
                "3. Zpět"
        );
    }

    public void run() {
        LibraryData data = JsonStorage.load();
        libraryService.setBooks(data.books);
        userService.setUsers(data.users);

        System.out.println("Library Manager");
        System.out.println("//----------------//");
        int choice = 0;

        do {
            System.out.println();
            showMenu();
            choice = getChoice();

            switch (choice) {
                case 1:
                    handleCreateUser();
                    break;
                case 2:
                    handleCreateBook();
                    break;
                case 3:
                    userService.getAllUsers();
                    break;
                case 4:
                    libraryService.getAllBooks();
                    break;
                case 5:
                    libraryService.getAvailableBooks();
                    break;
                case 6:
                    handleBorrowBook();
                    break;
                case 7:
                    handleReturnBook();
                    break;
                case 8:
                    do {
                        showSearchMenu();
                        choice = getChoice();

                        switch (choice) {
                            case 1:
                                handleSearchBookByName();
                                break;
                            case 2:
                                handleSearchBookByAuthor();
                                break;
                            case 3:
                                break;
                        }

                    } while (choice != 3);
                    break;
                case 9:
                    handleSearch();
                    break;
                case 0:
                    data.books = libraryService.getBooks();
                    data.users = userService.getUsers();
                    JsonStorage.save(data);
                    break;
            }
        } while (choice != 0);
    }

    private void handleSearch() {
        String query;
        System.out.println("Zadej co hledáš: ");
        query = sc.nextLine();
        List<Searchable> searchableList = libraryService.search(query, libraryService.getBooks());
        List<Searchable> searchableList2 = userService.search(query, userService.getUsers());
        List<Searchable> matches = new ArrayList<>();
        matches.addAll(searchableList);
        matches.addAll(searchableList2);
        if(matches.isEmpty()) {
            System.out.println("Nic sem nenašel. ");
            return;
        }
        for(Searchable item : matches) {
            System.out.println(item.getClass().getSimpleName() + ": " + item);
        }
    }


    private int getChoice() {
        int choice;
        while(!sc.hasNextInt()) {
            System.out.println("Chyba při zadávání, musíš zvolit číslo. ");
            sc.nextLine();
        }
        choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    private void handleSearchBookByName() {
        System.out.println("Zadej název knihy: ");
        String bookName = sc.nextLine();
        List<Book> books = libraryService.findBooksByName(bookName);
        if (!books.isEmpty())
            for (Book b : books)
                System.out.println(b.getInfo());
        else {
            System.out.println("Tuto knihu neeviduji.");
        }
    }

    private void handleSearchBookByAuthor() {
        System.out.println("Zadej název autora: ");
        String author = sc.nextLine();
        List<Book> booksByAuthor = libraryService.findBooksByAuthor(author);
        if (!booksByAuthor.isEmpty()){
            for(Book b : booksByAuthor)
                System.out.println(b);
        } else {
            System.out.println("Knihu s tímto autorem neeviduji.");
        }
    }

    private void handleCreateBook() {
        showCreateBookMenu();
        int choice = getChoice();
        if (choice == 4) {
            return;
        }
        if (!Set.of(1, 2, 3).contains(choice)) {
            System.out.println("Špatně zadáno. ");
            return;
        }
        System.out.println("Zadej jméno knihy: ");
        String name = sc.nextLine();
        System.out.println("Zadej jméno autora: ");
        String author = sc.nextLine();
        System.out.println("Zadej datum vydání ve formátu yyyy-MM-dd: ");
        String dateString = sc.nextLine();
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.out.println("Špatně zadané datum: " + e.getMessage());
        }
        Book book = switch (choice) {
            case 1 -> createPhysicalBook(name, author, date);
            case 2 -> createAudioBook(name, author, date);
            case 3 -> createEbook(name, author, date);
            default -> null;
        };

        if(book != null)
            libraryService.addBook(book);
    }

    private Book createPhysicalBook(String name, String author, LocalDate date) {
        System.out.println("Zadej lokaci: ");
        String location = sc.nextLine();
        return new PhysicalBook(name, author, date, location);
    }

    private Book createAudioBook(String name, String author, LocalDate date) {
        System.out.println("Zadej délku v minutách: ");
        int duration = Integer.parseInt(sc.nextLine());
        System.out.println("Zadej jméno vypravěče: ");
        String narrator = sc.nextLine();
        return new AudioBook(name, author, date, duration, narrator);
    }

    private Book createEbook(String name, String author, LocalDate date) {
        System.out.println("Zadej velikost souboru v MB: ");
        float size = Float.parseFloat(sc.nextLine());
        System.out.println("Zadej formát: ");
        showEbookFormat();
        EbookFormat format = null;
        do {
            int choice = getChoice();
            if (choice == 3) {
                System.out.println("Nepovedlo se vytvořit knihu.");
                return null;
            }
            if (choice == 1) {
                format = EbookFormat.PDF;
            } else if (choice == 2)
                format = EbookFormat.EPUB;
            else
                System.out.println("Chyba při výběru, zkus znovu.");
        } while(format == null);
        return new Ebook(name, author, date, size, format);
    }

    private void handleCreateUser() {
        System.out.println("Zadej jméno uživatele: ");
        String firstName = sc.nextLine();
        System.out.println("Zadej příjmení uživatele: ");
        String lastName = sc.nextLine();
        userService.createUser(firstName, lastName);
        System.out.println("Uživatel vytvořen.");
    }

    private void handleBorrowBook() {
        String lastName;
        String firstName;
        System.out.println("Zadej jméno knihy: ");
        String bookName = sc.nextLine();
        List<Book> booksByName = libraryService.findBooksByName(bookName);
        List<Book> booksToBorrow = new ArrayList<>();
        if (!booksByName.isEmpty()) {
            System.out.println("Kterou knížku chceš půjčti?: ");
            libraryService.findAvailableBooksByName(booksByName, booksToBorrow);
            for (int i = 0; i< booksToBorrow.size(); i++)
                System.out.println(i + 1 + ". " + booksToBorrow.get(i).getInfo());
            int choice = getChoice();
            Book bookToBorrow = null;
            try {
                 bookToBorrow = booksToBorrow.get(choice - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Chybný výběr, musíš vybírat z dostupných.");
                return;
            }
            if(bookToBorrow.getStatus().equals(BookStatus.AVAILABLE)) {
                System.out.println("Komu knihu půjčujeme? Křesní jméno: ");
                firstName = sc.nextLine();
                System.out.println("Příjmení: ");
                lastName = sc.nextLine();
                User bookToUser = userService.findUserByNames(firstName, lastName);
                if(bookToUser == null) {
                    System.out.println("Uživatele " + firstName + " " + lastName +
                            " nemáme v databázi, takže vytářím: ");
                    userService.createUser(firstName, lastName);
                    bookToUser = userService.findUserByNames(firstName,lastName);
                }
                userService.addBookToUser(bookToBorrow, bookToUser);
                libraryService.borrowBook(bookToBorrow);
            } else {
                System.out.println("Kniha už je půjčena.");
            }
        } else {
            System.out.println("Tuto knihu neeviduji.");
        }
    }

    private void handleReturnBook() {
        String bookName;
        String lastName;
        String firstName;
        System.out.println("Zadej název knihy: ");
        bookName = sc.nextLine();
        List<Book> booksByName = libraryService.findBooksByName(bookName);
        List<Book> booksToReturn = new ArrayList<>();
        if(!booksByName.isEmpty()) {
            System.out.println("Kterou knížku vracíš?: ");
            for(int i = 0; i < booksByName.size(); i++)
                if (booksByName.get(i).getStatus().equals(BookStatus.BORROWED)) {
                    booksToReturn.add(booksByName.get(i));
                }
            for (int i = 0; i< booksToReturn.size(); i++)
                System.out.println(i + 1 + ". " + booksToReturn.get(i).getInfo());
            int choice = getChoice();
            Book bookToReturn = null;
            try {
                bookToReturn = booksToReturn.get(choice - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Chybný výběr, musíš vybírat z dostupných.");
                return;
            }
            if(bookToReturn.getStatus().equals(BookStatus.BORROWED)) {
                System.out.println("Kdo knížku vrací? Křestní jméno: ");
                firstName = sc.nextLine();
                System.out.println("Příjmení: ");
                lastName = sc.nextLine();
                User userToRemoveBook = userService.findUserByNames(firstName, lastName);
                if(userToRemoveBook != null) {
                    final Book finalBookToReturn = bookToReturn;
                    boolean hasBook = userToRemoveBook.getBookList().stream()
                            .anyMatch(b -> b.getName().equalsIgnoreCase(finalBookToReturn.getName())
                                    && b.getClass().equals(finalBookToReturn.getClass()));
                    if(!hasBook) {
                        System.out.println("Uživatel " + firstName + " " + lastName + " nemá půjčenou knihu: " + bookToReturn.getInfo());
                        return;
                    }
                    userService.removeBookFromUser(bookToReturn, userToRemoveBook);
                    libraryService.returnBook(bookToReturn);
                } else {
                    System.out.println("Uživatele " + firstName + " " + lastName + " sem nenašel.");
                }
            } else {
                System.out.println("Knížka: "+bookToReturn.getInfo() +" není vypůjčena.");
            }
        }
    }
}

