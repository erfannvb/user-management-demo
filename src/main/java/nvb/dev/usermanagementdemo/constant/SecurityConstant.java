package nvb.dev.usermanagementdemo.constant;

public class SecurityConstant {

    private SecurityConstant() {
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String REGISTER_PATH = "/api/v1/user/register";
    public static final int TOKEN_EXPIRATION = 7200000;
    public static final String SECRET_KEY = "MFswDQYJKoZIhvcNAQEBBQADSgAwRwJAfRjTOapkgHSKQ/mEyADXLdJ93BS46DRm\n" +
            "N8TXIVDASV2K8m9mimhc6FanQswWqxzF1IJ6lKT7tgUXt+OnipxXmQIDAQAB";

}
