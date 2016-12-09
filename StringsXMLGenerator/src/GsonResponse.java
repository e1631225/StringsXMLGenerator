import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public class GsonResponse<T> {
//	String text;
	LinkedHashMap<String, Language> text;
	
	
	
	public class Language {
		String name;
		String dir;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDir() {
			return dir;
		}
		public void setDir(String dir) {
			this.dir = dir;
		}
		
		
	}
}
