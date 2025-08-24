import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the GitHub username: ");
        String username = input.nextLine();

        System.out.print("Enter how many activities to show: ");
        int limit = input.nextInt();
        input.nextLine();

        System.out.print("Enter event type to filter or press enter for all types ");
        String filter = input.nextLine().trim();

        String url = "https://api.github.com/users/" + username + "/events";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());

            if (response.statusCode() == 200) {
                JSONArray events = new JSONArray(response.body());

                int shown = 0;
                for (int i = 0; i < events.length() && shown < limit; i++) {
                    JSONObject event = events.getJSONObject(i);
                    String type = event.getString("type");
                    String repo = event.getJSONObject("repo").getString("name");

                    if (!filter.isEmpty() && !type.equalsIgnoreCase(filter)) {
                        continue;
                    }

                    switch (type) {
                        case "pushevent":
                            int commitCount = event.getJSONObject("payload")
                                    .getJSONArray("commits").length();
                            System.out.println("- Pushed " + commitCount + " commits to " + repo);
                            break;
                        case "issuesevent":
                            String action = event.getJSONObject("payload").getString("action");
                            System.out.println("- " + action + " an issue in " + repo);
                            break;
                        case "watchevent":
                            System.out.println("- Starred " + repo);
                            break;
                        default:
                            System.out.println("- " + type + " on " + repo);
                    }

                    shown++;
                }
            }
            else if (response.statusCode() == 404) {
                System.out.println("User not found: heh? " + username);
            }
            else if (response.statusCode() == 403) {
                System.out.println("Limit exceeded :(");
            }
            else {
                System.out.println("Unexpected error: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
