package fr.fms.booking.service;

import fr.fms.booking.dao.MeetingRoomRepository;
import fr.fms.booking.entities.MeetingRoom;
import org.springframework.stereotype.Service;

@Service
public class MeetingRoomService {
  private final MeetingRoomRepository meetingRoomRepository;

  public MeetingRoomService(MeetingRoomRepository meetingRoomRepository){
    this.meetingRoomRepository = meetingRoomRepository;
  }

  public void updateMeetingRoom(Long id, String name, int capacity){
    MeetingRoom meetingRoom = meetingRoomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Salle introuvable avec l'id: " + id ));

    meetingRoom.setName(name);
    meetingRoom.setCapacity(capacity);

    meetingRoomRepository.save(meetingRoom);
  }
}
