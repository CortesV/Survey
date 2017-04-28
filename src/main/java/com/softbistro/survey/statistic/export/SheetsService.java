package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.softbistro.survey.statistic.component.entity.StatisticColumnFilter;

@Service
public class SheetsService {

	@Autowired
	private GoogleAuthorization googleAuthorization;
	
	@Autowired
	private StatisticColumnFilter statisticColumnFilter;

	private static final Logger LOG = Logger.getLogger(SheetsService.class);

	/**
	 * Creating and configure new sheets
	 * 
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public String send(List<Map<String, Object>> list, List<String> filters)
			throws IOException, GeneralSecurityException {

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
	public Spreadsheet create(List<Map<String, Object>> list, Sheets sheetsService) {
		String title = list.get(0).get("survey_name") + " " + new java.util.Date().toString();

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
	public void insertData(String key, List<Map<String, Object>> list, List<String> filters) {
		try {

			SpreadsheetService spreadsheetService = new SpreadsheetService("SurveySoftbistro");

			spreadsheetService.setOAuth2Credentials(googleAuthorization.authorize());

			spreadsheetService.setProtocolVersion(SpreadsheetService.Versions.V3);

			URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "private", "full");

			WorksheetEntry worksheetEntry = spreadsheetService.getFeed(url, WorksheetFeed.class).getEntries().get(0);

			URL celledUrl = worksheetEntry.getCellFeedUrl();

			CellFeed cellFeed = spreadsheetService.getFeed(celledUrl, CellFeed.class);

			List<String> arrHeadersColumn = generateHeadersColumn(cellFeed, filters, list);

			ListEntry newRow = new ListEntry();

			Boolean isAttributeNext = false;

			for (int numberOfRecord = 0; numberOfRecord < list.size(); numberOfRecord++) {

				for (int column = 0; column < filters.size(); column++) {
					String header = arrHeadersColumn.get(column);

					if (!statisticColumnFilter.getFilterList().contains(header)){
						isAttributeNext = true;}
					
				
					if (statisticColumnFilter.getFilterList().contains(header) & !isAttributeNext) {

						fillValue(newRow, header, String.valueOf(list.get(numberOfRecord)
								.get(new StatisticColumnFilter().getFiltersMap().get(filters.get(column)))));
						

						
					}

					else {

						header = (list.get(numberOfRecord).get("group_name").toString()
								+ list.get(numberOfRecord).get("attribute").toString())
										.replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");

						fillValue(newRow, header, String.valueOf(list.get(numberOfRecord).get("attribute_value")));

					}
				}
				if ((!((numberOfRecord == 0)
						|| (numberOfRecord > 0 && (String.valueOf(list.get(numberOfRecord).get("answer_id"))
								.equals(String.valueOf(list.get(0).get("answer_id")))))
						|| (numberOfRecord > 0 && (String.valueOf(list.get(numberOfRecord).get("answer_id"))
								.equals(String.valueOf(list.get(numberOfRecord - 1).get("answer_id"))))))
						|| !((String.valueOf(list.get(numberOfRecord).get("answer_id"))
								.equals(String.valueOf(list.get(numberOfRecord + 1).get("answer_id"))))))) {
					
					spreadsheetService.insert(worksheetEntry.getListFeedUrl(), newRow);
					
					isAttributeNext = false;
					newRow = new ListEntry();
				}

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

	public List<String> generateHeadersColumn(CellFeed cellFeed, List<String> filters, List<Map<String, Object>> list) {
		List<String> arrNames = new LinkedList<>();

		try {

			for (String filter : filters) {

					arrNames.add(filter);
				}
			

			CellEntry cellEntry;

			int cell = 1;
			for (String name : arrNames) {

				cellEntry = new CellEntry(1, cell++, name);
				cellFeed.insert(cellEntry);
			}

		} catch (ServiceException | IOException e) {
			LOG.error("Generate headers " + e.getMessage());
		}
		return arrNames;
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