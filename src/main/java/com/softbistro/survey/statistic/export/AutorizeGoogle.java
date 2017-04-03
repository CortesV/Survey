package com.softbistro.survey.statistic.export;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

@Service
public class AutorizeGoogle {

	private static final String CLIENT_ID = "266627660621-qi8mcfjpillo59kbgsitkrq4tk4gm2j0.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "W5lplzGPgU7XkyG3Zgw3J-DK";
	private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	public GoogleCredential start() throws Exception {

		// if
		// (CLIENT_ID.equals("266627660621-qi8mcfjpillo59kbgsitkrq4tk4gm2j0.apps.googleusercontent.com")
		// || CLIENT_SECRET.equals("W5lplzGPgU7XkyG3Zgw3J-DK")) {
		// throw new RuntimeException("TODO: Get client ID and SECRET from
		// https://cloud.google.com/console");
		// }

		// get credentials similar to Java DrEdit example
		// https://developers.google.com/drive/examples/java
		// 4/R9kdUE_6e3JeHHCTqOPb3j9xxB7JB7UlEyAqFpUetLM
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
				jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE,
						"https://spreadsheets.google.com/feeds", "https://docs.google.com/feeds"))
								.setAccessType("online").setApprovalPrompt("auto").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
		System.out.println("Please open the following URL in your " + "browser then type the authorization code:");
		System.out.println("  " + url);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();

		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
		GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
		test(credential);

		return credential;
	}

	public void test(GoogleCredential credential) { // create the service and
													// pass it the credentials
													// you created earlier
		SpreadsheetService service = new SpreadsheetService("MyAppNameHere");
		service.setOAuth2Credentials(credential);

		// Define the URL to request. This should never change.
		URL SPREADSHEET_FEED_URL;
		try {
			SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

			// Make a request to the API and get all spreadsheets.
			SpreadsheetFeed feed;

			feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);

			List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = feed.getEntries();

			// Iterate through all of the spreadsheets returned
			for (com.google.gdata.data.spreadsheet.SpreadsheetEntry spreadsheet : spreadsheets) {
				// Print the title of this spreadsheet to the screen
				System.out.println(spreadsheet.getTitle().getPlainText());
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}