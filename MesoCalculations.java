import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class MesoCalculations {
	
	private TreeSet<String> stations;
	
	public MesoCalculations() {
		stations = new TreeSet<>();
	}

	public void read(String filename) throws IOException
    {
    	BufferedReader read = new BufferedReader(new FileReader(filename));
    	String line = read.readLine();
    	ArrayList<String> array = new ArrayList<>();
    	while (line != null) {
    		array.add(line);
        	line = read.readLine();
    	}
    	read.close();
    	for (String s: array) {
    		stations.add(new String(s));
    	}
    }
	
	public TreeSet<String> matchingStations(int distance, String chosen) {
		TreeSet<String> matching = new TreeSet<>();
		for (String station: stations) {
			int count = 0;
			for (int i = 0; i < station.length(); i++) {
				if (chosen.charAt(i) != station.charAt(i)) {
					count++;
				}
			}
			if (count == distance)
				matching.add(station);
		}
		return matching;
	}
	
	public int matchingDistance(int distance, String chosen) {
		int matching = 0;
		for (String station: stations) {
			int count = 0;
			for (int i = 0; i < station.length(); i++) {
				if (chosen.charAt(i) != station.charAt(i)) {
					count++;
				}
			}
			if (count == distance)
				matching++;
		}
		return matching;
	}
	
	public void addStation(String station) {
		stations.add(station);
	}
	
	public TreeSet<String> getStations() {
		return stations;
	}
	
	public String[] getStationsArray() {
		String[] array = new String[stations.size()];
		Object[] arrayObjects = stations.toArray();
		for (int i = 0; i < stations.size(); i++) {
			array[i] = arrayObjects[i].toString();
		}
		return array;
	}
	
	public int getStationIndex(String station) {
		String[] array = this.getStationsArray();
		int result = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(station)) {
				result = i;
				break;
			}
		}
		return result;
	}
	
	public String createRandomStation() {
		String custom = "";
		//Starts at 48, ends at 57; 10 total
		//Starts at 65, ends at 90; 26 total
		for (int i = 0; i < 4; i++) {
			int number = -1;
			while (number != 48 && number != 49 && number != 50 && number != 51 && number != 52 && number != 53 && 
					number != 54 && number != 55 && number != 56 && number != 57 && number != 65 && number != 66 && 
					number != 67 && number != 68 && number != 69 && number != 70 && number != 71 && number != 72 && 
					number != 73 && number != 74 && number != 75 && number != 76 && number != 77 && number != 78 && 
					number != 79 && number != 80 && number != 81 && number != 82 && number != 83 && number != 84 && 
					number != 85 && number != 86 && number != 87 && number != 88 && number != 89 && number != 90) {
				number = (int)(Math.random() * 90 + 1);
			}
			char character = (char)number;
			custom += character;
		}
		return custom;
	}
	
	public int calAverage(String station) {
		int[] cal = new int[3];
		cal[0] = ((int)Math.ceil((station.charAt(0) + station.charAt(1) + station.charAt(2) + station.charAt(3)) / 4.0));
		cal[1] = ((station.charAt(0) + station.charAt(1) + station.charAt(2) + station.charAt(3)) / 4);
		if (((station.charAt(0) + station.charAt(1) + station.charAt(2) + station.charAt(3)) / 4.0) % 1.0 >= 0.5)
			cal[2] = cal[0];
		else cal[2] = cal[1];
		return cal[2];
	}

}