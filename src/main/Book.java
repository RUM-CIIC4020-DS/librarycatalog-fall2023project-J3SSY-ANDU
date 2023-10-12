package main;

import java.time.LocalDate;

public class Book {
	private int ID;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;
	
	public Book(int ID, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut) {
		this.ID = ID;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.lastCheckOut = lastCheckOut;
		this.checkedOut = checkedOut;
	}
	
	public int getId() {
		return this.ID;
	}
	public void setId(int id) {
		this.ID = id;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return this.author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public LocalDate getLastCheckOut() {
		return this.lastCheckOut;
	}
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	public boolean isCheckedOut() {
		return this.checkedOut;
	}
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
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
//		se pregunta si isCheckedOut es true para entonces calcular el fee si el libro lleva fuera 31 dias o mas 
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
