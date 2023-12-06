package turn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import order.ImportOrder;

public class turnTest {

	private ImportOrder importOrder;
	// ------------------------------------------------------------
	private LocalDateTime date;
	// ------------------------------------------------------------
	private Turn turn;

	@BeforeEach
	void setUp() {
		importOrder = mock(ImportOrder.class);
		// ------------------------------------------------------------
		date = LocalDateTime.of(2023, Month.DECEMBER, 06, 15, 22);
		// ------------------------------------------------------------
		turn = new Turn(date, importOrder);
	}

	@Test
	void getDate_ReturnsCorrectDate() {
	    assertEquals(date, turn.getDate());
	}

	@Test
	void getOrder_ReturnsCorrectOrder() {
	    assertEquals(importOrder, turn.getOrder());
	}

}
