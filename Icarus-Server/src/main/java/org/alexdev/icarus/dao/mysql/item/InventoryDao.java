package org.alexdev.icarus.dao.mysql.item;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pets.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryDao {
    
    /**
     * Gets the inventory items.
     *
     * @param userId the user id
     * @return the inventory items
     */
    public static Map<Integer, Item> getInventoryItems(int userId) {

        Map<Integer, Item> items = new HashMap<>();
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT id, user_id, group_id, item_id, room_id, x, y, z, rotation, extra_data FROM items WHERE room_id = 0 AND user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                items.put(resultSet.getInt("id"), fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return items;
    }

    /**
     * Gets the item.
     *
     * @param id the id
     * @return the item
     */
    public static Item getItem(long id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Item item = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT id, user_id, item_id, room_id, x, y, z, rotation, extra_data FROM items WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
                
            if (resultSet.next()) {
                item = fill(resultSet);
            }
            
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return item;
    }

    /**
     * New item.
     *
     * @param itemId the item id
     * @param ownerId the owner id
     * @param extraData the extra data
     * @return the item
     */
    public static Item newItem(int itemId, int ownerId, String extraData, int groupId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Item item = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO items (user_id, group_id, item_id, extra_data) VALUES (?, ?, ?, ?)", sqlConnection);
            preparedStatement.setInt(1, ownerId);
            preparedStatement.setInt(2, groupId);
            preparedStatement.setInt(3, itemId);
            preparedStatement.setString(4, extraData);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                item = new Item(resultSet.getInt(1), ownerId, groupId, itemId, 0, "0", "0", 0, 0, extraData);
            }

        } catch (SQLException e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return item;
    }

    /**
     * New batch of inventory items
     *
     * @param itemId the item id
     * @param ownerId the owner id
     * @param extraData the extra data
     * @return the list of items
     */
    public static List<Item> newItems(int itemId, int ownerId, String extraData, int groupId, int amount) {
        List<Item> items = new ArrayList<Item>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO items (user_id, group_id, item_id, extra_data) VALUES (?, ?, ?, ?)", sqlConnection);

            for (int i = 0; i < amount; i++) {
                preparedStatement.setInt(1, ownerId);
                preparedStatement.setInt(2, groupId);
                preparedStatement.setInt(3, itemId);
                preparedStatement.setString(4, extraData);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                items.add(new Item(resultSet.getInt(1), ownerId, groupId, itemId, 0, "0", "0", 0, 0, extraData));
            }

        } catch (SQLException e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }
    
    /**
     * Gets the inventory pets.
     *
     * @param id the id
     * @return the inventory pets
     */
    public static Map<Integer, Pet> getInventoryPets(int id) {
        Map<Integer, Pet> items = new HashMap<>();
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM pets WHERE room_id = 0 AND owner_id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                items.put(resultSet.getInt("id"), PetDao.fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return items;
    }
    
    /**
     * Fill.
     *
     * @param row the row
     * @return the item
     * @throws Exception the exception
     */
    public static Item fill(ResultSet row) throws Exception {
        Item item = new Item(row.getInt("id"), row.getInt("user_id"), row.getInt("group_id"), row.getInt("item_id"), row.getInt("room_id"), row.getString("x"), row.getString("y"), row.getDouble("z"), row.getInt("rotation"), row.getString("extra_data"));
        return item;
    }
}
