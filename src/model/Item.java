package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	

	public static void addTransaction(Integer userId, Integer itemId, Integer quantity) {
        String insertHeaderQuery = "INSERT INTO transactionheaders (UserID) VALUES (?)";
        String insertDetailsQuery = "INSERT INTO transactiondetails (TransactionID, ItemID, Quantity) VALUES (?, ?, ?)";

        try {
            // Insert into transactionheader to get the generated TransactionID
            try (PreparedStatement headerPs = con.transactionPreparedStatement(insertHeaderQuery, Statement.RETURN_GENERATED_KEYS)) {
                headerPs.setInt(1, userId);
                headerPs.executeUpdate();

                // Retrieve the generated TransactionID
                ResultSet generatedKeys = headerPs.getGeneratedKeys();
                int transactionId = -1;
                if (generatedKeys.next()) {
                    transactionId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to get generated TransactionID.");
                }

                // Insert into transactiondetails with the obtained TransactionID
                try (PreparedStatement detailsPs = con.preparedStatement(insertDetailsQuery)) {
                    detailsPs.setInt(1, transactionId);
                    detailsPs.setInt(2, itemId);
                    detailsPs.setInt(3, quantity);
                    detailsPs.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static ObservableList<String> getTransactionByFan(Integer userId) {
	    ObservableList<String> transactionHistory = FXCollections.observableArrayList();

	    String query = "SELECT i.ItemName, i.Price, td.Quantity " +
	            "FROM TransactionHeaders th " +
	            "JOIN TransactionDetails td ON th.TransactionID = td.TransactionID " +
	            "JOIN Items i ON td.ItemID = i.ItemID " +
	            "WHERE th.UserID = ?";

	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setInt(1, userId);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                String itemName = rs.getString("ItemName");
	                int price = rs.getInt("Price");
	                int quantity = rs.getInt("Quantity");

	                // Format the transaction details
	                String transactionDetails = String.format("%s – %d – %d", itemName, price, quantity);

	                // Add to the ObservableList
	                transactionHistory.add(transactionDetails);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return transactionHistory;
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
