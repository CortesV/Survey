package com.softbistro.survey.imports.system.importt.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;
import javax.xml.bind.TypeConstraintException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvReader;
import com.softbistro.survey.imports.system.components.entities.ImportGroupQuestions;
import com.softbistro.survey.imports.system.components.entities.ImportSurvey;
import com.softbistro.survey.imports.system.components.interfaces.IImportSurveyDAO;
import com.softbistro.survey.imports.system.interfaces.IImportSurvey;
import com.softbistro.survey.question.components.entity.Question;

/**
 * Importing survey from file
 * 
 * @author olegnovatskiy
 */
@Service
public class CSVImport implements IImportSurvey {

	private static final String TRUE_VALUE_FROM_FILE = "Y";

	private static final Logger LOGGER = Logger.getLogger(CSVImport.class);

	private static final String GROUP = "Group";
	private static final String ANSWERS = "Answers";
	private static final String QUESTIONS = "Questions";
	private static final String COMMENT = "Comment";
	private static final String REQUIRED = "Required";
	private static final String VALUE = "Value";

	private static final String SURVEY_START_TIME = "Start";
	private static final String SURVEY_FINISH_TIME = "Finish";

	@Autowired
	private IImportSurveyDAO iSurveyDAO;

	private Integer generatedSurveyId = 0;

	/**
	 * Import survey from file of CSV format.
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public Integer fromFile(Part filePart, Integer clientId) {
		try {
			ImportSurvey importSurvey = prepareSurvey(filePart, clientId);

			generatedSurveyId = iSurveyDAO.saveSurvey(importSurvey);

		} catch (TypeConstraintException | IOException e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				filePart.delete();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return generatedSurveyId;

	}

	/**
	 * Import data from file to object of survey.
	 * 
	 * @param fileName
	 * @return
	 */
	private ImportSurvey prepareSurvey(Part filePart, Integer clientId) throws IOException {
		CsvReader csvReader = null;
		ImportSurvey importSurvey = new ImportSurvey();

		Map<String, Integer> groups = new HashMap<String, Integer>();
		List<Question> questions = new LinkedList<>();

		try {
			DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");

			String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			csvReader = new CsvReader(filePart.getInputStream(), Charset.forName("UTF-8"));

			csvReader.readHeaders();
			csvReader.readRecord();
			importSurvey.setStartTime((Date) dateFormat.parse(csvReader.get(SURVEY_START_TIME)));
			importSurvey.setFinishTime((Date) dateFormat.parse(csvReader.get(SURVEY_FINISH_TIME)));

			while (csvReader.readRecord()) {
				fillQuestionsOnGroups(csvReader, groups, questions);
			}
			importSurvey.setQuestions(questions);
			importSurvey.setGroups(groups);
			importSurvey.setTitle(fullFileName.substring(0, fullFileName.length() - 4));
			importSurvey.setClienId(clientId);

		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
		} finally {
			csvReader.close();
		}
		return importSurvey;
	}

	private void fillQuestionsOnGroups(CsvReader csvReader, Map<String, Integer> groups, List<Question> questions) {

		try {
			String groupName = csvReader.get(GROUP);
			ImportGroupQuestions group = new ImportGroupQuestions();
			group.setTitle(groupName);

			groups.put(groupName, 0);

			questions.add(prepareQuestion(csvReader));

		} catch (IOException e) {
			LOGGER.error("Problem with parsing group " + e.getMessage());
		}
	}

	private Question prepareQuestion(CsvReader csvReader) {
		Question question = null;
		try {

			String answerType = csvReader.get(ANSWERS);
			String groupName = csvReader.get(GROUP);

			question = new Question();
			question.setGroupName(groupName);

			question.setQuestion(csvReader.get(QUESTIONS));

			question.setRequiredComment(csvReader.get(COMMENT) == TRUE_VALUE_FROM_FILE);
			question.setRequired(csvReader.get(REQUIRED) == TRUE_VALUE_FROM_FILE);
			question.setQuestionChoices(csvReader.get(VALUE));

			if (answerType.equals("yes|no") || answerType.equals("boolean")) {
				answerType = "BOOLEAN";
			}

			question.setAnswerType(answerType.toUpperCase());

		} catch (IOException e) {
			LOGGER.error("Problem with parsing question " + e.getMessage());
		}

		return question;
	}

}
