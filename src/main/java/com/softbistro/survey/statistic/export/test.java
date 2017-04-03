package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

@Service
public class test {
	public void start() throws AuthenticationException, MalformedURLException, IOException, ServiceException {

		SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration-v1");

		// TODO: Authorize the service object for a specific user (see other
		// sections)

		SheetsService sheetsService = new SheetsService();

		AutorizeGoogle autorizeGoogle = new AutorizeGoogle();
		try {
			service.setOAuth2Credentials(autorizeGoogle.start());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Define the URL to request. This should never change.
		URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/public/full");

		// Make a request to the API and get all spreadsheets.
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		if (spreadsheets.size() == 0) {
			// TODO: There were no spreadsheets, act accordingly.
		}

		// TODO: Choose a spreadsheet more intelligently based on your
		// app's needs.
		SpreadsheetEntry spreadsheet = spreadsheets.get(0);
		System.out.println(spreadsheet.getTitle().getPlainText());

		// Get the first worksheet of the first spreadsheet.
		// TODO: Choose a worksheet more intelligently based on your
		// app's needs.
		WorksheetFeed worksheetFeed = service.getFeed(spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		WorksheetEntry worksheet = worksheets.get(0);

		// Fetch the list feed of the worksheet.
		URL listFeedUrl = worksheet.getListFeedUrl();
		ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

		// TODO: Choose a row more intelligently based on your app's needs.
		ListEntry row = listFeed.getEntries().get(0);

		// Update the row's data.
		row.getCustomElements().setValueLocal("firstname", "Sarah");
		row.getCustomElements().setValueLocal("lastname", "Hunt");
		row.getCustomElements().setValueLocal("age", "32");
		row.getCustomElements().setValueLocal("height", "154");

		// Save the row using the API.
		row.update();
	}
}
