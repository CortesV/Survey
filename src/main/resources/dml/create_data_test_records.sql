INSERT IGNORE INTO `attribute_values`(`id`, `attribute_id`, `participant_id`, `attribute_value`) VALUES(1, 1, 1, 'IntegrationTestsAttributeValue');

INSERT IGNORE INTO `attributes`(`id`, `group_id`, `attribute`) VALUES(1, 1, 'IntegrationTestAttribute');

INSERT IGNORE INTO `group`(`id`, `client_id`, `group_name`) VALUES(1, 1, 'IntegrationTestsGroupName');

INSERT IGNORE INTO `clients`(`id`, `client_name`, `email`, `password`, `status`) VALUES(1, 'IntegrationTestClientName', 'softbistrosurvey@gmail.com', 'IntegrationTestPassword', 'NEW');

INSERT IGNORE INTO `client_role`(`id`, `client_id`, `authority_id`)  VALUES(1, 1, 1);

INSERT IGNORE INTO `authority`(`id`, `name_authority`) VALUES(1, NULL);  

INSERT IGNORE INTO `connect_group_participant`(`id`, `group_id`, `participant_id`) VALUES(1, 1, 1);

INSERT IGNORE INTO `participant`(`id`, `email`, `first_name`, `last_name`) VALUES(1, 'softbistrosurvey@gmail.com', 'IntegrationTestFirstName', 'IntegrationLastName');

INSERT IGNORE INTO  `answers`(`id`, `question_id`, `participant_id`, `answer_type`, `answer_value`, `comment`) VALUES(1, 1, 1, 'RATE1-10', 'IntegrationTestAnswerValue', 'IntegrationTestComment');

INSERT IGNORE INTO `questions`(`id`, `survey_id`, `question`, `description_short`, `description_long`, `question_section_id`, `answer_type`, `question_choices`, `required`) VALUES(1, 1, 'IntegrationTestQuestion', 'IntegrationTestDescriptionShort', 'IntegrationTestDescriptionLong', 1, NULL, 'IntegrationTestQuestionChoices', 1);

INSERT IGNORE INTO `question_sections`(`id`, `client_id`, `section_name`, `description_short`, `description_long`) VALUES(1, 1, 'IntegrationTestSectionName', 'IntegrationTestDescriptionShort', 'IntegrationTestDescriptionLong');

INSERT IGNORE INTO `survey`(`id`, `client_id`, `name`, `theme`, `start_time`, `finish_time`, `status`) VALUES(1, 1, 'IntegrationTestName', 'IntegrationTestTheme', '2017-04-28', '2017-04-28', 'NEW');

INSERT IGNORE INTO `connect_group_survey`(`id`, `survey_id`, `group_id`) VALUES(1, 1, 1);