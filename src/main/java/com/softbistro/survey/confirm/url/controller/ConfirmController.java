package com.softbistro.survey.confirm.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.confirm.url.service.ConfirmService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "rest/survey/v1/confirm")
public class ConfirmController {
	@Autowired
	private ConfirmService confirmService;

	@ApiOperation(value = "Confirm Password", notes = "Confirm the password by uuid", tags = "Confirm")
	@RequestMapping(value = "/password/{uuid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> confirmPassword(@PathVariable(value = "uuid") String uuid) {
		return confirmService.confirmPassword(uuid);
	}

	@ApiOperation(value = "Confirm Email", notes = "Confirm the eMail by uuid", tags = "Confirm")
	@RequestMapping(value = "/client/{uuid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> confirmEmail(@PathVariable(value = "uuid") String uuid) {
		return confirmService.confirmEmail(uuid);
	}

}
