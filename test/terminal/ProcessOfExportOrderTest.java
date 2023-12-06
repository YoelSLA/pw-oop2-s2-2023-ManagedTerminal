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
import order.ExportOrder;
import service.Electricity;
import service.Weigh;
import truck.Truck;
import turn.Turn;

class ProcessOfExportOrderTest extends ManagedTerminalTest {

	private Turn turnExportOrder;
	// ------------------------------------------------------------
	private Reefer reefer;
	private Dry dry;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		dry = mock(Dry.class);

		reefer = mock(Reefer.class);
		when(reefer.consumesElectricity()).thenReturn(true);
		// ------------------------------------------------------------------------------------------
		turnExportOrder = mock(Turn.class);
		when(turnExportOrder.getDriver()).thenReturn(alberto);
		when(turnExportOrder.getTruck()).thenReturn(volvo);
		when(turnExportOrder.getDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 06, 00));
		// 12-11-23 | 06:00 Hs.
		// ------------------------------------------------------------------------------------------
		exportOrder = spy(new ExportOrder(dry, tripOne, lima, ivan, alberto, volvo));
		when(exportOrder.getTurn()).thenReturn(turnExportOrder);
		// ------------------------------------------------------------------------------------------
		buenosAires.registerTruckTransportCompany(transportVesprini);
	}

	@Test
	void testShouldHireExportServiceAndAddShipper() throws Exception {
		// Exercise
		buenosAires.hireExportService(exportOrder);
		// Assert
		assertTrue(buenosAires.getShippers().contains(ivan));
	}

	@Test
	void testShouldAddShipperWhenExportServiceHired() throws Exception {
		// Set Up
		buenosAires.registerShipper(ivan);
		// Exercise
		buenosAires.hireExportService(exportOrder);
		// Assert
		assertTrue(buenosAires.getShippers().contains(ivan));
	}

	@Test
	void testShouldThrowExceptionWhenHiringExportServiceWithoutRegisteredDriver() {
		// Set Up
		when(transportVesprini.getDrivers()).thenReturn(List.of(mock(Driver.class)));
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireExportService(exportOrder);
		}, "Driver not registered in the Managed Terminal.");
	}

	@Test
	void testShouldThrowExceptionWhenHiringExportServiceWithoutRegisteredTruck() {
		// Set Up
		when(transportVesprini.getTrucks()).thenReturn(List.of(mock(Truck.class)));
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireExportService(exportOrder);
		}, "Truck not registered in the Managed Terminal.");
	}

	@Test
	void testShouldSetTurnDateWhenExportServiceHired() throws Exception {
		// Exercise
		buenosAires.hireExportService(exportOrder);
		// Assert
		assertEquals(LocalDateTime.of(2023, Month.NOVEMBER, 12, 06, 00), exportOrder.getTurn().getDate());
		// 12-11-23 | 06:00 Hs.
	}

	@Test
	void testShouldAddExportOrderToManagedTerminalList() throws Exception {
		// Exercise
		buenosAires.hireExportService(exportOrder);
		// Assert
		assertTrue(buenosAires.getExportOrders().contains(exportOrder));
	}

	@Test
	void testTruckArrivedWithLoad_AddsWeighServiceToExportOrder_DryCargo() throws Exception {
		// Set Up
		buenosAires.setWeighingCost(400.0);
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 12, 05, 00);
		// 12-11-23 | 05:00 Hs.
		// Exercise
		buenosAires.truckArrivedWithLoad(exportOrder, alberto, volvo, arrivalDate);
		// Assert
		assertTrue("La orden de exportación contiene el servicio de pesado ya que la orden posee una carga Dry.",
				exportOrder.getServices().stream().anyMatch(Weigh.class::isInstance));
		assertTrue("La orden de exportación no contiene el servicio electrico ya que la orden posee una carga Dry.",
				exportOrder.getServices().stream().anyMatch(Weigh.class::isInstance));
		assertEquals("El servicio de pesado tiene el mismo que precio que el de la terminal gestionada.",
				buenosAires.getWeighingCost(), exportOrder.getServices().get(0).getPrice());
	}

	@Test
	void testTruckArrivedWithLoad_AddsWeighAndElectricityServiceToExportOrder_ReeferCargo() throws Exception {
		// Set Up
		buenosAires.setCostPerKw(500.0);
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 12, 05, 00);
		// 12-11-23 | 05:00 Hs.
		when(exportOrder.getLoad()).thenReturn(reefer);
		// Exercise
		buenosAires.truckArrivedWithLoad(exportOrder, alberto, volvo, arrivalDate);
		// Assert
		assertTrue("La orden de exportación contiene el servicio de pesado ya que la orden posee una carga Reefer.",
				exportOrder.getServices().stream().anyMatch(Weigh.class::isInstance));
		assertTrue(
				"La orden de exportación contiene el servicio de electricidad ya que la orden posee una carga Reefer.",
				exportOrder.getServices().stream().anyMatch(Electricity.class::isInstance));
		assertEquals("El servicio de electricidad tiene el mismo precio que el de la terminal gestionada.",
				buenosAires.getCostPerKw(), ((Electricity) exportOrder.getServices().get(1)).getPrice());
		assertEquals(
				"El servicio de electricidad tiene la misma fecha en la que llego el camión a la terminal gestionada.",
				arrivalDate, ((Electricity) exportOrder.getServices().get(1)).getStartConnection());
	}

	@Test
	void testTruckArrivedWithLoad_InvalidDriver_ThrowsException() throws Exception {
		// Set Up
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 12, 05, 00);
		// 12-11-23 | 05:00 Hs.
		// Assert
		assertThrows(RuntimeException.class,
				() -> buenosAires.truckArrivedWithLoad(exportOrder, mock(Driver.class), volvo, arrivalDate),
				"Driver does not match the order.");

	}

	@Test
	void testTruckArrivedWithLoad_InvalidTruck_ThrowsException() throws Exception {
		// Set Up
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 12, 05, 00);
		// 12-11-23 | 05:00 Hs.
		// Assert
		assertThrows(RuntimeException.class,
				() -> buenosAires.truckArrivedWithLoad(exportOrder, alberto, mock(Truck.class), arrivalDate),
				"Truck does not match the order.");
	}

	@Test
	void testTruckArrivedWithLoad_InvalidShiftTime_ThrowsException() throws Exception {
		// Set Up
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 12, 02, 00);
		// 12-11-23 | 02:00 Hs.
		// Assert
		assertThrows(RuntimeException.class,
				() -> buenosAires.truckArrivedWithLoad(exportOrder, alberto, volvo, arrivalDate),
				"Shift differs by more than 3 hours.");
	}

}
