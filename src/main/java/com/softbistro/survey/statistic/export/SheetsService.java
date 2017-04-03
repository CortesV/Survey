package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

@Service
public class SheetsService {

	/** Application name. */
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	private static final String CLIENT_ID = "266627660621-qi8mcfjpillo59kbgsitkrq4tk4gm2j0.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "W5lplzGPgU7XkyG3Zgw3J-DK";
	private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	public Spreadsheet create() throws IOException, GeneralSecurityException {
		Spreadsheet requestBody = new Spreadsheet();
		Sheets sheetsService = createSheetsService();

		String title = new java.util.Date().toString();
		// ----------configure file---------
		SpreadsheetProperties sp = new SpreadsheetProperties();
		sp.setTitle(title);

		requestBody.setProperties(sp);
		Sheet sheet = new Sheet();

		SheetProperties sheetProperties = new SheetProperties();

		sheetProperties.setTitle("Statstic of survey");

		sheet.setProperties(sheetProperties);
		List<Sheet> sheets = new ArrayList<>();
		sheets.add(sheet);
		// -----------------------------------------------
		requestBody.setSheets(sheets);

		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);

		Spreadsheet response = request.execute();
		insertData(response);

		return response;
	}

	public void insertData(Spreadsheet response) {
		try {

			URL SPREADSHEET_FEED_URL = new URL(
					"http://spreadsheets.google.com/feeds/worksheets/" + response.getSpreadsheetId() + "/private/full");

			SpreadsheetService spreadsheetService = new SpreadsheetService("Test");

			AutorizeGoogle autorizeGoogle = new AutorizeGoogle();
			try {
				spreadsheetService.setOAuth2Credentials(authorize());// (autorizeGoogle.start());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spreadsheetService.useSsl();

			WorksheetEntry worksheetEntry = spreadsheetService.getEntry(SPREADSHEET_FEED_URL, WorksheetEntry.class);
			worksheetEntry.setTitle(new PlainTextConstruct("hello"));
			worksheetEntry.setRowCount(20);
			worksheetEntry.setColCount(20);

			worksheetEntry.update();

			ListEntry row = new ListEntry();
			row.getCustomElements().setValueLocal("firstname", "Joe");
			row.getCustomElements().setValueLocal("lastname", "Smith");
			row.getCustomElements().setValueLocal("age", "26");
			row.getCustomElements().setValueLocal("height", "176");

			row.setAutomaticallyGeneratedTitle(new PlainTextConstruct("dsfds"));
			// row.setAutomaticallyGeneratedContent();
			// -----------------------------------------------

			row.getAdaptedEntry().setTitle(new PlainTextConstruct("hello"));
			spreadsheetService.update(SPREADSHEET_FEED_URL, row);

		} catch (ServiceException | IOException e) {
			e.printStackTrace();
		}

	}

	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = SheetsService.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
				jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE,
						"https://spreadsheets.google.com/feeds", "https://docs.google.com/feeds"))
								.setAccessType("offline").setApprovalPrompt("auto").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	public Sheets createSheetsService() throws IOException, GeneralSecurityException {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

		// TODO: Change placeholder below to generate authentication
		// credentials. See
		// https://developers.google.com/sheets/quickstart/java#step_3_set_up_the_sample
		//
		// Authorize using one of the following scopes:
		// "https://www.googleapis.com/auth/drive"
		// "https://www.googleapis.com/auth/spreadsheets"
		AutorizeGoogle autorizeGoogle = new AutorizeGoogle();
		// try {
		Credential credential = authorize(); // autorizeGoogle.start();

		return new Sheets.Builder(httpTransport, jsonFactory, credential).setApplicationName("Google-SheetsSample/0.1")
				.build();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return null;
		// }
	}
}