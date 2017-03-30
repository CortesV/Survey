package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;

/**
 * Export statistic about survey
 * 
 * @author zviproject
 *
 */
public class ExportStatisticInSheets {
	public void start() throws IOException, GeneralSecurityException {
		// TODO: Assign values to desired fields of `requestBody`:
		Spreadsheet requestBody = new Spreadsheet();

		requestBody.set("HEllow", null);
		Sheets sheetsService = createSheetsService();
		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);

		Spreadsheet response = request.execute();

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