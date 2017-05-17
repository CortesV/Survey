package com.softbistro.survey.imports.system.components.enums;

public enum TypeQuestion {

	RATE110("rate1-10"), RATE15("rate1-5"), RATE13("rate1-3"), YESNO("yesno"), LIST("list"), 
	MULTILIST("multilist"), INPUT("input"), MEMO("memo");

	private String value;

	public String getValue() {
		return value;
	}

	private TypeQuestion(String value) {
		this.value = value;
	}

}
