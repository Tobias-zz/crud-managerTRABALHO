package model.dao;

import model.Account;
import model.ModelException;

public interface AccountDAO {
	Account findByCredentials(String username, String password) throws ModelException;
}
