package com.softbistro.survey.statistic.component.interfacee;

import java.util.List;

import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;

/**
 * Methods for working with statistic
 * 
 * @author alex_alokhin
 *
 */
public interface IExportStatisticDao {
	
	/**
	 * Export statistic about surveys to string in format that we need
	 * 
	 * @return  string with data in appropriate format
	 */
	public String export();

}
