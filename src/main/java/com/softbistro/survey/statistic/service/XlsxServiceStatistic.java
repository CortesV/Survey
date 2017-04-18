package com.softbistro.survey.statistic.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.softbistro.survey.statistic.component.entity.ParticipantAttributes;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.service.GeneralStatisticDao;

@Service
public class XlsxServiceStatistic {

	@Autowired
	private GeneralStatisticDao generalStatisticDao;
	
	private CellStyle cellStyle;
	private Workbook workbook;
	private CreationHelper createHelper;
	
	/**
	 * Export statistic about surveys to xlsx file
	 * @return - file with content
	 */
	public File export() {
		File file = null;
		int rowNum = 0;
		int fieldIter = 0;
		Cell cell;
		ParticipantAttributes attr;
		
		try {
		file = new File("src/main/resources/importing_files/statistic.xlsx");
		if (!file.exists()){
			file.createNewFile();
		}
		
		FileOutputStream fop = new FileOutputStream(file.getPath());
		workbook = new XSSFWorkbook();
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet("Statistic Surveys");
		List<SurveyStatisticExport> surveyStatisticExport = generalStatisticDao.getAllStatistic();

		
		Row row = sheet.createRow(rowNum++);
		Field[] names = surveyStatisticExport.get(0).getClass().getDeclaredFields();
		for(Field s: names){
			cell = row.createCell(fieldIter++);
		    cell.setCellValue(s.getName()!="participantAttribute"?s.getName():"");
		}
		cell = row.createCell(fieldIter++);
		cell.setCellValue("Attribute Name");

		cell = row.createCell(fieldIter++);
		cell.setCellValue("Attribute Value");
		
		for (SurveyStatisticExport survey : surveyStatisticExport) {
			int cellNum=10;
            row = sheet.createRow(rowNum++);
            createList(survey, row);
            
            for (int i = 0; i < survey.getParticipantAttribute().size(); i++) {
          	      attr = survey.getParticipantAttribute().get(i);
          	      cellNum = 10;
          	  
            	  if(i!=0){
          		     row = sheet.createRow(rowNum++);
          	  	  }
            	  
            	  cell = row.createCell(cellNum++);
            	  cell.setCellValue(attr.getName());
            	  
            	  cell = row.createCell(cellNum++);
            	  cell.setCellValue(attr.getValue());
            	  
			}
		}
		
		workbook.write(fop);
		fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	
	/**
	 * method that retrieves object data and inserts data to row
	 * @param survey  - object that parses
	 * @param row - row of the table
	 */
	private void createList(SurveyStatisticExport survey, Row row) {
      int cellNum = 0;
      
      Cell cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getId());
   
      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getName());

      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getParticipantId());

      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getFirstName());

      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getLastName());

      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getGroupName());

      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getQuestionName());

      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getAnswer());
      
      cell = row.createCell(cellNum++);
      cell.setCellValue(survey.getComment());
      
      cell = row.createCell(cellNum++);
      cellStyle= workbook.createCellStyle();
      createHelper = workbook.getCreationHelper();
      cellStyle.setDataFormat(
      createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
      cell.setCellStyle(cellStyle);
      cell.setCellValue(survey.getAnswerDateTime());
      
      int i = 0;
      
	}

}
