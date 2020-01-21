package AAA;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class User {
	String name;
	List <Activity> activities;
	InputStream inputStream;
    OutputStream OutputStream;
    
   
    public User(String name) {
    	activities = new ArrayList<Activity>();
    	this.name = name;
    }
    
    public void addActivity(Activity activity) {
    	activities.add(activity);
    }
     
    public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return OutputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		OutputStream = outputStream;
	}

	public String toString() {
    	return this.name;
    }

	public List<Activity> getActivities() {
		return activities;
	}
	
	public String[] getActivitiesArr() {
		String [] strArr = new String[activities.size()];
		int i = 0;
		for(Activity activityObj: activities) {
			strArr[i++] = activityObj.getTaskName();
		}
		return strArr;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public Activity getActivity(String name) {
		for(Activity activityObj : activities) {
	        if(activityObj.getTaskName().equals(name)) {
	        	return activityObj;
	        }
	    }
		return null;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumberOpenActivities() {
		int aux = 0;
		for(Activity actitityObj: activities) {
			if(actitityObj.getState()!=0)
				++aux;
		}
		return aux;
	}

	
}
