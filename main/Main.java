package main;

import java.io.*;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.Server;

public class Main {

	public static final Scanner sc = new Scanner(System.in);
	
	// Protocol states Status | Login | Play
	public static void main(String[] args) throws IOException {
		String address;
		int port;

		// More effecient way of printing to console
		Logger log = Logger.getLogger(Main.class.getName());

		log.setUseParentHandlers(false);

		log.setLevel(Level.FINER);

		ConsoleHandler consoleHandler = new ConsoleHandler();

		log.addHandler(consoleHandler);

		consoleHandler.setLevel(Level.FINER);

		/*System.out.print("Server Address: ");
		address = sc.nextLine();
		System.out.print("Port: ");
		port = sc.nextInt();*/

		address = "127.0.0.1";
		port = 25565;

		Server server = new Server(address, port);

		server.statusPing(3000);
		
		server.connect(3000);
	}
}