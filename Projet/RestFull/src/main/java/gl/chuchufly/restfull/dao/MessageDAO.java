package gl.chuchufly.restfull.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gl.chuchufly.restfull.model.Message;

public class MessageDAO {
	private static final Map<String, Message> empMap = new HashMap<String, Message>();
	static {
        initEmps();
    }
 
    private static void initEmps() {
    	Message emp1 = new Message(1,"Put your hands",2,3);
    	Message emp2 = new Message(1,"Put your hands again",2,3);
    	Message emp3 = new Message(1,"Put your hands again too",2,3);
 
        empMap.put(String.valueOf(emp1.getId_message()), emp1);
        empMap.put(String.valueOf(emp2.getId_message()), emp2);
        empMap.put(String.valueOf(emp3.getId_message()), emp3);
    }
    public static Message getMessage(int id_message) {
        return empMap.get(String.valueOf(id_message));
    }
    public static Message addMessage(Message emp) {
        empMap.put(String.valueOf(emp.getId_message()), emp);
        return emp;
    }
    public static void deleteMessage(int id_message) {
        empMap.remove(String.valueOf(id_message));
    }
}
