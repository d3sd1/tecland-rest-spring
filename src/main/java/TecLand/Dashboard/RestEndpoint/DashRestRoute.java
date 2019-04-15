package TecLand.Dashboard.RestEndpoint;

/*
NOTE: those with /auth/ inside DO NOT REQUIRE LOGIN!!
 */
public class DashRestRoute {
    private static final String PREFIX = "/dash";
    public static final String LOGIN = PREFIX + "/auth/login";
    public static final String LOGOUT = PREFIX + "/logout";
    public static final String CHECK_SESSION = PREFIX + "/session";
    public static final String SESSION_DATA = PREFIX + "/session/data";
}
