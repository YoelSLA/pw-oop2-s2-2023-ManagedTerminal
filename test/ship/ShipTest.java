package ship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import phase.Arrived;
import phase.Departing;
import phase.Inbound;
import phase.Outbound;
import phase.Working;
import position.Position;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class ShipTest {

	// ------------------------------------------------------------
		private ManagedTerminal buenosAires;
		// ------------------------------------------------------------
		private Terminal lima;
		private Terminal valparaiso;
		private Terminal montevideo;
		// ------------------------------------------------------------
		private Stretch montevideoBuenosAires;
		private Stretch buenosAiresValparaiso;
		private Stretch valparaisoLima;
		private Stretch limaMontevideo;
		// ------------------------------------------------------------
		private MaritimeCircuit maritimeCircuitOne;
		// ------------------------------------------------------------
		private Trip tripOne;
		private Trip tripTwo;
		// ------------------------------------------------------------
		private Ship bismarck;
		
		@BeforeEach
		void setUp() throws Exception {

			buenosAires = mock(ManagedTerminal.class);
			when(buenosAires.getPosition()).thenReturn(new Position(-34.5795823299825, -58.373877081937));
			// -------------------------------------------------------------------------------------------
			lima = mock(Terminal.class);
			valparaiso = mock(Terminal.class);
			montevideo = mock(Terminal.class);
			when(montevideo.getPosition()).thenReturn(new Position(-34.90367464769443, -56.21226754075775));
			// ------------------------------------------------------------------------------------------
			montevideoBuenosAires = mock(Stretch.class);
			when(montevideoBuenosAires.getOrigin()).thenReturn(montevideo);
			when(montevideoBuenosAires.getDestiny()).thenReturn(buenosAires);
			when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(4));
			
			buenosAiresValparaiso = mock(Stretch.class);
			when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
			when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
			when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));

			valparaisoLima = mock(Stretch.class);
			when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
			when(valparaisoLima.getDestiny()).thenReturn(lima);
			when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));

			limaMontevideo = mock(Stretch.class);
			when(limaMontevideo.getOrigin()).thenReturn(lima);
			when(limaMontevideo.getDestiny()).thenReturn(montevideo);
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
			when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
					.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.
			when(tripOne.getOriginTerminal()).thenReturn(montevideo);
			tripTwo = mock(Trip.class);
			// ------------------------------------------------------------------------------------------
			bismarck = new Ship("La Perla Negra", "12345678", tripOne);
			when(tripOne.getShip()).thenReturn(bismarck);
			// ------------------------------------------------------------------------------------------

		}
		
		@Test
		void getIsOnTrip() {
			// Exercise
			bismarck.startTrip();	
			
			// Assert
			assertTrue(bismarck.getIsOnTrip());
		}
		
		@Test
		void testPhaseChange() {
			// Exercise
			bismarck.startTrip();
			
			// Assert
			assertEquals(Outbound.class, bismarck.getPhase().getClass());
		}
		
		@Test
		void testNextTerminal() {
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			
			// Exercise
			bismarck.startTrip();	
			
			// Assert
			assertEquals(buenosAires, bismarck.getTerminal());
		}
		
		@Test
		void testSetPositionSame() {
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			
			// Exercise
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -50.21226754075775));	
			
			// Assert
			assertEquals(Outbound.class, bismarck.getPhase().getClass());
		}
		
		@Test
		void testSetPositionNextPhase() {
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			
			// Exercise
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			
			// Assert
			assertEquals(Inbound.class, bismarck.getPhase().getClass());
			
			// Verify
			verify(buenosAires, times(1)).notifyShipInminentArrival(bismarck);
		}
		
		@Test
		void testShipArrivedToTerminal() {
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			
			// Exercise
			bismarck.setPosition(new Position(-34.5795823299825, -58.373877081937));	//cambio a Arrived
			
			// Assert
			assertEquals(Arrived.class, bismarck.getPhase().getClass());
			
			// Verify
			verify(buenosAires, times(1)).notifyShipArrival(bismarck);

		}
		
		@Test
		void testShipArrivedToTerminalFail() {
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			
			// Exercise
			bismarck.setPosition(new Position(-34.90367464769443, -58.373877081937));	// intento cambiar a Arrived, pero todavia no llego el buque a la terminal
			
			// Assert
			assertEquals(Inbound.class, bismarck.getPhase().getClass());
			
			// Verify
			verify(buenosAires, times(0)).notifyShipArrival(bismarck);

		}
		
		
		@Test
		void testStartWorking() {	
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			bismarck.setPosition(new Position(-34.5795823299825, -58.373877081937));	//cambio a Arrived
			
			// Exercise
			bismarck.startWorking();			
			
			// Assert
			assertEquals(Working.class, bismarck.getPhase().getClass());
		}
		
		@Test
		void testStartWorkingFail() {	
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			bismarck.setPosition(new Position(-34.90367464769443, -58.373877081937));	// intento cambiar a Arrived, pero todavia no llego el buque a la terminal
			
			// Exercise
			bismarck.startWorking();			
			
			// Assert
			assertEquals(Inbound.class, bismarck.getPhase().getClass());
		}
		
		@Test
		void testDepart() {	
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			bismarck.setPosition(new Position(-34.5795823299825, -58.373877081937));	//cambio a Arrived
			bismarck.startWorking(); // cambio a Working
			
			// Exercise
			bismarck.depart(); // cambio a Departing
			
			// Assert
			assertEquals(Departing.class, bismarck.getPhase().getClass());
		}
		
		@Test
		void testDepartToOutbound() {	
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			when(tripOne.getNextTerminal(buenosAires)).thenReturn(valparaiso);
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			bismarck.setPosition(new Position(-34.5795823299825, -58.373877081937)); // cambio a Arrived
			bismarck.startWorking(); // cambio a Working
			bismarck.depart(); // cambio a Departing
			
			// Exercise
			bismarck.setPosition(new Position(-35.5795823299825, -58.373877081937)); // cambio a Outbound nuevamente
			
			// Assert
			assertEquals(valparaiso, bismarck.getTerminal());
			assertEquals(Outbound.class, bismarck.getPhase().getClass());
			
			// Verify
			verify(buenosAires, times(1)).notifyShipDeparture(bismarck);
		}
		
		@Test
		void testDepartToOutboundFail() {	
			// Set Up
			when(tripOne.getNextTerminal(montevideo)).thenReturn(buenosAires);
			when(tripOne.getNextTerminal(buenosAires)).thenReturn(valparaiso);
			bismarck.startTrip();	
			bismarck.setPosition(new Position(-34.90367464769443, -58.21226754075775));	// cambio a Inbound
			bismarck.setPosition(new Position(-34.5795823299825, -58.373877081937)); // cambio a Arrived
			bismarck.startWorking(); // cambio a Working
			bismarck.depart(); // cambio a Departing
			
			// Exercise
			bismarck.setPosition(new Position(-34.5795823299825, -58.373877081937)); // intento cambiar a Outbound nuevamente, pero sigue en la misma posicion que la terminal
			
			// Assert
			assertEquals(Departing.class, bismarck.getPhase().getClass());

		}
		
		
		@Test
		void getShipName() {	
			assertEquals("La Perla Negra", bismarck.getName());
		}
		
		@Test
		void getShipImo() {	
			assertEquals("12345678", bismarck.getImo());
		}

		@Test
		void getTrip() {	
			assertEquals(tripOne, bismarck.getTrip());
		}
		
		@Test
		void setTripWithException() throws Exception {
			// Exercise
			bismarck.startTrip();
			
			// Assert
			assertThrows(RuntimeException.class, () -> bismarck.setTrip(tripTwo));
		}
		
		@Test
		void setTripWithNoException() throws Exception {
			// Exercise
			bismarck.setTrip(tripOne);
			
			// Assert
			assertEquals(tripOne, bismarck.getTrip());
		}
		
}
