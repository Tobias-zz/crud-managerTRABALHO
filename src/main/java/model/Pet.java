package model;

import java.util.Date;

public class Pet {
	private int id;
	private String name;
	private String species;
	private Date birthDate;
	private double weight;
	private boolean neutered;
	private User user;

	public Pet() {
		this(0);
	}

	public Pet(int id) {
		this.id = id;
		this.name = "";
		this.species = "CAO";
		this.birthDate = new Date();
	}

	public int getId() { return id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getSpecies() { return species; }
	public void setSpecies(String species) { this.species = species; }
	public Date getBirthDate() { return birthDate; }
	public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
	public double getWeight() { return weight; }
	public void setWeight(double weight) { this.weight = weight; }
	public boolean isNeutered() { return neutered; }
	public void setNeutered(boolean neutered) { this.neutered = neutered; }
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
}
