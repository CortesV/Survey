package com.softbistro.survey.imports.system.services;

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
import org.springframework.stereotype.Service;

/**
 * Class to uploading files to server.
 * 
 * @author olegnovatskiy
 */
@Service
public class UploadFiles {

	private static final String SOURCE_IMPORT_FILES = "src/main/resources/importing_files/";
	
	/**
	 * Upload file of import to server.
	 * 
	 * @param request
	 * @return Map<String, String> that contain two variables with name fileName and surveyName.
	 *  fileName - name of file on server.
	 *  surveyName - ім'я файлу, перш ніж він був завантажений на сервер і це ім'я імпортованого обстеження.
	 * @throws ServletException
	 * @throws IOException
	 */
	public Map<String, String> uploadCSV(HttpServletRequest request)
			throws ServletException, IOException, TypeConstraintException {

		final Part filePart = request.getPart("importSCVFile");

		final String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		
		final String fileExtension = FilenameUtils.getExtension(fullFileName);
		
		final String surveyName = fullFileName.substring(0, fullFileName.length()-4);
		
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
