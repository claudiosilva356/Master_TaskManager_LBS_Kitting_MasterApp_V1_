package AAA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String taskName;
	private  String taskDescription;
	private String details;
	private List<ActivityEvent> events;
	private  String quantity;
	private  int state;//1 - Ongoind ,  2 -  onhold, 0 - closed

	
	public Activity(String taskName, String taskDescription, String details, String quantity) {
		super();
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.quantity = quantity;
		this.details = details;
		this.state = 1; 
		this.events = new ArrayList<ActivityEvent>();
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public String getStateStr() {
		String str = "";
		if(state == 1) {
			str = "On Going";
		}else if(state == 2) {
			str = "On Hold";
		}else {
			str = "Done";
		}
		return str;
	}
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public void addEvents(String details) {
		events.add(new ActivityEvent(details));
	}
	
	public List<ActivityEvent> getEvents(){
		return this.events;
	}

	
	

}
