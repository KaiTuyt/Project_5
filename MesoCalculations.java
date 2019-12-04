import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class MesoCalculations {
	
	TreeSet<MesoStation> stations;
	
	public MesoCalculations() {
		stations = new TreeSet<>();
	}

	public void read(String filename) throws IOException
    {
    	BufferedReader read = new BufferedReader(new FileReader(filename));
    	String line = read.readLine();
    	while (line != null) {
    		stations.add(new MesoStation(line.toUpperCase()));
        	line = read.readLine();
    	}
    	read.close();
    }
	
	public TreeSet<MesoStation> matchingStations(int distance, MesoStation chosen) {
		TreeSet<MesoStation> matching = new TreeSet<>();
		for (MesoStation station: stations) {
			int count = 0;
			for (int i = 0; i < station.getStID().length(); i++) {
				if (chosen.getStID().charAt(i) == station.getStID().charAt(i)) {
					count++;
				}
			}
			if (count == distance)
				matching.add(station);
		}
		return matching;
	}
	
}