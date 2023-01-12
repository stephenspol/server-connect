package com.stephenspol.server.connect.networking;

import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.*;
import java.net.*;

import com.stephenspol.server.connect.networking.protocol.ClientboundManager;
import com.stephenspol.server.connect.networking.protocol.ServerboundManager;
import com.stephenspol.server.connect.Main;

public class Server {

    private final Logger log = Logger.getLogger(Server.class.getName());
    private final ConsoleHandler consoleHandler = new ConsoleHandler();
	private final FileHandler fileHandler;

    private final InetSocketAddress host;

    public static final String PREMATURE = "Premature end of stream.";
	public static final String INVALID_LENGTH = "Invalid string length.";
	public static final String INVALID_PACKET = "Invalid packetID.";

    public Server (String address, int port) {

		try {
			new File("logs").mkdir();

			fileHandler = new FileHandler("logs/Server Log - %u.txt");

			log.setUseParentHandlers(false);
			log.addHandler(consoleHandler);
			fileHandler.setFormatter(new SimpleFormatter());
			log.addHandler(fileHandler);

			log.setLevel(Level.FINER);
			consoleHandler.setLevel(Level.FINER);
			fileHandler.setLevel(Level.FINER);

			host = new InetSocketAddress(address, port);
		} catch (IOException e) {
            throw new ExceptionInInitializerError(e);   
        }
    }
    
    public void statusPing(int timeout) throws IOException {
        log.info("Starting Status Ping!\n");

		try (Socket socket = new Socket()) { 

            log.fine("Attempting to connect to server...");
            socket.connect(host, timeout); // 3000 is standard for timeout
            log.info("Connection to server successful!\n");

			ServerboundManager serverboundManager = new ServerboundManager(socket, 1);
			ClientboundManager clientboundManager = new ClientboundManager(socket, 1, serverboundManager);

			Thread serverbound = new Thread(serverboundManager);
			Thread clientbound = new Thread(clientboundManager);

			serverbound.start();

            // C->S : Request
			serverboundManager.execute(0x00);

			clientbound.start();

            // C->S : Ping
			serverboundManager.execute(0x01);

            while(clientbound.isAlive() || serverbound.isAlive()) {
				Thread.sleep(1000);
			}

            log.info("Status Ping Completed!\n");
        } catch (InterruptedException e) {
			log.log(Level.WARNING, "Thread Interrupted", e);
			
			Thread.currentThread().interrupt();
		}
    }

    public void connect(int timeout) throws IOException {

        Console console = System.console();

        Authentication authentication;
		String name;

        System.out.print("Username (Email if mitagated): ");
		String username = Main.sc.next();
		
		Main.sc.nextLine();
		
		char[] passArray = {};

		try
		{
			// Uses a array to store password to ensure security
			passArray = console.readPassword("Password: ");
		}

		catch (NullPointerException e)
		{
			log.warning("Could not get Console instance! (Password is no longer secure)");

			System.out.print("Password: ");
			passArray = Main.sc.next().toCharArray();

			Main.sc.nextLine();
		}

		finally
		{
			authentication = new Authentication(username, passArray);
		}

		boolean authenticated = authentication.connect();

		if (authenticated) {
			name = authentication.getName();

			log.info("Starting Login\n");

			try (final Socket socket = new Socket()) {
				log.fine("Attempting to connect to server...");
				socket.connect(host, timeout);
				log.info("Connection Completed!\n");

				ServerboundManager serverboundManager = new ServerboundManager(socket, 2);
				ClientboundManager clientboundManager = new ClientboundManager(socket, 2, serverboundManager);

				Thread serverbound = new Thread(serverboundManager);
				Thread clientbound = new Thread(clientboundManager);

				serverbound.start();

				// C->S : Login Start
				serverboundManager.execute(0x00, name);

				clientbound.start();

				// Keep main thread stuck in a loop to not kill socket while other thread is running
				while (clientbound.isAlive() || serverbound.isAlive()) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				log.log(Level.WARNING, "Thread Interrupted", e);

				Thread.currentThread().interrupt();
			}
		} else {
			log.severe("Failed to authenticate user!");
		}
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
