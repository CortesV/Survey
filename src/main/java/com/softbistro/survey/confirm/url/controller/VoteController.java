package com.softbistro.survey.confirm.url.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.confirm.url.component.entity.Answer;
import com.softbistro.survey.confirm.url.service.VoteService;
import com.softbistro.survey.response.Response;

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

	@RequestMapping(value = "/{uuid}", method = RequestMethod.POST)
	public Response vote(@PathVariable(value = "uuid") String uuid, @RequestBody List<Answer> answers) {
		return voteService.answerOnSurvey(uuid, answers);
	}

}
