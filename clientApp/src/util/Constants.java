package util;

import com.google.gson.Gson;

public class Constants {

    // global constants
    public final static String CLIENT_SCREEN = "Client Screen";
    public final static int REFRESH_RATE = 500;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/webServer";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginShortResponse";
    public final static String CLIENT_SCREEN_REFRESH = FULL_SERVER_PATH + "/clientScreenRefresherServlet";

    public final static String CHARGE_MONEY = FULL_SERVER_PATH + "/chargeMoneyServlet";
    public final static String WITHDRAW_MONEY = FULL_SERVER_PATH + "/withdrawMoneyServlet";

    public final static String LOAD_XML_FILE = FULL_SERVER_PATH + "/loadXmlFileServlet";
    public final static String UPLOAD_LOAN_MANUALLY = FULL_SERVER_PATH + "/uploadLoanManually";

    public final static String SHOW_RELEVANT_LOANS = FULL_SERVER_PATH + "/showRelevantLoansServlet";
    public final static String LOANS_TO_INVEST = FULL_SERVER_PATH + "/loansToInvestServlet";
    public final static String SALE_LOANS = FULL_SERVER_PATH + "/saleLoansServlet";
    public final static String BUY_LOANS = FULL_SERVER_PATH + "/buyLoansServlet";
    public final static String PAY_SINGLE_PAYMENT = FULL_SERVER_PATH + "/paySinglePaymentServlet";
    public final static String CLOSE_ENTIRE_LOAN = FULL_SERVER_PATH + "/closeEntireLoanServlet";

    public final static String GET_LOANER_LOANS_INFORMATION = FULL_SERVER_PATH + "/loanerLoansFromDatabaseServlet";
    public final static String GET_LENDER_LOANS_INFORMATION = FULL_SERVER_PATH + "/lenderLoansFromDatabaseServlet";
    public final static String GET_CATEGORIES_LIST = FULL_SERVER_PATH + "/categoriesListFromDatabaseServlet";

    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/sendChatServlet";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chatAreaServlet";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/usersNamesServlet";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
