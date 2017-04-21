package com.softbistro.survey.imports.system.importt.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
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
import com.softbistro.survey.imports.system.components.interfaces.ISurveyDAO;
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

	@Autowired
	private ISurveyDAO iSurveyDAO;

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

			// Creating survey object from file
			ImportSurvey importSurvey = new ImportSurvey();
			Map<String, Integer> groups = new HashMap<String, Integer>();
			List<Question> questions = new LinkedList<>();

			importDate(filePart, groups, questions);

			importSurvey.setQuestions(questions);
			importSurvey.setGroups(groups);
			importSurvey.setTitle(fullFileName.substring(0, fullFileName.length() - 4));
			importSurvey.setClienId(clientId);

			// Save survey to database
			iSurveyDAO.saveSurvey(importSurvey);
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
	private void importDate(Part filePart, Map<String, Integer> groups, List<Question> questions) throws IOException {
		CsvReader csvReader = null;
		try {

			csvReader = new CsvReader(filePart.getInputStream(), Charset.forName("UTF-8"));

			csvReader.readHeaders();

			while (csvReader.readRecord()) {
				saveFileData(csvReader, groups, questions);
			}

		} finally {
			csvReader.close();
		}
	}

	private void saveFileData(CsvReader csvReader, Map<String, Integer> groups, List<Question> questions) {

		try {

			String answerType = csvReader.get("Answers");
			String groupName = csvReader.get("Group");

			// Working with questions
			Question question = new Question();
			question.setGroupName(groupName);
			question.setQuestion(csvReader.get("Questions"));
			question.setRequiredComment(csvReader.get("Comment") == TRUE_VALUE_FROM_FILE);
			question.setRequired(csvReader.get("Required") == TRUE_VALUE_FROM_FILE);
			question.setQuestionChoices(csvReader.get("Value"));

			if (answerType.equals("yes|no") || answerType.equals("boolean")) {
				answerType = "boolean";
			}

			question.setAnswerType(answerType);

			questions.add(question);

			// Working with groups
			ImportGroupQuestions group = new ImportGroupQuestions();
			group.setTitle(groupName);

			groups.put(groupName, 0);

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

}
