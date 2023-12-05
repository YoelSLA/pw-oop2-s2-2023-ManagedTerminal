package terminal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import turn.Turn;

class ProcessOfImportOrderTest extends ManagedTerminalTest {

	private Turn turnImportOrder;
	// ------------------------------------------------------------
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
		turnImportOrder = mock(Turn.class);
		when(turnImportOrder.getDriver()).thenReturn(alberto);
		when(turnImportOrder.getTruck()).thenReturn(volvo);
		when(turnImportOrder.getDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 20, 00));
		// 12-11-23 | 06:00 Hs.
		// ------------------------------------------------------------------------------------------
		importOrder = spy(new ImportOrder(dry, tripTwo, montevideo, buenosAires, yoel, alberto, volvo));
		when(importOrder.getTurn()).thenReturn(turnImportOrder);
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
		assertEquals(LocalDateTime.of(2023, Month.NOVEMBER, 01, 20, 00), importOrder.getTurn().getDate());
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
	void truckLeaveWithLoad_ShouldNotIncludeExcessStorage_WhenConsigneeRetrievesOnTime() throws Exception {
		// Set Up
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 01, 18, 00);
		// 01-11-23 | 18:00 Hs.
		// Exercise
		buenosAires.truckLeaveWithLoad(importOrder, alberto, volvo, arrivalDate);
		// Assert
		assertFalse(
				"La orden de exportación no contiene el alcemamiento de pesado ya que el consignee lo retiro a tiempo.",
				importOrder.getServices().stream().anyMatch(ExcessStorage.class::isInstance));
		assertFalse("La orden de exportación no contiene el servicio electrico ya que la orden posee una carga Dry.",
				importOrder.getServices().stream().anyMatch(Electricity.class::isInstance));
	}

	@Test
	void truckLeaveWithLoad_ShouldIncludeExcessStorageWithCorrectPrice_WhenConsigneeDoesNotRetrieveOnTime()
			throws Exception {
		// Set Up
		buenosAires.setExcessStorageCost(2000.00);
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 3, 06, 00);
		// 03-11-23 | 06:00 Hs.
		// Exercise
		buenosAires.truckLeaveWithLoad(importOrder, alberto, volvo, arrivalDate);
		// Assert
		assertTrue(
				"La orden de exportación contiene el alcemamiento de pesado ya que el consignee no lo retiro a tiempo.",
				importOrder.getServices().stream().anyMatch(ExcessStorage.class::isInstance));
		assertEquals(2000.00, getExcessStorageServiceFromImportOrder().getPrice(), 0);
		assertEquals(20000.00, getExcessStorageServiceFromImportOrder().getPriceFor(dry), 0);
		assertFalse("La orden de exportación no contiene el servicio electrico ya que la orden posee una carga Dry.",
				importOrder.getServices().stream().anyMatch(Electricity.class::isInstance));

	}

	@Test
	void testTruckLeaveWithLoad_ElectricityServiceAndExcessStorage_RemovedOnTime() throws Exception {
		// Set Up
		buenosAires.setCostPerKw(1000.00);
		when(importOrder.getLoad()).thenReturn(reefer);
		buenosAires.hireImportService(importOrder);
		buenosAires.notifyShipArrival(bismarck);
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 01, 17, 00);
		// 01-11-23 | 17:00 Hs.

		// Exercise
		buenosAires.truckLeaveWithLoad(importOrder, alberto, volvo, arrivalDate);

		// Assert
		assertFalse(
				"La orden de importación no contiene el servicio de almacenamiento excesivo ya que el consignee lo retiró a tiempo.",
				importOrder.getServices().stream().anyMatch(ExcessStorage.class::isInstance));

		assertTrue("La orden de importación contiene el servicio eléctrico ya que la orden posee una carga Reefer.",
				importOrder.getServices().stream().anyMatch(Electricity.class::isInstance));

		assertEquals("La fecha de inicio del servicio eléctrico es igual a la de llegada del buque",
				LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00),
				getElectricityServiceFromImportOrder().getStartConnection());

		assertEquals("La fecha de fin del servicio eléctrico es igual a la de llegada para retirar la carga",
				LocalDateTime.of(2023, Month.NOVEMBER, 01, 17, 00),
				getElectricityServiceFromImportOrder().getEndConnection());

		assertEquals("El precio total del servicio eléctrico por la estadía en la terminal gestionada", 150000.00,
				getElectricityServiceFromImportOrder().getPriceFor(reefer), 0);
	}

	private Electricity getElectricityServiceFromImportOrder() {
		return (Electricity) importOrder.getServices().get(0);
	}

	private ExcessStorage getExcessStorageServiceFromImportOrder() {
		return (ExcessStorage) importOrder.getServices().get(0);
	}

}
