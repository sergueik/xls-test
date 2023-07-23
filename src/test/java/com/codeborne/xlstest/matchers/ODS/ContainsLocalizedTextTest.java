package com.codeborne.xlstest.matchers.ODS;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import com.codeborne.xlstest.ODS;
import static com.codeborne.xlstest.ODS.containsText;
import static com.codeborne.xlstest.ODS.doesNotContainText;

public class ContainsLocalizedTextTest {
	private ODS sheet;

	@Before
	public void before() throws Exception {
		sheet = new ODS(Objects.requireNonNull(
				getClass().getClassLoader().getResource("localized.ods")));
	}

	@Test
	public void canAssertThatXlsContainsText() throws IOException {
		for (String text : Arrays.asList(new String[] { "junit", "testng", "allure",
				"хабр", "разработка", "фриланс", "расчёт" })) {
			assertThat(sheet, containsText(text));
		}
	}

	@Test
	public void canAssertXlsDoesNotContainText() throws IOException {
		for (String text : Arrays.asList(new String[] { "xunit", "яндекс" })) {
			assertThat(sheet, doesNotContainText(text));
		}
	}

	@Test
	public void errorDescriptionForDoesNotContainTextMatcher() {
		String text = "хабр";
		try {
			assertThat(sheet, doesNotContainText(text));
			fail("expected AssertionError");
		} catch (AssertionError expected) {
			assertThat(expected.getMessage(), containsString(
					String.format("Expected: a ODS not containing text \"%s\"", text)));
		}
	}

	@Test
	public void errorDescriptionForContainsTextMatcher() {
		String text = "яндекс";
		String message = String.format("Expected: a ODS containing text \"%s\"",
				text);
		try {
			assertThat(sheet, containsText(text));
			fail("expected AssertionError");
		} catch (AssertionError expected) {
			assertThat(expected.getMessage(), containsString(message));
		}
	}
}
