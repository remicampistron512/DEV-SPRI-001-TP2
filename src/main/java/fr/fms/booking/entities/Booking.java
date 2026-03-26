package fr.fms.booking.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Booking implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;


  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  private MeetingRoom meetingRoom; // plusieurs reservations liées à une salle
  private LocalDate date;
  private LocalTime startDate;
  private LocalTime endDate;

  public Booking(long id, MeetingRoom meetingRoom, LocalDate date, LocalTime startDate,
      LocalTime endDate) {
    this.id = id;
    this.meetingRoom = meetingRoom;
    this.date = date;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Booking(LocalDate date,LocalTime startDate, LocalTime endDate, MeetingRoom meetingRoom){
    this.date = date;
    this.startDate = startDate;
    this.endDate = endDate;
    this.meetingRoom = meetingRoom;
  }
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public MeetingRoom getMeetingRoom() {
    return meetingRoom;
  }

  public void setMeetingRoom(MeetingRoom meetingRoom) {
    this.meetingRoom = meetingRoom;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalTime startDate) {
    this.startDate = startDate;
  }

  public LocalTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalTime endDate) {
    this.endDate = endDate;
  }
}
