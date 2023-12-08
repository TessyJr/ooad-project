package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Connect;

public class Panel {
	private static Connect con = Connect.getInstance();
	private Integer panelID;
	private Integer userID;
	private String title, desc, location;
	private Boolean isFinished;
	private Date start, end;

	public Integer getPanelID() {
		return panelID;
	}

	public void setPanelID(Integer panelID) {
		this.panelID = panelID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public static void addPanel(String title, String desc, String location, Date start, Date end, Integer userID) {
		String query = "INSERT INTO `PanelHeaders`(`PanelTitle`, `PanelDescription`, `Location`, `StartTime`, `EndTime`, `IsFinished`, `UserID`) VALUES (?, ?, ?, ?, ?, false, ?)";
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setString(1, title);
			ps.setString(2, desc);
			ps.setString(3, location);
			ps.setDate(4, start);
			ps.setDate(5, end);
			ps.setInt(6, userID);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static ObservableList<Panel> getAllPanelByInfluencer(Integer id) {
        ObservableList<Panel> panelList = FXCollections.observableArrayList();;

        String query = String.format("SELECT * FROM panelheaders WHERE UserID = '%d'", id);

        try (
        	PreparedStatement ps = con.preparedStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer panelId = rs.getInt("PanelID");
                String title = rs.getString("PanelTitle");
	            String desc = rs.getString("PanelDescription");
	            String location = rs.getString("Location");
	            Date startTime = rs.getDate("StartTime");
	            Date endTime = rs.getDate("EndTime");
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

	public Panel(Integer panelID, String title, String desc, String location, Date start,
			Date end, Boolean isFinished, Integer userID) {
		super();
		this.panelID = panelID;
		this.title = title;
		this.desc = desc;
		this.location = location;
		this.isFinished = isFinished;
		this.start = start;
		this.end = end;
		this.userID = userID;
	}
}
