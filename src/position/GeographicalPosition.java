package position;

public class GeographicalPosition {
	private double latitude;
	private double longitude;
	
	public GeographicalPosition(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void updateGeographicalPosition(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
