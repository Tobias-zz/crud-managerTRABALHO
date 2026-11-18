package model.dao;

import model.Account;
import model.ModelException;

public class MySQLAccountDAO implements AccountDAO {

	@Override
	public Account findByCredentials(String username, String password) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("SELECT * FROM accounts WHERE username = ? AND password = SHA2(?, 256);");
		db.setString(1, username);
		db.setString(2, password);
		db.executeQuery();
		Account a = null;
		while (db.next()) {
			a = new Account(db.getInt("id"));
			a.setUsername(db.getString("username"));
			break;
		}
		return a;
	}
}
