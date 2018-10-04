package rmit.mad.project.model;

import java.util.EmptyStackException;
import java.util.Stack;

public class RouteInfoDAO {

    private Stack<RouteInfo> routeInfoStack;

    private static RouteInfoDAO instance = new RouteInfoDAO();

    private RouteInfoDAO() {
        routeInfoStack = new Stack();
    }

    public static RouteInfoDAO getInstance() {
        return instance;
    }

    public void insert(RouteInfo routeInfo) {
        routeInfoStack.push(routeInfo);
    }

    public RouteInfo get() throws EmptyStackException {
        return routeInfoStack.pop();
    }

    public void clear() {
        routeInfoStack.empty();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RouteInfoDAO{");
        sb.append("routeInfoStack=").append(routeInfoStack.toString());
        sb.append('}');
        return sb.toString();
    }
}
