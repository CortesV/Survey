package com.survey.softbistro.sending.gmail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.survey.softbistro.response.ResponseStatus;
import com.survey.softbistro.sending.gmail.service.SurveyMessageService;

@RestController
@RequestMapping("survey/")
public class EmailController {

	@Autowired
	SurveyMessageService surveyMessageService;

	@RequestMapping(value = "sendemails", method = RequestMethod.GET)
	public ResponseStatus sendingMessages() {
		return surveyMessageService.send();
	}

	@RequestMapping(value = "survey_id{survey_id}/participant_id{participant_id}")
	public ResponseStatus confirmSurvey(@PathVariable("survey_id") int surveyId,
			@PathVariable("participant_id") int participantId) {
		return null;
	}
}
