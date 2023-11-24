package driver;

public class Driver {
	private String dni;
	private String name;
	
	public Driver(String dni, String name) {
		this.dni = dni;
		this.name = name;
	}
	
	public String getDni() {
		return dni;
	}
	public String getName() {
		return name;
	}
}
