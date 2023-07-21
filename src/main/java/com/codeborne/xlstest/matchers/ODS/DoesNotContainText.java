package com.codeborne.xlstest.matchers.ODS;

import org.hamcrest.Description;

import com.codeborne.xlstest.ODS;

public class DoesNotContainText extends ODSMatcher {

	private final String substring;

	public DoesNotContainText(String substring) {
		this.substring = reduceSpaces(substring);
	}

	@Override
	protected boolean matchesSafely(ODS item) {
		boolean contains = new ContainsText(substring).matchesSafely(item);
		return !contains;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("a ODS not containing text ")
				.appendValue(reduceSpaces(substring));
	}
}
