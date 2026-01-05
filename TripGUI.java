
/*
 * 
 * 03/12/2025
 *  D.D
 *  
 * 
 * 
 *  Function
This is the GUI manager section. it summons all the graphical elements on the screen,
 and decides how they are layed out.
  it also calls calculation functions from tripmanager.java
 


*/




/*GUI graphics
 ----------------todo
delete [x]    
edit trips 
filter by city or rating instead of just text search [x]
sort trips by distance, fare, or date [x]
add date/time field to trips so you can track when they happened
highlight search results in the display area 
add more stats like longest trip, highest tip, etc [x]

 */
package project;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;


public class TripGUI extends JFrame {
	
	// calls data 
	private TripManager tripManager;
	
	
	private JTextArea displayArea;
	private JLabel statsLabel;
	private JTextField searchField;
	
	// runs when opening new gui window
	public TripGUI() {
		tripManager = new TripManager();
		setupGUI();
	}
	
	// ========= GUI SETUP METHODS ========
	
	// 
	private void setupGUI() {
		setTitle("Data Explorer");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(createTopPanel(), BorderLayout.NORTH);
		add(createDisplayArea(), BorderLayout.CENTER);
		add(createBottomPanel(), BorderLayout.SOUTH);
		
		setVisible(true); // make window visible
	}
	
	// makes the button panel at top with all buttons
	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.add(createButton("Load Data", click -> loadData()));
		topPanel.add(createButton("Add Trip", click -> addTrip()));
		topPanel.add(createButton("Save Dataset", click -> saveData()));
		topPanel.add(createButton("Delete Trip", click -> deleteTrip()));
		return topPanel;
	}
	
	// makes the middle area where trips show up
	private JScrollPane createDisplayArea() {
		displayArea = new JTextArea();
		displayArea.setEditable(false); // stops typing
		return new JScrollPane(displayArea);
	}
	
	// makes the bottom panel with stats, search bar and sort
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
		
		statsLabel = new JLabel("Statistics: N/A");
		bottomPanel.add(statsLabel);

		JPanel searchPanel = new JPanel();
		searchPanel.add(new JLabel("Search"));
		searchField = new JTextField(15);
		searchPanel.add(searchField);
		searchPanel.add(createButton("Search", click -> search()));
		bottomPanel.add(searchPanel);
		
		JPanel sortPanel = new JPanel();
		JButton sortButton = new JButton("Sort");
		sortButton.addActionListener(click -> sortTrips());
		sortPanel.add(sortButton);
		bottomPanel.add(sortPanel);
		
		return bottomPanel;
	}
	
	// shortens sequence
	private JButton createButton(String text, java.awt.event.ActionListener action) {
		JButton button = new JButton(text);
		button.addActionListener(action);
		return button;
	}
	
	// ======== FILE OPERATIONS =========
	
	private void loadData() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				tripManager.loadFromFile(file.getAbsolutePath());
				refreshDisplay();
				showMessage(tripManager.getTotaltripsArray() + " trips found!");
			} catch (Exception ex) {
				showMessage("Unable to load: " + ex.getMessage());
			}
		}
	}
	
	// saving csv file
	private void saveData() {
		if (tripManager.getTotaltripsArray() == 0) {
			showMessage("ERROR: No data found to save.");
			return;
		}
		
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(this);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				tripManager.saveToFile(file.getAbsolutePath());
				showMessage("File saved succesfully!");
			} catch (Exception x) {
				showMessage("ERROR: Cannot save file " + x.getMessage());
			}
		}
	}
	
	// ========= TRIP OPERATION =====
	
	// click add trip button
	private void addTrip() {
		int highestId = tripManager.getHighestTripId();
		JTextField tripIdField = new JTextField("" + (highestId + 1));
		JTextField cityField = new JTextField();
		JTextField distanceField = new JTextField();
		JTextField durationField = new JTextField();
		JTextField fareField = new JTextField();
		JTextField tipField = new JTextField();
		JTextField ratingField = new JTextField();

		JPanel panel = new JPanel(new GridLayout(10, 5, 5, 5));
		panel.add(new JLabel("Trip ID:"));
		panel.add(tripIdField);
		panel.add(new JLabel("City:"));
		panel.add(cityField);
		panel.add(new JLabel("Distance (km):"));
		panel.add(distanceField);
		panel.add(new JLabel("Duration (min):"));
		panel.add(durationField);
		panel.add(new JLabel("Fare (€):"));
		panel.add(fareField);
		panel.add(new JLabel("Tip (€):"));
		panel.add(tipField);
		panel.add(new JLabel("Rating (1-5):"));
		panel.add(ratingField);

		int result = JOptionPane.showConfirmDialog(this, panel, "Add New Trip",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			try {
				// get all data in text first
				String city = cityField.getText().trim();
				String tripIdText = tripIdField.getText().trim();
				String distanceText = distanceField.getText().trim();
				String durationText = durationField.getText().trim();
				String fareText = fareField.getText().trim();
				String tipText = tipField.getText().trim();
				String ratingText = ratingField.getText().trim();
				
				// check if any are empty
				if (city.isEmpty() || tripIdText.isEmpty() || distanceText.isEmpty() ||
					durationText.isEmpty() || fareText.isEmpty() || tipText.isEmpty() || ratingText.isEmpty())  {
					showMessage("ERROR: Please fill in all fields.");
					return;
				}

				// convert to numbers (they they strings otherwise and mathematical equations
				// would not work
				int tripId = Integer.parseInt(tripIdText);
				double distance = Double.parseDouble(distanceText);
				int duration = Integer.parseInt(durationText);
				double fare = Double.parseDouble(fareText);
				double tip = Double.parseDouble(tipText);
				int rating = Integer.parseInt(ratingText);

				if (rating < 1 || rating > 5) {
					showMessage("ERROR: The rating must be 1-5");
					return;
				}

				if (tripManager.tripIdExists(tripId)) {
					showMessage("ERROR: Duplicate trip ID, try again.");
					return;
				}

				TripPara newTrip = new TripPara(tripId, city, distance, duration, fare, tip, rating);
				tripManager.addTrip(newTrip);
				refreshDisplay(); // update screen with new trip
				showMessage("Trip added succesfully!");
			} catch (NumberFormatException ex) {
				showMessage("ERROR: Please only use numbers.");
			}
		}
	}
	
	// deletes a trip by id
	private void deleteTrip() {
		// check if we have trips
		if (tripManager.getTotaltripsArray() == 0) {
			showMessage("ERROR: No trips to delete.");
			return;
		}

		int highestId = tripManager.getHighestTripId();
		// ask for trip id
		String input = JOptionPane.showInputDialog(this, "Enter Trip ID to delete:", highestId);

		if (input == null || input.trim().isEmpty()) {
			showMessage("ERROR: Please enter a trip ID.");
			return;
		}

		try {
			int tripId = Integer.parseInt(input.trim());
			// try to delete it
			boolean deleted = tripManager.deleteTrip(tripId);

			if (deleted) {
				refreshDisplay(); // update screen after deleting
				showMessage("Trip deleted successfully!");
			} else { // if it doesnt exist
				showMessage("ERROR: Trip ID not found.");
			}
		} catch (NumberFormatException x) {
			showMessage("ERROR: Please only use numbers for trip ID.");
		}
	}
	
	// ======= SEARCH AND SORT =========
	
	// 
	private void search() {
		String searchTerm = searchField.getText().trim();
		
		if (searchTerm.isEmpty()) { // make sure they typed something
			showMessage("ERROR: No letter/numbers detected.");
			return;
		}
		
		// count how many times the word appears in the file
	
		int count = tripManager.getSearch(searchTerm);
		showMessage("Sucessfully found \"" + searchTerm + "\" " + count + 
					" time(s) in the dataset");
	}
	
	private void sortTrips() {
		String[] options = {"tripid", "city", "distance", "duration", "fare", "tip", "rating"};
		
		String choice = (String) JOptionPane.showInputDialog(this, "Sort by:", "Sort Trips",
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		
			tripManager.getSort(choice);
			refreshDisplay();
		}
	
	// ======= DISPLAY AND UPDATE =========
	
	// puts all the trips in the text box
	private void displayTrips() {
		displayArea.setText(""); // wipe field
		ArrayList<TripPara> allTrips = tripManager.getAlltripsArray();
		
		
		displayArea.append("Trip ID\tCity\tDistance\tDuration\tFare\tTip\tRating\n");
		displayArea.append("-------------------------------------------------------------------------------------------------------------------------------------------------\n");
		
		// go through each trip and add it with tabs, so that it is formatted properly.
		// without it, they will not be the same length and it will be difficult to read
		for (int i = 0; i < allTrips.size(); i++) {
			TripPara trip = allTrips.get(i);
			displayArea.append(trip.getTripId() + "\t" + 
							  	trip.getCity() + "\t" + 
							  		trip.getDistance() + "km\t" + 
							  			trip.getDuration() + " minute(s)\t" + 
							  				"€" + trip.getFare() + "\t" + 
							  					"€" + trip.getTip() + "\t" + 
							  						trip.getRating() + "\n");
			
		}
	}
	
	// updates the stats label with totals and averages
	private void updateStats() {
		if (tripManager.getTotaltripsArray() == 0) {
			statsLabel.setText("Statistics: N/A");
			return;
		}
		
		// get all the numbers from trip manager
		int totalTrips = tripManager.getTotaltripsArray();
		double totalFare = tripManager.getTotalFare();
		double avgFare = tripManager.getAverageFare();
		double totalDistance = tripManager.getTotalDistance();
		double avgRating = tripManager.getAverageRating();
		
		// rounded numbers to 2 deci
		String stats = "Trips: " + totalTrips + 
				" | Total Fare: € " + String.format("%.2f", totalFare) + 
					" | Avg Fare: € " + String.format("%.2f", avgFare) + 
						" | Total Distance: " + String.format("%.2f", totalDistance) + "km" +
							" | Avg Rating: " + String.format("%.2f", avgRating);
		
		statsLabel.setText(stats);
	}
	// =========== UTILITY ======
	//shortens command
	private void refreshDisplay() {
		displayTrips(); // show the trips on screen
		updateStats(); // update the numbers at bottom
	}
	
	// shortens command
	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
}

