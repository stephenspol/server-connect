import java.io.*;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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

		System.out.print("Server Address: ");
		address = sc.nextLine();
		System.out.print("Port: ");
		port = sc.nextInt();

		Server server = new Server(address, port);

		server.statusPing(3000);
		
		server.connect(3000);
	}
	
	public static boolean getBit(byte num, int pos)
	{
		return ((num >> pos) & 1) == 1;
	}
	
	public static boolean[] getBits(byte num)
	{
		boolean[] bits = new boolean[7];
		
		for (int i = 0; i < bits.length; i++) bits[i] = getBit(num, i);
		
		return bits;
	}
}