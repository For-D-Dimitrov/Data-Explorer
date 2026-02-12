This program is used to view, edit and calculate various things in a  a .cvs text file, specifically taxi/Uber trip data.

 VIDEO : https://youtu.be/pQmypcjQPkg

Classes:

-Main.java
	This class boots up the GUI

-TripGUI.java
	This is the GUI manager section. It summons all the graphical elements on the screen and decides how they are laid out on the screen.
	It creates a window with three main areas: top panel with buttons (load data, add trip, save dataset, delete trip), middle display area showing all trips in a table format,
	and bottom panel with statistics, search bar, and sort button.
	When you click Load Data, it opens a file chooser to select a cvs file, then calls TripManager to load the data and displays all trips on screen.
	The Add Trip button opens a dialog box with the 7 datas from tripPara.java where you enter new trip data. It validates the input (checks for empty fields, duplicate ID, improper data) before adding the trip.
	The Delete Trip button asks for a Trip ID and removes that trip from the ArrayList if it exists.
	The Save Dataset button opens a file chooser to save the current ArrayList back to a cvsfile (you could technically save it as any file format0.
	Search functionality counts how many trips contain the search term by calling TripManager's search method.
	Sort button shows a menu with the 7 datas from tripPara and sorts the display info.
	Stats at the bottom show total trips, total fare, average fare, total distance, and average rating, all calculated by calling methods from TripManager.
	All display updates happen through refreshDisplay() which calls displayTrips() to show the trip list and updateStats() to update the stats

-TripManager.java 
	This is the data management section. It handles all the operations for trip data.
	It reads cvs files and converts each line into TripPara objects stored in an ArrayList
	The file splits cvs lines by commas, extracting 7 pieces of data 
	(Trip_ID, City, Distance, Duration, Fare, Tip, Rating) and converting them from strings into their intended data types (integers, doubles, strings)
	It performs all calculations like total fare, average fare, total distance, and average rating by looping through the ArrayList
	You can add new trips to the ArrayList, delete trips by their ID, and save the entire ArrayList back to a CSV file
	It also provides search functionality that counts how many trips contain a search term, and sorting functionality that can sortthe ArrayList by any field 
	All methods work on a single ArrayList<TripPara> called tripsArray


-TripPara.java
	This is the data structure template. It defines the format for a single trip object with 7 variables (tripid, city, distance, duration, fare, tip, rating).
	The constructor creates new trip objects by taking 7 parameters and storing them in the private variables.
	It has getter methods to read the data and setter methods to modify the data.
	This class acts as a blueprint - every trip in the program follows this exact structure.

-module-info.java
 

Features
All features have backup responses if something doesnt occur as intended (for example, you put in letters for the rating)
Load CSV files - Import trip data with 7 fields (Trip ID, City, Distance, Duration, Fare, Tip, Rating)
Add trips - Manual entry with validation for incorrect or missing data (the correct trip id is calculated and added for convinience)
Delete trips - Remove by Trip ID (the trip id is calculated and inputed automatically for convinience)
Save to CSV - Export modified data back to file.
Search - Find trips containing any search term and how many times it is found in the file 
Sort - Arrange by any field (ID, city, distance, duration, fare, tip, rating), alphabetically or numerically.
Statistics -  Display of total trips, total/average fare, total distance, average rating (it updates every time a data is updated)




