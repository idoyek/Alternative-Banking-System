package utils;

import chat.ChatManager;
import client.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static constants.Constants.INT_PARAMETER_ERROR;

public class ServletUtils {

	private static final String DATABASE_MANAGER_ATTRIBUTE_NAME = "databaseManager";
	//private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	//private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

	private static final Object databaseLock = new Object();
	//private static final Object userManagerLock = new Object();
	//private static final Object chatManagerLock = new Object();

	private static HistoryRewind historyRewind = new HistoryRewind();
	private static AdminLogin adminLogin = new AdminLogin();

	public static Database getDatabase(ServletContext servletContext) {

		synchronized (databaseLock) {

			if (servletContext.getAttribute(DATABASE_MANAGER_ATTRIBUTE_NAME) == null) {
				Database database = new Database();
				database.getYaz().setYaz(1);
				historyRewind.addToDatabasesList(database);
				servletContext.setAttribute(DATABASE_MANAGER_ATTRIBUTE_NAME, database);
			}
		}
		return (Database) servletContext.getAttribute(DATABASE_MANAGER_ATTRIBUTE_NAME);
	}

//	public static UserManager getUserManager(ServletContext servletContext) {
//
//		synchronized (userManagerLock) {
//			if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
//				servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
//			}
//		}
//		return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
//	}

//	public static ChatManager getChatManager(ServletContext servletContext) {
//		synchronized (chatManagerLock) {
//			if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
//				servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
//			}
//		}
//		return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
//	}


	public static void changeToRelevantCurrentDatabase(ServletContext servletContext){
		servletContext.setAttribute(DATABASE_MANAGER_ATTRIBUTE_NAME, historyRewind.getDatabasesList().get(historyRewind.getDatabasesList().size() - 1));
	}
	public static void changeToSliderRelevantDatabase(ServletContext servletContext, int index){
		servletContext.setAttribute(DATABASE_MANAGER_ATTRIBUTE_NAME, historyRewind.getDatabasesList().get(index));
	}
	public static Database getLastYazDatabase(){
		return historyRewind.getDatabasesList().get(historyRewind.getDatabasesList().size() - 1);
	}

	public static HistoryRewind getHistoryRewind() {
		return historyRewind;
	}

	public static AdminLogin getAdminLogin() {
		return adminLogin;
	}

	public static int getIntParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException numberFormatException) {
			}
		}
		return INT_PARAMETER_ERROR;
	}

/*
	private static ArrayList<Database> databasesList = new ArrayList<>();

	private static Database getDatabaseFromDatabasesList(ArrayList<Database> databasesList, int index){
		return databasesList.get(index);
	}

	public static Database getDatabase(ServletContext servletContext, int index) throws IOException, ClassNotFoundException {

		synchronized (userManagerLock) {

			//int index = 0;
			if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
				Database database = new Database();
				database.getYaz().setYaz(1);
				databasesList.add(database);
				servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, databasesList);
			}
			else if (databasesList.size() - 1 < index){
				//serialize and duplicate database
				writePersonsToFile(databasesList.get(databasesList.size() - 1));
				Database databaseFromFile = readPersonsFromFile();
				databasesList.add(databaseFromFile);
			}
			return getDatabaseFromDatabasesList((ArrayList<Database>) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME), index);
		}
	}
*/
}