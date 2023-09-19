import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book implements Serializable {
    
    private String title;
    private String author;
    private String publisher;
    private int releaseYear;
    private String location; // Стеллаж
    private String path; // Файл

    private String isbn;
    private int article; // Скорее всего индекс/ключ в структуре данных
 
    private String genre;
    private String tags;
    
    Scanner scanner;
    Pattern regex;
    Matcher matcher;
    
    public Book(String title,
                String author,
                String publisher,
                int releaseYear,
                String location,
                String path,
                String isbn,
                int article,
                String genre,
                String tags,
                Scanner scanner,
                Pattern regex,
                Matcher matcher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.location = location;
        this.path = path;
        this.isbn = isbn;
        this.article = article;
        this.genre = genre;
        this.tags = tags;
        this.scanner = scanner;
        this.regex = regex;
    }
    
    /* Я полагаю, а бог располагает, что все записи в файле корректны
       При добавлении в ручном режиме все проверяется */
    public Book(String fileString, Scanner scanner) {
        // TODO: Возможно тут необходимо проверить fileString
        //  на верное количество точек с запятой
        // System.out.println(fileString);
        String[] split = fileString.split(";");
        // for(int i = 0; i < split.length; i++) {
        //     System.out.println(i + " " + split[i]);
        // }
        this.title = split[0];
        this.author = split[1];
        this.publisher = split[2];
        this.releaseYear = Integer.parseInt(split[3]);
        this.genre = split[4];
        this.isbn = split[5];
        this.tags = split[6];
        this.location = split[7];
        this.path = split[8];
        this.scanner = scanner;
    }
    
    public void edit() {
        System.out.println("Меню редактирования книги. Введите цифру, подходящую вашему действию.");
        System.out.println("1 - Наименование");
        System.out.println("2 - Автор");
        System.out.println("3 - Издательство");
        System.out.println("4 - Год выпуска");
        System.out.println("5 - ISBN");
        System.out.println("6 - Жанр");
        System.out.println("7 - Теги");
        System.out.println("8 - Расположение");
        System.out.println("9 - Путь");
        int choice = -1;
        while(choice < 1 || choice > 9) {
            System.out.print("Ваше действие: ");
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Введите число из списка!");
                scanner.nextLine();
                continue;
            }
        }
        scanner.nextLine();
        switch (choice) {
            case 1:
                editTitle(); break;
            case 2:
                editAuthor(); break;
            case 3:
                editPublisher(); break;
            case 4:
                editReleaseYear(); break;
            case 5:
                editISBN(); break;
            case 6:
                editGenre(); break;
            case 7:
                editTags(); break;
            case 8:
                editLocation(); break;
            case 9:
                editPath(); break;
        }
    }
    
    public void editPath() {
        System.out.println("Введите новое значение расположения в файловой системе.");
        String input = scanner.nextLine().trim();
        if (input.length() > 1000) {
            System.out.println("Слишком длинное.");
            scanner.nextLine();
            editLocation();
        } else {
            setLocation(input);
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    public void editLocation() {
        System.out.println("Введите новое значение расположения в книжном зале.");
        String input = scanner.nextLine().trim();
        if (input.length() > 1000) {
            System.out.println("Слишком длинное.");
            scanner.nextLine();
            editLocation();
        } else {
            setLocation(input);
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    public void editTags() {
        System.out.println("Введите новое значение списка тегов.");
        String input = scanner.nextLine().trim();
        if (input.length() > 1000) {
            System.out.println("Список слишком длинный.");
            scanner.nextLine();
            editTags();
        } else {
            setTags(input);
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    public void editGenre() {
        System.out.println("Введите новое значение жанра.");
        String input = scanner.nextLine().trim();
        if (input.length() > 200) {
            System.out.println("Слишком длинное.");
            scanner.nextLine();
            editGenre();
        } else {
            setGenre(input);
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    public void editISBN() {
        System.out.println("Введите новое значение ISBN.");
        String isbnRegex = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
        regex = Pattern.compile(isbnRegex);
        String input = scanner.nextLine().trim();
        matcher = regex.matcher(input);
        if (matcher.matches()) {
            System.out.println("Некорректный формат.");
            scanner.nextLine();
            setIsbn(input);
        } else {
            editISBN();
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    public void editReleaseYear() {
        System.out.println("Введите новое значение года выпуска.");
        int input = 0;
        try {
            input = scanner.nextInt();
            if (input < 1800 || input > 2100) {
                System.out.println("Неверное значение года.");
                editReleaseYear();
            } else {
                setReleaseYear(input);
                System.out.println("Изменения внесены успешно.");
            }
        } catch (Exception e) {
            System.out.println("Введите верное значение года.");
            scanner.nextLine();
            editReleaseYear();
        }
        
    }
    
    public void editPublisher() {
    System.out.println("Введите новое значение издательства.");
        String input = scanner.nextLine().trim();
        if (input.length() > 50) {
            System.out.println("Слишком длинное.");
            scanner.nextLine();
            editPublisher();
        } else {
            setPublisher(input);
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    public void editTitle() {
        System.out.println("Введите новое значение наименования книги.");
        String input = scanner.nextLine().trim();
        if (input.length() > 100) {
            System.out.println("Слишком длинное.");
            scanner.nextLine();
            editTitle();
        } else {
            setTitle(input);
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    public void editAuthor() {
        System.out.println("Введите новое значение автора книги.");
        System.out.println("Рекомендуется вводить сначала фамилию автора и инициалы далее, следующего автора через запятую");
        System.out.println("Пожалуйста, не используйте символ \"точка с запятой\" (;) для разделения!");
        String input = scanner.nextLine().trim();
        if (input.length() > 50) {
            System.out.println("Слишком длинное.");
            scanner.nextLine();
            editAuthor();
        } else if (input.contains(";")) {
            System.out.println("Пожалуйста, не используйте символ \"точка с запятой\" (;) для разделения!");
            scanner.nextLine();
            editAuthor();
        } else {
            setAuthor(input);
            System.out.println("Изменения внесены успешно.");
        }
    }
    
    // Separator
    
    void printVertical() {
        System.out.println("Наименование: " + title);
        System.out.println("Автор: " + author);
        System.out.println("Издательство: " + publisher);
        System.out.println("Год выпуска: " + releaseYear);
        System.out.println("Расположение: " + location);
        System.out.println("Путь: " + path);
        System.out.println("ISBN: " + isbn);
        System.out.println("Артикул: " + article);
        System.out.println("Жанр: " + genre);
        System.out.println("Теги: " + tags);
    }
    
    void printHorizontal(boolean location, boolean path) {
        System.out.printf("%s %s\t %s %6sг. %-20s АРТИКУЛ: %10d\n",
                cropToLength(title, 30),
                cropToLength(author, 30),
                cropToLength(publisher, 30),
                cropToLength(String.valueOf(releaseYear), 5),
                cropToLength(isbn, 20), article);
    }
    
    private String cropToLength(String arg, int length) {
        if (arg.length() > length) {
            return arg.substring(0, length - 3) + "...";
        } else {
            int diff = Math.abs(arg.length() - length);
            StringBuilder toAppend = new StringBuilder();
            for (int i = 0; i < diff; i++) {
                toAppend.append(' ');
            }
            return arg + toAppend;
        }
    }
    
    // Separator
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public void setArticle(int article) {
        this.article = article;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }

    // Разделение
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public int getReleaseYear() {
        return releaseYear;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public int getArticle() {
        return article;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public String getTags() {
        return tags;
    }
}
