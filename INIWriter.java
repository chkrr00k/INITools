package chkrr00k.persister;
//"proudly" made by chkrr00k
// LGPL licence

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.AccessDeniedException;

public class INIWriter {
	
	private INISettings settings;
	private File file;

	public INIWriter(INISettings settings, String fileName) throws AccessDeniedException {
		super();
		this.settings = settings;
		this.file = new File(fileName);
		
		if(this.file.exists() && !this.file.canWrite()){
			throw new AccessDeniedException("Unable to write existing file");
		}
	}
	
	public INIWriter(INISettings settings, File file) throws AccessDeniedException {
		super();
		this.settings = settings;
		this.file = file;
		
		if(this.file.exists() && !this.file.canWrite()){
			throw new AccessDeniedException("Unable to write existing file");
		}
	}

	public void write() throws IOException{
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file)));
		writer.write("; Created with INIWriter by chkrr00k" + System.lineSeparator());
		writer.write(this.settings.toString());
		writer.flush();
		writer.close();
	}
}
