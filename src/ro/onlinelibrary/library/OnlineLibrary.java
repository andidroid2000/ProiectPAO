package ro.onlinelibrary.library;

import ro.onlinelibrary.comparators.NameComparator;
import ro.onlinelibrary.others.Address;
import ro.onlinelibrary.people.Author;
import ro.onlinelibrary.people.Personnel;
import ro.onlinelibrary.people.readers.Student;
import ro.onlinelibrary.people.readers.Adult;

import java.util.*;


public class OnlineLibrary {
    private String name;
    private Address address;
    private List<Personnel> personnel = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private List<Student> students = new ArrayList<>(); // a list to store all the student readers
    private List<Adult> adults = new ArrayList<>(); // a list used to store all the adult readers
    private List<Book> books = new ArrayList<>(); // a list to store all the books from the library
    private List<List<Book>> booksBorrowedByStudents; // a list with books loaned by the library to all student readers
    private List<List<Book>> booksBorrowedByAdults; // a list with books loaned by the library to all adult readers

// the city in which the physical library is situated
    public OnlineLibrary(String name, Address  address) {
        this.address = address;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // accessors to the number of readers of each type
    public int getNoOfAdultReaders(){
        return adults.size();
    }

    public int getNoOfStudentReaders(){
        return students.size();
    }

// accessors to the storage place where info is held about the borrowings
    public List<List<Book>> getBooksBorrowedByStudents(){
        return booksBorrowedByStudents;
    }

    public List<List<Book>> getBooksBorrowedByAdults(){
        return booksBorrowedByAdults;
    }

    public List<Book> getBooks() {
        return books;
    }

    // accessors to lists with info about subscribed readers
    public List<Student> getStudents()
    {
        return students;
    }

    public List<Adult> getAdults()
    {
        return adults;
    }

// mutators of the storage place where info is held about the borrowings
    public void setBooksBorrowedByStudents(List<List<Book>> booksBorrowedByStudents) {
        this.booksBorrowedByStudents = booksBorrowedByStudents;
    }

    public void setBooksBorrowedByAdults(List<List<Book>> booksBorrowedByAdults) {
        this.booksBorrowedByAdults = booksBorrowedByAdults;
    }

// method for subscribing readers
    public void addReader(Student student){
        students.add(student);
    }

    public void addReader(Adult adult){
        adults.add(adult);
    }

// method for adding books to the library
    public void addBook(Book book){
        books.add(book);
    }

// method for adding employees
    public void addEmployee(Personnel employee)
    {
        personnel.add(employee);
    }
    public void addEvent(Event event)
    {
        events.add(event);
    }
// method to actualise the borrowed books of the readers - polymorphism for adult/student
    public void studentBorrowsBook(Student student, Book book) {
        var studentId = students.indexOf(student);
        int i =0;
        List<Book> books= booksBorrowedByStudents.get(studentId);
        while(i<4){
            if(books.get(i).getTitle() == null) {
                booksBorrowedByStudents.get(studentId).set(i,book);
                break;
            }
            else {
                i++;
            }
        }
    }

    public void adultBorrowsBook(Adult adult, Book book){
        var adultId = adults.indexOf(adult);
        int i =0;
        List<Book> books= booksBorrowedByAdults.get(adultId);
        while(i<3){
            if(books.get(i).getTitle() == null) {
                booksBorrowedByAdults.get(adultId).set(i,book);
                break;
            }
            else {
                i++;
            }
        }
    }

// method to show info about a certain student/adult borrowings
    public String showBorrowedBooks(String firstName, String lastName, int max, List<Book> bookBorrowed){
        StringBuilder buffer = new StringBuilder();
        buffer.append("<< " + firstName + " " + lastName + " >> \n");
        buffer.append("\t Books borrow status:\n");
        int counter = 0;
        for (Book book: bookBorrowed) {
            var title = book.getTitle();
            if(title != null)
            {
                counter++;
                buffer.append("\t " + counter + ". " + title + "("+book.getPublishingYear()+")\n");
            }
        }
        buffer.append("\t Total: " + counter + " of " +max+ " possible.\n");
        if(counter == 0){
            buffer.append("\t No books borrowed at the moment!\n");
        }
        return buffer.toString();
    }

// method to show all the subscribed readers of the library
    public String showReaders(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("<< Student readers >>\n");
        int counter  = 0;
        for (Student student: students) {
            counter++;
            buffer.append(counter + ". " + student.getFirstName() + " " + student.getLastName() + "\n");
            buffer.append("\t- School:" + student.getSchool() + "\n");
            buffer.append("\t- Year:" + student.getYear() + "\n");
        }
        counter  = 0;
        buffer.append("<< Adult readers >>\n");
        for (Adult adult: adults) {
            counter++;
            buffer.append(counter + ". " + adult.getFirstName() + " "+ adult.getLastName() + "\n");
            buffer.append("\t- Company:" + adult.getEmployerCompany()+ "\n");
            buffer.append("\t- Job:" + adult.getJob() + "\n");
        }
        return buffer.toString();
    }

// method to show info about the books
    public String showBooks(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("<< Books >>\n");
        int counter  = 0;
        for (Book book: books) {
            counter++;
            buffer.append(counter + ". " + book.getTitle().toUpperCase() + " ("+book.getPublishingYear() +") by "+ book.getAuthor().getFirstName()
                    + " " + book.getAuthor().getLastName() +"\n");
            buffer.append("\t - Genre:" + book.getGenre() + "\n");
            buffer.append("\t - Description:" + book.getDescription() + "\n");
        }
        return buffer.toString();
    }

    public static void sort(List<Book> books)
    {
        Collections.sort(books, new NameComparator());
    }

    public Set<Author> getDistinctAuthors()
    {
        Set<Author> authors = new HashSet<>();
        for(Book book: books)
            authors.add(book.author);
        return authors;
    }

    public String showAuthors(Set<Author> authors)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<< Authors >>\n");

        int counter  = 0;
        for (Author author: authors) {
            counter++;
            buffer.append(counter + ". " + author.getFirstName() + " " + author.getLastName() + "\n");
        }
        return buffer.toString();
    }
}
