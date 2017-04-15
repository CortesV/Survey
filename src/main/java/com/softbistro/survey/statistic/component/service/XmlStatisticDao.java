package com.softbistro.survey.statistic.component.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.interfacee.IExportStatisticDao;
import com.thoughtworks.xstream.XStream;

@Repository
public class XmlStatisticDao implements IExportStatisticDao{
	
	@Autowired
	private GeneralStatisticDao generalStatisticDao;
	
	private XStream xstream = null;
	
	@Override
	public String export() {
		xstream = new XStream();
		
		List<SurveyStatisticExport> surveyStatisticExport = generalStatisticDao.getAllStatistic();

		return xstream.toXML(surveyStatisticExport);
	}
	
	
	
}
