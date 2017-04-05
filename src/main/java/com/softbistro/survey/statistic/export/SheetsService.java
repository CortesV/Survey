package com.softbistro.survey.statistic.export;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;

@Resource
public class SheetsService {

	/**
	 * Creating and configure new sheets
	 * 
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Spreadsheet sendGoogleSheets(List<SurveyStatisticExport> statistic)
			throws IOException, GeneralSecurityException {
		GoogleAuthorization googleAuthorization = new GoogleAuthorization();
		Sheets sheetsService = googleAuthorization.getSheetsService();
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

		Sheets.Spreadsheets.Create request = sheetsService.spreadsheets()
				.create(createSheets(statistic, sheetsService));

		Spreadsheet response = request.execute();

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

}