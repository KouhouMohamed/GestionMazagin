package ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mysql.jdbc.Connection;

import Classes.Ligne;
import javafx.collections.ObservableList;

class Programm{
	public static void main(String[] args) {
	
		String tab[]={"A B", "C D", "H I", "M N", "M N", "A B", "H I", "A B", "C D" };
		Map<String, Integer> word = new HashMap<String, Integer>();
		
		for(int i=0;i<tab.length;i++) {
			if(word.containsKey(tab[i])) {
				word.replace(tab[i], word.get(tab[i])+1);
			}
			else {
				word.put(tab[i], 1);
			}
			
		}
		Set<Entry<String,Integer>> set=word.entrySet();
		for(Entry s : set) {
			System.out.println(s.getKey() + ":"+s.getValue());
		}
		
		Arrays.sort(tab);
		for(int i=0;i<tab.length;i++) {
			System.out.println(tab[i]);
			
		}
		
	}
}