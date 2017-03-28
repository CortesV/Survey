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

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.imports.system.interfaces.IImportSurvey;
import com.softbistro.survey.response.Response;

@RestController
@RequestMapping("/rest/survey/importsurvay/v1.0")
public class ImportSurvayController {

	private static final String SOURCE_IMPORT_FILES = "src/main/resources/importing_files/";

	@Autowired
	private IImportSurvey iImportSurvey;

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public Response importSurvey(HttpServletRequest request, @RequestParam(name = "clientId") Long clientId) {

		Map<String, String> importAttribute;
		try {
			importAttribute = uploadImportFile(request);
		} catch (ServletException | IOException e) {

			return new Response(null, HttpStatus.FAILED_DEPENDENCY, "Import failed: " + e.getMessage());
		}

		String importFileName = importAttribute.get("fileName");
		String surveyName = importAttribute.get("surveyName");

		Response importSurveyResponse = iImportSurvey.importSyrveyCSV(importFileName, surveyName, clientId);

		deleteImportFile(importFileName);

		return importSurveyResponse;

	}

	/**
	 * Upload file of import to server.
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private Map<String, String> uploadImportFile(HttpServletRequest request) throws ServletException, IOException {

		Map<String, String> responseAttribute = new HashMap<String, String>();

		final Part filePart = request.getPart("importSCVFile");
		final String fileExtension = FilenameUtils
				.getExtension(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());
		String fileName = String.format("%s.%s", UUID.randomUUID().toString(), fileExtension);

		String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		String surveyName = fullFileName.substring(0, fullFileName.length()-4);
		responseAttribute.put("surveyName", surveyName);
		responseAttribute.put("fileName", fileName);

		OutputStream out = null;
		InputStream filecontent = null;

		out = new FileOutputStream(new File(String.format("%s%s", SOURCE_IMPORT_FILES, fileName)));
		filecontent = filePart.getInputStream();

		int read = 0;
		final byte[] bytes = new byte[1024];

		while ((read = filecontent.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}

		out.close();
		filecontent.close();
		out.close();
		filecontent.close();

		return responseAttribute;
	}

	/**
	 * Delete importing files from source folder in server.
	 * 
	 * @param nameDeletingFile
	 * @return
	 */
	private Boolean deleteImportFile(String nameDeletingFile) {

		File fileDeleting = new File(String.format("%s%s", SOURCE_IMPORT_FILES, nameDeletingFile));
		return fileDeleting.delete();
	}

}
