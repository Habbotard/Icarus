package org.alexdev.icarus.http;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.duckhttpd.server.WebServer;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.http.util.config.Configuration;
import org.alexdev.icarus.http.util.web.ServerResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcarusWeb {

    private static Logger logger = LoggerFactory.getLogger(IcarusWeb.class);
    private static Logger errorLogger = LoggerFactory.getLogger("ErrorLogger");

    private static Configuration config;
    private static WebServer instance;

    public static void main(String[] args) throws Exception {

        Settings settings = Settings.getInstance();
        settings.setResponses(new ServerResponses());

        config = new Configuration();
        config.load();
        config.setSettings(settings);

        Routes.register();
        logger.info("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        /*Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM `site_config`", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("key").toUpperCase().replace(".", "_") + "(\"" + resultSet.getString("key") + "\"),");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }*/

        int port = 80;
        logger.info("Starting http service on port " + port);

        instance = new WebServer(port);
        instance.start();
    }

    public static Logger getErrorLogger() {
        return errorLogger;
    }
}
