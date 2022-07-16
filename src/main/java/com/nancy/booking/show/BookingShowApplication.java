package com.nancy.booking.show;

import com.nancy.booking.show.controller.ShowController;
import com.nancy.booking.show.controller.UserBookingController;
import com.nancy.booking.show.controller.UserController;
import com.nancy.booking.show.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.ValidationException;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class BookingShowApplication implements ApplicationRunner {

	@Autowired
	UserController userController;

	@Autowired
	ShowController showController;

	@Autowired
	UserBookingController userBookingController;

	public static void main(String[] args) {
		SpringApplication.run(BookingShowApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		Scanner console = new Scanner(System.in);
		// Header
		System.out.println("******************Welcome******************");
		System.out.println("********************To*********************");
		System.out.println("**************Book My Show**************");
		int option;
		//Initial do/while
		do {
			do {
				System.out.println("\nLogin (1), Quit (-1)");
				System.out.print("Please choose an option: ");
				option = console.nextInt();
			} while (option != 1 && option != -1);
			startProcess(option, console);
		} while (option != -1);
		System.out.println("**************Thank you for visiting our page**************");
	}

	private void startProcess(int option, Scanner console) {
		String userName;
		String password;
		if(option != -1) {
			System.out.println("\n=====User Login======");
			System.out.print("Enter username: ");
			userName = console.next();
			System.out.print("Enter password: ");
			password = console.next();
			UserDTO loggedInUser = userController.findUser(userName, password);
			if(loggedInUser != null) {
				System.out.println("Login Successful!");
				if(loggedInUser.getRole().equals(Role.ADMIN)){
					startProcessForAdmin(console);
				} else if(loggedInUser.getRole().equals(Role.BUYER)) {
					startProcessForBuyer(console, loggedInUser);
				}
			}
			else
			{
				System.out.println("Login failed! Please try again.");
			}
		}

}

	private void startProcessForBuyer(Scanner console, UserDTO loggedInUser) {
		int choice = 0;
		while (choice != -1) {
			System.out.println("\n Check Seat Availability (1), Book a Ticket (2), Cancel a Ticket (3), Logout (-1)");
			System.out.print("Please choose an option: ");
			choice = console.nextInt();
			switch (choice) {
				case 1:
					//Check seat availability
					checkSeatAvailability(console);
					break;
				case 2:
					// Book Ticket
					bookTicket(console, loggedInUser);
					break;
				case 3:
					// Cancel a ticket
					cancelTicket(console);
					break;
				default:
			}
		}
	}

	private void cancelTicket(Scanner console) {
		long ticketNo;
		long phoneNo;
		System.out.print("Enter ticket number : ");
		ticketNo = console.nextLong();
		System.out.print("Enter the phone no used for ticket booking: ");
		phoneNo = console.nextLong();
		try {
			userBookingController.cancelTicket(ticketNo, phoneNo);
			System.out.println("YOUR BOOKING IS CANCELLED AND REFUND INITIATED!!");
		} catch (ValidationException e) {
			System.err.println(e.getMessage());
		}
	}

	private void bookTicket(Scanner console, UserDTO loggedInUser) {
		int showNumber;
		long phoneNo;
		String[] selectedSeats;
		UserBookingDTO bookingDetails;
		System.out.println("Please check the availability of seats before booking");
		System.out.print("Enter show number : ");
		showNumber = console.nextInt();
		if(showController.checkifShowExists(showNumber)) {
			System.out.print("Enter your phone no : ");
			phoneNo = console.nextLong();
			System.out.print("Enter the seats (comma-separated) : ");
			selectedSeats = console.next().split(",");
			if(selectedSeats != null && selectedSeats.length > 0) {
				try {
					bookingDetails = userBookingController.bookTicket(showNumber, phoneNo, selectedSeats, loggedInUser);
					Formatter formatter = new Formatter();
					formatter.format("%15s %15s %15s %100s\n", "Ticket #", "Show #", "Phone no", "Seats allocated");
					formatter.format("%15d %15d %15d %100s\n", bookingDetails.getId(), bookingDetails.getShowId().getShowNumber(),
							bookingDetails.getPhoneNo(), String.join(", ", bookingDetails.getUsrBookedSeats()));
					System.out.println("YOUR TICKET IS CONFIRMED!!");
					System.out.println(formatter);
				} catch (ValidationException e) {
					System.err.println(e.getMessage());
					System.err.println("Incorrect user input entered. Please select options again.");
					// Suppress the exception so that user can proceed to enter user inputs instead of restarting application
				}
			}
		} else {
			System.out.println("Incorrect show number. Show details not found");
		}
	}

	private void checkSeatAvailability(Scanner console) {
		int showNumber;
		ShowDTO show;
		int nBRows;
		List<String> availableSeats;
		Character firstChar;
		System.out.print("Enter show number : ");
		showNumber = console.nextInt();
		StringBuilder availableSeatsStr = new StringBuilder();
		if(showController.checkifShowExists(showNumber)) {
			show = showController.retrieveShowDetails(showNumber);
			nBRows = show.getNbOfRows();
			System.out.println("Below are the available seats for the show :");
			availableSeats = show.getAvailableSeats();
			int endAscii = nBRows + 65;
			char maxSeat = ((char)endAscii);
			for(char alphabet = 'A'; alphabet < maxSeat ; alphabet++)
			{
				for(String str : availableSeats) {
					firstChar = str.charAt(0);
					if(firstChar.equals(alphabet)) {
						availableSeatsStr = availableSeatsStr.append(str).append(", ");
					}
				}
				System.out.println(availableSeatsStr);
				availableSeatsStr.delete(0, availableSeatsStr.length());
			}

		} else {
			System.out.println("Incorrect show number. Show details not found");
		}
	}

	private void startProcessForAdmin(Scanner console) {
		int choice = 0;
		while(choice != -1) {
			System.out.println("\n Setup a Show (1), View all Shows (2), View Show Details (3), Logout (-1)");
			System.out.print("Please choose an option: ");
			choice = console.nextInt();
			switch(choice) {
				case 1:
					//Setup a Show
					setupShow(console);
					break;
				case 2:
					// Dispaly all shows
					displayAllShows();
					break;
				case 3:
					// View Show
					viewShowDetails(console);
					break;
				default :
			}
		}
		System.out.println("User has been logged out. Goodbye!");

	}

	private void viewShowDetails(Scanner console) {
		int showNumber;
		ShowDTO showDTO;
		System.out.print("Enter show number to view: ");
		showNumber = console.nextInt();
		showDTO = showController.retrieveShowDetails(showNumber);
		if(showDTO != null) {
			System.out.println("**************Show Details**************");
			System.out.println("Show Number :" + showNumber);
			if(showDTO.getBookingList() == null || showDTO.getBookingList().isEmpty()) {
				System.out.println("There is no booking done for this show");
			} else {
				Formatter formatter = new Formatter();
				formatter.format("%15s %15s %100s\n", "Ticket #", "Phone #", "Seats allocated");
				for (UserBookingDTO userBooking : showDTO.getBookingList()) {
					if(userBooking.getUsrBookedSeats() != null && !userBooking.getUsrBookedSeats().isEmpty())
						formatter.format("%15d %15d %100s\n", userBooking.getId(), userBooking.getPhoneNo(),
								String.join(", ", userBooking.getUsrBookedSeats()));
				}
				System.out.println(formatter);
			}
		} else {
			System.out.println("Incorrect show number. Show details not found");
		}
	}

	private void displayAllShows() {
		List<ShowDTO> showDTOList = showController.listAllShows();
		if(showDTOList != null && !showDTOList.isEmpty()) {
			System.out.println("****************************List of Shows****************************");
			Formatter formatter = new Formatter();
			formatter.format("%10s %20s %15s %15s %20s %20s\n", "Show #", "Movie Name", "Show Date", "Show Time", "No.of rows", "No.of seats per row");
			for(ShowDTO show : showDTOList){
				formatter.format("%10s %20s %15s %15s %20d %20d\n", show.getShowNumber(), show.getMovieName(),show.getShowDate().toString(), show.getShowTime().toString(), show.getNbOfRows(), show.getNbSeatsPerRow());
			}
			System.out.println(formatter);
		}
		else {
			System.out.println("No shows setup. Please setup a show");
		}
	}

	private void setupShow(Scanner console) {
		int showNumber;
		int nBRows;
		int nBSeatsPerRow;
		ShowDTO showDTO;
		System.out.print("Enter show number to setup: ");
		showNumber = console.nextInt();
		if(!showController.checkifShowExists(showNumber)) {
			System.out.print("Enter number of rows for this show: ");
			nBRows = console.nextInt();
			System.out.print("Enter number of seats per row: ");
			nBSeatsPerRow = console.nextInt();
			try {
				showDTO = showController.setupShow(Movie.EXCUSE_ME_BROTHER, nBRows, nBSeatsPerRow, showNumber);
				if (showDTO != null)
					System.out.println("Show setup is completed");
			} catch (ValidationException e) {
				System.err.println("Validation exception occured. Please select options again.");
				// Suppress the exception so that user can proceed to enter user inputs instead of restarting application
			}
		}
		else {
			System.out.println("Show has been setup already...");
		}
	}
}
