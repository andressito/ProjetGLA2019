import Plane;
import java.util.List;
public interface PlaneDao {
    void add( Plane plane );
	Plane delete(int planeId);
    List<Plane> search(String atc_numbers);
    List<Plane> getListePlane();
    List<Plane> getPlaneDetails(int planeId);
}