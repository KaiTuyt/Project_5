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
				if (chosen.charAt(i) == station.charAt(i)) {
					count++;
				}
			}
			if (count == distance)
				matching.add(station);
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

}