package com.softbistro.survey.imports.system.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.csvreader.CsvReader;
import com.softbistro.survey.imports.system.components.entities.GroupQuestions;
import com.softbistro.survey.imports.system.components.entities.Question;
import com.softbistro.survey.imports.system.components.entities.Survey;
import com.softbistro.survey.imports.system.components.enums.TypeQuestion;
import com.softbistro.survey.imports.system.components.interfaces.ISurvey;
import com.softbistro.survey.imports.system.interfaces.IImportSurvey;
import com.softbistro.survey.response.Response;

@Service
public class ImportSurvey implements IImportSurvey {

	private static final String SOURCE_IMPORT_FILES = "src/main/resources/importing_files/";
	private static final String TRUE_VALUE_FROM_FILE = "Y";

	private static Logger log = Logger.getLogger(ImportSurvey.class);

	@Autowired
	private ISurvey iSurvey;

	/**
	 * Import survey from file of CSV format.
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public Response importSyrveyCSV(String fileName, String surveyName, Long clientId) {

		try {

			Survey survey = importDate(fileName);
			survey.setTitle(surveyName);
			survey.setClienId(clientId);

			return iSurvey.saveSurvey(survey);

		} catch (IOException e) {

			log.error(e.getMessage());

			return new Response(e.getMessage(), HttpStatus.FAILED_DEPENDENCY, "");
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
			csvReader.readHeaders();
			Survey survey = new Survey();

			while (csvReader.readRecord()) {

				String currentGroup = csvReader.get("Group");

				// If statement for first visitation of group
				if (groupQuestions.getTitle() == null) {

					groupQuestions.setTitle(csvReader.get("Group"));
				}

				if (currentGroup.equals(groupQuestions.getTitle())) {

					Response responseReadQuestion = readRecord(csvReader);
					if (responseReadQuestion.getResponseStatus() == HttpStatus.CREATED) {

						questions.add((Question) responseReadQuestion.getData());
					}

				} else {

					groupQuestions.setQuestions(questions);
					listGroupQuestions.add(groupQuestions);
					questions.clear();
					groupQuestions.setTitle(csvReader.get("Group"));

					Response responseReadQuestion = readRecord(csvReader);
					if (responseReadQuestion.getResponseStatus() == HttpStatus.CREATED) {

						questions.add((Question) responseReadQuestion.getData());
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
	private Response readRecord(CsvReader csvReader) {

		try {

			String required = csvReader.get("Required");
			String type = csvReader.get("Answers");
			String commentRequired = csvReader.get("Comment");
			Question question = new Question();
			question.setQuestion(csvReader.get("Questions"));// set value of
																// question

			if (required.equals(TRUE_VALUE_FROM_FILE)) {
				question.setRequired(true);
			} else {
				question.setRequired(false);
			}

			if (commentRequired.equals(TRUE_VALUE_FROM_FILE)) {
				question.setRequired_comment(true);
			} else {
				question.setRequired_comment(false);
			}

			if (type.equals("yes|no") || type.equals("boolean")) {
				type = "boolean";
			}

			question.setAnswer_type(type);
			question.setQuestion_choices(csvReader.get("Value"));
			if (question.getAnswer_type().equals(TypeQuestion.LIST.getValue())
					|| question.getAnswer_type().equals(TypeQuestion.MULTILIST.getValue())) {

				String[] mas = csvReader.get("Value").split("\\|");
				question.setAnswersList(mas);
			}

			return new Response(question, HttpStatus.CREATED, null);

		} catch (IOException e) {

			log.error(e.getMessage());
			return new Response(e.getMessage(), HttpStatus.NOT_EXTENDED, null);
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
