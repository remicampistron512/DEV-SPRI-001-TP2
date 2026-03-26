package fr.fms.booking.ui;

import fr.fms.booking.dao.BookingRepository;
import fr.fms.booking.dao.MeetingRoomRepository;
import fr.fms.booking.entities.Booking;
import fr.fms.booking.entities.MeetingRoom;
import fr.fms.booking.service.MeetingRoomService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleMenus {
  private static final String CHOICE_TEXT = "Votre choix : ";
  private final Scanner in = new Scanner(System.in);
  private final MeetingRoomRepository meetingRoomRepository;
  private final BookingRepository bookingRepository;
  private final MeetingRoomService meetingRoomService;
  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  public ConsoleMenus(BookingRepository bookingRepository,
      MeetingRoomRepository meetingRoomRepository, MeetingRoomService meetingRoomService){
    this.bookingRepository = bookingRepository;
    this.meetingRoomRepository = meetingRoomRepository;
    this.meetingRoomService = meetingRoomService;
  }

  public void run() {
    mainMenu();
    System.out.println("A bientôt.");
  }

  private void mainMenu() {
    while (true) {
      System.out.println("\n=== MENU PRINCIPAL ===");
      System.out.println("1) Créer une salle de réunion");
      System.out.println("2) Modifier une salle de réunion");
      System.out.println("3) Supprimer une salle de réunion");
      System.out.println("4) Créer une réservation");
      System.out.println("5) Supprimer une réservation");
      System.out.println("6) Afficher les réservations par salle");
      System.out.println("7) Afficher les réservations par date");
      System.out.println("8) Afficher les réservations par capacité");
      System.out.println("9) Afficher les salles disponibles par créneau");
      System.out.println("0) Sortir du programme");

      int choice = readInt(CHOICE_TEXT, 0, 9);
      switch (choice) {
        case 1 -> createMeetingRoomMenu();
        case 2 -> modifyMeetingRoomMenu();
        case 3 -> deleteMeetingRoomMenu();
        case 4 -> createBookingMenu();
        case 5 -> deleteBookingMenu();
        case 6 -> displayBookingsByRoomMenu();
        case 7 -> displayBookingsByDateMenu();
        case 8 -> displayBookingsByCapacityMenu();
        case 9 -> displayAvailableRoomsByTimeSlotMenu();
        case 0 -> {
          return;
        } // exit application
        default -> System.out.println("Choix invalide.");
      }
    }
  }

  private void createMeetingRoomMenu() {
    String name = readStr("Entrer le nom de la salle");
    int capacity = readInt("Entrer la capacité de la salle",0,100);
    meetingRoomRepository.save(new MeetingRoom(name, capacity));

  }

  private void modifyMeetingRoomMenu() {

    printAllMeetingRooms();

    long  meetingRoomId = (long) readExistingMeetingRoomId();

    String name = readStr("Entrez le nom de la salle");
    int capacity = readInt("Entrez la capacité de la salle",0,100);

    meetingRoomService.updateMeetingRoom(meetingRoomId,name,capacity);
  }

  private long readExistingMeetingRoomId() {
    while (true){
      long id = readLong("Choisissez l'id de la salle : ");
      if(meetingRoomRepository.existsById(id)){
        return id;
      }
      System.out.println("Salle introuvable");
    }
  }

  private void printAllMeetingRooms() {
    for (MeetingRoom meetingRoom : meetingRoomRepository.findAll()) {
      System.out.println(meetingRoom);
    }
  }

  private void deleteMeetingRoomMenu() {
    printAllMeetingRooms();
    long meetingRoomId = readExistingMeetingRoomId();

    try {
      meetingRoomRepository.deleteById(meetingRoomId);
      System.out.println("La salle de réunion a bien été supprimée");
    } catch (Exception e) {
      System.out.println("Impossible de supprimer la salle " + e.getMessage());
    }

  }

  private void createBookingMenu() {
      LocalDate date = readDate("Rentrer la date de la reservation");

      LocalTime startDate = readTime("Heure de début : ");

      LocalTime endDate = readTime("Heure de fin : ",startDate);

      printAllMeetingRooms();
      long meetingRoomId = readExistingMeetingRoomId();
      if(!meetingRoomService.hasOverlap(meetingRoomId,date,startDate,endDate)){
        MeetingRoom meetingRoom = meetingRoomRepository.getReferenceById(meetingRoomId);
        bookingRepository.save(new Booking(date, startDate,endDate,meetingRoom));
        System.out.println("Le créneau a bien été enregistré");
      } else {
        System.out.println("Ce créneau est déjà pris");
      }
  }
  private LocalTime readTime(String prompt, LocalTime startTime){
    while (true) {
      System.out.print(prompt);
      String input = in.nextLine().trim();
      LocalTime dateTime = validateDateTimeFormat(input);
      if(dateTime != null){
        if(dateTime.isAfter(startTime)) {
          if (dateTime.isAfter(LocalTime.of(8, 0)) && dateTime.isBefore((LocalTime.of(18, 0)))) {
            return dateTime;
          } else {
            System.out.println("L'horaire doit être compris entre 8:00 et 18:00");
          }
        } else {
          System.out.println("L'horaire de fin doit être après l'horaire de début");
        }
      }
    }
  }

  private LocalTime readTime(String prompt){
    while (true) {
      System.out.print(prompt);
      String input = in.nextLine().trim();
      LocalTime dateTime = validateDateTimeFormat(input);
      if(dateTime != null){
        if(dateTime.isAfter(LocalTime.of(8,0)) && dateTime.isBefore((LocalTime.of(18,0)))){
          return dateTime;
        } else {
          System.out.println("L'horaire doit être compris entre 8:00 et 18:00");
        }
      }
    }
  }
  private LocalDate readDate(String prompt){
    while (true) {
      System.out.print(prompt);
      String input = in.nextLine().trim();
      LocalDate date = validateDateFormat(input);
      if (date != null) {
        if (dateIsInTheFuture(date)) {
          return date;
        } else {
          System.out.println("Une date ne peut être dans le passé");
        }
      }
    }
  }
  private LocalDate validateDateFormat(String input) {
      try {
        return LocalDate.parse(input, DATE_FORMAT);
      } catch (DateTimeParseException e) {
        System.out.println(
            "Format de date invalide. Merci d'utiliser dd-MM-yyyy (e.g. 26-03-2026).");
      }
    return null;
  }
   private Boolean dateIsInTheFuture(LocalDate date){
    return date.isAfter(LocalDate.now());
   }
  private  LocalTime validateDateTimeFormat(String input) {
     try {
        return LocalTime.parse(input, TIME_FORMAT);
      } catch (DateTimeParseException e) {
        System.out.println("Format d'heure invalide. Merci d'utiliser HH:mm (ex. 09:30).");
      }
    return null;
  }


  private void deleteBookingMenu() {
    printAllBookings();
    long bookingId = readExistingBookingId();

    try {
      bookingRepository.deleteById(bookingId);
      System.out.println("La salle de réunion a bien été supprimée");
    } catch (Exception e) {
      System.out.println("Impossible de supprimer la salle " + e.getMessage());
    }
  }

  private long readExistingBookingId() {
    while (true){
      long id = readLong("Choisissez l'id de la réservation : ");
      if(bookingRepository.existsById(id)){
        return id;
      }
      System.out.println("Réservation introuvable");
    }
  }

  private void printAllBookings() {
    for (Booking booking : bookingRepository.findAll()) {
      System.out.println(booking);
    }
  }

  private void displayBookingsByRoomMenu() {
  }

  private void displayBookingsByDateMenu() {
  }

  private void displayBookingsByCapacityMenu() {
  }

  private void displayAvailableRoomsByTimeSlotMenu() {
  }

















  /**
   * Permet de saisir un entier et de vérifier sa validité
   * @param prompt Message à afficher
   * @param min Valeur minimum
   * @param max Valeur maximum
   * @return la valeur nettoyée
   */
  private int readInt(String prompt, int min, int max) {
    while (true) {
      System.out.print(prompt);
      String s = in.nextLine().trim();
      try {
        int v = Integer.parseInt(s);
        if (v < min || v > max) {
          System.out.printf("Saisir %d..%d%n", min, max);
          continue;
        }
        return v;
      } catch (NumberFormatException e) {
        System.out.println("Nombre invalide.");
      }
    }
  }

  private String readStr(String prompt) {
    while (true) {
      System.out.print(prompt);
      String s = in.nextLine().trim();

      if (!s.isEmpty()) {
        return s;
      }

      System.out.println("Le texte saisi ne peut être vide. Merci de réessayer .");
    }
  }

  private long readLong(String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = in.nextLine();

      try {
        return Long.parseLong(input.trim());
      } catch (NumberFormatException e) {
        System.out.println("Veuillez entrer un nombre valide.");
      }
    }
  }
}
