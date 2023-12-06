package bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order.ExportOrder;
import service.Electricity;
import service.ExcessStorage;
import service.Washed;
import service.Weigh;
import terminal.ManagedTerminal;
import terminal.Terminal;

class BillTest {
	
	private ManagedTerminal buenosAires;
	// ------------------------------------------------------------
	private Terminal lima;
	// ------------------------------------------------------------
	private Electricity electricity;
	private ExcessStorage excessStorage;
	private Washed washed;
	private Weigh weigh;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;
	// ------------------------------------------------------------
	private LocalDateTime date;
	// ------------------------------------------------------------
	private Bill bill;
	
	@BeforeEach
	void setUp() {
		
		buenosAires = mock(ManagedTerminal.class);
		when(buenosAires.getName()).thenReturn("Puerto de Buenos Aires");
		// -------------------------------------------------------------------------------------------
		lima = mock(Terminal.class);
		when(lima.getName()).thenReturn("Puerto de Lima");
		// -------------------------------------------------------------------------------------------
		electricity = mock(Electricity.class);
		when(electricity.getPrice()).thenReturn(1000.0);
		when(electricity.getName()).thenReturn("Electricity");
		
		excessStorage = mock(ExcessStorage.class);
		when(excessStorage.getPrice()).thenReturn(2000.0);
		when(excessStorage.getName()).thenReturn("Excess Storage");
		
		washed = mock(Washed.class);
		when(washed.getPrice()).thenReturn(3000.0);
		when(washed.getName()).thenReturn("Washed");
		
		weigh = mock(Weigh.class);
		when(weigh.getPrice()).thenReturn(4000.0);
		when(weigh.getName()).thenReturn("Weigh");
		// -------------------------------------------------------------------------------------------
		exportOrder = mock(ExportOrder.class);
		when(exportOrder.getServices()).thenReturn(List.of(electricity, excessStorage, weigh, washed));
		when(exportOrder.priceOfServices()).thenReturn(10000.00);
		when(exportOrder.getOrigin()).thenReturn(buenosAires);
		when(exportOrder.getDestiny()).thenReturn(lima);
		// -------------------------------------------------------------------------------------------
		date = LocalDateTime.of(2023, Month.DECEMBER, 06, 19, 05);
		// -------------------------------------------------------------------------------------------
		bill = new Bill(date, exportOrder);
		
	}
	
	@Test
	void getBroadcastDate_ReturnsCorrectBroadcastDate() {
	    assertEquals(date, bill.getBroadcastDate());
	}

	@Test
	void getOrder_ReturnsCorrectOrder() {
	    assertEquals(exportOrder, bill.getOrder());
	}

	@Test
	void printInvoice_PrintsInvoiceDetails() {
	    bill.printInvoice();
	}
	
	

}
