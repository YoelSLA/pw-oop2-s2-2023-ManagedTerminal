package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bill.Bill;
import terminal.ManagedTerminal;

class ShipperTest {

	private Shipper shipper;
	private Bill bill;
	private ManagedTerminal terminal;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	// Esta variable de instancia la agregamos para poder testear los prints 
	private final PrintStream standardOut = System.out;
	// Esta variable la agregamos para poder resetear al valor original
	
	
	@BeforeEach
	void setUp() {
		shipper = new Shipper("12567892", "Carla Ruiz");
		bill = mock(Bill.class);
		terminal = mock(ManagedTerminal.class);
		when(terminal.getName()).thenReturn("montevideo");
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@Test
	void getDni() {
		assertEquals("12567892", shipper.getDni());
	}

	@Test
	void getName() {
		assertEquals("Carla Ruiz", shipper.getName());
	}

	@Test
	void receiveMailAboutBill() {
		shipper.sendMailAboutBill(terminal, shipper, bill);
		verify(bill, times(1)).printInvoice();
	}
	
	@Test
	void receiveMailAboutShipInminentArrival() {
		// Set Up
		shipper.sendMailAboutShipInminentArrival(terminal, shipper, "Your ship is closed!");
		
		// Assert
		assertEquals("Your ship is closed to montevideo", outputStreamCaptor.toString().trim());
	}
	
	@Test
	void receiveMailAboutShipArrival() {
		// Set Up
		shipper.sendMailAboutShipArrival(terminal, shipper, "Your ship is here!");
		
		// Assert
		assertEquals("Your ship has arrived to montevideo", outputStreamCaptor.toString().trim());
	}

	
}
