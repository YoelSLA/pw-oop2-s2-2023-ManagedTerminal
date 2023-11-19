package filteredSearch;


/**
 * Clase que representa al Operador Logico OR.
 * Extiende la superclase BinaryOperator.
 * Su constructor requiere 2 proposiciones: rightStatement y leftStatement.
 * 
 * @author Gabriela Fascetta
 */
public class Or extends BinaryOperator {

	public Or(Filter rightStatement, Filter leftStatement) {
		setRightStatement(rightStatement);
		setLeftStatement(leftStatement);
	}

}
