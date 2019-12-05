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
