package com.softbistro.survey.statistic.export.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.softbistro.survey.statistic.component.entity.ParticipantAttributes;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.interfacee.IExportFile;
import com.softbistro.survey.statistic.component.service.GeneralStatisticDao;

/**
 * Export data to CSV file
 * 
 * @author alex_alokhin
 *
 */
@Service
public class CsvStatisticService implements IExportFile {

	@Autowired
	private GeneralStatisticDao generalStatisticDao;

	private final static String FILE_PATH = "src/main/resources/importing_files/statistic.";

	private final static char DELIMETER = ',';

	private static final Logger LOGGER = Logger.getLogger(CsvStatisticService.class);

	/**
	 * Export statistic about surveys to CSV file
	 * 
	 * @param extension
	 *            - extension of file
	 * @return - file with content
	 */
	@Override
	public File exportToFile(String extension) {
		File file = null;
		CsvWriter csvWriter;
		List<SurveyStatisticExport> surveyStatisticExport = generalStatisticDao.getAllStatistic();
		try {
			file = new File(FILE_PATH + extension);
			if (!file.exists()) {
				file.createNewFile();
			}

			csvWriter = new CsvWriter(new FileWriter(file.getAbsolutePath()), DELIMETER);

			String[] columns = new String[] { "id", "name", "participantId", "firstName", "lastName", "groupName",
					"questionName", "answer", "comment", "answerDateTime", "Attribute name", "Attribute value" };
			for (int i = 0; i < columns.length; i++) {
				csvWriter.write(columns[i]);
			}
			csvWriter.endRecord();
			surveyStatisticExport.stream().forEach(survey -> {
				try {
					ParticipantAttributes attribute;

					csvWriter.write(String.valueOf(survey.getId()));
					csvWriter.write(survey.getName());
					csvWriter.write(String.valueOf(survey.getParticipantId()));
					csvWriter.write(survey.getFirstName());
					csvWriter.write(survey.getLastName());
					csvWriter.write(survey.getGroupName());
					csvWriter.write(survey.getQuestionName());
					csvWriter.write(survey.getAnswer());
					csvWriter.write(survey.getComment());
					csvWriter.write(String.valueOf(survey.getAnswerDateTime()));
					for (int i = 0; i < survey.getParticipantAttribute().size(); i++) {
						if (i != 0) {
							csvWriter.endRecord();
							for (int j = 0; j < 10; j++) {
								csvWriter.write(null);
							}
						}

						attribute = survey.getParticipantAttribute().get(i);
						csvWriter.write(attribute.getName());
						csvWriter.write(attribute.getValue());
					}

					csvWriter.endRecord();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
				}

			});

			csvWriter.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return null;
		} finally {
			file.deleteOnExit();
		}
		return file;
	}
}
