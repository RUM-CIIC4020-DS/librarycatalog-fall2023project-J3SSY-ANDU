package main;

import java.time.LocalDate;


/**
 * @author jessy
 * @version 10/13/2023
 */
public class Book {
	private int ID;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;
	
	/**
	 * Constructor
	 * @param ID Book's ID
	 * @param title Book's title
	 * @param author Book's author
	 * @param genre Book's genre
	 * @param lastCheckOut Book's lastCheckOut date
	 * @param checkedOut Book's boolean variable to know if is checkedOut
	 */
	public Book(int ID, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut) {
		this.ID = ID;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.lastCheckOut = lastCheckOut;
		this.checkedOut = checkedOut;
	}
	
	/**
	 * ID getter
	 * @return value passed as Book's ID in Constructor
	 */
	public int getId() {
		return this.ID;
	}
	
	/**
	 * ID setter
	 * @param id new value passed
	 */
	public void setId(int id) {
		this.ID = id;
	}
	
	/**
	 * Title getter
	 * @return value passed as Book's title in Constructor
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Title setter
	 * @param title new value passed
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Author getter
	 * @return value passed as Book's Author in Constructor
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Author setter
	 * @param author new value passed
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Genre getter
	 * @return value passed as Book's Genre in Constructor
	 */
	public String getGenre() {
		return this.genre;
	}
	
	/**
	 * Genre setter
	 * @param genre new value passed
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * LastCheckOut getter
	 * @return value passed as LastCheckOut date in Constructor
	 */
	public LocalDate getLastCheckOut() {
		return this.lastCheckOut;
	}
	
	/**
	 * LastCheckOut setter
	 * @param lastCheckOut new value passed
	 */
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	
	/**
	 * CheckedOut getter
	 * @return value passed as CheckedOut in Constructor
	 */
	public boolean isCheckedOut() {
		return this.checkedOut;
	}
	
	/**
	 * CheckedOut setter
	 * @param checkedOut new value passed
	 */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		String title = this.title.toUpperCase();
		String author = this.author.toUpperCase();
		return title + " BY " + author;
	}
	
	/**
	 * Method that calculates how much a checkedOut book has in late fees
	 * @return total fee
	 */
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 * 
		 * Primero se toma en consideracion si el libro esta checkedOut
		 * Si es asi se calcula cuanto tiempo lleva fuera el libro
		 * Se calcula si lleva fuera años y se multiplican los dias por año
		 * Si es cierto o no el if anterior se calculan los dias que lleva fuera
		 * Se devuelve el resultado en float al final
		 */		 
		LocalDate todayDate = LocalDate.of(2023, 9, 15);
		float result = 0;
		float addedDays = 0;
		
		if (this.isCheckedOut()) {
			if (todayDate.getYear() != this.getLastCheckOut().getYear()) {
				int years = todayDate.getYear() - this.getLastCheckOut().getYear();
				addedDays += 365 * years;
			}
			if (todayDate.getDayOfYear() >= this.lastCheckOut.getDayOfYear() + 31 || todayDate.getYear() != this.getLastCheckOut().getYear()) {
				addedDays += todayDate.getDayOfYear() - this.lastCheckOut.getDayOfYear();
			} else if (todayDate.getDayOfYear() <= this.lastCheckOut.getDayOfYear() + 31 && todayDate.getYear() != this.getLastCheckOut().getYear()) {
				addedDays += todayDate.getDayOfYear() - this.lastCheckOut.getDayOfYear();
			}
		}
		if (addedDays == 0) result = 0;
		else {
			result = (float) (10.0 + (1.50 * (addedDays - 31)));
		}
		return result;
	}
}
