package AAA;

import java.io.Serializable;
import java.util.Date;

public class ActivityEvent implements Serializable {
	String detail;
	Date date;

	public ActivityEvent(String details) {
		this.detail = details;
		date = new Date();
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
