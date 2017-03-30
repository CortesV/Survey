package com.softbistro.survey.question.components.interfaces;

import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.response.Response;

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
	public Response findQuestionById(Long id);

	/**
	 * Save client to database
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	public Response saveQuestion(Question question);

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	public Response deleteQuestion(Long id);

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
	public Response updateQuestion(Question question, Long id);
}
