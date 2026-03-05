import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    ReservationService service = new ReservationService();

    @Test
    public void testAddReservationValidDates() {
        int guestId = 1;
        String roomType = "Standard";
        String checkIn = "2026-03-05";
        String checkOut = "2026-03-06";

        assertDoesNotThrow(() -> service.addReservation(guestId, roomType, checkIn, checkOut));
    }

    @Test
    public void testAddReservationInvalidDates() {
        int guestId = 1;
        String roomType = "Standard";
        String checkIn = "2026-03-06";
        String checkOut = "2026-03-05";

        Exception exception = assertThrows(Exception.class, () -> {
            service.addReservation(guestId, roomType, checkIn, checkOut);
        });

        assertNotNull(exception, "❌ Reservation should fail with invalid dates");
    }
}