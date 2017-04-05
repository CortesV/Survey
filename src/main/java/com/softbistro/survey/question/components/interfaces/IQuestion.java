package com.softbistro.survey.question.components.interfaces;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.question.components.entity.Question;

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
	public ResponseEntity<Question> findQuestionById(Long id);

	/**
	 * Save client to database
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Object> saveQuestion(Question question);

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Object> deleteQuestion(Long id);

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
	public ResponseEntity<Object> updateQuestion(Question question, Long id);
}
