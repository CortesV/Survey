package com.softbistro.survey.statistic.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.softbistro.survey.statistic.component.entity.ParticipantAttributes;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.service.GeneralStatisticDao;

@Service
public class CsvServiceStatistic {

	@Autowired
	private GeneralStatisticDao generalStatisticDao;
	
	/**
	 * Export statistic about surveys to csv file
	 * @return - file with content
	 */
	public File export() {
		File file = null;
		CsvWriter csvWriter;
		List<SurveyStatisticExport> surveyStatisticExport = generalStatisticDao.getAllStatistic();   
		ParticipantAttributes attr;
		try {
		file = new File("src/main/resources/importing_files/statistic.csv");
		if (!file.exists()){
			file.createNewFile();
		}
		
		csvWriter = new CsvWriter(new FileWriter(file.getAbsolutePath()),',');

		String[] columns = new String[]{"id","name","participantId","firstName","lastName","groupName","questionName",
				"answer","comment","answerDateTime","Attribute name","Attribute value"};
		for (int i = 0; i < columns.length; i++) {
			csvWriter.write(columns[i]);
		}
		csvWriter.endRecord();
		
		for (SurveyStatisticExport survey : surveyStatisticExport) {
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
            	if(i!=0){
         		    csvWriter.endRecord();
         		    for (int j = 0; j < 10; j++) {
						csvWriter.write(null);
					}
         	  	}
            	
          	    attr = survey.getParticipantAttribute().get(i);
          	    csvWriter.write(attr.getName());
    			csvWriter.write(attr.getValue());
			}
            
            csvWriter.endRecord();
		}
		
		csvWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
		
	}
}
