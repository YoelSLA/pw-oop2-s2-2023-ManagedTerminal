package client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsigneeTest {

	private Consignee consignee;

	@BeforeEach
	void setUp() {
		consignee = new Consignee("12567890", "Raul Gonzalez");
	}

	@Test
	void getDni() {
		assertEquals("12567890", consignee.getDni());
	}

	@Test
	void getName() {
		assertEquals("Raul Gonzalez", consignee.getName());
	}

}
