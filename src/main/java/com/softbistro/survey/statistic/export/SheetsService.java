package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;

@Resource
public class SheetsService {

	@Autowired
	private GoogleAuthorization googleAuthorization;

	/**
	 * Creating and configure new sheets
	 * 
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Spreadsheet sendGoogleSheets(List<SurveyStatisticExport> statistic)
			throws IOException, GeneralSecurityException {

		Sheets sheetsService = googleAuthorization.getSheetsService();

		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets()
				.create(createSheets(statistic, sheetsService));

		Spreadsheet response = request.execute();

		insertMainRows(response.getSpreadsheetId());

		return response;

	}

	/**
	 * Main configure for creating sheets
	 * 
	 * @param statistic
	 * @param sheetsService
	 * @return
	 */
	public Spreadsheet createSheets(List<SurveyStatisticExport> statistic, Sheets sheetsService) {
		String title = statistic.get(0).getName() + new java.util.Date().toString();

		Spreadsheet requestBody = new Spreadsheet();

		SpreadsheetProperties sp = new SpreadsheetProperties();
		sp.setTitle(title);

		requestBody.setProperties(sp);

		SheetProperties sheetProperties = new SheetProperties();
		sheetProperties.setTitle("Statstic of survey");

		Sheet sheet = new Sheet();
		sheet.setProperties(sheetProperties);

		List<Sheet> sheets = new ArrayList<>();
		sheets.add(sheet);

		requestBody.setSheets(sheets);

		return requestBody;
	}

	/**
	 * Insert heders name column for created google sheet
	 * 
	 * @param key
	 */
	public void insertMainRows(String key) {
		try {

			SpreadsheetService service1 = new SpreadsheetService("Survey Softbistro");

			service1.setOAuth2Credentials(googleAuthorization.authorize());

			service1.setProtocolVersion(SpreadsheetService.Versions.V3);

			URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "private", "full");

			WorksheetEntry worksheetEntry;
			worksheetEntry = service1.getFeed(url, WorksheetFeed.class).getEntries().get(0);

			URL celledUrl = worksheetEntry.getCellFeedUrl();

			CellFeed cellFeed = service1.getFeed(celledUrl, CellFeed.class);

			CellEntry cellEntry = new CellEntry(1, 1, "Survey id");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 2, "Survey name");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 3, "Participant id");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 4, "First name");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 5, "Last name");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 6, "Group name");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 7, "Question name");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 8, "Answer");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 9, "Comment");
			cellFeed.insert(cellEntry);

			cellEntry = new CellEntry(1, 10, "Answer data");
			cellFeed.insert(cellEntry);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

}