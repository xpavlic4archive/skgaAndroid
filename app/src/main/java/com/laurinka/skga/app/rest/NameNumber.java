package com.laurinka.skga.app.rest;

public class NameNumber {
	
	String name;
	String number;
	public NameNumber(String aName, String aNumber) {
		this.name = aName;
		this.number = aNumber;
	}
	public String getName() {
		return name;
	}
	public String getNumber() {
		return number;
	}
	@Override
	public String toString() {
		return "NameNumber [name=" + name + ", number=" + number + "]";
	}

	
}
