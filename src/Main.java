import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    
    private static int currentYear = 2023;
    private static Scanner scanner = new Scanner(System.in);
    // private static Pattern regex;
    // private static Matcher matcher;
    private static MenuState menuState = MenuState.MENU_MAIN;
    // private static MenuState menuStatePrev;
    private static int numEntry = 0;
    private static BookCollection bookCollection = new BookCollection(currentYear);
    private static boolean running = true;
    private static Book bookToEdit = null;
    
    static int getNumEntry(int lowBoundExcluded, int highBoundExcluded, Scanner scanner) {
        int numToReturn = -0xFFFF;
        while (numToReturn < lowBoundExcluded || numToReturn > highBoundExcluded) {
            try {
                System.out.print("Ваш выбор: ");
                numToReturn = scanner.nextInt(); scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Введите число из списка!");
            }
        }
        return numToReturn;
    }
    
    static void runMenu () {
        
        while(running) {
            System.out.println();
            switch(menuState) {
                case MENU_MAIN:
                    doMainMenu();
                    break;
                case MENU_ADD:
                    doAddMenu();
                    break;
                case MENU_EDIT:
                    doEditMenu();
                    break;
                case MENU_DELETE:
                    doDeleteMenu();
                    break;
                case MENU_DISPLAY:
                    doDisplayMenu();
                    break;
                case MENU_SEARCH:
                    doSearchMenu();
                    break;
                // case MENU_SEARCH_TITLE:
                    // doSearchTitleMenu();
                    // break;
                // case MENU_SEARCH_AUTHOR:
                    // TODO: copy-paste?
                    // break;
                // case MENU_SEARCH_PUBLISHER:
                    // TODO: copy-paste?
                    // break;
                case MENU_EXIT:
                    doExitMenu();
                    break;
            }
        }
    }
    
    private static void doExitMenu() {
        System.out.println("Выход...");
        running = false;
    }
    
    private static void doWorkFoundBooks (List<Book> foundBooks) {
        if (foundBooks != null) {
            if (foundBooks.size() > 0) {
                System.out.println("Перечень книг.");
                BookCollection.listBooks(foundBooks);
                System.out.println("Введите число, соответствующее книге.");
                numEntry = getNumEntry(1, foundBooks.size(), scanner);
                System.out.println("Вы выбрали книгу номер " + numEntry);
                bookToEdit = foundBooks.get(numEntry - 1);
                System.out.println("Что вы хотите сделать?");
                System.out.println("1 - Редактировать запись о книге");
                System.out.println("2 - Удалить запись о книге");
                System.out.println("3 - Вернуться в главное меню");
                numEntry = getNumEntry(1, 3, scanner);
                switch (numEntry) {
                    case 1:
                        bookToEdit.edit();
                        menuState = MenuState.MENU_MAIN;
                        break;
                    case 2:
                        menuState = MenuState.MENU_DELETE;
                        break;
                    case 3:
                        menuState = MenuState.MENU_MAIN;
                        break;
                }
            } else {
                /* Вынужденный повтор кода? */
                // TODO: посмотреть можно ли поправить
                System.out.println("Книг не найдено.");
                System.out.println("Что вы хотите сделать?");
                System.out.println("1 - Перейти в меню поиска");
                System.out.println("2 - Вернуться в главное меню");
                numEntry = getNumEntry(1, 2, scanner);
                switch (numEntry) {
                    case 1:
                        menuState = MenuState.MENU_SEARCH;
                        break;
                    case 2:
                        menuState = MenuState.MENU_MAIN;
                        break;
                }
            }
        } else {
            System.out.println("Книг не найдено.");
            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Перейти в меню поиска");
            System.out.println("2 - Вернуться в главное меню");
            numEntry = getNumEntry(1, 2, scanner);
            switch (numEntry) {
                case 1:
                    menuState = MenuState.MENU_SEARCH;
                    break;
                case 2:
                    menuState = MenuState.MENU_MAIN;
                    break;
            }
        }
    }
    
    
    // /* Функция ищет книгу в зависимости от параметра
    //     */
    // private static List<Book> getBookListBySearchType(MenuState selectedMenu, String searchString) {
    //     switch (selectedMenu) {
    //         case MENU_SEARCH_TITLE:
    //
    //         case MENU_SEARCH_AUTHOR:
    //             doWorkFoundBooks(bookCollection.findInAuthor(searchString));
    //         case MENU_SEARCH_PUBLISHER:
    //             doWorkFoundBooks(bookCollection.findInPublisher(searchString));
    //     }
    //
    //
    //
    // }
    
    private static void processBookListForMenu () {
    
    }
    
    private static void doSearchMenu() {
        System.out.println("Поиск книги. Введите цифру, соответствующую требуемому действию.");
        System.out.println("1 - Поиск по наименованию");
        System.out.println("2 - Поиск по автору");
        System.out.println("3 - Поиск по издательству");
        numEntry = getNumEntry(1, 3, scanner);
        switch (numEntry) {
            case 1:
                System.out.println("Меню поиска по наименованию.");
                System.out.println("Введите подстроку, которая должна содержаться в наименовании книги.");
                String entry = scanner.nextLine().trim();
                System.out.printf("Вы ввели \"%s\"\n", entry);
                doWorkFoundBooks(bookCollection.findInTitle(entry));
                break;
            case 2:
                System.out.println("Меню поиска по автору.");
                System.out.println("Введите подстроку, которая должна содержаться в строке, содержащей автора книги.");
                entry = scanner.nextLine().trim();
                System.out.printf("Вы ввели \"%s\"\n", entry);
                doWorkFoundBooks(bookCollection.findInAuthor(entry));
                break;
            case 3:
                System.out.println("Меню поиска по издательству.");
                System.out.println("Введите подстроку, которая должна содержаться в строке, содержащей издательство книги.");
                entry = scanner.nextLine().trim();
                System.out.printf("Вы ввели \"%s\"\n", entry);
                doWorkFoundBooks(bookCollection.findInPublisher(entry));
                break;
        }
    }
    
    private static void doDisplayMenu() {
        bookCollection.displayBooks();
        menuState = MenuState.MENU_MAIN;
    }
    
    private static void doDeleteMenu() {
        System.out.println("Удаление книги.");
        if (bookToEdit == null) {
            System.out.println("К сожалению, вы не выбрали книгу для удаления.");
            System.out.println("Воспользуйтесь поиском для выбора книги.");
            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Перейти в меню поиска");
            System.out.println("2 - Вернуться в главное меню");
            numEntry = getNumEntry(1, 2, scanner);
            switch (numEntry) {
                case 1:
                    menuState = MenuState.MENU_SEARCH;
                    break;
                case 2:
                    menuState = MenuState.MENU_MAIN;
                    break;
            }
        } else {
            System.out.println("Выбранная книга:");
            bookToEdit.printVertical();
            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Удалить запись о книге");
            System.out.println("2 - Перейти в меню поиска");
            System.out.println("3 - Вернуться в главное меню");
            numEntry = getNumEntry(1, 3, scanner);
            switch (numEntry) {
                case 1:
                    bookCollection.deleteBook(bookToEdit);
                    menuState = MenuState.MENU_MAIN;
                    break;
                case 2:
                    menuState = MenuState.MENU_SEARCH;
                    break;
                case 3:
                    menuState = MenuState.MENU_MAIN;
                    break;
            }
        }
    }
    
    private static void doEditMenu() {
        System.out.println("Редактирование книги.");
        if (bookToEdit == null) {
            System.out.println("К сожалению, вы не выбрали книгу для редактирования.");
            System.out.println("Воспользуйтесь поиском для выбора книги.");
            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Перейти в меню поиска");
            System.out.println("2 - Вернуться в главное меню");
            numEntry = getNumEntry(1, 2, scanner);
            switch (numEntry) {
                case 1:
                    menuState = MenuState.MENU_SEARCH;
                    break;
                case 2:
                    menuState = MenuState.MENU_MAIN;
                    break;
            }
        } else {
            System.out.println("Выбранная книга:");
            bookToEdit.printVertical();
            System.out.println("Что вы хотите сделать?");
            System.out.println("1 - Редактировать запись о книге");
            System.out.println("2 - Вернуться в главное меню");
            numEntry = getNumEntry(1, 2, scanner);
            switch (numEntry) {
                case 1:
                    bookCollection.editBook(bookToEdit);
                    menuState = MenuState.MENU_MAIN;
                    break;
                case 2:
                    menuState = MenuState.MENU_MAIN;
                    break;
            }
        }
    }
    
    private static void doAddMenu() {
        // System.out.println();
        System.out.println("Добавление книги.");
        bookCollection.addBook(currentYear);
        menuState = MenuState.MENU_MAIN;
    }
    
    private static void doMainMenu() {
        numEntry = -0xFFFFFFFF;
        // System.out.println("\n");
        System.out.println("Главное меню. Введите цифру, подходящую вашему действию.");
        System.out.println("1 - Добавить книгу");
        System.out.println("2 - Редактировать книгу");
        System.out.println("3 - Удалить книгу");
        System.out.println("4 - Показать книги");
        System.out.println("5 - Поиск книги");
        System.out.println("6 - Выход");
        numEntry = getNumEntry(1, 6, scanner);
        switch (numEntry) {
            case 1:
                menuState = MenuState.MENU_ADD;
                break;
            case 2:
                menuState = MenuState.MENU_EDIT;
                break;
            case 3:
                menuState = MenuState.MENU_DELETE;
                break;
            case 4:
                menuState = MenuState.MENU_DISPLAY;
                break;
            case 5:
                menuState = MenuState.MENU_SEARCH;
                break;
            case 6:
                menuState = MenuState.MENU_EXIT;
                break;
        }
    }
    
    public static void main(String[] args)
    {
        runMenu();
    }
}
