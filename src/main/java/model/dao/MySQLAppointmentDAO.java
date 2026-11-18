package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Appointment;
import model.ModelException;
import model.Pet;

public class MySQLAppointmentDAO implements AppointmentDAO {

	@Override
	public boolean save(Appointment a) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("INSERT INTO appointments VALUES (DEFAULT, ?, ?, ?, ?, ?);");
		db.setString(1, a.getReason());
		db.setDate(2, a.getAppointmentDate());
		db.setDouble(3, a.getPrice());
		db.setInt(4, a.isReturnNeeded() ? 1 : 0);
		db.setInt(5, a.getPet().getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Appointment a) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("UPDATE appointments SET reason = ?, appointment_date = ?, "
				+ "price = ?, return_needed = ?, pet_id = ? WHERE id = ?");
		db.setString(1, a.getReason());
		db.setDate(2, a.getAppointmentDate());
		db.setDouble(3, a.getPrice());
		db.setInt(4, a.isReturnNeeded() ? 1 : 0);
		db.setInt(5, a.getPet().getId());
		db.setInt(6, a.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Appointment a) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("DELETE FROM appointments WHERE id = ?;");
		db.setInt(1, a.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Appointment> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		List<Appointment> list = new ArrayList<Appointment>();
		db.createStatement();
		db.executeQuery("SELECT * FROM appointments ORDER BY appointment_date DESC, id DESC");
		while (db.next()) {
			list.add(createAppointment(db));
		}
		return list;
	}

	@Override
	public Appointment findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		db.prepareStatement("SELECT * FROM appointments WHERE id = ?;");
		db.setInt(1, id);
		db.executeQuery();
		Appointment a = null;
		while (db.next()) {
			a = createAppointment(db);
			break;
		}
		return a;
	}

	private Appointment createAppointment(DBHandler db) throws ModelException {
		Appointment a = new Appointment(db.getInt("id"));
		a.setReason(db.getString("reason"));
		a.setAppointmentDate(db.getDate("appointment_date"));
		a.setPrice(db.getDouble("price"));
		a.setReturnNeeded(db.getInt("return_needed") == 1);

		PetDAO petDAO = DAOFactory.createDAO(PetDAO.class);
		Pet pet = petDAO.findById(db.getInt("pet_id"));
		a.setPet(pet);
		return a;
	}
}
