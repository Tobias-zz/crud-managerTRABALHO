package model;

import java.util.Date;

public class Appointment {
	private int id;
	private String reason;
	private Date appointmentDate;
	private double price;
	private boolean returnNeeded;
	private Pet pet;

	public Appointment() {
		this(0);
	}

	public Appointment(int id) {
		this.id = id;
		this.reason = "";
		this.appointmentDate = new Date();
	}

	public int getId() { return id; }
	public String getReason() { return reason; }
	public void setReason(String reason) { this.reason = reason; }
	public Date getAppointmentDate() { return appointmentDate; }
	public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
	public boolean isReturnNeeded() { return returnNeeded; }
	public void setReturnNeeded(boolean returnNeeded) { this.returnNeeded = returnNeeded; }
	public Pet getPet() { return pet; }
	public void setPet(Pet pet) { this.pet = pet; }
}
