package com.survey.softbistro.components.interfaces;

import java.util.Map;

import com.survey.softbistro.components.entity.Question;

/**
 * 
 * @author cortes
 *
 */
public interface IQuestion {

	/**
	 * Find question in database by id of question
	 * 
	 * @param id
	 *            id - id of question
	 * @return return - all information about question
	 */
	public Question findQuestionById(Long id);

	/**
	 * Save client to database
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	public Map<String, String> saveQuestion(Question question);

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	public Map<String, String> deleteQuestion(Long id);

	/**
	 * Update information of question
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @param id
	 *            id-id of question
	 * @return return - status of execution this method
	 */
	public Map<String, String> updateQuestion(Question question, Long id);
}
