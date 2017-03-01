package com.survey.softbistro.sending.gmail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.survey.softbistro.response.ResponseStatus;
import com.survey.softbistro.sending.gmail.service.EmailSenderService;

@RestController
@RequestMapping("survey/")
public class EmailController {

	@Autowired
	EmailSenderService emailSenderService;

	@RequestMapping(value = "sendemails", method = RequestMethod.GET)
	public ResponseStatus sendingMessages() {
		return emailSenderService.send();
	}
}
