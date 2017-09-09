package org.alexdev.icarus.dao.mysql.catalogue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.log.Log;

public class ClubDao {

    public static long[] getSubscription(int userID) {

        long[] subscriptionData = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("SELECT * FROM users_subscriptions WHERE user_id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                subscriptionData = new long[] { resultSet.getLong("expire_time"), resultSet.getLong("bought_time") };

            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return subscriptionData;
    }

    public static void create(int userID, long expireTime, long boughtTime) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO users_subscriptions (user_id, expire_time, bought_time) VALUES (?, ?, ?)", sqlConnection);
            preparedStatement.setInt(1, userID);
            preparedStatement.setLong(2, expireTime);
            preparedStatement.setLong(3, boughtTime);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void update(int userID, long expireTime, long boughtTime) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE users_subscriptions SET expire_time = ?, bought_time = ? WHERE user_id = ?", sqlConnection);
            preparedStatement.setLong(1, expireTime);
            preparedStatement.setLong(2, boughtTime);
            preparedStatement.setInt(3, userID);  
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void delete(int userID) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("DELETE FROM users_subscriptions WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userID);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
