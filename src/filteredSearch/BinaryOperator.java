package filteredSearch;

/**
 * Clase que representa un Operador Logico Binario.
 * 
 * Un operador binario requiere 2 proposiciones: rightStatement y leftStatement.
 * 
 * @author Gabriela Fascetta
 */
public class BinaryOperator implements Filter{
	
	private Filter rightStatement;
	private Filter leftStatement;
	
	public BinaryOperator() {}
		
	public void setRightStatement(Filter filter) {
		this.rightStatement = filter;
	}
	
	public void setLeftStatement(Filter filter) {
		this.leftStatement = filter;
	}
}
