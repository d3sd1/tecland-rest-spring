package TecLand.Dashboard.RestEndpoint;

/*
NOTE: those with /auth/ inside DO NOT REQUIRE LOGIN!!
 */
public class DashRestRoute {
    /* KERNEL ROUTES */
    public static final String LOGIN_NOT_NEEDED_PREFIX = "/auth";
    private static final String PREFIX = "/dash";

    /* CONTROLLER ROUTES */
    public static final String LOGIN = PREFIX + LOGIN_NOT_NEEDED_PREFIX + "/login";
    public static final String LOGOUT = PREFIX + "/logout";
    public static final String CHECK_SESSION = PREFIX + "/session";
    public static final String SESSION_DATA = PREFIX + "/session/data";
}
