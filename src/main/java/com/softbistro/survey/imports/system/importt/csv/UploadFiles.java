package com.softbistro.survey.imports.system.importt.csv;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Class to uploading files to server.
 * 
 * @author olegnovatskiy
 */
@Service
public class UploadFiles {

	private static final String SOURCE_IMPORT_FILES = "src/main/resources/importing_files/";

	private static final Logger LOGGER = Logger.getLogger(UploadFiles.class);

	/**
	 * Upload file of import to server.
	 * 
	 * @param request
	 * @return Map<String, String> that contain two variables with name fileName
	 *         and surveyName. fileName - name of file on server. surveyName -
	 *         name of file, before it will download on server and it name of
	 *         imported file.
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	public Map<String, String> uploadCSV(HttpServletRequest request) {
		Part filePart = null;
		try {
			filePart = request.getPart("file");

			String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			String fileName = String.format("%s.%s", UUID.randomUUID().toString(),
					FilenameUtils.getExtension(fullFileName));

			InputStream fileContent = filePart.getInputStream();

			File createdFile = new File(String.format("%s%s", SOURCE_IMPORT_FILES, fileName));

			Files.write(Paths.get(createdFile.getAbsolutePath()), IOUtils.toByteArray(fileContent));

			Map<String, String> attributes = new HashMap<>();
			attributes.put("fileName", fileName);
			attributes.put("surveyName", fullFileName.substring(0, fullFileName.length() - 4));

			return attributes;

		} catch (IOException | ServletException e) {
			LOGGER.error(e.getMessage());
			return null;
		} finally {
			// try {
			// // delete file from server
			// filePart.delete();
			// } catch (IOException e) {
			// LOGGER.error(e.getMessage());
			// }
		}
	}

}
