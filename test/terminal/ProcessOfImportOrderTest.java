package terminal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import driver.Driver;
import load.Dry;
import load.Reefer;
import order.ImportOrder;
import service.Electricity;
import service.ExcessStorage;
import truck.Truck;

class ProcessOfImportOrderTest extends ManagedTerminalTest {

	private Reefer reefer;
	private Dry dry;
	// ------------------------------------------------------------
	private ImportOrder importOrder;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		dry = mock(Dry.class);

		reefer = mock(Reefer.class);
		when(reefer.getConsumptionkWh()).thenReturn(50.00);
		when(reefer.consumesElectricity()).thenReturn(true);
		// ------------------------------------------------------------------------------------------
		importOrder = spy(new ImportOrder(yoel, tripTwo, dry, montevideo, buenosAires, alberto, volvo));
		// ------------------------------------------------------------------------------------------
		buenosAires.registerTruckTransportCompany(transportVesprini);
	}

	@Test
	void testShouldHireImportServiceAndAddConsignee() throws Exception {
		// Exercise
		buenosAires.hireImportService(importOrder);
		// Assert
		assertTrue(buenosAires.getConsignees().contains(yoel));
	}

	@Test
	void testShouldAddConsigneeWhenImportServiceHired() throws Exception {
		// Set Up
		buenosAires.registerConsignee(yoel);
		// Exercise
		buenosAires.hireImportService(importOrder);
		// Assert
		assertTrue(buenosAires.getConsignees().contains(yoel));
	}

	@Test
	void testShouldThrowExceptionWhenHiringImportServiceWithoutRegisteredDriver() {
		// Set Up
		when(transportVesprini.getDrivers()).thenReturn(List.of(mock(Driver.class)));
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireImportService(importOrder);
		}, "Driver not registered in the Managed Terminal.");
	}

	@Test
	void testShouldThrowExceptionWhenHiringImportServiceWithoutRegisteredTruck() {
		// Set Up
		when(transportVesprini.getTrucks()).thenReturn(List.of(mock(Truck.class)));
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireImportService(importOrder);
		}, "Truck not registered in the Managed Terminal.");
	}

	@Test
	void testShouldSetTurnDateWhenImportServiceHired() throws Exception {
		// Exercise
		buenosAires.hireImportService(importOrder);
		// Assert
		assertEquals(1, buenosAires.getTurns().size());
		assertEquals(LocalDateTime.of(2023, Month.NOVEMBER, 01, 20, 00), buenosAires.getTurns().get(0).getDate());
		// 01-11-23 | 20:00 Hs.
	}

	@Test
	void testShouldAddImportOrderToManagedTerminalList() throws Exception {
		// Exercise
		buenosAires.hireImportService(importOrder);
		// Assert
		assertTrue(buenosAires.getImportOrders().contains(importOrder));
	}

	@Test
	void testTruckLeaveWithLoad_NoExcessStorageServiceAfterTimelyCargoRemoval() throws Exception {

		// Set Up
		buenosAires.hireImportService(importOrder);
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 01, 18, 00); // 01-11-23 | 18:00 Hs.

		// Exercise
		buenosAires.truckLeaveWithLoad(importOrder, alberto, volvo, arrivalDate);

		// Assert
		assertTrue("The import order does not contain excess storage service as the cargo was removed on time.",
				importOrder.getServices().stream().noneMatch(ExcessStorage.class::isInstance));
	}

	@Test
	void truckLeaveWithLoad_ExcessStorageServiceDueToDelayedCargoRemoval() throws Exception {

		// Set Up
		buenosAires.setExcessStorageCost(1000.00);
		buenosAires.hireImportService(importOrder);
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 3, 06, 00); // 03-11-23 | 06:00 Hs.

		// Exercise
		buenosAires.truckLeaveWithLoad(importOrder, alberto, volvo, arrivalDate);

		// Assert
		assertTrue("The import order contains excess storage service due to delayed cargo removal.",
				importOrder.getServices().stream().anyMatch(ExcessStorage.class::isInstance));

		ExcessStorage excessStorageService = getExcessStorageServiceFromImportOrder();

		assertEquals("The price of the excess storage service is the same as set by the managed terminal.", 1000.00,
				excessStorageService.getPrice(), 0);

		assertEquals("The total price of the excess storage service.", 10000.00, excessStorageService.getPriceFor(dry),
				0);
	}

	@Test
	void truckLeaveWithLoad_ElectricityServiceForReeferCargoOnTimeRemoval() throws Exception {
		// Set Up
		buenosAires.setExcessStorageCost(1000.00);
		buenosAires.setCostPerKw(300.00);
		buenosAires.hireImportService(importOrder);
		when(importOrder.getLoad()).thenReturn(reefer);
		buenosAires.notifyShipArrival(bismarck);
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 01, 17, 00); // 01-11-23 | 17:00 Hs.

		// Exercise
		buenosAires.truckLeaveWithLoad(importOrder, alberto, volvo, arrivalDate);

		// Assert
		assertTrue("The import order does not contain excess storage service as the cargo was removed on time.",
				importOrder.getServices().stream().noneMatch(ExcessStorage.class::isInstance));

		assertTrue("The import order contains electricity service as it has a Reefer cargo.",
				importOrder.getServices().stream().anyMatch(Electricity.class::isInstance));

		Electricity electricityService = getElectricityServiceFromImportOrder();

		assertEquals("The start date of the electricity service is equal to the ship's arrival date.",
				LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00), electricityService.getStartConnection());

		assertEquals("The end date of the electricity service is equal to the truck's arrival date for cargo removal.",
				LocalDateTime.of(2023, Month.NOVEMBER, 01, 17, 00), electricityService.getEndConnection());

		assertEquals("The total price of the electricity service for the stay at the managed terminal.", 45000.00,
				electricityService.getPriceFor(reefer), 0);
	}

	private Electricity getElectricityServiceFromImportOrder() {
		return (Electricity) importOrder.getServices().get(0);
	}

	private ExcessStorage getExcessStorageServiceFromImportOrder() {
		return (ExcessStorage) importOrder.getServices().get(0);
	}

}
