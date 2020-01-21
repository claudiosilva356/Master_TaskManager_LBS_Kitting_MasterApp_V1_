package Master_TaskManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import AAA.Activity;
import AAA.ActivityEvent;
import AAA.User;

public class ServerCore {
	
	List <User> user;
	
	public ServerCore() {
		user = new ArrayList<User>();
	}
	
	private void addUser(String name) {
		user.add(new User(name));
	}
	
	public void setUserConnections(String userName,InputStream inputStream,OutputStream OutputStream) {
		User userAux = getUser(userName);
		userAux.setInputStream(inputStream);
		userAux.setOutputStream(OutputStream);
	}
	
	public void addActivity(String userName,String taskName,String taskDescription,String details,String quantity) {
		if(getUser(userName)==null) {
			addUser(userName);
			getUser(userName).addActivity(new Activity(taskName,taskDescription,details,quantity));
		}else {
			getUser(userName).addActivity(new Activity(taskName,taskDescription,details,quantity));
		}
	}
	
	private User getUser(String userName) {
		for(User userObj : user) {
	        if(userObj.toString().equals(userName)) {
	        	return userObj;
	        }
	    }
		return null;
	}
	
	public List<Activity> getActivities(String userName) {
		return getUser(userName).getActivities();
	}
	
	public void closeActivity(String userName, String activityName, String event) {
		User user = getUser(userName);
		user.getActivity(activityName).addEvents(event);
		user.getActivity(activityName).setState(0);
	}
	
	public void updateActivity(String userName, String activityName, String event) {
		User user = getUser(userName);
		user.getActivity(activityName).addEvents(event);
	}
	
	public void updateActivityState(String userName, String activityName, int state) {
		User user = getUser(userName);
		user.getActivity(activityName).setState(state);;
	}
	
	public List<String> listAllUsers() {
		List <String> auxStrArr = new ArrayList<String>();

		for(User userObj : user) {
	        String auxStr = "Name: *" + userObj.getName() + "* - " + userObj.getNumberOpenActivities() + " open tasks";
	        auxStrArr.add(auxStr);
		}
		return auxStrArr;
		
	}
	
	public List<String> listUserActivities(String userName){
		List <String> auxStrArr = new ArrayList<String>();
		User user = getUser(userName);
		for(Activity activityObj : user.getActivities()) {
	        String auxStr = "Task Details: *" + activityObj.getTaskName() + "* - " + activityObj.getTaskDescription() + " - " + activityObj.getStateStr();
	        auxStrArr.add(auxStr);
		}
		return auxStrArr;
	}
	
	
	public List<String> listUserEvents(String userName, String activity){
		List <String> auxStrArr = new ArrayList<String>();
		User user = getUser(userName);

		for(ActivityEvent activityEventObj : user.getActivity(activity).getEvents()) {
	        String auxStr = "Event: " + activityEventObj.getDetail() + " - " + activityEventObj.getDate();
	        auxStrArr.add(auxStr);
		}
		return auxStrArr;
		
	}

}
