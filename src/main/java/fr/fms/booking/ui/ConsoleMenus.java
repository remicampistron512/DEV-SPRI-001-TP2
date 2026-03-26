package fr.fms.booking.ui;

import fr.fms.booking.dao.BookingRepository;
import fr.fms.booking.dao.MeetingRoomRepository;
import java.util.Scanner;

public class ConsoleMenus {
  private static final String CHOICE_TEXT = "Votre choix : ";
  private final Scanner in = new Scanner(System.in);
  private final MeetingRoomRepository meetingRoomRepository;
  private final BookingRepository bookingRepository;
  public ConsoleMenus(BookingRepository bookingRepository,
      MeetingRoomRepository meetingRoomRepository){
    this.bookingRepository = bookingRepository;
    this.meetingRoomRepository = meetingRoomRepository;
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
        case 1 -> createMeetignRoomMenu();
        case 2 -> mdofifyMeetingRoomMenu(5);
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
}
