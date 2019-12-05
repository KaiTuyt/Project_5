# Project 5

Project Five consists of two separate files that use the ideas of the first three projects, and implements them into a user-friendly GUI. The two files, Driver.java and MesoCalculations.java,  produce a way for the GUI to display to the user a Hamming Station's selected distances, as well as finding stations with a similar distance, adding stations, creating random stations, and printing stations that start with a certain character.


### **Problem-Solving Approach**

For this project, we were tasked with creating a program that followed the constraints set by the project's outline. I decided to split the program itself into two different classes: Driver.java, the file that displays the GUI itself, and MesoCalculations.java, the file that calculates all of the required information.

In Driver.java, the program creates the GUI by creating multiple smaller GridPanes and adding them to one final GridPane. The top left part of the GUI holds a Slider with values from 1-4 which represent a desired Hamming Distance to search for, as well as a TextField that displays the chosen distance. Below that there is a ComboBox that has a list of all stations from the provided text file, "Mesonet.txt". In-between these two, there is a Button that, when pressed, displays the stations in ascending order that have a distance equal to the value selected by the Slider for the station chosen in the ComboBox. In the middle/bottom left section of the GUI, there is a Button and a set of TextFields that show the amount of stations with a specific Hamming Distance (ranging from 0-4) compared to the chosen station when the Button is pressed. In addition, at the very bottom of the left side, there is another Button with a TextField that allows for a custom station to be added to the list; however, the station must follow the constraints of how a normal station is formatted (only 4 characters consisting of letters and numbers). On the right side, for our free zone area, I have added a few simple additions to help assist the program, a few of which have been taken from previous projects. At the top, the selected station's index within the list of stations is displayed when the "Calculate HD" Button is pressed. Beneath that, there is a button that, when pressed, adds a station of random uppercase letters and numbers to the list. After that, I have included some of the functions that we accomplished in Project 2; the chosen station's Ascii Average, Letter Average, and the amount of stations that start with the same letter are displayed, as well as a list of said stations.

In MesoCalculations.java, the program handles all of the behind-the-scenes work that is required for it to function. The file reads in the Mesonet file provided, then creates a TreeSet of Strings based on the input received. A TreeSet is used here in order to maintain a sorted list of stations, while also making sure that no duplicates can be added to it. MesoCalculations.java contains functions that find the amount of stations with a similar distance, add stations to the list, return the list of stations (as both a TreeSet AND a String array), find a station's index within the list, create a random station, calculate the ASCII average and the letter average, find the number of stations with the same letter average, and find the amount of stations that start with the same character.

Below is a further explanation of each of the files in depth and their properties.

## **Files**

### Driver

##### **Variables**

`private static final int SLIDER_DEFAULT` - Sets the slider's default value.

`private static final int SLIDER_HIGH` - Sets the slider's maximum value.

`private static final int SLIDER_LOW` - Sets the slider's minimum value.

`private static final int LISTVIEW_HEIGHT = 200` - Sets displayStations' default height.

`private static final int PANE_VGAP = 10` - Sets each GridPane's vertical gap.

`private static final int PANE_HGAP = 20` - Sets each GridPane's horizontal gap.

`private static final Insets SMALL_GRID_PADDING = new Insets(5, 5, 5, 5)` - Sets the amount of padding that each element in each GridPane has (including other GridPanes).

`protected String[] list` - String array that contains the stations provided in Mesonet.txt.

`protected Slider slider` - Slider ranging from 1 to 4 that determines

`protected ComboBox stations` - ComboBox that holds all values from Mesonet.txt. Can be added to with addStationButton or randomButton.

`protected Button showStationButton; protected Button calculateButton; protected Button addStationButton; protected Button randomButton` - Each individual button that accomplishes a different task (should be self-explanatory).

`protected TextField chosenIndex; protected TextField asciiAverage; protected TextField letterAverage; protected TextField numberOfSimilar; protected TextField chosenDistance; protected TextField distance0; protected TextField distance1; protected TextField distance2; protected TextField distance3; protected TextField distance4; protected TextField customStation` - Each individual text field that holds a different value (should be self-explanatory).

`protected ListView displayStations` - ListView that displays all stations that have a similar hamming distance with the selected station (determined by stations & chosenDistance).

`protected Label similarStations; protected Label enterLabel; protected Label value; protected Label compareWith; protected Label freeZone; protected Label index; protected Label asciiLabel; protected Label letterLabel; protected Label similarLabel` - Each individual Label that displays a certain message (most should be self-explanatory).

`protected GridPane majorGridPane; protected GridPane topLeftPane; protected GridPane middleLeftPane; protected GridPane bottomLeftPane; protected GridPane topRightPane; protected GridPane topMiddleRightPane` - Each individual GridPane that the GUI uses. MajorGridPane holds each other GridPane (each other should be self-explanatory).

`protected Scene display` - Scene that holds all components and displays the GUI.

##### **Methods**

`@Override public void start(Stage applicationStage)` - Starts the creation of the GUI. First creates a new MesoCalculations, then reads in the Mesonet file to fill it. The method then creates each GridPane and its' corresponding elements, adding listeners to the Slider and actions to the Buttons when needed. It finished by setting the scene and title, then shows the GUI to the user.

`public static void main(String[] args)` - Simply launches the GUI.

### MesoCalculations

##### **Variables**

`private TreeSet<String> stations` - TreeSet of Strings that holds the stations provided from Mesonet.txt as well as any other stations that may be added later on.

##### **Constructors**

`public MesoCalculations()` - Initializes stations as a new TreeSet of Strings.

##### **Methods**

`public void read(String filename) throws IOException` - Method that takes in info from a file. Creates a BufferedReader and String to parse through the file, as well as an ArrayList of Strings to hold the stations temporarily. Once each station is added, the reader is closed and each string is moved into stations.

`public TreeSet<String> matchingStations(int distance, String chosen)` - Method which returns a TreeSet of stations with a similar distance to the chosen station. Creates a new TreeSet for the matching stations to be held in, then loops through stations to find each station's hamming distance from the chosen station. If the distance is equal to the one specified in the parameters, the station is added to the matching set. After going through the entire list, the matching set is returned.

`public int matchingDistance(int distance, String chosen)` - Method which returns an integer based on the amount of stations which have a desired distance from a chosen station. Creates an integer that holds the amount of matching stations, then loops through stations to find the distance of each station. If the distance between the current station and the chosen station are the same, then the amount of matching stations increments by 1. After going through every station, the amount of matching stations is returned.

`public void addStation(String station)` - Simply adds the given station to the TreeSet.

`public TreeSet<String> getStations()` - Returns the TreeSet of stations.

`public String[] getStationsArray()` - Returns stations, but formatted as a String array instead of a TreeSet.

`public int getStationIndex(String station)` - Returns the index of a chosen station within the list of stations. First creates a String array that can be iterated through, then loops through the array until the station is found. The resulting index is then returned.

`public String createRandomStation()` - Creates a random station and adds it to the list of stations. This method assumes that all stations will be composed of four characters, represented by either capital letters or numbers. Creates an empty String to hold the custom station. The method then goes through a loop where a random number is generated between 1 and 90 until a number that represents a character that complies with our above constraints is met, then converts that number into a character and adds it to the custom string. This is performed 4 times before returning the custom String.

`public int calAverage(String station)` - Method that finds the ASCII average of a station's characters. Similar to the same method from Project 2. Creates a temporary integer array, storing the ASCII ceiling of the station in the first position and the ASCII floor in the second position. After this, the ASCII average is determined by whether or not the average of the ASCII values is rounded up or down, and is filled in the third slot of the array. The third slot is then returned.

`public char letterAverage(String station)` - Method that finds the letter average of a station's characters. Similar to the same method from Project 2. Calls the calAverage method above and converts the result into a character, which is then returned.

`public int numberOfStationWithLetterAvg(char c)` - Finds the amount of stations that start with the same character as a chosen station. Creates a temporary integer to hold the amount of stations with the same starting character, then iterates over the set of stations, incrementing same whenever the starting character of the current station is equal to the desired character. The method the returns the amount of recorded stations with the same starting character.

`public String stationsWithLetterAvg(char c)` - Prints out the stations that start with the same character as a chosen station. Creates a temporary String to hold the output of the method, then iterates over the set of stations, adding to the output the current station whenever its' starting character is equal to the desired character. After this, the memthod returns the String of stations, with each separated by a space. The program prints up to four stations to a line, helping to minimize the amount of space taken up in the GUI. 
