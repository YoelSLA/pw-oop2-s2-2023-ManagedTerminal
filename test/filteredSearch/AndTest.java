package filteredSearch;
import filteredSeach.And;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AndTest {
	
	private boolean leftStatement = true;
	private boolean rightStatement = true;
	
	/*
	@BeforeEach
	void setUp() {
	}
	*/

	@Test
	void test() {
		assertEquals(leftStatement, rightStatement);
	}

}
