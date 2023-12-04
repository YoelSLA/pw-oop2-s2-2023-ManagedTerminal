package terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Consignee;
import client.Shipper;
import driver.Driver;
import load.Dry;
import load.Reefer;
import maritimeCircuit.MaritimeCircuit;
import order.ExportOrder;
import order.ImportOrder;
import position.Position;
import routing.Routing;
import service.Electricity;
import service.Weigh;
import ship.Ship;
import shippingLine.ShippingLine;
import stretch.Stretch;
import trip.Trip;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;
import turn.Turn;

class ManagedTerminalTest {

	private ManagedTerminal buenosAires; // SUT
	// ------------------------------------------------------------
	private Terminal cartagena;
	private Terminal guayaquil;
	private Terminal montevideo;
	private Terminal lima;
	private Terminal valparaiso;
	// ------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
	private Stretch montevideoBuenosAires;
	private Stretch limaMontevideo;
	private Stretch guayaquilCartagena;
	private Stretch cartagenaValparaiso;
	// ------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;
	private MaritimeCircuit maritimeCircuitThree;
	// ------------------------------------------------------------
	private Ship bismarck;
	private Ship lusitania;
	private Ship nautilus;
	private Ship victory;
	// ------------------------------------------------------------
	private Trip tripOne;
	private Trip tripTwo;
	private Trip tripThree;
	// ------------------------------------------------------------
	private ShippingLine apmMaersk;
	private ShippingLine seaLand;
	// ------------------------------------------------------------
	private Consignee yoel;
	// ------------------------------------------------------------
	private Shipper ivan;
	// ------------------------------------------------------------
	private Driver alberto;
	private Driver luis;
	// ------------------------------------------------------------
	private Truck volvo;
	private Truck scania;
	// ------------------------------------------------------------
	private TruckTransportCompany transportVesprini;
	// ------------------------------------------------------------
	private Turn turnExportOrder;
	private Turn turnImportOrder;
	// ------------------------------------------------------------
	private Reefer reefer;
	private Dry dry;
	// ------------------------------------------------------------
	private Electricity electricity;
	private Weigh weigh;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;
	// ------------------------------------------------------------
	private ImportOrder importOrder;
	// ------------------------------------------------------------
	private Routing ferFewerIntermediateTerminals;

	@BeforeEach
	void setUp() throws Exception {

		// ROUTING
		ferFewerIntermediateTerminals = mock(Routing.class);
		// ------------------------------------------------------------------------------------------
		// MANAGED TERMINAL
		buenosAires = new ManagedTerminal(ferFewerIntermediateTerminals);
		// ------------------------------------------------------------------------------------------
		// TERMINAL
		cartagena = mock(Terminal.class);
		when(cartagena.getPosition()).thenReturn(new Position(10.406410810421113, -75.53249051855994));

		guayaquil = mock(Terminal.class);
		when(guayaquil.getPosition()).thenReturn(new Position(-2.248507058282952, -79.92707873175921));

		montevideo = mock(Terminal.class);
		when(montevideo.getPosition()).thenReturn(new Position(-34.90367464769443, -56.21226754075775));

		lima = mock(Terminal.class);
		when(lima.getPosition()).thenReturn(new Position(-11.996924092435204, -77.12802438304938));

		valparaiso = mock(Terminal.class);
		when(valparaiso.getPosition()).thenReturn(new Position(-33.03216348772764, -71.62714052895188));
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

		guayaquilCartagena = mock(Stretch.class);
		when(guayaquilCartagena.getOrigin()).thenReturn(guayaquil);
		when(guayaquilCartagena.getDestiny()).thenReturn(cartagena);
		when(guayaquilCartagena.getPrice()).thenReturn(1.500);
		when(guayaquilCartagena.getTime()).thenReturn(Duration.ofHours(12));

		cartagenaValparaiso = mock(Stretch.class);
		when(cartagenaValparaiso.getOrigin()).thenReturn(cartagena);
		when(cartagenaValparaiso.getDestiny()).thenReturn(valparaiso);
		when(cartagenaValparaiso.getPrice()).thenReturn(1.200);
		when(cartagenaValparaiso.getTime()).thenReturn(Duration.ofHours(22));
		// ------------------------------------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretches())
				.thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso, valparaisoLima, limaMontevideo));

		maritimeCircuitThree = mock(MaritimeCircuit.class);
		when(maritimeCircuitThree.getStretches())
				.thenReturn(List.of(valparaisoLima, limaGuayaquil, guayaquilCartagena, cartagenaValparaiso));
		// ------------------------------------------------------------------------------------------
		// SHIP
		bismarck = mock(Ship.class);
		lusitania = mock(Ship.class);
		nautilus = mock(Ship.class);
		victory = mock(Ship.class);
		// ------------------------------------------------------------------------------------------
		// TRIP
		tripOne = mock(Trip.class);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 0)); // 12-11-23 | 12:00
																									// Hs.
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getShip()).thenReturn(bismarck);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 0)); // 12-11-23 | 12:00 Hs.

		tripTwo = mock(Trip.class);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.DECEMBER, 1, 10, 0)); // 01-12-23 | 10:00
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.getShip()).thenReturn(lusitania);

		tripThree = mock(Trip.class);
		when(tripThree.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.OCTOBER, 10, 18, 0)); // 10-10-23 | 18:00
		when(tripThree.getMaritimeCircuit()).thenReturn(maritimeCircuitThree);
		when(tripThree.getShip()).thenReturn(victory);
		// ------------------------------------------------------------------------------------------
		// SHIPPING LINE
		apmMaersk = mock(ShippingLine.class);
		when(apmMaersk.getShips()).thenReturn(List.of(nautilus, bismarck));
		when(apmMaersk.getMaritimeCircuits()).thenReturn(List.of(maritimeCircuitOne));
		when(apmMaersk.getTrips()).thenReturn(List.of(tripOne));

		seaLand = mock(ShippingLine.class);
		when(seaLand.getShips()).thenReturn(List.of(lusitania, victory));
		when(seaLand.getMaritimeCircuits()).thenReturn(List.of(maritimeCircuitTwo, maritimeCircuitThree));
		when(seaLand.getTrips()).thenReturn(List.of(tripTwo, tripThree));
		// ------------------------------------------------------------------------------------------
		// CONSIGNEE
		yoel = mock(Consignee.class);
		// ------------------------------------------------------------------------------------------
		// SHIPPER
		ivan = mock(Shipper.class);
		// ------------------------------------------------------------------------------------------
		// DRIVER
		alberto = mock(Driver.class);
		luis = mock(Driver.class);
		// ------------------------------------------------------------------------------------------
		// TRUCK
		volvo = mock(Truck.class);
		scania = mock(Truck.class);
		// ------------------------------------------------------------------------------------------
		// TRUCK TRANSPORT COMPANY
		transportVesprini = mock(TruckTransportCompany.class);
		when(transportVesprini.getDrivers()).thenReturn(Arrays.asList(alberto));
		when(transportVesprini.getTrucks()).thenReturn(Arrays.asList(volvo));
		// ------------------------------------------------------------------------------------------
		// REEFER
		dry = mock(Dry.class);
		when(dry.getName()).thenReturn("Dry");

		reefer = mock(Reefer.class);
		when(reefer.getName()).thenReturn("Reefer");
		// ------------------------------------------------------------------------------------------
		electricity = mock(Electricity.class);
		when(electricity.getPrice()).thenReturn(2000.0);
		when(electricity.getStartConnection()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00));
//		when(electricity.getName()).thenReturn("Electricity");
//		weigh = mock(Weigh.class);
//		when(weigh.getPrice()).thenReturn(1000.0);
//		when(weigh.getName()).thenReturn("Weigh");
		// ------------------------------------------------------------------------------------------
		// TURN
		turnExportOrder = mock(Turn.class);
		when(turnExportOrder.getDriver()).thenReturn(alberto);
		when(turnExportOrder.getTruck()).thenReturn(volvo);
		when(turnExportOrder.getDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 06, 00)); // 12-11-23 |
																										// 06:00
		turnImportOrder = mock(Turn.class);
		when(turnImportOrder.getDriver()).thenReturn(alberto);
		when(turnImportOrder.getTruck()).thenReturn(volvo);
		// ------------------------------------------------------------------------------------------
		// EXPORT ORDER
		exportOrder = mock(ExportOrder.class);
		when(exportOrder.getClient()).thenReturn(ivan);
		when(exportOrder.getTurn()).thenReturn(turnExportOrder);
		when(exportOrder.getLoad()).thenReturn(dry);
		when(exportOrder.getTrip()).thenReturn(tripOne);
		// ------------------------------------------------------------------------------------------
		// IMPORT ORDER
		importOrder = mock(ImportOrder.class);
		when(importOrder.getClient()).thenReturn(yoel);
		when(importOrder.getTurn()).thenReturn(turnImportOrder);
		when(importOrder.getLoad()).thenReturn(reefer);
		when(importOrder.getTrip()).thenReturn(tripTwo);
	}

	// ------------------------------------------------------------
	// INITIALIZE MANAGED TERMINAL
	// ------------------------------------------------------------
	@Test
	void testShouldHaveNoConsigneesInitially() {
		assertEquals(0, buenosAires.getConsignees().size());
	}

	@Test
	void testShouldHaveNoExportOrdersInitially() {
		assertEquals(0, buenosAires.getExportOrders().size());
	}

	@Test
	void testShouldHaveNoImportOrdersInitially() {
		assertEquals(0, buenosAires.getImportOrders().size());
	}

	@Test
	void testShouldHaveCorrectRoutingTerminalsInitially() {
		assertEquals(ferFewerIntermediateTerminals, buenosAires.getRouting());
	}

	@Test
	void testShouldHaveNoShippersInitially() {
		assertEquals(0, buenosAires.getShippers().size());
	}

	@Test
	void testShouldHaveNoShippingLinesInitially() {
		assertEquals(0, buenosAires.getShippingLines().size());
	}

	@Test
	void testShouldHaveNoTruckTransportCompaniesInitially() {
		assertEquals(0, buenosAires.getTruckTransportCompanies().size());
	}

	@Test
	void testShouldHaveCorrectName() {
		assertEquals("Puerto de Buenos Aires", buenosAires.getName());
	}

	@Test
	void testShouldHaveCorrectPosition() {
		assertEquals(-34.5795823299825, buenosAires.getPosition().getLatitude());
		assertEquals(-58.373877081937, buenosAires.getPosition().getLongitude());
	}

	// ------------------------------------------------------------
	// REGISTRATION METHODS
	// ------------------------------------------------------------
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
		buenosAires.registerShippingCompany(apmMaersk);
		// Assert
		assertEquals(List.of(apmMaersk), buenosAires.getShippingLines());
	}

	@Test
	void testRegisteringTruckTransportCompanyShouldAddToTruckTransportCompaniesList() {
		// Exercise
		buenosAires.registerTruckTransportCompany(transportVesprini);
		// Assert
		assertEquals(List.of(transportVesprini), buenosAires.getTruckTransportCompanies());
	}

}
