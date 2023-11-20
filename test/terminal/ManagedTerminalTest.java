package terminal;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Shipper;
import driver.Driver;
import geographicalPosition.GeographicalPosition;
import order.ExportOrder;
import routing.TimeRouting;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;

class ManagedTerminalTest {

	private ManagedTerminal terminalBuenosAires; // SUT
	private ExportOrder exportOrderOfYoel; // DOC
	private Shipper yoel; // DOC
	private Truck scaniaR; // DOC
	private Driver carlos; // DOC
	private TruckTransportCompany monroySchiavon;

	@BeforeEach
	void setUp() {
		terminalBuenosAires = new ManagedTerminal("Puerto de Buenos Aires", new GeographicalPosition(),
				new TimeRouting());
		exportOrderOfYoel = mock(ExportOrder.class);
		yoel = mock(Shipper.class);
		scaniaR = mock(Truck.class);
		carlos = mock(Driver.class);
		monroySchiavon = mock(TruckTransportCompany.class);

		when(exportOrderOfYoel.getShipper()).thenReturn(yoel);
		when(exportOrderOfYoel.getDriver()).thenReturn(carlos);
		when(exportOrderOfYoel.getTruck()).thenReturn(scaniaR);
		when(monroySchiavon.getTrucks()).thenReturn(List.of());
		when(monroySchiavon.getDrivers()).thenReturn(List.of());
		terminalBuenosAires.registerTruckTransportCompany(monroySchiavon);

	}

	@Test
	void testTheShipperIsNotRegisteredInTheManagedTerminaThereforeItIsRegistered() {
		// Set Up
		when(monroySchiavon.getDrivers()).thenReturn(List.of(carlos));
		when(monroySchiavon.getTrucks()).thenReturn(List.of(scaniaR));
		// Excerise
		terminalBuenosAires.hireExportService(exportOrderOfYoel);
		// Assert
		assertTrue(terminalBuenosAires.getShippers().contains(yoel));
	}

	@Test
	void testTheExportOrderIsNotRegisteredBecauseTheDriverIsNotRegistered() {
		// Set Up
		when(monroySchiavon.getTrucks()).thenReturn(List.of(scaniaR));
		// Assert
		assertThrows(RuntimeException.class, () -> {
			terminalBuenosAires.hireExportService(exportOrderOfYoel);
		});
	}

	@Test
	void testTheExportOrderIsNotRegisteredBecauseTheTruckIsNotRegistered() {
		// Set Up
		when(monroySchiavon.getDrivers()).thenReturn(List.of(carlos));
		// Assert
		assertThrows(RuntimeException.class, () -> {
			terminalBuenosAires.hireExportService(exportOrderOfYoel);
		});
	}

	@Test
	void testTheExportOrderIsValidAndAShiftIsAssigned() {

		final LocalDateTime dateTobeAssigned = LocalDateTime.of(2023, Month.FEBRUARY, 1, 11, 40);
		// Set Up
		when(monroySchiavon.getDrivers()).thenReturn(List.of(carlos));
		when(monroySchiavon.getTrucks()).thenReturn(List.of(scaniaR));
		terminalBuenosAires.hireExportService(exportOrderOfYoel);
		// Exercise
		terminalBuenosAires.assignShiftFor(exportOrderOfYoel, dateTobeAssigned);
		// Assert
		assertEquals(exportOrderOfYoel, terminalBuenosAires.getExportOrders().get(0));
		assertEquals(exportOrderOfYoel.getDateTruck(), terminalBuenosAires.getExportOrders().get(0).getDateTruck());
	}

}
