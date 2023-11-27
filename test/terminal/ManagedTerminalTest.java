package terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Consignee;
import client.Shipper;
import driver.Driver;
import geographicalPosition.GeographicalPosition;
import maritimeCircuit.MaritimeCircuit;
import order.ExportOrder;
import routing.Routing;
import shippingLine.ShippingLine;
import stretch.Stretch;
import trip.Trip;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;

class ManagedTerminalTest {

	private Consignee consigneeGabriela;
//-------------------------------------------------------------
	private Driver driverAlberto;
//-------------------------------------------------------------
	private ExportOrder exportOrderOfAlejandra;
//-------------------------------------------------------------
	private GeographicalPosition positionBuenosAires;
//-------------------------------------------------------------
	private MaritimeCircuit maritimeCircuit;
//-------------------------------------------------------------
	private Routing ferFewerIntermediateTerminals;
	private Routing lowerPrice;
	private Routing shorterTime;
//-------------------------------------------------------------
	private Shipper shipperAlejandra;
	private ShippingLine apmMaersk;
//-------------------------------------------------------------
	private Stretch buenosAiresSantiago;
	private Stretch santiagoQuito;
	private Stretch quitoLima;
	private Stretch limaBuenosAires;
//-------------------------------------------------------------
	private Terminal santiago;
	private Terminal quito;
	private Terminal lima;
//-------------------------------------------------------------
	private Trip trip;
//-------------------------------------------------------------
	private Truck volvoTruck;
//-------------------------------------------------------------
	private TruckTransportCompany transporteVesprini;
//-------------------------------------------------------------
	private ManagedTerminal buenosAires; // SUT

	@BeforeEach
	void setUp() {
//-------------------------------------------------------------
		// DRIVER
		driverAlberto = mock(Driver.class);
//-------------------------------------------------------------
		// CONSIGNEE
		consigneeGabriela = mock(Consignee.class);
//-------------------------------------------------------------
		// EXPORT ORDER
		exportOrderOfAlejandra = mock(ExportOrder.class);

		when(exportOrderOfAlejandra.getShipper()).thenReturn(shipperAlejandra);
		when(exportOrderOfAlejandra.getDriver()).thenReturn(driverAlberto);
		when(exportOrderOfAlejandra.getTruck()).thenReturn(volvoTruck);
		when(exportOrderOfAlejandra.getTrip()).thenReturn(trip);
//-------------------------------------------------------------
		// GEOGRAPHICAL POSITION
		positionBuenosAires = mock(GeographicalPosition.class);

		when(positionBuenosAires.getLatitude()).thenReturn(-34.5795823299825);
		when(positionBuenosAires.getLongitude()).thenReturn(-58.373877081937);
//-------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuit = mock(MaritimeCircuit.class);

		when(maritimeCircuit.getStretchs())
				.thenReturn(Arrays.asList(buenosAiresSantiago, santiagoQuito, quitoLima, limaBuenosAires));
//-------------------------------------------------------------
		// SHIPPER
		shipperAlejandra = mock(Shipper.class);
//-------------------------------------------------------------
		// SHIPPING LINE
		apmMaersk = mock(ShippingLine.class);
//-------------------------------------------------------------
		// STRETCH
		buenosAiresSantiago = mock(Stretch.class);
		when(buenosAiresSantiago.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresSantiago.getDestiny()).thenReturn(santiago);
		when(buenosAiresSantiago.getTime()).thenReturn(Duration.ofHours(3));

		santiagoQuito = mock(Stretch.class);
		when(santiagoQuito.getOrigin()).thenReturn(santiago);
		when(santiagoQuito.getDestiny()).thenReturn(quito);
		when(santiagoQuito.getTime()).thenReturn(Duration.ofHours(5));

		quitoLima = mock(Stretch.class);
		when(quitoLima.getOrigin()).thenReturn(quito);
		when(quitoLima.getDestiny()).thenReturn(lima);
		when(quitoLima.getTime()).thenReturn(Duration.ofHours(7));

		limaBuenosAires = mock(Stretch.class);
		when(limaBuenosAires.getOrigin()).thenReturn(lima);
		when(limaBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(limaBuenosAires.getTime()).thenReturn(Duration.ofHours(15));
//-------------------------------------------------------------
		// TERMINAL
		santiago = mock(Terminal.class);
		quito = mock(Terminal.class);
		lima = mock(Terminal.class);
//-------------------------------------------------------------
		// TRUCK
		volvoTruck = mock(Truck.class);
//-------------------------------------------------------------
		// TRUCK TRANSPORT COMPANY
		transporteVesprini = mock(TruckTransportCompany.class);

		when(transporteVesprini.getDrivers()).thenReturn(Arrays.asList(driverAlberto));
		when(transporteVesprini.getTrucks()).thenReturn(Arrays.asList(volvoTruck));
//-------------------------------------------------------------
		// TRIP
		trip = mock(Trip.class);

		when(trip.getStartDate()).thenReturn(LocalDateTime.of(2023, 11, 26, 10, 0));
		when(trip.getMaritimeCircuit()).thenReturn(maritimeCircuit);
//-------------------------------------------------------------
		// ROUTING
		ferFewerIntermediateTerminals = mock(Routing.class);
		lowerPrice = mock(Routing.class);
		shorterTime = mock(Routing.class);

//-------------------------------------------------------------
		// MANAGED TERMINAL
		buenosAires = new ManagedTerminal(positionBuenosAires, ferFewerIntermediateTerminals);
	}

// ----------------------------------
// CREATION
// ----------------------------------
	@Test
	void testAManagedTerminalIsCreated() {

		// Assert
		assertEquals(0, buenosAires.getConsignees().size());
		assertEquals(0, buenosAires.getExportOrders().size());
		assertEquals(positionBuenosAires, buenosAires.getGeographicalPosition());
		assertEquals(0, buenosAires.getImportOrders().size());
		assertEquals(ferFewerIntermediateTerminals, buenosAires.getRouting());
		assertEquals(0, buenosAires.getShippers().size());
		assertEquals(0, buenosAires.getShippingCompanies().size());
		assertEquals(0, buenosAires.getTruckTransportCompanies().size());
		assertEquals(0, buenosAires.getTurns().size());
	}

// ----------------------------------
// METHODS TO ADD TO MANAGED TERMINAL
// ----------------------------------
	@Test
	void testAManagedTerminalRegistersAConsignee() {
		// Exercise
		buenosAires.registerConsignee(consigneeGabriela);
		// Assert
		assertEquals(1, buenosAires.getConsignees().size());
	}

	@Test
	void testAManagedTerminalRegistersAShipper() {
		// Exercise
		buenosAires.registerShipper(shipperAlejandra);
		// Assert
		assertEquals(1, buenosAires.getShippers().size());
	}

	@Test
	void testAManagedTerminalRegistersAShippingCompany() {
		// Exercise
		buenosAires.registerShippingCompany(apmMaersk);
		// Assert
		assertEquals(1, buenosAires.getShippingCompanies().size());
	}

	@Test
	void testAManagedTerminalRegistersATruckTransportCompany() {
		// Exercise
		buenosAires.registerTruckTransportCompany(transporteVesprini);
		// Assert
		assertEquals(1, buenosAires.getTruckTransportCompanies().size());
	}

// ----------------------------------
// EXPORT
// ----------------------------------
	@Test
	void testTheExportOrderIsNotRegisteredBecauseTheDriverIsNotRegistered() {
		// Set Up
		when(transporteVesprini.getDrivers()).thenReturn(List.of());
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireExportService(exportOrderOfAlejandra);
		});
	}

	@Test
	void testTheExportOrderIsNotRegisteredBecauseTheTruckIsNotRegistered() {
		// Set Up
		when(transporteVesprini.getDrivers()).thenReturn(List.of());
		// Assert
		assertThrows(RuntimeException.class, () -> {
			buenosAires.hireExportService(exportOrderOfAlejandra);
		});
	}

	@Test
	void testTheShipperIsNotRegisteredInTheManagedTerminaThereforeItIsRegistered() {
		// Excerise
		buenosAires.registerTruckTransportCompany(transporteVesprini);
		buenosAires.hireExportService(exportOrderOfAlejandra);
		// Assert
		assertTrue(buenosAires.getShippers().contains(shipperAlejandra));
	}

//	@Test
//	void testTheExportOrderIsValidAndAShiftIsAssigned() {
//		// Excerise
//		buenosAires.registerShippingCompany(apmMaersk);
//		buenosAires.hireExportService(exportOrderOfAlejandra);
//		// Assert
//		assertEquals(LocalDateTime.of(2023, 12, 10, 10, 10), buenosAires.getTurns().get(0).getDateTurn());
//		assertEquals(1, buenosAires.getTurns().size());
//
//	}

// ----------------------------------
// IMPORT
// ----------------------------------

// ----------------------------------
// WHEN TRUCK ARRIVED WITH LOAD
// ----------------------------------

// ----------------------------------
// WHEN TRUCK LEAVE WITH LOAD
// ----------------------------------
}
