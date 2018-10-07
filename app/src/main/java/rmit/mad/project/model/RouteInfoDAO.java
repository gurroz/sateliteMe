package rmit.mad.project.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class RouteInfoDAO {

    private List<RouteInfo> listInfoStack;

    private static RouteInfoDAO instance = new RouteInfoDAO();

    private RouteInfoDAO() {
        listInfoStack = new ArrayList<>();
    }

    public static RouteInfoDAO getInstance() {
        return instance;
    }

    public void insert(RouteInfo routeInfo) {
        listInfoStack.add(routeInfo);
    }

    public RouteInfo get() {
        if(listInfoStack.size() > 0) {
            Collections.sort(listInfoStack, new RouteInfoSorting());
            return listInfoStack.remove(0);
        } else {
            return null;
        }

    }

    public void clear() {
        listInfoStack.clear();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RouteInfoDAO{");
        sb.append("routeInfoStack=").append(listInfoStack.toString());
        sb.append('}');
        return sb.toString();
    }

    class RouteInfoSorting implements Comparator<RouteInfo> {
        @Override
        public int compare(RouteInfo t1, RouteInfo t2) {
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            try {
                Date t1Meet = dateFormat.parse(t1.getMeetingTime());
                Date t2Meet = dateFormat.parse(t2.getMeetingTime());
                return t1Meet.compareTo(t2Meet);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }
}
