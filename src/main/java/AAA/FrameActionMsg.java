package AAA;

import java.io.Serializable;
import java.util.List;

public class FrameActionMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//#### Actions between server/Client ####
	//GetActivities: To client get activities - Client->Server
	//SetActivities: To Server send activities - Client->Server
	//SetActivityUpdate: To set a new activity status or update - Client<->Server
	//CloseActivity: To close an activity - Client<->Server
	//OnHoldActivity: To put an activity on hold, will not be sent to Client - Client<->Server
	
	private String userName;
	private  String action;
	private  String taskName;
	private  String taskDescription;
	private  double quantity;
	List <Activity> activities;
	private  int state;
	private String details;
	
	public FrameActionMsg() {
		this.userName = "";
		this.action = "";
		this.taskName = "";
		this.taskDescription = "";
		this.quantity = 0;
		this.state = 1;
		this.details = "";
	}
	
	
	public FrameActionMsg(String userName, String action, String taskName, String taskDescription, double quantity,
			int state, String details) {
		super();
		this.userName = userName;
		this.action = action;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.quantity = quantity;
		this.state = state;
		this.details = details;
	}



	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public List<Activity> getActivities() {
		return activities;
	}


	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public String[] getActivitiesArr() {
		String [] strArr = new String[activities.size()];
		int i = 0;
		for(Activity activityObj: activities) {
			strArr[i++] = activityObj.getTaskName();
		}
		return strArr;
	}

	
	
	
}
