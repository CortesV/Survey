package com.softbistro.survey.confirm.url.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.confirm.url.component.entity.Answer;
import com.softbistro.survey.confirm.url.component.entity.VotePage;
import com.softbistro.survey.confirm.url.service.VoteService;

import io.swagger.annotations.ApiOperation;

/**
 * For vote in survey
 * 
 * @author zviproject
 *
 */
@RestController
@RequestMapping("rest/survey/v1/answer")
public class VoteController {

	@Autowired
	private VoteService voteService;

	/**
	 * Writing answers to questions in the database
	 * 
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "Write the Answer", notes = "Write answer to data base by uuid and answer list"
			+ "(Answer etitys contains : survey id, question, description short,"
			+ " description Long, questionSection Id, answer Type, question Choices, required, required Comment. "
			+ "Field answerType can have value such as: RATE1-10, RATE1-5, RATE1-3, BOOLEAN, LIST, MULTILIST, INPUT, MEMO)", tags = "Answer")
	@RequestMapping(value = "/{uuid}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> vote(@PathVariable(value = "uuid") String uuid, @RequestBody List<Answer> answers) {

		voteService.answerOnSurvey(uuid, answers);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Response for site with information about questions
	 * 
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "Information about questions", notes = "Response for site with information about questions by uuid", tags = "Answer")
	@RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<List<VotePage>> getVotePage(@PathVariable(value = "uuid") String uuid) {

		return new ResponseEntity<>(voteService.getVotePage(uuid), HttpStatus.OK);
	}
}
