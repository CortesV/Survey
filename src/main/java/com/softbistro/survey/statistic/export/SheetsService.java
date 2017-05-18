package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
	public String send(List<SurveyStatisticExport> list, List<String> filters)
			throws IOException, GeneralSecurityException {

		if (list.isEmpty()) {
			throw new NoSuchElementException("No content");
		}
		@SuppressWarnings("static-access")
		Sheets sheetsService = googleAuthorization.getSheetsService();

		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(create(list, sheetsService));

		Spreadsheet response = request.execute();

		insertData(response.getSpreadsheetId(), list, filters);

		publicAccess(response.getSpreadsheetId());

		return response.getSpreadsheetUrl();

	}

	/**
	 * Main configure for creating sheets
	 * 
	 * @param list
	 * @param sheetsService
	 * @return
	 */
	public Spreadsheet create(List<SurveyStatisticExport> list, Sheets sheetsService) {
		String title = list.get(0).getName() + " " + new java.util.Date().toString();

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
	@SuppressWarnings("static-access")
	public void insertData(String key, List<SurveyStatisticExport> list, List<String> filters) {
		try {

			SpreadsheetService spreadsheetService = new SpreadsheetService("SurveySoftbistro");

			spreadsheetService.setOAuth2Credentials(googleAuthorization.authorize());

			spreadsheetService.setProtocolVersion(SpreadsheetService.Versions.V3);

			URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "private", "full");

			WorksheetEntry worksheetEntry = spreadsheetService.getFeed(url, WorksheetFeed.class).getEntries().get(0);

			URL celledUrl = worksheetEntry.getCellFeedUrl();

			CellFeed cellFeed = spreadsheetService.getFeed(celledUrl, CellFeed.class);

			List<String> arrHeadersColumn = generateHeadersColumn(cellFeed, filters);

			surveyStatisticExportToMap(list).forEach(item -> {

				ListEntry newRow = new ListEntry();
				arrHeadersColumn.stream().forEach(header -> {
					fillValue(newRow, header, item.get(header));
				});

				try {
					spreadsheetService.insert(worksheetEntry.getListFeedUrl(), newRow);

				} catch (IOException | ServiceException e) {
					LOG.error("Insert data " + e.getMessage());
				}
			});

		} catch (ServiceException | IOException e) {
			LOG.error("Insert data " + e.getMessage());
		}

	}

	private List<Map<String, String>> surveyStatisticExportToMap(List<SurveyStatisticExport> list) {
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		try {
			list.stream().forEach(item -> {
				Map<String, String> statisticMap = new HashMap<String, String>();

				statisticMap.put("SurveyID", item.getId().toString());
				statisticMap.put("SurveyName", item.getName());
				statisticMap.put("ParticipantFirstName", item.getFirstName());
				statisticMap.put("ParticipantLastName", item.getLastName());
				statisticMap.put("QuestionGroupName", item.getGroupName());
				statisticMap.put("QuestionName", item.getQuestionName());
				statisticMap.put("ParticipantID", item.getParticipantId().toString());
				statisticMap.put("Answer", item.getAnswer());
				statisticMap.put("Comment", item.getComment());
				statisticMap.put("AnswerDateAndTime", item.getAnswerDateTime().toString());

				item.getParticipantAttribute().stream().forEach(attr -> statisticMap
						.put(attr.getName().replaceAll("[^\\p{Alpha}\\p{Digit}]+", ""), attr.getValue()));

				listMap.add(statisticMap);
			});

		} catch (Exception e) {
			LOG.error("Prepearing export data " + e.getMessage());
		}

		return listMap;
	}

	private void fillValue(ListEntry newRow, String name, String value) {
		if (value != null) {
			newRow.getCustomElements().setValueLocal(name, value);
		} else {
			newRow.getCustomElements().setValueLocal(name, "");
		}

	}

	public List<String> generateHeadersColumn(CellFeed cellFeed, List<String> filters) {

		CellEntry cellEntry;

		int cell = 1;

		for (String name : filters) {
			cellEntry = new CellEntry(1, cell++, name);

			try {
				cellFeed.insert(cellEntry);
			} catch (ServiceException | IOException e) {
				LOG.error("Generate headers " + e.getMessage());
			}
		}
		return filters;

	}

	private void publicAccess(String fileId) {
		try {
			@SuppressWarnings("static-access")
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