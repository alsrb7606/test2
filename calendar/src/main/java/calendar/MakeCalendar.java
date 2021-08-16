package calendar;

import java.util.Calendar;

public class MakeCalendar {
	Calendar cal;
	int year,month;
	
	public MakeCalendar() {
		// TODO Auto-generated constructor stub
		cal=Calendar.getInstance();
	}
	
	public MakeCalendar(Calendar cal,int year,int month) {
		// TODO Auto-generated constructor stub
		this.year=year;
		this.month=month;
		this.cal=cal;
		this.cal.set(this.year,this.month-1,1);
	}
	
	public Calendar getCal()
	{
		return cal;
	}
}
