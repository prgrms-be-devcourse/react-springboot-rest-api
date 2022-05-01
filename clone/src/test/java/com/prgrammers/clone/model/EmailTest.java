package com.prgrammers.clone.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	@DisplayName("email regex test")
	void testInvalidEmail() {
		//given
		//when
		//then
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Email("asdads@"));
	}

	@Test
	@DisplayName("email valid test")
	void testValidEmail(){
	    //given
		//when
		final String address = "sss@programmers.co.kr";
		Email email = new Email(address);
	    //then
		Assertions.assertEquals(email.getAddress(),address);
	}
}