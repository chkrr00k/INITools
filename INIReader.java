package chkrr00k.persister;
//"proudly" made by chkrr00k
// LGPL licence

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;

public class INIReader {

	private static Pattern INISECTION;
	private static Pattern INIVALUE;

	private File file;
	private INISettings settings;
	private long lastModified;

	public INIReader(String fileName) throws AccessDeniedException {
		this.file = new File(fileName);
		this.settings = null;
		this.lastModified = 0;
		
		if(!this.file.canRead()){
			throw new AccessDeniedException("Unable to read file");
		}

		INIReader.INISECTION = Pattern.compile("\\[(.+)\\]");
		INIReader.INIVALUE = Pattern.compile("([^;].*) ?= ?(.*)");
	}
	public INIReader(File file) throws AccessDeniedException {
		this.file = file;
		this.settings = null;
		this.lastModified = 0;
		
		if(!this.file.canRead()){
			throw new AccessDeniedException("Unable to read file");
		}

		INIReader.INISECTION = Pattern.compile("\\[(.+)\\]");
		INIReader.INIVALUE = Pattern.compile("([^;].*) ?= ?(.*)");
	}

	private String read() throws IOException {
		StringBuilder result = new StringBuilder();
		this.lastModified = this.file.lastModified();
		try(BufferedReader br = new BufferedReader(new FileReader(this.file))){
			String line;
			while((line = br.readLine()) != null){
				result.append(line + "\n");
			}
		}
		return result.toString();
	}

	public INISettings getSettings() throws IOException {
		if(this.settings == null || this.lastModified != this.file.lastModified()){
			this.settings = this.iniSettingsfy(this.process(this.read()));
		}
		return this.settings;
	}

	private INISettings iniSettingsfy(Map<String, List<Pair<String, String>>> input) {
		return new INISettings(input);
	}

	private Map<String, List<Pair<String, String>>> process(String input) {
		String current = "";
		Map<String, List<Pair<String, String>>> storage = new HashMap<String, List<Pair<String, String>>>();
		for(Pair<String, String> pair : this.parseAll(input)){
			if(pair.getValue() == null && !storage.containsKey(pair.getKey())){
				storage.put(pair.getKey(), new LinkedList<Pair<String, String>>());
				current = pair.getKey();
			}else if(pair.getValue() != null && !current.equals("")){
				storage.get(current).add(pair);
			}
		}
		return storage;
	}

	private List<Pair<String, String>> parseAll(String input) {
		List<Pair<String, String>> result = new LinkedList<Pair<String, String>>();
		Pair<String, String> tmp = null;
		for(String line : input.split("\n")){
			tmp = this.parse(line.trim());
			if(tmp != null){
				result.add(tmp);
			}
		}
		return result;
	}

	private Pair<String, String> parse(String input) {
		Matcher m = INIReader.INISECTION.matcher(input);
		if(m.matches()){
			return new Pair<String, String>(m.group(1).trim(), null);
		}else{
			m = INIReader.INIVALUE.matcher(input);
			if(m.matches()){
				return new Pair<String, String>(m.group(1).trim(), m.group(2).trim());
			}
		}
		return null;
	}

}
