package com.softbistro.survey.imports.system.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private ISurvey iSurvey;

	/**
	 * Import survey from file of CSV format.
	 * 
	 * @param importFileName
	 * @return
	 */
	@Override
	public Response importSyrveyCSV(String importFileName, String titleSurvey, Long clientId) {

		Response response = importing(importFileName);

		if (response.getResponseStatus() == HttpStatus.CREATED) {

			Survey survey = (Survey) response.getData();
			survey.setTitle(titleSurvey);
			survey.setClienId(clientId);
			
			return iSurvey.saveSurvay(survey);

		}

		return new Response(null, HttpStatus.FAILED_DEPENDENCY, "Import failed. " + response.getResponseDescription());

	}

	/**
	 * Import data from file.
	 * 
	 * @param importFileName
	 * @return
	 */
	private Response importing(String importFileName) {

		File importFile = new File(String.format("%s%s", SOURCE_IMPORT_FILES, importFileName));
		CsvReader csvReader = null;
		Survey survey = new Survey();
		List<GroupQuestions> listGroupQuestions = new ArrayList<>();
		List<Question> questions = null;

		try {

			csvReader = new CsvReader(new FileReader(importFile));

			csvReader.readHeaders();

			GroupQuestions groupQuestions = null;

			while (csvReader.readRecord()) {

				String currentGroup = csvReader.get("Group");

				if (groupQuestions == null) {

					groupQuestions = new GroupQuestions();
					questions = new ArrayList<>();
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
					groupQuestions = new GroupQuestions();
					questions = new ArrayList<>();
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

		} catch (FileNotFoundException e) {

			csvReader.close();

			return new Response(null, HttpStatus.NOT_FOUND, e.getMessage());
		} catch (IOException e) {

			csvReader.close();

			return new Response(null, HttpStatus.FAILED_DEPENDENCY, e.getMessage());
		} finally {

			csvReader.close();
		}

		return new Response(survey, HttpStatus.CREATED, null);
	}

	private Response readRecord(CsvReader csvReader) {
		Question question = new Question();

		String required;
		try {
			required = csvReader.get("Required");

			String type = csvReader.get("Answers");
			String comment = csvReader.get("Comment");

			question.setValue(csvReader.get("Questions"));

			if (required.equals(TRUE_VALUE_FROM_FILE)) {
				question.setRequired(true);
			} else {
				question.setRequired(false);
			}

			if (comment.equals(TRUE_VALUE_FROM_FILE)) {
				question.setComment(true);
			} else {
				question.setComment(false);
			}

			question.setType(type);
			if (question.getType().equals(TypeQuestion.LIST.getValue())
					|| question.getType().equals(TypeQuestion.MULTILIST.getValue())) {

				String[] mas = csvReader.get("Value").split("\\|");
				question.setAnswers(mas);
			}
		} catch (IOException e) {

			return new Response(null, HttpStatus.NOT_EXTENDED, e.getMessage());
		}
		return new Response(question, HttpStatus.CREATED, null);
	}

}
