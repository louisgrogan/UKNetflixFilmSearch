import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Iterator;
import java.util.Scanner;

public class UKNetflixFilmSearch {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//Gets User Search Query
		Scanner myScanner = new Scanner(System.in);
	    System.out.println("Enter film name: ");
	    String filmName = myScanner.nextLine().replaceAll("\\s+","%20");
		
	    //API Search
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://streaming-availability.p.rapidapi.com/search/basic?country=gb&service=netflix&type=movie&keyword=" + filmName + "&language=en"))
				.header("x-rapidapi-host", "streaming-availability.p.rapidapi.com")
				.header("x-rapidapi-key", "5be1ae3b1dmshffee9488265055fp1bbbfejsn217e08aeb271")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject myObject = new JSONObject(response.body());
		JSONArray results = new JSONArray(myObject.getJSONArray("results"));
		
		//Prints Any Search Results (Maximum 8 Due to API)
		if (results.length() > 0) {
			int count = 1;
			System.out.println("Search Results (Max: 8): ");
			System.out.println("");
			
			for (Object result: results) {
				Iterator<String> keys = ((JSONObject) result).keys();
				
				//Prints Film Name
				System.out.print(count + ". ");
				System.out.print(((JSONObject) result).get("originalTitle") + " (");
				
				//Prints Cast List
				JSONArray castList = new JSONArray(((JSONObject) result).getJSONArray("cast"));
				int i=0;
				while (i<castList.length() & i<3) {
					System.out.print(castList.get(i));
					if (i<2 & i<castList.length()-1) {
						System.out.print(", ");
					}
					i++;
				}
				
				System.out.println(")");
				count++;
			}
			
		//Prints Message if no Results Found
		} else {
			System.out.println("No Films Found");
		}
		
	}

}