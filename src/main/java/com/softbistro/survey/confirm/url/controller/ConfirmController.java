package com.softbistro.survey.confirm.url.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	private static final Logger LOGGER = Logger.getLogger(ConfirmController.class);

	@Autowired
	private ConfirmService confirmService;

	@ApiOperation(value = "Confirm Password", notes = "Confirm the password by uuid", tags = "Confirm")
	@RequestMapping(value = "/password/{uuid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> confirmPassword(@PathVariable(value = "uuid") String uuid) {

		try {

			confirmService.confirmPassword(uuid);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Confirm Email", notes = "Confirm the eMail by uuid", tags = "Confirm")
	@RequestMapping(value = "/client/{uuid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> confirmEmail(@PathVariable(value = "uuid") String uuid) {

		try {

			confirmService.confirmEmail(uuid);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
