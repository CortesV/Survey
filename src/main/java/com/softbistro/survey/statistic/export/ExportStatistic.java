package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class ExportStatistic {
	public void exportStatistikToSheets() throws IOException, GeneralSecurityException {
		// The ID of the spreadsheet to update.
		String spreadsheetId = "1a9lrqhTC47kwp_59g-fnQD5bVwOaCaIQakXOQ9AGjgQ"; // TODO:
																				// Update
																				// placeholder
																				// value.

		// How the input data should be interpreted.
		String valueInputOption = "RAW"; // TODO: Update placeholder value.

		// The new values to apply to the spreadsheet.
		List<ValueRange> data = new ArrayList<>(); // TODO: Update placeholder
		data.add("Hello world");// value.

		// TODO: Assign values to desired fields of `requestBody`:
		BatchUpdateValuesRequest requestBody = new BatchUpdateValuesRequest();
		requestBody.setValueInputOption(valueInputOption);
		requestBody.setData(data);

		Sheets sheetsService = createSheetsService();
		Sheets.Spreadsheets.Values.BatchUpdate request = sheetsService.spreadsheets().values()
				.batchUpdate(spreadsheetId, requestBody);

		BatchUpdateValuesResponse response = request.execute();

		// TODO: Change code below to process the `response` object:
		System.out.println(response);
	}

	public static Sheets createSheetsService() throws IOException, GeneralSecurityException {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

		// TODO: Change placeholder below to generate authentication
		// credentials. See
		// https://developers.google.com/sheets/quickstart/java#step_3_set_up_the_sample
		//
		// Authorize using one of the following scopes:
		// "https://www.googleapis.com/auth/drive"
		// "https://www.googleapis.com/auth/spreadsheets"
		GoogleSheetsService googleSheetsService = new GoogleSheetsService();
		Credential credential = googleSheetsService.authorize();

		return new Sheets.Builder(httpTransport, jsonFactory, credential).setApplicationName("Google-SheetsSample/0.1")
				.build();
	}
}
