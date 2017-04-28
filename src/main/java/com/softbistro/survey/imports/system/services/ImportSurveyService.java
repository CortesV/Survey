package com.softbistro.survey.imports.system.services;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.imports.system.importt.csv.CSVImport;
import com.softbistro.survey.imports.system.interfaces.IImportSurvey;

/**
 * Major distributor import file<br>
 * coordinate files to services, that can work with this file format
 * 
 * @author zviproject
 *
 */
@Service
public class ImportSurveyService {

	@Autowired
	private CSVImport csvImport;

	private static final Logger LOGGER = Logger.getLogger(ImportSurveyService.class);
	private Integer generatedSurveyId = 0;

	/**
	 * Select service for working with file by format
	 * 
	 * @param request
	 * @param clientId
	 */
	public Integer importFile(HttpServletRequest request, Integer clientId) {

		try {
			Part filePart = request.getPart("file");

			String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			String format = FilenameUtils.getExtension(fullFileName);

			IImportSurvey iImportSurvey = selectImportServiceRealization(format);

			generatedSurveyId = iImportSurvey.fromFile(filePart, clientId);

		} catch (IOException | ServletException e) {
			LOGGER.error(e.getMessage());
		}

		return generatedSurveyId;
	}

	private IImportSurvey selectImportServiceRealization(String format) {

		switch (format.toUpperCase()) {
		case "CSV":
			return csvImport;

		default:
			return null;
		}
	}

}
