import java.io.*;
import java.util.*;

public class FlightPlanner {
	
	static HashMap<String, ArrayList<String>> listOfFlights = new HashMap<String, ArrayList<String>>();
	static ArrayList<String> originCities = new ArrayList<String>();
	static ArrayList<String> destinationCities = new ArrayList<String>();
	static ArrayList<String> route = new ArrayList<String>();
	static String origin;
	static String destination;
	
	public static void main(String[] args) {
		
		readFile();
		
		System.out.println("Welcome to Flight Planner.");
		System.out.println("Here is a list of all of the cities in our database: ");
		
		listOfOriginCities(originCities);
		
		startingCity();
		
		getNextCity(origin);
		
		printRoute(route);
	}

	static String startingCity() {
		Scanner input = new Scanner(System.in);
		String startingCity;
		
		System.out.println("Please enter a starting city: ");
		startingCity = input.nextLine();
		String currentCity = origin;
		
		while (true) {
		String nextCity = currentCity;
		route.add(nextCity);
		if (nextCity.equals(origin)) 
			break;
		currentCity = nextCity;
		}

		
		route.add(startingCity);
		
		input.close();
		
		return startingCity;
	
	}

	private static void readFile() {
		File fileName = new File("flights.txt");
		BufferedReader br;
		int arrow;
					
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			arrow = line.indexOf("->");

			while (br.readLine() != null) {

				origin = line.substring(0, arrow).trim();
				destination = line.substring(arrow + 2).trim();

				if (!(originCities.contains(origin))) {
					originCities.add(origin);

					ArrayList<String> destinationCities = new ArrayList<>();
					destinationCities.add(destination);
					listOfFlights.put(origin, destinationCities);
				} else {
					if (listOfFlights.containsKey(origin)) {
						listOfFlights.get(origin).add(destination);
					}
				}
			}
			
			defineCity(origin);
			defineCity(destination);
			possibleDestinations(origin).add(destination);
			
			br.close();
		} catch (IOException ex) {
			System.out.println("Input/Output Error.");
		}
	}

	private static void listOfOriginCities(ArrayList<String> originCities) {
		for(int i = 0; i < originCities.size(); i++) {
			String city = originCities.get(i);
			System.out.println(" " + city);
			
		}
	}

	private static ArrayList<String> possibleDestinations(String origin) {
		return listOfFlights.get(origin);
	}
	
	private static void printRoute(ArrayList<String> route) {
		System.out.println("Your route is: ");
		for(int i = 0; i <route.size(); i++) {
			System.out.println(route.get(i) + " -> ");
		}
	}
	
	private static void defineCity(String cityName) {
		if (!originCities.contains(cityName)) {
		originCities.add(cityName);
		listOfFlights.put(cityName, new ArrayList<String>());
		}
	}
	
	private static String getNextCity(String city) {
		Scanner input = new Scanner(System.in);
		
		ArrayList<String> destinations = possibleDestinations(city);
		String nextCity = null;
		
		while (true) {
			System.out.println("From " + city + " , you can fly to:");
			possibleDestinations(city);
			
			System.out.println("Where do you want to go?");
			nextCity = input.nextLine().trim();
		
			if (destinations.contains(nextCity)) 
				break;
		
			if(nextCity == null)
				break;
			
			System.out.println("Cannot get to " + nextCity + " from " + city + ".");
		}
		return nextCity;
	}
}
	
	
	
	
