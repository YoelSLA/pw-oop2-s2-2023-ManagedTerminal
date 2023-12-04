package turn;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import driver.Driver;
import truck.Truck;

public class turnTest {

	private Turn turn;
	
	private Driver driver;
	private Truck truck;
	private LocalDateTime date1 = LocalDateTime.of(2023, 11, 30, 12, 00);
	private LocalDateTime date2 = LocalDateTime.of(2023, 11, 10, 12, 00);
	
	
	@BeforeEach
	void setUp() {
		driver = mock(Driver.class);
		truck = mock(Truck.class);
		turn = new Turn(driver,truck,date1);
	}
	
	
	@Test
	void getDriver() {
		assertEquals(driver, turn.getDriver());
	}
	
	@Test
	void getTruck() {
		assertEquals(truck, turn.getTruck());
	}
	
	@Test
	void getDate() {
		assertEquals(date1, turn.getDate());
	}
	
	@Test
	void setDate() {
		turn.setDate(date2);
		assertEquals(date2, turn.getDate());
	}

}
