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
import view.FanHomePage;

public class Panel {
	private static Connect con = Connect.getInstance();
	private Integer panelID;
	private Integer userID;
	private String panelTitle, panelDescription, location;
	private Boolean isFinished;
	private LocalDateTime startTime, endTime;

	public Integer getPanelID() {
		return panelID;
	}

	public void setPanelID(Integer panelID) {
		this.panelID = panelID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getPanelTitle() {
		return panelTitle;
	}

	public void setPanelTitle(String panelTitle) {
		this.panelTitle = panelTitle;
	}

	public String getPanelDescription() {
		return panelDescription;
	}

	public void setPanelDescription(String panelDescription) {
		this.panelDescription = panelDescription;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
	public static void finishPanel(int panelID) {
	    String query = "UPDATE PanelHeaders SET IsFinished = 1 WHERE PanelID = ?";
	    
	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setInt(1, panelID);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the exception as needed (log, throw, etc.)
	    }
	}

	public static void addPanel(String title, String desc, String location, LocalDateTime start, LocalDateTime end, Integer userID) {
		String query = "INSERT INTO `PanelHeaders`(`PanelTitle`, `PanelDescription`, `Location`, `StartTime`, `EndTime`, `IsFinished`, `UserID`) VALUES (?, ?, ?, ?, ?, false, ?)";
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setString(1, title);
			ps.setString(2, desc);
			ps.setString(3, location);
			ps.setTimestamp(4, Timestamp.valueOf(start));
	        ps.setTimestamp(5, Timestamp.valueOf(end));
			ps.setInt(6, userID);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static ObservableList<Panel> getAllPanelByInfluencer(Integer id) {
	    ObservableList<Panel> panelList = FXCollections.observableArrayList();

	    String query = String.format("SELECT * FROM panelheaders WHERE UserID = '%d'", id);

	    try (PreparedStatement ps = con.preparedStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Integer panelId = rs.getInt("PanelID");
	            String title = rs.getString("PanelTitle");
	            String desc = rs.getString("PanelDescription");
	            String location = rs.getString("Location");

	            // Use rs.getTimestamp() to retrieve Timestamp and convert to LocalDateTime
	            Timestamp startTimestamp = rs.getTimestamp("StartTime");
	            LocalDateTime startTime = startTimestamp.toLocalDateTime();

	            Timestamp endTimestamp = rs.getTimestamp("EndTime");
	            LocalDateTime endTime = endTimestamp.toLocalDateTime();

	            Boolean isFinished = rs.getBoolean("IsFinished");
	            Integer userId = rs.getInt("UserID");

	            Panel panel = new Panel(panelId, title, desc, location, startTime, endTime, isFinished, userId);
	            panelList.add(panel);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return panelList;
	}

	
	public static ObservableList<Panel> getAllPanels() {
	    ObservableList<Panel> panelList = FXCollections.observableArrayList();

	    String query = "SELECT * FROM panelheaders";

	    try (PreparedStatement ps = con.preparedStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Integer panelId = rs.getInt("PanelID");
	            String title = rs.getString("PanelTitle");
	            String desc = rs.getString("PanelDescription");
	            String location = rs.getString("Location");

	            Timestamp startTimestamp = rs.getTimestamp("StartTime");
	            LocalDateTime startTime = startTimestamp.toLocalDateTime();

	            Timestamp endTimestamp = rs.getTimestamp("EndTime");
	            LocalDateTime endTime = endTimestamp.toLocalDateTime();

	            Boolean isFinished = rs.getBoolean("IsFinished");
	            Integer userId = rs.getInt("UserID");

	            Panel panel = new Panel(panelId, title, desc, location, startTime, endTime, isFinished, userId);
	            panelList.add(panel);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return panelList;
	}
	
	public static void addAttendee(Integer panelId, Integer userId) {
		String query = "INSERT INTO `PanelDetails`(`PanelID`, `UserID`) VALUES (?, ?)";
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setInt(1, panelId);
			ps.setInt(2, userId);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static boolean hasAttended(Integer panelId, Integer userId) {
	    String query = "SELECT COUNT(*) FROM PanelDetails WHERE PanelID = ? AND UserID = ?";
	    
	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setInt(1, panelId);
	        ps.setInt(2, userId);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	public static ObservableList<String> getAllAttendees(Integer panelId) {
	    ObservableList<String> attendeesList = FXCollections.observableArrayList();

	    String query = "SELECT u.Username " +
	                   "FROM users u " +
	                   "JOIN paneldetails pd ON u.UserID = pd.UserID " +
	                   "WHERE pd.PanelID = ?";

	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setInt(1, panelId);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                String username = rs.getString("Username");
	                attendeesList.add(username);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return attendeesList;
	}

	
	public Panel(Integer panelID, String panelTitle, String panelDescription, String location,
			LocalDateTime startTime, LocalDateTime endTime, Boolean isFinished, Integer userID) {
		super();
		this.panelID = panelID;
		this.panelTitle = panelTitle;
		this.panelDescription = panelDescription;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isFinished = isFinished;
		this.userID = userID;
	}
}
