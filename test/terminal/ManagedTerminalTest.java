package terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Consignee;
import client.Shipper;
import driver.Driver;
import maritimeCircuit.MaritimeCircuit;
import routing.ShorterTime;
import ship.Ship;
import shippingLine.ShippingLine;
import stretch.Stretch;
import trip.Trip;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;

class ManagedTerminalTest {

	protected ShorterTime shorterTime;
	// ------------------------------------------------------------
	protected ManagedTerminal buenosAires;
	// ------------------------------------------------------------
	protected Terminal guayaquil;
	protected Terminal lima;
	protected Terminal valparaiso;
	protected Terminal montevideo;
	// ------------------------------------------------------------
	protected Stretch buenosAiresValparaiso;
	protected Stretch valparaisoLima;
	protected Stretch limaGuayaquil;
	protected Stretch guayaquilBuenosAires;
	protected Stretch montevideoBuenosAires;
	protected Stretch limaMontevideo;
	// ------------------------------------------------------------
	protected MaritimeCircuit maritimeCircuitOne;
	protected MaritimeCircuit maritimeCircuitTwo;
	// ------------------------------------------------------------
	protected Trip tripOne;
	protected Trip tripTwo;
	// ------------------------------------------------------------
	protected Ship bismarck;
	// ------------------------------------------------------------
	protected ShippingLine apmMaersk;
	// ------------------------------------------------------------
	protected Consignee yoel;
	// ------------------------------------------------------------
	protected Shipper ivan;
	// ------------------------------------------------------------
	protected Driver alberto;
	// ------------------------------------------------------------
	protected Truck volvo;
	// ------------------------------------------------------------
	protected TruckTransportCompany transportVesprini;

	@BeforeEach
	void setUp() throws Exception {
		shorterTime = new ShorterTime();
		// -------------------------------------------------------------------------------------------
		buenosAires = new ManagedTerminal(shorterTime);
		// -------------------------------------------------------------------------------------------
		guayaquil = mock(Terminal.class);
		lima = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		montevideo = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
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

		montevideoBuenosAires = mock(Stretch.class);
		when(montevideoBuenosAires.getOrigin()).thenReturn(montevideo);
		when(montevideoBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(montevideoBuenosAires.getPrice()).thenReturn(2.905);
		when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(4));

		limaMontevideo = mock(Stretch.class);
		when(limaMontevideo.getOrigin()).thenReturn(lima);
		when(limaMontevideo.getDestiny()).thenReturn(montevideo);
		when(limaMontevideo.getPrice()).thenReturn(1.497);
		when(limaMontevideo.getTime()).thenReturn(Duration.ofHours(26));
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretches())
				.thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso, valparaisoLima, limaMontevideo));
		when(maritimeCircuitTwo.originTerminal()).thenReturn(montevideo);
		// ------------------------------------------------------------------------------------------
		tripOne = mock(Trip.class);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 0));
		// 12-11-23 | 12:00 Hs.
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 0)); // 12-11-23 | 12:00 Hs.
		when(tripOne.getShip()).thenReturn(bismarck);

		tripTwo = mock(Trip.class);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00));
		// 01-11-23 | 10:00 Hs.
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.
		when(tripTwo.getShip()).thenReturn(bismarck);
		// ------------------------------------------------------------------------------------------
		bismarck = mock(Ship.class);
		when(bismarck.getTrip()).thenReturn(tripTwo);
		// ------------------------------------------------------------------------------------------
		apmMaersk = mock(ShippingLine.class);
		when(apmMaersk.getMaritimeCircuits()).thenReturn(List.of(maritimeCircuitOne, maritimeCircuitTwo));
		when(apmMaersk.getTrips()).thenReturn(List.of(tripOne, tripTwo));
		when(apmMaersk.getShips()).thenReturn(List.of(bismarck));
		// ------------------------------------------------------------------------------------------
		ivan = mock(Shipper.class);
		// ------------------------------------------------------------------------------------------
		yoel = mock(Consignee.class);
		// ------------------------------------------------------------------------------------------
		alberto = mock(Driver.class);
		// ------------------------------------------------------------------------------------------
		volvo = mock(Truck.class);
		// ------------------------------------------------------------------------------------------
		transportVesprini = mock(TruckTransportCompany.class);
		when(transportVesprini.getDrivers()).thenReturn(List.of(alberto));
		when(transportVesprini.getTrucks()).thenReturn(List.of(volvo));
	}

	@Test
	void x() throws Exception {
		assertEquals(0, buenosAires.getConsignees().size());
	}

	@Test
	void testExportOrdersSizeIsZero() {
		assertEquals(0, buenosAires.getExportOrders().size());
	}

	@Test
	void testImportOrdersSizeIsZero() {
		assertEquals(0, buenosAires.getImportOrders().size());
	}

	@Test
	void testShippersSizeIsZero() {
		assertEquals(0, buenosAires.getShippers().size());
	}

	@Test
	void testClearShippingLinesResultsInSizeZero() {
		// Set Up
		buenosAires.getShippingLines().clear();
		// Exercise
		assertEquals(0, buenosAires.getShippingLines().size());
	}

	@Test
	void testClearTruckTransportCompaniesResultsInSizeZero() {
		// Set Up
		buenosAires.getTruckTransportCompanies().clear();
		// Exercise
		assertEquals(0, buenosAires.getTruckTransportCompanies().size());
	}

	@Test
	void testRoutingIsShorterTime() {
		assertEquals(shorterTime, buenosAires.getRouting());
	}

	@Test
	void testTerminalNameIsPuertoDeBuenosAires() {
		assertEquals("Puerto de Buenos Aires", buenosAires.getName());
	}

	@Test
	void testTerminalLatitudeAndLongitude() {
		assertEquals(-34.5795823299825, buenosAires.getPosition().getLatitude());
		assertEquals(-58.373877081937, buenosAires.getPosition().getLongitude());
	}

	@Test
	void testCostPerBigLoadIsZero() {
		assertEquals(0.0, buenosAires.getCostPerBigLoad());
	}

	@Test
	void testCostPerKwIsZero() {
		assertEquals(0.0, buenosAires.getCostPerKw());
	}

	@Test
	void testCostPerSmallLoadIsZero() {
		assertEquals(0.0, buenosAires.getCostPerSmallLoad());
	}

	@Test
	void testExcessStorageCostIsZero() {
		assertEquals(0.0, buenosAires.getExcessStorageCost());
	}

	@Test
	void testWeighingCostIsZero() {
		assertEquals(0.0, buenosAires.getWeighingCost());
	}

	@Test
	void testRegisteringConsigneeShouldAddToConsigneesList() {
		// Exercise
		buenosAires.registerConsignee(yoel);
		// Assert
		assertEquals(List.of(yoel), buenosAires.getConsignees());
	}

	@Test
	void testRegisteringShipperShouldAddToShippersList() {
		// Exercise
		buenosAires.registerShipper(ivan);
		// Assert
		assertEquals(List.of(ivan), buenosAires.getShippers());
	}

	@Test
	void testRegisteringShippingCompanyShouldAddToShippingLinesList() {
		// Exercise
		when(apmMaersk.hasTerminal(buenosAires)).thenReturn(true);
		buenosAires.registerShippingLine(apmMaersk);
		// Assert
		assertEquals(List.of(apmMaersk), buenosAires.getShippingLines());
	}

	@Test
	void testRegisteringTruckTransportCompanyShouldAddToTruckTransportCompaniesList() {
		// Set Up
		buenosAires.getTruckTransportCompanies().clear();
		buenosAires.registerTruckTransportCompany(transportVesprini);
		// Assert
		assertEquals(List.of(transportVesprini), buenosAires.getTruckTransportCompanies());
	}

}
