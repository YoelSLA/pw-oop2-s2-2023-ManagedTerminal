package position;

public class Position {

	private double latitude;
	private double longitude;

	public Position(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static Integer distanceInKilometersBetween(Position positionOne, Position positionTwo) {

		// Radio de la Tierra en kilómetros.
		final Integer RADIO = 6371;

		// Se convierte las coordenadas en grados radianes.
		final Double LATITUDE_ONE_RADIAN = Math.toRadians(positionOne.getLatitude());
		final Double LONGITUDE_ONE_RADIAN = Math.toRadians(positionOne.getLongitude());
		final Double LATITUDE_TWO_RADIAN = Math.toRadians(positionTwo.getLatitude());
		final Double LONGITUDE_TWO_RADIAN = Math.toRadians(positionTwo.getLongitude());

		// Se obtiene la diferencia entre las coordenadas.
		final Double DIFFERENCE_LATITUDE = LATITUDE_TWO_RADIAN - LATITUDE_ONE_RADIAN;
		final Double DIFFERENCE_LONGITUDE = LONGITUDE_TWO_RADIAN - LONGITUDE_ONE_RADIAN;

		// Se resuelve la fórmula de la distancia de haversine.
		final Double SIN_SQUARED_HALF_DELTA = calculateSinSquaredHalfDelta(DIFFERENCE_LATITUDE, DIFFERENCE_LONGITUDE,
				LATITUDE_ONE_RADIAN, LATITUDE_TWO_RADIAN);
		final Double ANGULAR_DISTANCE = calculateAngularDistance(SIN_SQUARED_HALF_DELTA);

		// Redondear a dos decimales
		return (int) Math.round(RADIO * ANGULAR_DISTANCE);
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setPosition(Double latitude, Double longitude) {
		setLatitude(latitude);
		setLongitude(longitude);
	}

	private static Double calculateSinSquaredHalfDelta(Double differenceLatitude, Double differenceLongitude,
			Double latitudeOne, Double latitudeTwo) {
		return Math.sin(differenceLatitude / 2) * Math.sin(differenceLatitude / 2)
				+ Math.cos(Math.toRadians(latitudeOne)) * Math.cos(Math.toRadians(latitudeTwo))
						* Math.sin(differenceLongitude / 2) * Math.sin(differenceLongitude / 2);
	}

	private static Double calculateAngularDistance(Double sinSquaredHalfDelta) {
		return 2 * Math.atan2(Math.sqrt(sinSquaredHalfDelta), Math.sqrt(1 - sinSquaredHalfDelta));
	}

}
