package client;

public abstract class Client {

	private int dni;
	private String name;
	
	public Client(int dni, String name) {
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
