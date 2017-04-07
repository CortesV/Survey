package com.softbistro.survey.imports.system.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.TypeConstraintException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvReader;
import com.softbistro.survey.imports.system.components.entities.GroupQuestions;
import com.softbistro.survey.imports.system.components.entities.Question;
import com.softbistro.survey.imports.system.components.entities.Survey;
import com.softbistro.survey.imports.system.components.interfaces.ISurveyDAO;
import com.softbistro.survey.imports.system.interfaces.IImportSurvey;

/**
 * Importing survey from file
 * 
 * @author olegnovatskiy
 */
@Service
public class ImportSurvey implements IImportSurvey {

	private static final String SOURCE_IMPORT_FILES = "src/main/resources/importing_files/";

	private static final String TRUE_VALUE_FROM_FILE = "Y";

	private static Logger log = Logger.getLogger(ImportSurvey.class);

	@Autowired
	private ISurveyDAO iSurveyDAO;

	@Autowired
	private UploadFiles uploadFiles;

	/**
	 * Import survey from file of CSV format.
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public void importSyrveyCSV(HttpServletRequest request, Long clientId) {

		Map<String, String> attributes = null;

		try {

			attributes = uploadFiles.uploadCSV(request);

		} catch (TypeConstraintException | ServletException | IOException e1) {

			log.error(e1.getMessage());

		}

		String fileName = attributes.get("fileName");
		String surveyName = attributes.get("surveyName");

		System.out.println(fileName + surveyName);
		
		try {

			Survey survey = importDate(fileName);
			survey.setTitle(surveyName);
			survey.setClienId(clientId);

			iSurveyDAO.saveSurvey(survey);

		} catch (IOException e) {

			log.error(e.getMessage());

		} finally {

			deleteImportFile(fileName);
		}

	}

	/**
	 * Import data from file to object of survey.
	 * 
	 * @param fileName
	 * @return
	 */
	private Survey importDate(String fileName) throws IOException {

		CsvReader csvReader = null;

		try {
			File importFile = new File(String.format("%s%s", SOURCE_IMPORT_FILES, fileName));

			List<GroupQuestions> listGroupQuestions = new ArrayList<>();
			GroupQuestions groupQuestions = new GroupQuestions();
			List<Question> questions = new ArrayList<>();
			csvReader = new CsvReader(new FileReader(importFile));
			Survey survey = new Survey();

			csvReader.readHeaders();

			while (csvReader.readRecord()) {

				String currentGroup = csvReader.get("Group");

				if (groupQuestions.getTitle() == null) {

					groupQuestions.setTitle(csvReader.get("Group"));
				}

				if (currentGroup.equals(groupQuestions.getTitle())) {

					Question responseReadQuestion = readRecord(csvReader);
					if (responseReadQuestion != null) {

						questions.add((Question) responseReadQuestion);
					}

				} else {

					groupQuestions.setQuestions(questions);
					listGroupQuestions.add(groupQuestions);
					questions = new ArrayList<>();
					groupQuestions = new GroupQuestions();
					groupQuestions.setTitle(csvReader.get("Group"));

					Question responseReadQuestion = readRecord(csvReader);
					if (responseReadQuestion != null) {

						questions.add((Question) responseReadQuestion);
					}
				}
			}
			
			groupQuestions.setQuestions(questions);
			listGroupQuestions.add(groupQuestions);
			survey.setGroupQuestions(listGroupQuestions);

			return survey;

		} finally {

			if (csvReader != null)
				csvReader.close();
		}
	}

	/**
	 * Read date of question and create new Question object.
	 * 
	 * @param csvReader
	 * @return Response response
	 */
	private Question readRecord(CsvReader csvReader) {

		try {
			String required = csvReader.get("Required");
			String type = csvReader.get("Answers");
			String commentRequired = csvReader.get("Comment");
			Question question = new Question();
			question.setText(csvReader.get("Questions"));// set value of
															// question

			if (required.equals(TRUE_VALUE_FROM_FILE)) {
				question.setRequired(true);
			} else {
				question.setRequired(false);
			}

			if (commentRequired.equals(TRUE_VALUE_FROM_FILE)) {
				question.setRequiredComment(true);
			} else {
				question.setRequiredComment(false);
			}

			if (type.equals("yes|no") || type.equals("boolean")) {
				type = "boolean";
			}

			question.setAnswerType(type);
			question.setQuestionChoices(csvReader.get("Value"));

			return question;

		} catch (IOException e) {

			log.error(e.getMessage());
			
			return null;
		}
	}

	/**
	 * Delete importing files from source folder at server.
	 * 
	 * @param nameDeletingFile
	 * @return
	 */
	private Boolean deleteImportFile(String nameDeletingFile) {

		File fileDeleting = new File(String.format("%s%s", SOURCE_IMPORT_FILES, nameDeletingFile));
		return fileDeleting.delete();
	}
}
