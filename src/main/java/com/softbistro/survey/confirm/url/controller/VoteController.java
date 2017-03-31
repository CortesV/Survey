package com.softbistro.survey.confirm.url.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@ApiOperation(value = "Write the Answer", notes = "Write answer to data base by uuid and answer list", tags = "Answer")
	@RequestMapping(value = "/{uuid}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> vote(@PathVariable(value = "uuid") String uuid, @RequestBody List<Answer> answers) {
		return voteService.answerOnSurvey(uuid, answers);
	}

	/**
	 * Response for site with information about questions
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<List<VotePage>> getVotePage(@PathVariable(value = "uuid") String uuid) {
		return voteService.getVotePage(uuid);
	}
}
