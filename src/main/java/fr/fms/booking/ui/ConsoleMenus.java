package fr.fms.booking.ui;

import fr.fms.booking.dao.BookingRepository;
import fr.fms.booking.dao.MeetingRoomRepository;
import fr.fms.booking.entities.MeetingRoom;
import fr.fms.booking.service.MeetingRoomService;
import java.util.Scanner;

public class ConsoleMenus {
  private static final String CHOICE_TEXT = "Votre choix : ";
  private final Scanner in = new Scanner(System.in);
  private final MeetingRoomRepository meetingRoomRepository;
  private final BookingRepository bookingRepository;
  private final MeetingRoomService meetingRoomService;

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

  private Object readExistingMeetingRoomId() {
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
  }

  private void createBookingMenu() {
  }

  private void deleteBookingMenu() {
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
