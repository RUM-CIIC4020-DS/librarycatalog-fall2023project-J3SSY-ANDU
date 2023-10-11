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

public class LibraryCatalog {
	
		
	public LibraryCatalog() throws IOException {
		this.getBooksFromFiles();
		this.getUsersFromFiles();
	}
	private List<Book> getBooksFromFiles() throws IOException {
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
	
	private List<User> getUsersFromFiles() throws IOException {
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
	public List<Book> getBookCatalog() {
		try {
			return this.getBooksFromFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<User> getUsers() {
		try {
			return this.getUsersFromFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void addBook(String title, String author, String genre) throws IOException {
		LocalDate date = LocalDate.of(2023, 9, 15);
		int newBookId = this.getBookCatalog().size()+1;		
		FileWriter file = new FileWriter("data/catalog.csv", true);
		BufferedWriter catalogFile = new BufferedWriter(file);
		catalogFile.newLine();
		catalogFile.append(newBookId + "," + title + "," + author + "," + genre + "," + date + ",false");
		catalogFile.close();
		return;
	}
	public void removeBook(int id) throws IOException {
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
		newCatalogFile.newLine();
		String line = catalogFile.readLine();
		while (line != null) {
			String[] bookInfoArray = line.split(",");
			if (Integer.parseInt(bookInfoArray[0]) != id) {
				newCatalogFile.write(line);
				newCatalogFile.newLine();
			}
			line = catalogFile.readLine();
		}
		catalogFile.close();
		newCatalogFile.close();
		oldF.delete();
		newF.renameTo(new File(catalogFileName));
		return;
	}	
	
	public boolean checkOutBook(int id) throws IOException {
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
		newCatalogFile.newLine();
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
			newCatalogFile.write(line);
			newCatalogFile.newLine();
			line = catalogFile.readLine();
		}
		catalogFile.close();
		newCatalogFile.close();
		oldF.delete();
		newF.renameTo(new File(catalogFileName));
		return result;
	}
	public boolean returnBook(int id) throws IOException {
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
		newCatalogFile.newLine();
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
			newCatalogFile.write(line);
			newCatalogFile.newLine();
			line = catalogFile.readLine();
		}
		catalogFile.close();
		newCatalogFile.close();
		oldF.delete();
		newF.renameTo(new File(catalogFileName));
		return result;
	}
	
	public boolean getBookAvailability(int id) {
		for (Book val : this.getBookCatalog()) {
			if (val.getId() == id) {
				return val.isCheckedOut();
			}
		}
		return false;
	}
	public int bookCount(String title) {
		int count = 0;
		for (Book val : this.getBookCatalog()) {
			if (val.getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
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
		output += "Adventure\t\t\t\t\t" + (/*Place here the amount of adventure books*/) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (/*Place here the amount of fiction books*/) + "\n";
		output += "Classics\t\t\t\t\t" + (/*Place here the amount of classics books*/) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (/*Place here the amount of mystery books*/) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (/*Place here the amount of science fiction books*/) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (/*Place here the total number of books*/) + "\n\n";
		
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
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" (/*Place here the total number of books that are checked out*/) + "\n\n";
		
		
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

			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (/*Place here the total amount of money owed to the library.*/) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		return null;
	}
	
	public List<User> searchForUsers(FilterFunction<User> func) {
		return null;
	}
	
}
