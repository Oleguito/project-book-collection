import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookCollection {
    
    private List <Book> books = new ArrayList <Book>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void listBooks(List<Book> bookList) {
        int len = bookList.size();
            for(int i = 0; i < len; i++) {
                System.out.printf("%d - ", i);
                bookList.get(i).printHorizontal(true, true);
            }
    }
    
    public List<Book> readBooksListFromFile(String fileName) {
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
            booksThis.add(new Book(fileLines.get(i), scanner));
        }
        return booksThis;
    }
    
    public BookCollection() {
        books = readBooksListFromFile("books.csv");
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
            if (book.getTitle().contains(substring)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
    
    public void addBook() {
        String bookLine = "Дядя Вася;Василий;Вася интернешнл;1912;Про Васю;978-5-498-34543-2;книга, книга про васю, интересная книга;Н/Д;Н/Д";
        Book book = new Book(bookLine, scanner);
        book.editTitle();
        book.editAuthor();
        book.editPublisher();
        book.editReleaseYear();
        book.editGenre();
        book.editISBN();
        book.editTags();
        book.editLocation();
        book.editPath();
        
        books.add(book);
        // TODO: Записать в файл
    }
}