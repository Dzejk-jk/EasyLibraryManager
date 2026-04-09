package cz.jk.library;

import cz.jk.library.service.LibraryService;
import cz.jk.library.service.UserService;
import cz.jk.library.ui.ConsoleMenu;

public class Main {
    static void main() {
        LibraryService libraryService = new LibraryService();
        UserService userService = new UserService();
        ConsoleMenu menu = new ConsoleMenu(libraryService, userService);

        menu.run();
    }
}

