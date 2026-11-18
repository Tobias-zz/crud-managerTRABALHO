package model.dao;

import java.util.List;

import model.ModelException;
import model.Pet;

public interface PetDAO {
	boolean save(Pet pet) throws ModelException;
	boolean update(Pet pet) throws ModelException;
	boolean delete(Pet pet) throws ModelException;
	List<Pet> listAll() throws ModelException;
	Pet findById(int id) throws ModelException;
}
