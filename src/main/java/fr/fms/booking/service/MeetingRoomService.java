package fr.fms.booking.service;

import fr.fms.booking.dao.BookingRepository;
import fr.fms.booking.dao.MeetingRoomRepository;
import fr.fms.booking.entities.Booking;
import fr.fms.booking.entities.MeetingRoom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MeetingRoomService {
  private final BookingRepository bookingRepository;
  private final MeetingRoomRepository meetingRoomRepository;
  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
  public void displayBookingsByRoom(Long roomId) {
    MeetingRoom room = meetingRoomRepository.findById(roomId)
        .orElseThrow(() -> new IllegalArgumentException("Salle introuvable : id=" + roomId));

    List<Booking> bookings = bookingRepository.findByMeetingRoomId(roomId);

    if (bookings.isEmpty()) {
      System.out.println("Aucune réservation pour la salle " + room.getName() + ".");
      return;
    }

    bookings.sort(
        Comparator.comparing(Booking::getDate)
            .thenComparing(Booking::getStartDate)
    );

    System.out.println("\nRéservations de la salle : " + room.getName());
    System.out.println("Capacité : " + room.getCapacity());
    System.out.println("----------------------------------------");

    for (Booking booking : bookings) {
      System.out.println(
          "Date : " + booking.getDate().format(DATE_FORMAT)
              + " | Début : " + booking.getStartDate().format(TIME_FORMAT)
              + " | Fin : " + booking.getEndDate().format(TIME_FORMAT)
      );
    }
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
