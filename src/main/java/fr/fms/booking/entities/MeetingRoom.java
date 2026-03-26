package fr.fms.booking.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class MeetingRoom implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;


  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private int capacity;

  @OneToMany(mappedBy = "meetingRoom")
  private Collection<Booking> bookings;

  public MeetingRoom(){}
  public MeetingRoom(long id, String name, int capacity) {
    this.id = id;
    this.name = name;
    this.capacity = capacity;
  }

  public MeetingRoom(String name, int capacity) {
    this.name = name;
    this.capacity = capacity;
  }
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", capacity='" + capacity + '\'' +
        '}';
  }
}
