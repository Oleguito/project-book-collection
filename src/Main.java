import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    
    static int getNumEntry(int lowBoundExcluded, int highBoundExcluded, Scanner scanner) {
        int numToReturn = -0xFFFF;
        while (numToReturn < lowBoundExcluded || numToReturn > highBoundExcluded) {
            try {
                System.out.print("Ваш выбор: ");
                if(scanner.hasNextLine()) scanner.nextLine();
                numToReturn = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Введите число из списка!");
            }
        }
        return numToReturn;
    }
    
    public static void main(String[] args)
    {
     
        Scanner scanner = new Scanner(System.in);
        Pattern regex;
        Matcher matcher;
        MenuState menuState = MenuState.MENU_MAIN;
        MenuState menuStatePrev;
        int numEntry = 0;
        BookCollection bookCollection = new BookCollection();
        boolean running = true;
        Book bookToEdit = null;
        
        while(running) {
            switch(menuState) {
                case MENU_MAIN:
                    numEntry = 0;
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
                    break;
                case MENU_ADD:
                    System.out.println("Добавление книги.");
                    bookCollection.addBook();
                    menuState = MenuState.MENU_MAIN;
                    break;
                case MENU_EDIT:
                    System.out.println("Редактирование книги.");
                    if (bookToEdit == null) {
                        System.out.println("К сожалению, вы не выбрали книгу для редактирования.");
                        System.out.println("Воспользуйтесь поиском для выбора книги.");
                    } else {
                        System.out.println("Выбранная книга:");
                        bookToEdit.printVertical();
                        bookToEdit.edit();
                    }
                    break;
                case MENU_DELETE:
                    break;
                case MENU_DISPLAY:
                    bookCollection.displayBooks();
                    menuState = MenuState.MENU_MAIN;
                    break;
                case MENU_SEARCH:
                    System.out.println("Поиск книги. Введите цифру, соответствующую требуемому действию.");
                    System.out.println("1 - Поиск по наименованию");
                    System.out.println("3 - Поиск по автору");
                    System.out.println("3 - Поиск по издательству");
                    numEntry = getNumEntry(1, 3, scanner);
                    switch (numEntry) {
                        case 1:
                            menuState = MenuState.MENU_SEARCH_TITLE;
                            break;
                        case 2:
                            menuState = MenuState.MENU_SEARCH_AUTHOR;
                            break;
                        case 3:
                            menuState = MenuState.MENU_SEARCH_PUBLISHER;
                            break;
                    }
                    break;
                case MENU_SEARCH_TITLE:
                    System.out.println("Меню поиска по наименованию.");
                    System.out.println("Введите подстроку, которая должна содержаться в наименовании книги.");
                    if(scanner.hasNextLine()) scanner.nextLine();
                    String entry = scanner.nextLine();
                    System.out.printf("Вы ввели \"%s\"\n", entry);
                    List<Book> foundBooks = bookCollection.findInTitle(entry);
                    if (foundBooks != null) {
                        System.out.println("Перечень книг.");
                        BookCollection.listBooks(foundBooks);
                        System.out.println("Введите число, соответствующее книге.");
                        numEntry = getNumEntry(1, foundBooks.size(), scanner);
                        System.out.println("Вы выбрали книгу номер " + numEntry);
                        bookToEdit = foundBooks.get(numEntry - 1);
                        
                    } else {
                        System.out.println("Книг не найдено.");
                        menuState = MenuState.MENU_MAIN;
                    }
                    break;
                case MENU_SEARCH_AUTHOR:
                    break;
                case MENU_SEARCH_PUBLISHER:
                    break;
                case MENU_EXIT:
                    System.out.println("Выход...");
                    running = false;
                    break;
            }
            menuStatePrev = menuState;
        }
        
        
        
        // Book book = new Book(
        //         "Стань диким!",
        //         "Эрин Хантер",
        //         "Олма-Пресс",
        //         2003,
        //         "N/A",
        //         "N/A",
        //         "5-224-04342-5",
        //         -1,
        //         "Сказка",
        //         "детская литература, коты-воители, кошки, животные",
        //         scanner, null, null);
        
        // System.out.println(book.equals(book2));
        
        // book.printVertical();
        // book.edit();
        // book.printVertical();
        
        // FileUtils.objectToFile(book,"file.bin");
        
        
        
        
        
    }
}
