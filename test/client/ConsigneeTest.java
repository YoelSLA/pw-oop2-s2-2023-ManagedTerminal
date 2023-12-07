package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bill.Bill;
import ship.Ship;
import terminal.ManagedTerminal;

class ConsigneeTest {

	private Consignee consignee;
	private Bill bill;
	private ManagedTerminal terminal;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	// Esta variable de instancia la agregamos para poder testear los prints 
	private final PrintStream standardOut = System.out;
	// Esta variable la agregamos para poder resetear al valor original
	
	@BeforeEach
	void setUp() {
		consignee = new Consignee("12567890", "Raul Gonzalez");
		bill = mock(Bill.class);
		terminal = mock(ManagedTerminal.class);
		when(terminal.getName()).thenReturn("montevideo");
		System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@AfterEach
	public void tearDown() {
	    System.setOut(standardOut);
	}

	@Test
	void getDni() {
		assertEquals("12567890", consignee.getDni());
	}

	@Test
	void getName() {
		assertEquals("Raul Gonzalez", consignee.getName());
	}

	@Test
	void receiveMailAboutBill() {
		// Set Up
		consignee.sendMailAboutBill(terminal, consignee, bill);
		
		// Verify
		verify(bill, times(1)).printInvoice();
	}
	
	@Test
	void receiveMailAboutShipInminentArrival() {
		// Set Up
		consignee.sendMailAboutShipInminentArrival(terminal, consignee, "Your ship is closed!");
		
		// Assert
		assertEquals("Your ship is closed to montevideo", outputStreamCaptor.toString().trim());
	}
	
	@Test
	void receiveMailAboutShipArrival() {
		// Set Up
		consignee.sendMailAboutShipArrival(terminal, consignee, "Your ship is here!");
		
		// Assert
		assertEquals("Your ship has arrived to montevideo", outputStreamCaptor.toString().trim());
	}

}
