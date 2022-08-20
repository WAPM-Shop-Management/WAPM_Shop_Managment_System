package spring.constant;

public class ApplicationConstant {

    /**
     * Response Codes
     */
    public static final int RESOURCE_NOT_FOUND = 404;
    public static final int INVALID_INPUT = 400;

    /**
     * Token constants
     */
    public static final String SECRET = "WJj3dD91YV5uy48olbDC+Dx11JaFzCR9q2e0T4BmAGU";
    public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    /**
     * SMS Text
     */
    public static final String NEW_ORDER_REQUEST_CUSTOMER = "Hello %s, your order #%s has been received. We will send another text when it’s ready to take away.";
    public static final String ORDER_READY_TO_PICKUP = "Hello %s, thanks for order. Your order #%s is now ready to take away.";

    public static final String NEW_ORDER_REQUEST_ADMIN = "Hello Owner, New order #%s has been received. Please check the admin portal for more details.";

}
