package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Pet;
import model.User;

public class MySQLPetDAO implements PetDAO {

	@Override
	public boolean save(Pet pet) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("INSERT INTO pets VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);");
		db.setString(1, pet.getName());
		db.setString(2, pet.getSpecies());
		db.setDate(3, pet.getBirthDate());
		db.setDouble(4, pet.getWeight());
		db.setInt(5, pet.isNeutered() ? 1 : 0);
		db.setInt(6, pet.getUser().getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Pet pet) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("UPDATE pets SET name = ?, species = ?, birth_date = ?, "
				+ "weight = ?, neutered = ?, user_id = ? WHERE id = ?");
		db.setString(1, pet.getName());
		db.setString(2, pet.getSpecies());
		db.setDate(3, pet.getBirthDate());
		db.setDouble(4, pet.getWeight());
		db.setInt(5, pet.isNeutered() ? 1 : 0);
		db.setInt(6, pet.getUser().getId());
		db.setInt(7, pet.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Pet pet) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("DELETE FROM pets WHERE id = ?;");
		db.setInt(1, pet.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Pet> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		List<Pet> pets = new ArrayList<Pet>();
		db.createStatement();
		db.executeQuery("SELECT * FROM pets ORDER BY name");
		while (db.next()) {
			pets.add(createPet(db));
		}
		return pets;
	}

	@Override
	public Pet findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("SELECT * FROM pets WHERE id = ?;");
		db.setInt(1, id);
		db.executeQuery();
		Pet p = null;
		while (db.next()) {
			p = createPet(db);
			break;
		}
		return p;
	}

	private Pet createPet(DBHandler db) throws ModelException {
		Pet p = new Pet(db.getInt("id"));
		p.setName(db.getString("name"));
		p.setSpecies(db.getString("species"));
		p.setBirthDate(db.getDate("birth_date"));
		p.setWeight(db.getDouble("weight"));
		p.setNeutered(db.getInt("neutered") == 1);

		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
		User user = userDAO.findById(db.getInt("user_id"));
		p.setUser(user);
		return p;
	}
}
