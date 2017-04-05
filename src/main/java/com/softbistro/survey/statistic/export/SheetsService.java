package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;

@Service
public class SheetsService {

	/** Application name. */
	private static final String APPLICATION_NAME = "Survey SoftBistro";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	@Autowired
	private GoogleAuthorization googleAuthorization;

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

	/**
	 * Creating and configure new sheets
	 * 
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Spreadsheet sendGoogleSheets(List<SurveyStatisticExport> statistc)
			throws IOException, GeneralSecurityException {
		Spreadsheet requestBody = new Spreadsheet();

		Sheets sheetsService = googleAuthorization.getSheetsService();

		String title = statistc.get(0).getName() + new java.util.Date().toString();

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
		// insertData(response);

		return response;
	}

}