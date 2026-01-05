package project;


/*
03/12/2025
D.D
Function
	TripManager calculates all the different needs of TripGUI. it sorts a file turned into an array. it splits the array into several strings from all
	the stats. It then uses the format of TripPara.java to format it properly and turn those strings into integers 
*/

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TripManager {
	// list to hold all the tripsArray
	private ArrayList<TripPara> tripsArray;

	// constructor to make new array
	public TripManager() {
		tripsArray = new ArrayList<>();
	}

	// ==== FILE OPERATIONS =======
	
	// reads tripsArray from a csv file and loads them into the list
	public void loadFromFile(String filename) throws Exception {
		tripsArray.clear(); // clear out any old data first
		Scanner scanner = new Scanner(new File(filename));
		scanner.nextLine(); // skip the first line cause its just headers
		
		// keep reading lines until there arent any more
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split("[,\t]"); // split by comma or tab
			
			
			if (parts.length >= 7) {
				// parse each part into the right type
				//	parsing means to convert the data to its specific type of data
				// I added ".trim()"  here because csv files data have spaces
				int tripId = Integer.parseInt(parts[0].trim());
				String city = parts[1].trim();
				double distance = Double.parseDouble(parts[2].trim());
				int duration = Integer.parseInt(parts[3].trim());
				double fare = Double.parseDouble(parts[4].trim());
				double tip = Double.parseDouble(parts[5].trim());
				int rating = Integer.parseInt(parts[6].trim());
				
				// create a new trip and add it to the list
				tripsArray.add(new TripPara(tripId, city, distance, duration, fare, tip, rating));
			}
		}
		scanner.close();
	}

	// write back to a csv file
	public void saveToFile(String filename) throws Exception {
		PrintWriter writer = new PrintWriter(filename);
		writer.println("Trip_ID,City,Distance,Duration,Fare,Tip,Rating"); // header
		
		// loop through each trip and write its data
		for (TripPara trip : tripsArray) {
			writer.print(trip.getTripId() + ",");
			writer.print(trip.getCity() + ",");
			writer.print(trip.getDistance() + ",");
			writer.print(trip.getDuration() + ",");
			writer.print(trip.getFare() + ",");
			writer.print(trip.getTip() + ",");
			writer.println(trip.getRating());
		}
		writer.close();
	}
	
	// adds to list
	public void addTrip(TripPara trip) {
		tripsArray.add(trip);
	}

	// deletes a trip by its id
	public boolean deleteTrip(int tripId) {
		for (int i = 0; i < tripsArray.size(); i++) {
			if (tripsArray.get(i).getTripId() == tripId) {
				tripsArray.remove(i);
				return true; // found and deleted
			}
		}
		return false; 
	}	
	
	
	// ======= TRIP MANAGEMENT ====
	// returns array list
	public ArrayList<TripPara> getAlltripsArray() {
		return tripsArray;
	}

	// returns how many trips are in the array
	public int getTotaltripsArray() {
		return tripsArray.size();
	}

	// ======= SEARCH AND VALIDATION =====

	// searches for a term in all the trip data and counts how many tripsArray have it
	public int getSearch(String term) {
		int count = 0;

		
		// go through each trip
		for (TripPara trip : tripsArray) {
			
			if (String.valueOf(trip.getTripId()).contains(term) ||
				trip.getCity().toLowerCase().contains(term) ||
				String.valueOf(trip.getDistance()).contains(term) ||
				String.valueOf(trip.getDuration()).contains(term) ||
				String.valueOf(trip.getFare()).contains(term) ||
				String.valueOf(trip.getTip()).contains(term) ||
				String.valueOf(trip.getRating()).contains(term)) {
				count++;
			}
		}
		return count;
	}

	public boolean tripIdExists(int tripId) {
		for (TripPara trip : tripsArray) {
			if (trip.getTripId() == tripId) {
				return true;
			}
		}
		return false;
	}

	// returns the highest trip id in the list
	// added for convinence when adding or removing data, so that the user doesnt need to manually submit it
	public int getHighestTripId() {
		int highestId = 0;
			
		// compares trip id until it finds the highest
		for (int i = 0; i < tripsArray.size(); i++) {
			if (tripsArray.get(i).getTripId() > highestId) {
				highestId = tripsArray.get(i).getTripId();
			}
		}
			return highestId;
	}

	//========== SORTING =========
	
	// this command makes it easy to add more variables later on, instead of making a separate code for all of them
	// I could have used a switch here but decided not to as this is easier for me
	// i used the sort function from java.util.ArrayList here
	//
	public ArrayList<TripPara> getSort(String field) {
	if (field.equals("tripid")) {
		tripsArray.sort((a, b) -> a.getTripId() - b.getTripId());
	}
			else if (field.equals("city")) {
			tripsArray.sort((a, b) -> a.getCity().compareTo(b.getCity()));
			}		
				else if (field.equals("distance")) {
					tripsArray.sort((a, b) -> Double.compare(a.getDistance(), b.getDistance()));
				} 	
					else if (field.equals("duration")) {
						tripsArray.sort((a, b) -> a.getDuration() - b.getDuration());
					} 
							else if (field.equals("fare")) {
								tripsArray.sort((a, b) -> Double.compare(a.getFare(), b.getFare()));
							} 
								else if (field.equals("tip")) {
									tripsArray.sort((a, b) -> Double.compare(a.getTip(), b.getTip()));
								} 
									else if (field.equals("rating")) {
										tripsArray.sort((a, b) -> a.getRating() - b.getRating());
									}
		return tripsArray;
	}

	// ===== STATISTICS AND CALCULATIONS =========
	
	// adds up all the fares in cv
	public double getTotalFare() {
		double total = 0;
		for (TripPara trip : tripsArray) {
			total += trip.getFare();
		}
		return total;
	}

	// calculates the average fare
	public double getAverageFare() {
		if (tripsArray.size() == 0) {
			return 0; // doesnt allow to divide 0
		}
		return getTotalFare() / tripsArray.size();
	}

	// adds up all distance
	public double getTotalDistance() {
		double total = 0;
		for (TripPara trip : tripsArray) {
			total += trip.getDistance();
		}
		return total;
	}

	// average rating
	public double getAverageRating() {
		if (tripsArray.size() == 0) {
			return 0; 
		}
		double total = 0;
		for (TripPara trip : tripsArray) {
			total += trip.getRating();
		}
		return total / tripsArray.size();
	}
}