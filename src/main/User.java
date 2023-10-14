package main;

import interfaces.List;

/**
 * @author jessy
 * @version 10/13/2023
 */

public class User {
	private int ID;
	private String name;
	private List<Book> checkedOutList;
	
	/**
	 * Constructor
	 * @param ID private variable ID
	 * @param name private variable name
	 * @param checkedOutList private variable checkedOutList
	 */
	public User(int ID, String name, List<Book> checkedOutList) {
		this.ID = ID;
		this.name = name;
		this.checkedOutList = checkedOutList;
	}
	
	/**
	 * ID getter
	 * @return value passed as ID in the constructor
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
	 * Name getter
	 * @return value passed as name in the constructor
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Name setter
	 * @param name new value passed
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * CheckedOutList getter
	 * @return value passed as checkedOutList in the constructor
	 */
	public List<Book> getCheckedOutList() {
		return this.checkedOutList;
	}

	/**
	 * CheckedOutList setter
	 * @param checkedOutList new value passed
	 */
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
}
