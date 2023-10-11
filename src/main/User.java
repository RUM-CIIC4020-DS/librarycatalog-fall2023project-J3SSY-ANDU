package main;

import interfaces.List;

public class User {
	private int ID;
	private String name;
	private List<Book> checkedOutList;
	
	public User(int ID, String name, List<Book> checkedOutList) {
		this.ID = ID;
		this.name = name;
		this.checkedOutList = checkedOutList;
	}
	public int getId() {
		return this.ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getCheckedOutList() {
		return this.checkedOutList;
	}

	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
}
