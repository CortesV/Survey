package com.softbistro.survey.confirm.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.confirm.url.service.ConfirmService;
import com.softbistro.survey.response.Response;

@RestController
@RequestMapping(value = "rest/survey/v1/confirm")
public class ConfirmController {
	@Autowired
	private ConfirmService confirmService;

	@RequestMapping(value = "/password/{uuid}", method = RequestMethod.GET)
	public Response confirmPassword(@PathVariable(value = "uuid") String uuid) {
		return confirmService.confirmPassword(uuid);
	}

	@RequestMapping(value = "/client/{uuid}", method = RequestMethod.GET)
	public Response confirmEmail(@PathVariable(value = "uuid") String uuid) {
		return confirmService.confirmEmail(uuid);
	}

}
