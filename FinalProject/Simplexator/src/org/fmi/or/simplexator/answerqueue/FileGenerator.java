package org.fmi.or.simplexator.answerqueue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

public class FileGenerator {
	public FileGenerator() {

	}

	public File getFile() {
		PrintWriter writer = null;
		File file = null;
		UUID id = UUID.randomUUID();
		try {
		
			file = new File("/solutions/"+id.toString()+".tex");
		
			writer = new PrintWriter(file);
			writer.println("This is a test message");
			writer.flush();
			writer.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
