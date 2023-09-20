import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookCollection {
    
    private List <Book> books = new ArrayList <Book>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void listBooks(List<Book> bookList) {
        int len = bookList.size();
            for(int i = 0; i < len; i++) {
                System.out.printf("%d - ", i + 1);
                bookList.get(i).printHorizontal(true, true);
            }
    }
    
    public List<Book> readBooksListFromFile(String fileName, int currentYear) {
        FileReader fr;
        BufferedReader br;
        List<String> fileLines = new ArrayList<String>();
        List<Book> booksThis = new ArrayList<Book>();
        String line;
        /* Эти скобки среда поставила и без них не работает */
        {
            try {
                fr = new FileReader(fileName);
                br = new BufferedReader(fr);
                while((line = br.readLine()) != null) {
                    fileLines.add(line);
                }
                fr.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for(int i = 1; i < fileLines.size(); i++) {
            booksThis.add(new Book(fileLines.get(i), scanner, currentYear));
        }
        return booksThis;
    }
    
    public BookCollection(int currentYear) {
        books = readBooksListFromFile("books.csv", currentYear);
    }
    
    public void displayBooks() {
        System.out.println("Отображение книг");
        for(Book book : books) {
            book.printHorizontal(false, false);
        }
    }
    
    public List<Book> findInTitle(String substring) {
        List<Book> foundBooks = new ArrayList<Book>();
        for(Book book : books) {
            if (book.getTitle().toLowerCase().contains(substring.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
    
    public List<Book> findInAuthor(String substring) {
        List<Book> foundBooks = new ArrayList<Book>();
        for(Book book : books) {
            if (book.getAuthor().toLowerCase().contains(substring.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
    
    public List<Book> findInPublisher(String substring) {
        List<Book> foundBooks = new ArrayList<Book>();
        for(Book book : books) {
            if (book.getPublisher().toLowerCase().contains(substring.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
    
    public void editBook(Book book) {
        if (book != null) {
            book.edit();
            System.out.println("Запись о книге изменена.");
            writeBooksToFile("books.csv");
            System.out.println("Изменения сохранены.");
        } else {
            System.out.println("Книга не выделена");
        }
    }
    
    public void addBook(int currentYear) {
        String bookLine = "Title;Author;Publisher;0001;Genre;978-5-498-34543-2;tag1, tag2, tag3;N/A;N/A";
        Book book = new Book(bookLine, scanner, currentYear);

        book.editReleaseYear(currentYear);
        book.editTitle();
        book.editAuthor();
        book.editPublisher();
        book.editGenre();
        // book.editISBN();
        book.editTags();
        book.editLocation();
        book.editPath();
        
        
        // System.out.println(books.get(books.size() - 1).toString());
        // System.out.println(bookLine);
        // System.out.println(books.get(books.size() - 1).toString().equals(bookLine));
        books.add(book);
        System.out.println("Книга добавлена.");
        writeBooksToFile("books.csv");
        System.out.println("Изменения сохранены.");
        // TODO: Включить выключенные
    }
    
    private void writeBooksToFile (String fileName) {
        FileWriter fw;
        BufferedWriter bw;
        try {
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            bw.write("Наименование;Автор;Издательство;Год выпуска;Жанр;ISBN;Теги;Расположение;Путь");
            for(Book book : books) {
                bw.write(String.format(
                    "%s;%s;%s;%s;%s;%s;%s;%s;%s;\n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getReleaseYear(),
                    book.getGenre(),
                    book.getIsbn(),
                    book.getTags(),
                    book.getLocation(),
                    book.getPath()
                ));
            }
            
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public void deleteBook(Book bookToDelete) {
        books.remove(bookToDelete);
        System.out.println("Книга удалена.");
        writeBooksToFile("books.csv");
        System.out.println("Изменения сохранены.");
    }
}