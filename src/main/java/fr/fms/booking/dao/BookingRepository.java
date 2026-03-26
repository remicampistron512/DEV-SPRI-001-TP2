package fr.fms.booking.dao;

import fr.fms.booking.entities.Booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findByMeetingRoomIdAndDate(Long meetingRoomId, LocalDate date);
}
