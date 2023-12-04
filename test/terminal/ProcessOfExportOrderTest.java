package terminal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Shipper;
import driver.Driver;
import load.Dry;
import load.Reefer;
import maritimeCircuit.MaritimeCircuit;
import order.ExportOrder;
import service.Electricity;
import service.Weigh;
import shippingLine.ShippingLine;
import stretch.Stretch;
import trip.Trip;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;
import turn.Turn;

class ProcessOfExportOrderTest extends ManagedTerminal2Test {

	private Terminal guayaquil;
	private Terminal lima;
	private Terminal valparaiso;
	// ------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
	// ------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	// ------------------------------------------------------------
	private Trip tripOne;
	// ------------------------------------------------------------
	private ShippingLine apmMaersk;
	// ------------------------------------------------------------
	private Shipper ivan;
	// ------------------------------------------------------------
	private Driver alberto;
	// ------------------------------------------------------------
	private Truck volvo;
	// ------------------------------------------------------------
	private TruckTransportCompany transportVesprini;
	// ------------------------------------------------------------
	private Turn turnExportOrder;
	// ------------------------------------------------------------
	private Reefer reefer;
	private Dry dry;
	// ------------------------------------------------------------
	private Electricity electricity;
	private Weigh weigh;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// -------------------------------------------------------------------------------------------
		// TERMINAL
		guayaquil = mock(Terminal.class);
		lima = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		// STRETCH
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
		when(buenosAiresValparaiso.getPrice()).thenReturn(1.040);
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);
		when(valparaisoLima.getPrice()).thenReturn(2.024);
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);
		when(limaGuayaquil.getPrice()).thenReturn(1.821);
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(guayaquilBuenosAires.getPrice()).thenReturn(2.192);
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));
		// ------------------------------------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);
		// ------------------------------------------------------------------------------------------
		// TRIP
		tripOne = mock(Trip.class);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 0));
		// 12-11-23 | 12:00 Hs.
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 0)); // 12-11-23 | 12:00 Hs.
		// ------------------------------------------------------------------------------------------
		// SHIPPING LINE
		apmMaersk = mock(ShippingLine.class);
		when(apmMaersk.getMaritimeCircuits()).thenReturn(List.of(maritimeCircuitOne));
		when(apmMaersk.getTrips()).thenReturn(List.of(tripOne));
		// ------------------------------------------------------------------------------------------
		// SHIPPER
		ivan = mock(Shipper.class);
		// ------------------------------------------------------------------------------------------
		// DRIVER
		alberto = mock(Driver.class);
		// ------------------------------------------------------------------------------------------
		// TRUCK
		volvo = mock(Truck.class);
		// ------------------------------------------------------------------------------------------
		// TRUCK TRANSPORT COMPANY
		transportVesprini = mock(TruckTransportCompany.class);
		when(transportVesprini.getDrivers()).thenReturn(List.of(alberto));
		when(transportVesprini.getTrucks()).thenReturn(List.of(volvo));
		// ------------------------------------------------------------------------------------------
		// LOAD
		dry = mock(Dry.class);
		when(dry.getName()).thenReturn("Dry");

		reefer = mock(Reefer.class);
		when(reefer.getName()).thenReturn("Reefer");
		// ------------------------------------------------------------------------------------------
		electricity = mock(Electricity.class);
		when(electricity.getPrice()).thenReturn(2000.0);
		when(electricity.getStartConnection()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00));
		// 12-11-23 | 12:00 Hs.
		when(electricity.getName()).thenReturn("Electricity");

		weigh = mock(Weigh.class);
		when(weigh.getPrice()).thenReturn(1000.0);
		when(weigh.getName()).thenReturn("Weigh");
		// ------------------------------------------------------------------------------------------
		// TURN
		turnExportOrder = mock(Turn.class);
		when(turnExportOrder.getDriver()).thenReturn(alberto);
		when(turnExportOrder.getTruck()).thenReturn(volvo);
		when(turnExportOrder.getDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 06, 00));
		// 12-11-23 | 06:00 Hs.
		// ------------------------------------------------------------------------------------------
		// EXPORT ORDER
		exportOrder = spy(new ExportOrder(dry, tripOne, buenosAires, lima, ivan, alberto, volvo));
		when(exportOrder.getClient()).thenReturn(ivan);
		when(exportOrder.getTurn()).thenReturn(turnExportOrder);
		when(exportOrder.getLoad()).thenReturn(dry);
		when(exportOrder.getTrip()).thenReturn(tripOne);
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
