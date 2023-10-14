package main;

import java.io.IOException;
import java.time.LocalDate;

import data_structures.DoublyLinkedList;
import interfaces.List;

public class TestMain {

	/*
	 * You can use this method for testing. If you run it as is 
	 * you should be able to generate the same report as report/expected_report.txt
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Book class testing
//		Constructor: Book(int ID, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut)

//		LocalDate date1 = LocalDate.of(2023, 8, 27);
//		LocalDate date2 = LocalDate.of(2022, 9, 14);
//		LocalDate date3 = LocalDate.of(2023, 8, 15);

//		Book newBook1 = new Book(1,"The Secret of the Old Clock","Carolyn Keene","Mystery",date1,true);
//		Book newBook2 = new Book(1,"The Secret of the Old Clock","Carolyn Keene","Mystery",date2,true);
//		Book newBook3 = new Book(1,"The Secret of the Old Clock","Carolyn Keene","Mystery",date3,true);
		
//		float fee1 = newBook1.calculateFees();
//		float fee2 = newBook2.calculateFees();
//		float fee3 = newBook3.calculateFees();
		
//		float sum = fee1 + fee2;
		
//		System.out.println(sum);
//		System.out.println(newBook.getId());
//		newBook.setId(8);
//		System.out.println(newBook.getId());
//		System.out.println(newBook.getTitle());
//		newBook.setTitle("Jessy's Story");
//		System.out.println(newBook.getTitle());
//		System.out.println(newBook.getAuthor());
//		newBook.setAuthor("Jessy Andujar");
//		System.out.println(newBook.getAuthor());
//		System.out.println(newBook.getGenre());
//		newBook.setGenre("Drama");
//		System.out.println(newBook.getGenre());
//		float fee = newBook.calculateFees();
//		System.out.println(newBook.getLastCheckOut());
//		newBook.setLastCheckOut(newDate);
//		System.out.println(newBook.getLastCheckOut());
//		System.out.println(newBook.isCheckedOut());
//		newBook.setCheckedOut(false);
//		System.out.println(newBook.isCheckedOut());
//		newBook.setCheckedOut(true);
//		System.out.println(newBook.isCheckedOut());
//		System.out.println(newBook.toString());
//		float newFee = newBook.calculateFees();
//		float totalFee = fee + newFee;
//		System.out.println("Total fee: " + totalFee);
//		System.out.println(newBook);


//		User class testing
//		Constructor: User(int ID, String name, List<Book> checkedOutList)
//		List<Book> listOfBooks = new DoublyLinkedList<Book>();
//		listOfBooks.add(newBook);
//		User newUser = new User(1,"John Smith",listOfBooks);
//		System.out.println(newUser.getId());
//		newUser.setId(2);
//		System.out.println(newUser.getId());
//		System.out.println(newUser.getName());
//		newUser.setName("Jessy Andujar");
//		System.out.println(newUser.getName());
//		System.out.println(newUser.getCheckedOutList());
//		listOfBooks.add(new Book(1, "Title", "Author", "Genre", date, true));
//		System.out.println(newUser.getCheckedOutList());
//		System.out.println(newUser);

		
		
//		LibraryCatalog class testing
		try {
			LibraryCatalog lc = new LibraryCatalog();
//			System.out.println(lc.getBookCatalog());
//			System.out.println(lc.getUsers());
//			System.out.println(lc.getBookCatalog());
//			lc.addBook("Atomic Habits", "James Clear", "Self-Help");
//			System.out.println(lc.getBookCatalog());
//			lc.removeBook(1);
//			System.out.println(lc.getBookCatalog());
//			System.out.println(lc.checkOutBook(23));
//			System.out.println(lc.returnBook(2));
//			System.out.println(lc.getBookAvailability(23));
//			System.out.println(lc.bookCount("The Hobbit"));
			lc.generateReport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
