package driver;

public class Driver {
	private int dni;
	private String name;
	
	public Driver(int dni, String name) {
		this.dni = dni;
		this.name = name;
	}
	
	public int getDni() {
		return dni;
	}
	public String getName() {
		return name;
	}
}
