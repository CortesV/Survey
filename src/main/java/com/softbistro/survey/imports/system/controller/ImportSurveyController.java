package com.softbistro.survey.imports.system.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.bind.TypeConstraintException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.imports.system.interfaces.IImportSurvey;
import com.softbistro.survey.response.Response;

@RestController
@RequestMapping("/rest/survey/v1.0/importsurvey")
public class ImportSurveyController {

	private static final String SOURCE_IMPORT_FILES = "src/main/resources/importing_files/";

	@Autowired
	private IImportSurvey iImportSurvey;

	/**
	 * Importing survey from selected file of csv type to datadase.
	 * 
	 * @param request
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public Response importSurvey(HttpServletRequest request, @RequestParam(name = "clientId") Long clientId) {

		try {

			Map<String, String> attributes = uploadCSVFile(request);

			return iImportSurvey.importSyrveyCSV(attributes.get("fileName"), attributes.get("surveyName"), clientId);

		} catch (ServletException | IOException | TypeConstraintException e) {

			return new Response(e.getMessage(), HttpStatus.FAILED_DEPENDENCY,
					"Import files is failed: " + e.getMessage());
		}
	}

	/**
	 * Upload file of import to server.
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private Map<String, String> uploadCSVFile(HttpServletRequest request)
			throws ServletException, IOException, TypeConstraintException {

		final Part filePart = request.getPart("importSCVFile");
		final String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		final String fileExtension = FilenameUtils.getExtension(fullFileName);
		System.out.println(fullFileName);
		final String surveyName = "";
		if ( !fileExtension.equals("csv") && !fileExtension.equals("CSV") ) {

			throw new TypeConstraintException("File is not a csv file.");
		}

		String fileName = String.format("%s.%s", UUID.randomUUID().toString(), fileExtension);
		OutputStream out = new FileOutputStream(new File(String.format("%s%s", SOURCE_IMPORT_FILES, fileName)));
		InputStream filecontent = filePart.getInputStream();

		int read = 0;
		final byte[] bytes = new byte[1024];

		while ((read = filecontent.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}

		out.close();
		filecontent.close();

		Map<String, String> attributes = new HashMap<>();
		attributes.put("fileName", fileName);
		attributes.put("surveyName", surveyName);
		
		return attributes;
	}

}
