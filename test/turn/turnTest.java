package turn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import driver.Driver;
import truck.Truck;

public class turnTest {

	private Turn turn;

	private Driver driver;
	private Truck truck;

	@BeforeEach
	void setUp() {
		driver = mock(Driver.class);
		truck = mock(Truck.class);
		turn = new Turn(driver, truck);
	}

	@Test
	void getDriver() {
		assertEquals(driver, turn.getDriver());
	}

	@Test
	void getTruck() {
		assertEquals(truck, turn.getTruck());
	}
}
