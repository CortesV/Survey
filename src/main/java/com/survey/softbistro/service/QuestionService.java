package com.survey.softbistro.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.softbistro.components.entity.Question;
import com.survey.softbistro.components.interfaces.IQuestion;

@Service
public class QuestionService {

	@Autowired
	IQuestion iQuestion;

	/**
	 * Find question in database by id of question
	 * 
	 * @param id
	 *            id - id of question
	 * @return return - all information about question
	 */
	public Question findQuestionById(Long id) {

		return iQuestion.findQuestionById(id);
	}

	/**
	 * Save client to database
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	public Map<String, String> saveQuestion(Question question) {

		return iQuestion.saveQuestion(question);
	}

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	public Map<String, String> deleteQuestion(Long id) {

		return iQuestion.deleteQuestion(id);
	}

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
	public Map<String, String> updateQuestion(Question question, Long id) {

		return iQuestion.updateQuestion(question, id);
	}
}
