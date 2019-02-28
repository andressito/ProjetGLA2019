package tpGL;

import java.util.List;

public interface ReservationDAO {
	List<Reservation> getReservation();
	List<Reservation> getReservationDetails(int ReservationID);
	Reservation cancel(int ReservationID);
	Reservation validate(int ReservationID);
}
