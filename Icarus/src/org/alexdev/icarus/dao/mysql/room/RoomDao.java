package org.alexdev.icarus.dao.mysql.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

import com.google.common.collect.Lists;

public class RoomDao {
    
    public static List<Room> getPublicRooms(boolean storeInMemory) {

        List<Room> rooms = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE room_type = " + RoomType.PUBLIC.getTypeCode(), sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                Room room = RoomManager.find(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                rooms.add(room);

                if (storeInMemory) {
                    RoomManager.addRoom(room);
                }
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    public static List<Room> getPlayerRooms(int userID, boolean storeInMemory) {

        List<Room> rooms = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE owner_id = " + userID, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                Room room = RoomManager.find(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                rooms.add(room);

                if (storeInMemory) {
                    RoomManager.addRoom(room);
                }
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    public static Room getRoom(int roomID, boolean storeInMemory) {

        Room room = null;
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE id = " + roomID, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("id");

                room = RoomManager.find(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                if (storeInMemory) {
                    RoomManager.addRoom(room);
                }
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return room;
    }

    public static List<Integer> getRoomRights(int roomID) {

        List<Integer> rooms = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM room_rights WHERE room_id = " + roomID, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rooms.add(Integer.valueOf(resultSet.getInt("user_id")));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }


        return rooms;
    }

    public static void clearRoomRights(int roomID) {
        Dao.getStorage().execute("DELETE FROM room_rights WHERE room_id = '" + roomID + "'");
    }

    public static void removeRoomRights(int roomID, int userID) {
        Dao.getStorage().execute("DELETE FROM room_rights WHERE room_id = '" + roomID + "' AND user_id = '" + userID + "'");
    }

    public static void addRoomRights(int roomID, int userID) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO room_rights (room_id, user_id) VALUES (?, ?)", sqlConnection);
            preparedStatement.setInt(1, roomID);
            preparedStatement.setInt(2, userID);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteRoom(Room room) {
        Dao.getStorage().execute("DELETE FROM rooms WHERE id = " + room.getData().getID());
    }

    public static void updateRoom(Room room) {

        RoomData data = room.getData();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("UPDATE rooms SET name = ?, description = ?, "
                    + "state = ?, password = ?, users_max = ?, category = ?, tags = ?, trade_state = ?, allow_pets = ?, allow_pets_eat = ?, " 
                    + "allow_walkthrough = ?, hidewall = ?, wall_thickness = ?, floor_thickness = ?, who_can_mute = ?, who_can_kick = ?, who_can_ban = ?, "
                    + "chat_mode = ?, chat_size = ?, chat_speed = ?, chat_distance = ?, chat_flood = ?, wallpaper = ?, floor = ?, outside = ?, model = ? WHERE id = ?", sqlConnection);

            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, data.getDescription());
            preparedStatement.setInt(3, data.getState().getStateCode());
            preparedStatement.setString(4, data.getPassword());
            preparedStatement.setInt(5, data.getUsersMax());
            preparedStatement.setInt(6, data.getCategory());
            preparedStatement.setString(7, String.join(",", data.getTags()));
            preparedStatement.setInt(8, data.getTradeState());
            preparedStatement.setInt(9, data.isAllowPets() ? 1 : 0);
            preparedStatement.setInt(10, data.isAllowPetsEat() ? 1 : 0);
            preparedStatement.setInt(11, data.isAllowWalkthrough() ? 1 : 0);
            preparedStatement.setInt(12, data.hasHiddenWall() ? 1 : 0);
            preparedStatement.setInt(13, data.getWallThickness());
            preparedStatement.setInt(14, data.getFloorThickness());
            preparedStatement.setInt(15, data.getWhoCanMute());
            preparedStatement.setInt(16, data.getWhoCanKick());
            preparedStatement.setInt(17, data.getWhoCanBan());
            preparedStatement.setInt(18, data.getBubbleMode());
            preparedStatement.setInt(19, data.getBubbleType());
            preparedStatement.setInt(20, data.getBubbleScroll());
            preparedStatement.setInt(21, data.getChatMaxDistance());
            preparedStatement.setInt(22, data.getChatFloodProtection());
            preparedStatement.setString(23, data.getWall());
            preparedStatement.setString(24, data.getFloor());
            preparedStatement.setString(25, data.getLandscape());
            preparedStatement.setString(26, data.getModel());
            preparedStatement.setInt(27, data.getID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

    }

    public static void saveChatlog(Player chatter, int roomID, String chatType, String message) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO room_chatlogs (user, room_id, timestamp, message_type, message) VALUES (?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, chatter.getDetails().getName());
            preparedStatement.setInt(2, roomID);
            preparedStatement.setLong(3, Util.getCurrentTimeSeconds());

            if (chatType.equals("CHAT")) {
                preparedStatement.setInt(4, 0);
            } else if (chatType.equals("SHOUT")) {
                preparedStatement.setInt(4, 1);
            } else {
                preparedStatement.setInt(4, 2);
            }

            preparedStatement.setString(5, message);
            preparedStatement.execute();

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }


    public static Room fill(ResultSet row) throws SQLException {

        RoomType type = RoomType.getType(row.getInt("room_type"));

        String ownerName = "";

        if (type == RoomType.PRIVATE) {
            ownerName = PlayerDao.getName(row.getInt("owner_id"));
        }

        Room instance = new Room();

        instance.getData().fill(row.getInt("id"), type, row.getInt("owner_id"), ownerName, row.getString("name"), 
                row.getInt("state"), row.getString("password"), row.getInt("users_now"),
                row.getInt("users_max"), row.getString("description"), row.getInt("trade_state"), row.getInt("score"), row.getInt("category"), 
                row.getInt("category"), row.getString("model"), row.getString("wallpaper"), row.getString("floor"), row.getString("outside"), 
                row.getBoolean("allow_pets"), row.getBoolean("allow_pets_eat"), row.getBoolean("allow_walkthrough"), row.getBoolean("hidewall"), 
                row.getInt("wall_thickness"), row.getInt("floor_thickness"), row.getString("tags"), row.getInt("chat_mode"), row.getInt("chat_size"), row.getInt("chat_speed"),
                row.getInt("chat_distance"), row.getInt("chat_flood"), row.getInt("who_can_mute"), row.getInt("who_can_kick"), row.getInt("who_can_ban"), row.getString("thumbnail"));

        return instance;
    }
}
