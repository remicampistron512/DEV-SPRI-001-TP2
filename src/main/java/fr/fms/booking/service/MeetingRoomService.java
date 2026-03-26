package fr.fms.booking.service;

import fr.fms.booking.dao.BookingRepository;
import fr.fms.booking.dao.MeetingRoomRepository;
import fr.fms.booking.entities.Booking;
import fr.fms.booking.entities.MeetingRoom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MeetingRoomService {
  private final BookingRepository bookingRepository;
  private final MeetingRoomRepository meetingRoomRepository;

  public MeetingRoomService(BookingRepository bookingRepository, MeetingRoomRepository meetingRoomRepository){
    this.bookingRepository = bookingRepository;
    this.meetingRoomRepository = meetingRoomRepository;
  }

  public void updateMeetingRoom(Long id, String name, int capacity){
    MeetingRoom meetingRoom = meetingRoomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Salle introuvable avec l'id: " + id ));

    meetingRoom.setName(name);
    meetingRoom.setCapacity(capacity);

    meetingRoomRepository.save(meetingRoom);
  }
  public boolean hasOverlap(Long roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
    List<Booking> bookings = bookingRepository.findByMeetingRoomIdAndDate(roomId, date);

    for (Booking existing : bookings) {
      if (startTime.isBefore(existing.getEndDate()) &&
          endTime.isAfter(existing.getStartDate())) {
        return true;
      }
    }

    return false;
  }
}
