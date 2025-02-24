package com.codeborne.xlstest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.hamcrest.Matcher;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import static com.codeborne.xlstest.IO.readBytes;
import static com.codeborne.xlstest.IO.readFile;

import com.codeborne.xlstest.matchers.ODS.ContainsRow;
import com.codeborne.xlstest.matchers.ODS.ContainsText;
import com.codeborne.xlstest.matchers.ODS.DoesNotContainText;

public class ODS {
	public final String name;
	public final SpreadSheet ods;

	private ODS(String name, byte[] content) {
		this.name = name;
		try (InputStream inputStream = new ByteArrayInputStream(content)) {
			ods = SpreadSheet.get(new ODPackage(inputStream));
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid ODS " + name, e);
		}
	}

	public ODS(File file) {
		this(file.getAbsolutePath(), readFile(file));
	}

	public ODS(URL url) throws IOException {
		this(url.toString(), readBytes(url));
	}

	public ODS(URI uri) throws IOException {
		this(uri.toURL());
	}

	public ODS(byte[] content) {
		this("", content);
	}

	public ODS(InputStream inputStream) throws IOException {
		this(readBytes(inputStream));
	}

	public static Matcher<ODS> containsText(String text) {
		return new ContainsText(text);
	}

	public static Matcher<ODS> containsRow(String... cellTexts) {
		return new ContainsRow(cellTexts);
	}

	public static Matcher<ODS> doesNotContainText(String text) {
		return new DoesNotContainText(text);
	}

	// see also:
	// https://stackoverflow.com/questions/64423111/javajopendocument-nullpointerexception-when-using-getcellat0-0
	// https://www.jopendocument.org/docs/org/jopendocument/dom/ODValueType.html
	public Object safeOOCellValue(Cell<ODDocument> cell) {
		if (cell == null) {
			return null;
		}
		Object result;
		String data = cell.getElement().getValue();
		ODValueType type = cell.getValueType();
		switch (type) {
		case FLOAT:
			result = Double.valueOf(data);
			break;
		case STRING:
			result = data;
			break;
		case TIME:
			result = null; // TODO
			break;
		case BOOLEAN:
			result = Boolean.getBoolean(data);
			break;
		default:
			throw new IllegalStateException("Can't evaluate cell value");
		}
		return result;
	}
}
