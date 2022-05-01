package com.prgrammers.clone.model;

import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class Email {

	private final String address;

	public Email(String address) {
		Assert.notNull(address, "address should not be null");
		Assert.isTrue(address.length() >= 4 && address.length() <= 50,
				"address length mush be between 4 and 50 characters");
		Assert.isTrue(checkAddress(address), "Invalid email address");
		this.address = address;
	}

	@Override
	public boolean equals(Object compareObject) {
		if (this == compareObject)
			return true;

		if (compareObject == null || getClass() != compareObject.getClass())
			return false;

		Email email = (Email)compareObject;
		return Objects.equals(address, email.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address);
	}

	private static boolean checkAddress(String address) {
		return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Email{" +
				"address='" + address + '\'' +
				'}';
	}
}
