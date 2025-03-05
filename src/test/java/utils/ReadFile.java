package utils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ReadFile {
	
	public Map<String, Object> readYaml(String fileName) {
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getClass()
		  .getClassLoader()
		  .getResourceAsStream(fileName);
		Map<String, Object> map = yaml.load(inputStream);
		return map;
	}
	
	public List<PetAvailablePojo> readYamlAsList(String fileName) {
		 Yaml yaml = new Yaml();
		 InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream(fileName);
		 return yaml.load(inputStream);
	}
}
