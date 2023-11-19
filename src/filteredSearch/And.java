package filteredSearch;


/**
 * Clase que representa al Operador Logico AND.
 * Extiende la superclase BinaryOperator.
 * Su constructor requiere 2 proposiciones: rightStatement y leftStatement.
 * 
 * @author Gabriela Fascetta
 */
public class And extends BinaryOperator {

	public And(Filter rightStatement, Filter leftStatement) {
		setRightStatement(rightStatement);
		setLeftStatement(leftStatement);
	}

}
