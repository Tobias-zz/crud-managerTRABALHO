package model;

public class Account {
	private int id;
	private String username;

	public Account() {
		this(0);
	}

	public Account(int id) {
		this.id = id;
		this.username = "";
	}

	public int getId() { return id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
}
