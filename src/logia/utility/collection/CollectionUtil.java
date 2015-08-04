package logia.utility.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Class CollectionUtil.
 * 
 * @author Paul Mai
 */
public class CollectionUtil {

	/**
	 * Sort list by page.
	 *
	 * @param <T> the generic type
	 * @param list the full list
	 * @param page the page number, start at 1
	 * @param size the records per page
	 * @return the list
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	public static <T> List<T> sortListByPage(Collection<T> list, int page, int size) throws IndexOutOfBoundsException {
		List<T> temp = new ArrayList<T>(list);
		if (list.size() > 0 && page > 0) {
			int start = (page - 1) * size;
			int records = 0;
			int n = list.size() / size;
			if (page <= n) {
				records = size;
				int end = start + records;
				temp = temp.subList(start, end);
			}
			else if (page - n == 1) {
				records = list.size() % size;
				int end = start + records;
				temp = temp.subList(start, end);
			}
			else {
				temp.clear();
			}
		}
		return temp;
	}

}
