package terminal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import load.Dry;
import load.Reefer;
import maritimeCircuit.MaritimeCircuit;
import order.ExportOrder;
import order.ImportOrder;
import ship.Ship;
import shippingLine.ShippingLine;
import stretch.Stretch;
import trip.Trip;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;

class ShippingCommunication extends ManagedTerminalTest {

	private Terminal montevideo;
	private Terminal lima;
	private Terminal valparaiso;
	// ------------------------------------------------------------
	private Stretch montevideoBuenosAires;
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaMontevideo;
	// ------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	// ------------------------------------------------------------
	private Ship bismarck;
	// ------------------------------------------------------------
	private Trip tripOne;
	// ------------------------------------------------------------
	private ShippingLine apmMaersk;
	// ------------------------------------------------------------
	private Consignee yoel;
	// ------------------------------------------------------------
	private Shipper ivan;
	// ------------------------------------------------------------
	private Driver alberto;
	// ------------------------------------------------------------
	private Truck volvo;
	// ------------------------------------------------------------
	private TruckTransportCompany transportVesprini;
	// ------------------------------------------------------------
	private Reefer reefer;
	private Dry dry;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;
	// ------------------------------------------------------------
	private ImportOrder importOrder;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// -------------------------------------------------------------------------------------------
		montevideo = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		lima = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		montevideoBuenosAires = mock(Stretch.class);
		when(montevideoBuenosAires.getOrigin()).thenReturn(montevideo);
		when(montevideoBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(montevideoBuenosAires.getPrice()).thenReturn(2.905);
		when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(4));

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

		limaMontevideo = mock(Stretch.class);
		when(limaMontevideo.getOrigin()).thenReturn(lima);
		when(limaMontevideo.getDestiny()).thenReturn(montevideo);
		when(limaMontevideo.getPrice()).thenReturn(1.497);
		when(limaMontevideo.getTime()).thenReturn(Duration.ofHours(26));
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso, valparaisoLima, limaMontevideo));
		when(maritimeCircuitOne.originTerminal()).thenReturn(montevideo);
		// ------------------------------------------------------------------------------------------
		tripOne = mock(Trip.class);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00));
		// 01-11-23 | 10:00 Hs.
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getShip()).thenReturn(bismarck);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00));
		// 01-11-23 | 14:00 Hs.
		// ------------------------------------------------------------------------------------------
		bismarck = mock(Ship.class);
		when(bismarck.getTrip()).thenReturn(tripOne);
		// ------------------------------------------------------------------------------------------
		apmMaersk = mock(ShippingLine.class);
		when(apmMaersk.getShips()).thenReturn(List.of(bismarck));
		when(apmMaersk.getMaritimeCircuits()).thenReturn(List.of(maritimeCircuitOne));
		when(apmMaersk.getTrips()).thenReturn(List.of(tripOne));
		// ------------------------------------------------------------------------------------------
		yoel = mock(Consignee.class);
		// ------------------------------------------------------------------------------------------
		ivan = mock(Shipper.class);
		// ------------------------------------------------------------------------------------------
		alberto = mock(Driver.class);
		// ------------------------------------------------------------------------------------------
		volvo = mock(Truck.class);
		// ------------------------------------------------------------------------------------------
		transportVesprini = mock(TruckTransportCompany.class);
		when(transportVesprini.getDrivers()).thenReturn(List.of(alberto));
		when(transportVesprini.getTrucks()).thenReturn(List.of(volvo));
		// ------------------------------------------------------------------------------------------
		dry = mock(Dry.class);
		reefer = mock(Reefer.class);
		// ------------------------------------------------------------------------------------------
		exportOrder = spy(new ExportOrder(ivan, tripOne, dry, lima, alberto, volvo));
		// ------------------------------------------------------------------------------------------
		importOrder = spy(new ImportOrder(yoel, tripOne, dry, montevideo, buenosAires, alberto, volvo));

	}

	@Test
	void testNotifyShipInminentArrival_ShipAndOrdersNotified() throws Exception {
		// Exercise
		buenosAires.registerShippingLine(apmMaersk);
		buenosAires.registerTruckTransportCompany(transportVesprini);
		buenosAires.hireExportService(exportOrder);
		buenosAires.hireImportService(importOrder);
		buenosAires.notifyShipInminentArrival(bismarck);
		// Verify
		verify(bismarck, times(2)).getTrip();
		verify(exportOrder, times(2)).getTrip();
		verify(exportOrder, times(3)).getClient();
		verify(importOrder, times(2)).getTrip();
		verify(importOrder, times(3)).getClient();
		verify(yoel, times(1)).sendMailAboutShipInminentArrival(buenosAires, yoel, "Ship is near");
		verify(ivan, times(1)).sendMailAboutShipInminentArrival(buenosAires, ivan, "Ship is near");
	}

	@Test
	void testNotifyShipArrival_StartWorkingCalledOnce() throws Exception {
		// Exercise
		buenosAires.registerShippingLine(apmMaersk);
		buenosAires.registerTruckTransportCompany(transportVesprini);
		buenosAires.hireExportService(exportOrder);
		buenosAires.hireImportService(importOrder);
		buenosAires.notifyShipArrival(bismarck);
		// Verify
		verify(bismarck, times(1)).startWorking();
	}

	@Test
	void testNotifyShipArrival_DepartCalledOnce() throws Exception {
		// Exercise
		buenosAires.registerShippingLine(apmMaersk);
		buenosAires.registerTruckTransportCompany(transportVesprini);
		buenosAires.hireExportService(exportOrder);
		buenosAires.hireImportService(importOrder);
		buenosAires.notifyShipArrival(bismarck);
		// Verify
		verify(bismarck, times(1)).depart();
	}

	@Test
	void testNotifyShipDeparture_ShouldNotifyClientsAndSendBillToShipper() throws Exception {
		// Exercise
		buenosAires.registerShippingLine(apmMaersk);
		buenosAires.registerTruckTransportCompany(transportVesprini);
		buenosAires.hireExportService(exportOrder);
		buenosAires.hireImportService(importOrder);
		buenosAires.notifyShipDeparture(bismarck);
		// Verify
		verify(exportOrder, times(3)).getClient();

	}

}
