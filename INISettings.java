package chkrr00k.persister;
//"proudly" made by chkrr00k
// LGPL licence

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public class INISettings {

	private Map<String, Map<String, String>> storage;

	public INISettings(Map<String, List<Pair<String, String>>> input) {
		this.storage = new HashMap<String, Map<String, String>>();
		for(String key : input.keySet()){
			this.storage.put(key, this.toMap(input.get(key)));
		}
	}

	public INISettings() {
		this.storage = new HashMap<String, Map<String, String>>();
	}

	private Map<String, String> toMap(List<Pair<String, String>> list) {
		Map<String, String> result = new HashMap<String, String>();
		for(Pair<String, String> pair : list){
			result.put(pair.getKey(), pair.getValue());
		}
		return result;
	}

	public Map<String, String> getSettings(String field) {
		if(this.storage.containsKey(field)){
			return this.storage.get(field);
		}else{
			return null;
		}
	}

	public String getSetting(String field, String key) {
		if(this.storage.containsKey(field) && this.storage.get(field).containsKey(key)){
			return this.storage.get(field).get(key);
		}else{
			return null;
		}
	}

	public void addSettings(String field, Map<String, String> values) {
		if(this.storage.containsKey(field)){
			this.storage.get(field).putAll(values);
		}else{
			this.storage.put(field, values);
		}
	}

	public void addSetting(String field, String key, String value) {
		if(this.storage.containsKey(field)){
			this.storage.get(field).put(key, value);
		}else{
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.put(key, value);
			this.storage.put(field, newMap);
		}
	}

	public void removeSettings(String field) {
		if(this.storage.containsKey(field)){
			this.storage.remove(field);
		}
	}

	public void removeSetting(String field, String key) {
		if(this.storage.containsKey(field)){
			this.storage.get(field).remove(key);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String ls = System.lineSeparator();
		for(String key : this.storage.keySet()){
			builder.append("[" + key + "]" + ls);
			for(String valKey : this.storage.get(key).keySet()){
				builder.append(valKey + "=" + this.storage.get(key).get(valKey) + ls);
			}
			builder.append(ls);
		}

		return builder.toString();
	}

}
