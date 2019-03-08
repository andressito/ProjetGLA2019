package gl.chuchufly.restfull.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gl.chuchufly.restfull.model.Plane;

public class PlaneDAO {

    List<Plane> list;
	private static final Map<String, Plane> empMap = new HashMap<String, Plane>();
	static{
	    initEmps();
	}
    private static void initEmps() {
    	Plane emp1 = new Plane(1,"Avion ZE0121", 20, 0);
    	Plane emp2 = new Plane(2,"Avion ZEDE85", 30, 0);
    	Plane emp3 = new Plane(3,"Avion HEDE01", 40, 0);
    	Plane emp4 = new Plane(4,"Avion KTDE01", 40, 0);
    	
        empMap.put(String.valueOf(emp1.getPlaneId()), emp1);
        empMap.put(String.valueOf(emp2.getPlaneId()), emp2);
        empMap.put(String.valueOf(emp3.getPlaneId()), emp3);
        empMap.put(String.valueOf(emp4.getPlaneId()), emp4);
    }
    public static Plane getPlane(int id_plane) {
        return empMap.get(String.valueOf(id_plane));
    }
    public static Plane addPlane(Plane emp) {
        empMap.put(String.valueOf(emp.getPlaneId()), emp);
        return emp;
    }
    public static void deletePlane(int id_message) {
        empMap.remove(String.valueOf(id_message));
    }
    public static Plane updatePlane(Plane emp) {
        empMap.put(String.valueOf(emp.getPlaneId()), emp);
        return emp;
    }
    public static List<Plane> getAllPlane() {
        Collection<Plane> c = empMap.values();
        List<Plane> list = new ArrayList<Plane>();
        list.addAll(c);
        return list;
    }
}
