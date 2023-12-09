package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Connect;

public class Item {
	private static Connect con = Connect.getInstance();
	private Integer userID, itemID, price;
	private String itemName, itemDescription;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getItemID() {
		return itemID;
	}

	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer itemPrice) {
		this.price = itemPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public static void addItem(String name, String desc, Integer price, Integer userID) {
		String query = "INSERT INTO `Items`(`ItemName`, `ItemDescription`, `Price`, `UserID`) VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setString(1, name);
			ps.setString(2, desc);
			ps.setInt(3, price);
			ps.setInt(4, userID);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static ObservableList<Item> getAllItemByVendor(Integer id) {
	    ObservableList<Item> itemList = FXCollections.observableArrayList();

	    String query = String.format("SELECT * FROM items WHERE UserID = '%d'", id);

	    try (PreparedStatement ps = con.preparedStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Integer itemId = rs.getInt("ItemId");
	            String name = rs.getString("ItemName");
	            String desc = rs.getString("ItemDescription");
	            Integer price = rs.getInt("Price");

	            Integer userId = rs.getInt("UserID");

	            Item item = new Item(itemId, name, desc, price, userId);
	            itemList.add(item);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return itemList;
	}
	
	public static void DeleteItem(Integer id) {
		  String query = String.format("DELETE  FROM Items WHERE UserID = %d", id);
		 
		     PreparedStatement ps = con.preparedStatement(query);
		     try {
		   ps.executeUpdate();
		  } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		 }
	
	public static void updateItem(int itemId, String name, String desc, int price) {
        String query = "UPDATE items SET ItemName = ?, ItemDescription = ?, Price = ? WHERE ItemId = ?";
        
        try (PreparedStatement ps = con.preparedStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setInt(3, price);
            ps.setInt(4, itemId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public Item(Integer itemID, String itemName, String itemDescription, Integer price, Integer userID) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.price = price;
		this.userID = userID;
	}

	
}
