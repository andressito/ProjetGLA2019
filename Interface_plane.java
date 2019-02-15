package tpGL;

import java.util.List;

public interface Interface_plane {
	//return list of planes 
List<Plane> getPlanes();
 //return list of planes assigned to atcNumber
List<Plane> getPlanes(String atcNumber);

}
