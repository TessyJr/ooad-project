package main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {
	
	public Query() {
		// TODO Auto-generated constructor stub
		Connect con = Connect.getInstance();
		
		String query = "CREATE TABLE IF NOT EXISTS Users (\r\n"
		        + "    UserID INT PRIMARY KEY AUTO_INCREMENT,\r\n"
		        + "    Username VARCHAR(255) NOT NULL,\r\n"
		        + "    Email VARCHAR(255) NOT NULL,\r\n"
		        + "    Password VARCHAR(255) NOT NULL,\r\n"
		        + "    Role VARCHAR(255) NOT NULL\r\n"
		        + ");";

		String query2 = "CREATE TABLE IF NOT EXISTS Items (\r\n"
		        + "    ItemId INT PRIMARY KEY AUTO_INCREMENT,\r\n"
		        + "    ItemName VARCHAR(255) NOT NULL,\r\n"
		        + "    ItemDescription VARCHAR(255) NOT NULL,\r\n"
		        + "    Price INT NOT NULL,\r\n"
		        + "    UserID INT NOT NULL,\r\n"
		        + "    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE \r\n"
		        + ");";

		String query3 = "CREATE TABLE IF NOT EXISTS TransactionHeaders (\r\n"
		        + "    TransactionID INT PRIMARY KEY AUTO_INCREMENT,\r\n"
		        + "    UserID INT NOT NULL,\r\n"
		        + "    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE\r\n"
		        + ");";

		String query4 = "CREATE TABLE IF NOT EXISTS PanelHeaders (\r\n"
		        + "    PanelID INT PRIMARY KEY AUTO_INCREMENT,\r\n"
		        + "    PanelTitle VARCHAR(255) NOT NULL,\r\n"
		        + "    PanelDescription VARCHAR(255) NOT NULL,\r\n"
		        + "    Location VARCHAR(255) NOT NULL,\r\n"
		        + "    StartTime DATETIME NOT NULL,\r\n"
		        + "    EndTime DATETIME NOT NULL,\r\n"
		        + "    IsFinished BOOLEAN NOT NULL,\r\n"
		        + "    UserID INT NOT NULL,\r\n"
		        + "    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE\r\n"
		        + ");";

		String query5 = "CREATE TABLE IF NOT EXISTS TransactionDetails(\r\n"
		        + "    TransactionID INT,\r\n"
		        + "    ItemID INT,\r\n"
		        + "    Quantity INT,\r\n"
		        + "    FOREIGN KEY (TransactionID) REFERENCES TransactionHeaders(TransactionID) ON UPDATE CASCADE ON DELETE CASCADE,\r\n"
		        + "    FOREIGN KEY (ItemID) REFERENCES Items(ItemID) ON UPDATE CASCADE ON DELETE CASCADE\r\n"
		        + ");";

		String query6 = "CREATE TABLE IF NOT EXISTS PanelDetails(\r\n"
		        + "    PanelID INT,\r\n"
		        + "    UserID INT,\r\n"
		        + "    FOREIGN KEY (PanelID) REFERENCES PanelHeaders(PanelID) ON UPDATE CASCADE ON DELETE CASCADE,\r\n"
		        + "    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON UPDATE CASCADE ON DELETE CASCADE\r\n"
		        + ");";
		
		con.execute(query);
		con.execute(query2);
		con.execute(query3);
		con.execute(query4);
		con.execute(query5);
		con.execute(query6);
		
		
		String checkAdminQuery = "SELECT * FROM Users WHERE Role = 'Admin' LIMIT 1";
		ResultSet resultSet = con.executeQuery(checkAdminQuery);

		try {
			if (!resultSet.next()) {
			    String queryAdmin = "INSERT INTO `Users`(`Username`, `Email`, `Password`, `Role`) VALUES ('Admin', 'admin@gmail.com', 'admin1234', 'Admin')";
			    con.executeUpdate(queryAdmin);
			    System.out.println("Admin account created successfully.");
			} else {
			    System.out.println("Admin account already exists.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
