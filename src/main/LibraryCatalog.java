package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;


/**
 * @author jessy
 * @version 10/13/2023
 */
public class LibraryCatalog {
	
	/**
	 * No parameter Constructor	
	 * @throws IOException file existence, etc...
	 */
	public LibraryCatalog() throws IOException {
		this.getBooksFromFiles();
		this.getUsersFromFiles();
	}
	
	/**
	 * private variable that reads from the catalog.csv file and create a List of Books
	 * @return List of Books
	 * @throws IOException file related errors
	 */
	private List<Book> getBooksFromFiles() throws IOException {
		/**
		 * Se lee el catalog.csv y se guardan los datos de los libros en un ArrayList()
		 * Porque add() es O(1)
		 */
		List<Book> bookList = new ArrayList<Book>();
		FileReader catalog = new FileReader("data/catalog.csv");
		BufferedReader in = new BufferedReader(catalog);
		String header = in.readLine();
		String bookInfo = in.readLine();
		while (bookInfo != null) {
			String[] bookInfoArray = bookInfo.split(",");
			String[] date = bookInfoArray[4].split("-");
			LocalDate actualDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
			Book book = new Book(Integer.parseInt(bookInfoArray[0]), bookInfoArray[1], bookInfoArray[2], bookInfoArray[3], actualDate, Boolean.parseBoolean(bookInfoArray[5]));
			bookList.add(book);
			bookInfo = in.readLine();
		}
		in.close();
		return bookList;
	}
	
	/**
	 * private variable that reads from the user.csv file and create a List of Books
	 * @return List of users
	 * @throws IOException file related errors
	 */
	private List<User> getUsersFromFiles() throws IOException {
		/**
		 * Se lee el user.csv y se guardan los datos de los users en un ArrayList()
		 * Se utiliza ArrayList() en ambas listas: userList y bookArrayList
		 * Porque get y add son O(1)
		 */
		List<User> userList = new ArrayList<User>();
		FileReader userFile = new FileReader("data/user.csv");
		BufferedReader in = new BufferedReader(userFile);
		String header = in.readLine();
		String userInfo = in.readLine();
		while (userInfo != null) {
			String[] userInfoArray = userInfo.split(",");
			List<Book> bookArrayList = new ArrayList<Book>();
			if (userInfoArray.length > 2) {
				userInfoArray[2] = userInfoArray[2].substring(1, userInfoArray[2].length()-1);
				String[] bookList = userInfoArray[2].split(" ");
				for (int i = 0; i < bookList.length; i++) {
					for (int j = 0; j < this.getBooksFromFiles().size(); j++) {
						if (this.getBooksFromFiles().get(j).getId() == Integer.parseInt(bookList[i])) {
							bookArrayList.add(this.getBooksFromFiles().get(j));
							break;
						}
					}
				}
			}
			User user = new User(Integer.parseInt(userInfoArray[0]), userInfoArray[1], bookArrayList);
			userList.add(user);
			userInfo = in.readLine();
		}
		in.close();
		return userList;
	}
	
	/**
	 * getBookFromFiles getter
	 * @return List of Books in catalog.csv file
	 */
	public List<Book> getBookCatalog() {
		try {
			return this.getBooksFromFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * getUsersFromFiles getter
	 * @return List of Users in user.csv file
	 */
	public List<User> getUsers() {
		try {
			return this.getUsersFromFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method adds a book to the catalog of our library
	 * @param title new Book's title
	 * @param author new Book's author 
	 * @param genre new Book's genre
	 * @throws IOException file related errors
	 */
	public void addBook(String title, String author, String genre) throws IOException {
		/**
		 * Se rellena el libro con los valores predeterminados en la fecha y checkedOut
		 * Se calcula el id sumando 1 al size del catalogo
		 * Se utiliza true dentro de FileWriter para activar modo append en vez de write
		 * Se utilizan el resto de valores del libro con los valores de los parametros
		 */
		LocalDate date = LocalDate.of(2023, 9, 15);
		int newBookId = this.getBookCatalog().size()+1;		
		FileWriter file = new FileWriter("data/catalog.csv", true);
		BufferedWriter catalogFile = new BufferedWriter(file);
		catalogFile.newLine();
		catalogFile.append(newBookId + "," + title + "," + author + "," + genre + "," + date + ",false");
		catalogFile.close();
		return;
	}
	
	/**
	 * This method removes a book from the library catalog
	 * @param id ID's book to remove
	 * @throws IOException file related errors
	 */
	public void removeBook(int id) throws IOException {
		/**
		 * Se lee catalog.csv y se crea y se escribe un nuevo file que copia catalog.csv sin el libro 
		 * con el id pasado por parametro
		 * Al final se borra el file anterior y se renombra el nuevo file con el nombre del anterior
		 */
		String catalogFileName = "data/catalog.csv";
		String tempFile = "data/temp.csv";
		File oldF = new File(catalogFileName);
		File newF = new File(tempFile);
		FileReader oldFile = new FileReader("data/catalog.csv");
		FileWriter newFile = new FileWriter(tempFile, true);
		BufferedReader catalogFile = new BufferedReader(oldFile);
		BufferedWriter newCatalogFile = new BufferedWriter(newFile);
		String header = catalogFile.readLine();
		newCatalogFile.write(header);
		String line = catalogFile.readLine();
		while (line != null) {
			String[] bookInfoArray = line.split(",");
			if (Integer.parseInt(bookInfoArray[0]) != id) {
				newCatalogFile.newLine();
				newCatalogFile.write(line);
			}
			line = catalogFile.readLine();
		}
		catalogFile.close();
		newCatalogFile.close();
		oldF.delete();
		newF.renameTo(new File(catalogFileName));
		return;
	}	
	
	/**
	 * This method checks-out a book from the catalog and updates the information of the book
	 * @param id ID's book to checks-out
	 * @return true if the book can be checkedOut or false if the book cannot be checkedOut or doesn't exist
	 * @throws IOException file related errors
	 */
	public boolean checkOutBook(int id) throws IOException {
		/**
		 * Se lee catalog.csv y a la misma vez se crea y se escribe un nuevo file que guarda el catalogo
		 * buscando el libro con el mismo id que el parametro y se cambia checkedOut a true si es false y 
		 * se devuelve true o si el libro no esta o es true, se devuelve false
		 */
		String checkOutDate = "2023-09-15";
		String catalogFileName = "data/catalog.csv";
		String tempFile = "data/temp.csv";
		File oldF = new File(catalogFileName);
		File newF = new File(tempFile);
		FileReader oldFile = new FileReader(catalogFileName);
		FileWriter newFile = new FileWriter(tempFile, true);
		BufferedReader catalogFile = new BufferedReader(oldFile);
		BufferedWriter newCatalogFile = new BufferedWriter(newFile);
		String header = catalogFile.readLine();
		newCatalogFile.write(header);
		String line = catalogFile.readLine();
		boolean result = false;
		while (line != null) {
			String[] bookInfoArray = line.split(",");
			if (Integer.parseInt(bookInfoArray[0]) == id) {
				if (!Boolean.parseBoolean(bookInfoArray[5])) {
					bookInfoArray[5] = "true";
					bookInfoArray[4] = checkOutDate;
					line = bookInfoArray[0] + "," + bookInfoArray[1] + "," + bookInfoArray[2] + "," + bookInfoArray[3] + "," + bookInfoArray[4] + "," + bookInfoArray[5];
					result = true;
				}
			}
			newCatalogFile.newLine();
			newCatalogFile.write(line);
			line = catalogFile.readLine();
		}
		catalogFile.close();
		newCatalogFile.close();
		oldF.delete();
		newF.renameTo(new File(catalogFileName));
		return result;
	}
	
	/**
	 * This method returns a book to the catalog and updates it's information
	 * @param id ID's book to return
	 * @return true if the book is returned to the catalog or false if the book is already returned or doesn't exist 
	 * @throws IOException file related errors
	 */
	public boolean returnBook(int id) throws IOException {
		/**
		 * Se lee catalog.csv y a la misma vez se crea y se escribe un nuevo file que guarda el catalogo
		 * buscando el libro con el mismo id que el parametro y se cambia checkedOut a false si es true y 
		 * se devuelve true o si el libro no esta o es false, se devuelve false
		 * Al final se borra el file anterior y se renombra el nuevo file con el nombre del anterior
		 */
		String catalogFileName = "data/catalog.csv";
		String tempFile = "data/temp.csv";
		File oldF = new File(catalogFileName);
		File newF = new File(tempFile);
		FileReader oldFile = new FileReader(catalogFileName);
		FileWriter newFile = new FileWriter(tempFile, true);
		BufferedReader catalogFile = new BufferedReader(oldFile);
		BufferedWriter newCatalogFile = new BufferedWriter(newFile);
		String header = catalogFile.readLine();
		newCatalogFile.write(header);
		String line = catalogFile.readLine();
		boolean result = false;
		while (line != null) {
			String[] bookInfoArray = line.split(",");
			if (Integer.parseInt(bookInfoArray[0]) == id) {
				if (Boolean.parseBoolean(bookInfoArray[5])) {
					bookInfoArray[5] = "false";
					line = bookInfoArray[0] + "," + bookInfoArray[1] + "," + bookInfoArray[2] + "," + bookInfoArray[3] + "," + bookInfoArray[4] + "," + bookInfoArray[5];
					result = true;
				}
			}
			newCatalogFile.newLine();
			newCatalogFile.write(line);
			line = catalogFile.readLine();
		}
		catalogFile.close();
		newCatalogFile.close();
		oldF.delete();
		newF.renameTo(new File(catalogFileName));
		return result;
	}
	
	/**
	 * This method checks if a book is available to checkout
	 * @param id ID's book to check availability
	 * @return true if the book is in the catalog and false if the book is not available
	 */
	public boolean getBookAvailability(int id) {
		/**
		 * Se itera por el catalogo hasta encontrar el libro con el mismo id que el parametro
		 */
		for (Book val : this.getBookCatalog()) {
			if (val.getId() == id) {
				return !val.isCheckedOut();
			}
		}
		return false;
	}
	
	/**
	 * This method count how many books with specific title are in the catalog
	 * @param title title's book to count
	 * @return the count of the books
	 */
	public int bookCount(String title) {
		/**
		 * Se itera por el catalogo buscando los libros con el title igual al parametro
		 * y se devuelve la cuenta de todos estos
		 */
		int count = 0;
		for (Book val : this.getBookCatalog()) {
			if (val.getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * This method count how many books with specific genre are in the catalog
	 * @param genre genre of the book to count
	 * @return the count of the books
	 */
	public int booksPerGenre(String genre) {
		/**
		 * Se itera por el catalogo buscando los libros con el genre igual al parametro
		 * y se devuelve la cuenta de todos estos
		 */
		int count = 0;
		for (Book val : this.getBookCatalog()) {
			if (val.getGenre().equals(genre)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * The total count of the books in the catalog
	 * @return catalog size
	 */
	@SuppressWarnings("unused")
	public int totalBooks() {
		return this.getBookCatalog().size();
	}
	
	/**
	 * This method returns a string of all the books that are checkeOut
	 * @return books checked out
	 */
	public String booksCheckedOut() {
		/**
		 * Se busca por el catalogo los libros checkedOut y se devuelven
		 * en un String utilizando el metodo toString() de cada libro
		 */
		String books = "";
		for (Book book : this.getBookCatalog()) {
			if (book.isCheckedOut()) {
				books += book + "\n";
			}
		}
		return books;
	}
	
	/**
	 * This method counts the check out books
	 * @return count of the books
	 */
	public int totalCheckedOutBooks() {
		/**
		 * Se busca en el catalogo los libros que esten checkedOut, se cuentan
		 * y se devuelve el total
		 */
		int count = 0;
		for (Book book : this.getBookCatalog()) {
			if (book.isCheckedOut()) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * This method calculates all the fees that are due to the library by specific user
	 * @param user user
	 * @return sum of all the fees of specific user
	 */
	public float userTotalFees(User user) {
		/**
		 * Se obtiene un user y se itera por checkedOutList y sumamos los fees de cada libro 
		 * para obtener el total de fees por user
		 */
		float sum = 0;
		for (Book book : user.getCheckedOutList()) {
			sum += book.calculateFees();
		}
		return sum;
	}
	
	/**
	 * This method returns a string with all the users that owes late fees with their total fees
	 * @return string with users and total fees per user
	 */
	public String usersWithLateFees() {
		/**
		 * Buscamos en la lista de user a los user que tengas libros checkedOut
		 * y utilizamos el metodo userTotalFees para calcular los fees por user
		 * y devolvemos un String con los nombres de los users con sus respectivos fees
		 */
		String users = "";
		for (User user : this.getUsers()) {
			if (!user.getCheckedOutList().isEmpty()) {
				float fees = userTotalFees(user);
				users += user.getName() + "\t\t\t\t\t$" + String.format("%.02f",fees) + "\n"; 
			}
		}
		return users;
	}
	
	/**
	 * This method calculates all the fees that are due to the library by every user
	 * @return sum of fees
	 */
	public float totalFees() {
		/**
		 * Buscamos por la lista de users los users que tengan libros checkedOut
		 * y calculamos los fees por libro y sumamos el total de fees 
		 */
		float sum = 0;
		for (User user : this.getUsers()) {
			if (!user.getCheckedOutList().isEmpty()) {
				for (Book book : user.getCheckedOutList()) {
					sum += book.calculateFees();
				}
			}
		}
		return sum;
	}
	
	/**
	 * This method generates a report with all the most important info of the different users and catalog
	 * @throws IOException file related erros
	 */
	public void generateReport() throws IOException {
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + (booksPerGenre("Adventure")) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (booksPerGenre("Fiction")) + "\n";
		output += "Classics\t\t\t\t\t" + (booksPerGenre("Classics")) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (booksPerGenre("Mystery")) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (booksPerGenre("Science Fiction")) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (totalBooks()) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		output += booksCheckedOut();
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (totalCheckedOutBooks()) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		output += usersWithLateFees();
			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (String.format("%.02f", totalFees())) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		String reportName = "report/report.txt";
		File report = new File(reportName);
		FileWriter reportWriter = new FileWriter(report);
		BufferedWriter reportBuffered = new BufferedWriter(reportWriter);
		reportBuffered.write(output);
		reportBuffered.close();
		reportWriter.close();
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	
	/**
	 * Search for books that qualify for the FilterFunction condition
	 * @param func FilterFunction
	 * @return List of Books
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		/** 
		 * Elegí utilizar ArrayList() para utilizar add() y tener complejidad de tiempo O(1) al añadirlo al final
		 */
		List<Book> list = new ArrayList<Book>();
		for (Book book : this.getBookCatalog()) {
			if (func.filter(book)) {
				list.add(book);
			}
		}
		return list;
	}
	
	/**
	 * Search for users that qualify for the FilterFunction condition
	 * @param func FilterFunction
	 * @return List of Users
	 */
	public List<User> searchForUsers(FilterFunction<User> func) {
		/** 
		 * Elegí utilizar ArrayList() para utilizar add() y tener complejidad de tiempo O(1) al añadirlo al final
		 */
		List<User> list = new ArrayList<User>();
		for (User user : this.getUsers()) {
			if (func.filter(user)) {
				list.add(user);
			}
		}
		return list;
	}
	
}
