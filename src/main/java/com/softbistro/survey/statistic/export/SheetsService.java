package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;

@Service
public class SheetsService {

	@Autowired
	private GoogleAuthorization googleAuthorization;

	private static final Logger LOG = Logger.getLogger(SheetsService.class);

	/**
	 * Creating and configure new sheets
	 * 
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public String send(List<SurveyStatisticExport> statistic) throws IOException, GeneralSecurityException {

		Sheets sheetsService = googleAuthorization.getSheetsService();

		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(create(statistic, sheetsService));

		Spreadsheet response = request.execute();

		insertData(response.getSpreadsheetId(), statistic);

		publicAccess(response.getSpreadsheetId());

		return response.getSpreadsheetUrl();

	}

	/**
	 * Main configure for creating sheets
	 * 
	 * @param statistic
	 * @param sheetsService
	 * @return
	 */
	public Spreadsheet create(List<SurveyStatisticExport> statistic, Sheets sheetsService) {
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
	public void insertData(String key, List<SurveyStatisticExport> statistic) {
		try {

			SpreadsheetService spreadsheetService = new SpreadsheetService("SurveySoftbistro");

			spreadsheetService.setOAuth2Credentials(googleAuthorization.authorize());

			spreadsheetService.setProtocolVersion(SpreadsheetService.Versions.V3);

			URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "private", "full");

			WorksheetEntry worksheetEntry = spreadsheetService.getFeed(url, WorksheetFeed.class).getEntries().get(0);

			URL celledUrl = worksheetEntry.getCellFeedUrl();

			CellFeed cellFeed = spreadsheetService.getFeed(celledUrl, CellFeed.class);

			List<String> arrHeadersColumn = generateHeadersColumn(cellFeed);

			ListEntry newRow = new ListEntry();

			int column = 0;
			for (int numberOfRecord = 0; numberOfRecord < statistic.size(); numberOfRecord++) {

				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getId().toString());
				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getName());
				fillValue(newRow, arrHeadersColumn.get(column++),
						statistic.get(numberOfRecord).getParticipantId().toString());
				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getFirstName());
				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getLastName());

				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getGroupName());
				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getQuestionName());
				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getAnswer());

				fillValue(newRow, arrHeadersColumn.get(column++), statistic.get(numberOfRecord).getComment());
				fillValue(newRow, arrHeadersColumn.get(column),
						statistic.get(numberOfRecord).getAnswerDateTime().toString());
				spreadsheetService.insert(worksheetEntry.getListFeedUrl(), newRow);

				column = 0;
			}
		} catch (ServiceException | IOException e) {
			LOG.error("Insert data " + e.getMessage());
		}

	}

	private void fillValue(ListEntry newRow, String name, String value) {
		if (value != null) {
			newRow.getCustomElements().setValueLocal(name, value);
		} else {
			newRow.getCustomElements().setValueLocal(name, "");
		}

	}

	public List<String> generateHeadersColumn(CellFeed cellFeed) {
		List<String> arrNames = new LinkedList<>();

		try {
			arrNames.add("SurveyId");
			arrNames.add("SurveyName");
			arrNames.add("ParticipantId");
			arrNames.add("FirstName");
			arrNames.add("LastName");
			arrNames.add("GroupName");
			arrNames.add("QuestionName");
			arrNames.add("Answer");
			arrNames.add("Comment");
			arrNames.add("AnswerData");

			CellEntry cellEntry;

			int cell = 1;
			for (String name : arrNames) {

				cellEntry = new CellEntry(1, cell++, name);
				cellFeed.insert(cellEntry);
			}

		} catch (ServiceException | IOException e) {
			LOG.error("Generate headers " + e.getMessage());
			e.printStackTrace();
		}
		return arrNames;
	}

	private void publicAccess(String fileId) {
		try {
			Drive service = googleAuthorization.getDriveService();
			Permission newPermission = new Permission();
			newPermission.setType("anyone");
			newPermission.setRole("writer");
			service.permissions().create(fileId, newPermission).execute();
		} catch (IOException e) {
			LOG.error("Public access " + e.getMessage());
		}
	}

}