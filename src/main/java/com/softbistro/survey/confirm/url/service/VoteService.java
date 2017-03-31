package com.softbistro.survey.confirm.url.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.confirm.url.component.entity.Answer;
import com.softbistro.survey.confirm.url.component.interfacee.IVote;

@Service
public class VoteService {
	@Autowired
	private IVote iVote;

	/**
	 * Writing answers to questions in the database
	 * 
	 * @param uuid
	 * @return
	 */
	public ResponseEntity<Object> answerOnSurvey(String uuid, List<Answer> answers) {
		return iVote.answerOnSurvey(uuid, answers);
	}
}
