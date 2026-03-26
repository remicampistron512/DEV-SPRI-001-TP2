package fr.fms.booking;

import fr.fms.booking.dao.BookingRepository;
import fr.fms.booking.dao.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingApplication implements CommandLineRunner {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private MeetingRoomRepository meetingRoomRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

  @Override
  public void run (String... args) throws Exception {
    var menus = new ConsoleMenus(bookingRepository,meetingRoomRepository);
    menus.run();
  }
}
