package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Connect;

public class Transaction {
	private static Connect con = Connect.getInstance();
    private String itemName;
    private Integer price;
    private Integer quantity;
    
	public Transaction(String itemName, Integer price, Integer quantity) {
		super();
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public static void addTransaction(Integer userId, Integer itemId, Integer quantity) {
        String insertHeaderQuery = "INSERT INTO transactionheaders (UserID) VALUES (?)";
        String insertDetailsQuery = "INSERT INTO transactiondetails (TransactionID, ItemID, Quantity) VALUES (?, ?, ?)";

        try {
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
	
	public static ObservableList<Transaction> getTransactionByFan(Integer userId) {
	    ObservableList<Transaction> transactionHistory = FXCollections.observableArrayList();

	    String query = "SELECT i.ItemName, i.Price, td.Quantity\r\n"
	    		+ "FROM TransactionHeaders th\r\n"
	    		+ "JOIN TransactionDetails td ON th.TransactionID = td.TransactionID\r\n"
	    		+ "JOIN Items i ON td.ItemID = i.ItemID\r\n"
	    		+ "WHERE th.UserID = ?;";
	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setInt(1, userId);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                String itemName = rs.getString("ItemName");
	                int price = rs.getInt("Price");
	                int quantity = rs.getInt("Quantity");

	                // Format the transaction details
	                Transaction transaction = new Transaction(itemName, price, quantity);

	                // Add to the ObservableList
	                transactionHistory.add(transaction);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return transactionHistory;
	}
}

