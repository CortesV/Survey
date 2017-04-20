package com.softbistro.survey.imports.system.importt.csv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;
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
public class CSVImport implements IImportSurvey {

	private static final String SOURCE_IMPORT_FILES = "src/main/resources/importing_files/";

	private static final String TRUE_VALUE_FROM_FILE = "Y";

	private static final Logger LOGGER = Logger.getLogger(CSVImport.class);

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
	public void fromFile(Part filePart, Long clientId) {
		try {
			String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			Survey survey = new Survey();

			importDate(filePart, survey);

			survey.setTitle(fullFileName.substring(0, fullFileName.length() - 4));
			survey.setClienId(clientId);

			iSurveyDAO.saveSurvey(survey);
		} catch (TypeConstraintException | IOException e) {

			LOGGER.error(e.getMessage());

		} finally {
			try {
				filePart.delete();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}

	}

	/**
	 * Import data from file to object of survey.
	 * 
	 * @param fileName
	 * @return
	 */
	private void importDate(Part filePart, Survey survey) throws IOException {
		CsvReader csvReader = null;
		try {
			List<GroupQuestions> questionGroups = new ArrayList<>();
			GroupQuestions questionGroup = new GroupQuestions();
			List<Question> questions = new ArrayList<>();

			csvReader = new CsvReader(filePart.getInputStream(), ';', Charset.forName("UTF-8"));
			csvReader.readHeaders();
			csvReader.readRecord();

			String currentGroup = csvReader.get("Group");

			if (questionGroup.getTitle() == null) {

				questionGroup.setTitle(csvReader.get("Group"));
			}

			if (currentGroup.equals(questionGroup.getTitle())) {

				Question responseReadQuestion = readRecord(csvReader);
				if (responseReadQuestion != null) {

					questions.add((Question) responseReadQuestion);
				}

			} else {

				questionGroup.setQuestions(questions);
				questionGroups.add(questionGroup);
				questions = new ArrayList<>();
				questionGroup = new GroupQuestions();
				questionGroup.setTitle(csvReader.get("Group"));

				Question responseReadQuestion = readRecord(csvReader);
				if (responseReadQuestion != null) {

					questions.add((Question) responseReadQuestion);
				}
			}

			questionGroup.setQuestions(questions);
			questionGroups.add(questionGroup);
			survey.setGroupQuestions(questionGroups);

			// return survey;

		} finally {
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

			LOGGER.error(e.getMessage());

			return null;
		}
	}

	/**
	 * Delete imported file from source folder at server.
	 * 
	 * @param nameDeletingFile
	 * @return
	 */
	private Boolean deleteImportFile(String nameDeletingFile) {

		File fileDeleting = new File(String.format("%s%s", SOURCE_IMPORT_FILES, nameDeletingFile));
		return fileDeleting.delete();
	}
}
