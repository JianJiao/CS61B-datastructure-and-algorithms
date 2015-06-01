/* Date.java */

import java.io.*;

class Date {

  /* Put your private data fields here. */
	private int year,month,day;
  /** Constructs a date with the given month, day and year.   If the date is
   *  not valid, the entire program will halt with an error message.
   *  @param month is a month, numbered in the range 1...12.
   *  @param day is between 1 and the number of days in the given month.
   *  @param year is the year in question, with no digits omitted.
   */
  public Date(int month, int day, int year) {
		if(isValidDate(month,day,year)){
			this.month = month;
			this.day = day;
			this.year = year;
		}else{
			System.out.println("Invalid date!");	
			System.exit(0);
		}

  }

  /** Constructs a Date object corresponding to the given string.
   *  @param s should be a string of the form "month/day/year" where month must
   *  be one or two digits, day must be one or two digits, and year must be
   *  between 1 and 4 digits.  If s does not match these requirements or is not
   *  a valid date, the program halts with an error message.
   */
  public Date(String s) {
		int month,day,year;
		s = s.trim();
		String[] nums = s.split("/");
		if(nums.length!=3){
			System.out.println("Wrong format!");
			System.exit(0);
		}
		month = Integer.parseInt(nums[0]);
		day = Integer.parseInt(nums[1]);
		year = Integer.parseInt(nums[2]);
		if(isValidDate(month,day,year)){
			this.month = month;
			this.day = day;
			this.year = year;
		}else{
			System.out.println("Invalid date!");	
			System.exit(0);
		}
  }

  /** Checks whether the given year is a leap year.
   *  @return true if and only if the input year is a leap year.
   */
  public static boolean isLeapYear(int year) {
		if(year%4==0){
			if(year%100==0){
				if(year%400==0){
					return true;	
				}else{
					return false;
				}
			}else{
				return true;	
			}
		}else{
			return false;
		}
  }

  /** Returns the number of days in a given month.
   *  @param month is a month, numbered in the range 1...12.
   *  @param year is the year in question, with no digits omitted.
   *  @return the number of days in the given month.
   */
  public static int daysInMonth(int month, int year) {
		boolean leap = isLeapYear(year);
		switch (month){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 2:
				if(leap){
					return 29;	
				}else{
					return 28;	
				}
			default:
				return 30;
		}
  }

	public int getYear(){
		return this.year;	
	}

	public int getMonth(){
		return this.month;	
	}

	public int getDay(){
		return this.day;	
	}

  /** Checks whether the given date is valid.
   *  @return true if and only if month/day/year constitute a valid date.
   *
   *  Years prior to A.D. 1 are NOT valid.
   */
  public static boolean isValidDate(int month, int day, int year) {
		if(month>0&&month<13){
			if(year>0){
				if(day>0&&day<=daysInMonth(month, year)){
					return true;	
				}
			}
		}
		return false;
	}

  /** Returns a string representation of this date in the form month/day/year.
   *  The month, day, and year are expressed in full as integers; for example,
   *  12/7/2006 or 3/21/407.
   *  @return a String representation of this date.
   */
  public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(month).append("/").append(day).append("/").append(year);
		return sb.toString();
  }

  /** Determines whether this Date is before the Date d.
   *  @return true if and only if this Date is before d. 
   */
  public boolean isBefore(Date d) {
		int[] rslt = compNumbers(d);
		int d1 = rslt[0];
		int d2 = rslt[1];
		if(d1<d2){
			return true;	
		}else{
			return false;	
		}
  }

	private int[] compNumbers(Date d){
		int d1 = this.year*10000 + this.month*100 + this.day;
		int d2 = d.getYear()*10000 + d.getMonth()*100 + d.getDay();
		int[] rslt = {d1, d2};
		return rslt;
	}



  /** Determines whether this Date is after the Date d.
   *  @return true if and only if this Date is after d. 
   */
  public boolean isAfter(Date d) {
		int[] rslt = compNumbers(d);
		int d1 = rslt[0];
		int d2 = rslt[1];
		if(d1>d2){
			return true;	
		}else{
			return false;	
		}
  }

  /** Returns the number of this Date in the year.
   *  @return a number n in the range 1...366, inclusive, such that this Date
   *  is the nth day of its year.  (366 is used only for December 31 in a leap
   *  year.)
   */
  public int dayInYear() {
		int dayth=0;
		for(int i=1;i<month;i++){
				dayth += daysInMonth(i,year);
		}
		dayth += day;
		return dayth;
  }

  /** Determines the difference in days between d and this Date.  For example,
   *  if this Date is 12/15/2012 and d is 12/14/2012, the difference is 1.
   *  If this Date occurs before d, the result is negative.
   *  @return the difference in days between d and this date.
   */
  public int difference(Date d) {
		int diff;
		long days1,days2;
		days1 = this.toDays();
		days2 = d.toDays();
		diff = (int) (days1 - days2);
		return diff;
  }
	
	private long toDays(){
		long days=0;
		for(int y=0;y<this.year;y++){
			days += daysInYear(y);
		}
		for(int m=0;m<this.month;m++){
			days += daysInMonth(m,this.year);
		}
		days += this.day;
		return days;
	}
//  //the below three methods all assume positive differences
//	private static int dayDiff(int endD,int startD){
//		return (endD - startD);
//	}
//	
//	private static int monthDiff(int endM, int startM){
//		int diff =0;
//		for(int m=startM;m<=endM;m++){
//			diff += daysInMonth()	
//		}
//	}
//	
//	private static int yearDiff(int endY, int startY){
//	
//	}

	private static int daysInYear(int year){
		if(isLeapYear(year)){
			return 366;	
		}else{
			return 365;	
		}
	}

  public static void main(String[] argv) {
    System.out.println("\nTesting constructors.");
    Date d1 = new Date(1, 1, 1);
    System.out.println("Date should be 1/1/1: " + d1);
    d1 = new Date("2/4/2");
    System.out.println("Date should be 2/4/2: " + d1);
    d1 = new Date("2/29/2000");
    System.out.println("Date should be 2/29/2000: " + d1);
    d1 = new Date("2/29/1904");
    System.out.println("Date should be 2/29/1904: " + d1);

    d1 = new Date(12, 31, 1975);
    System.out.println("Date should be 12/31/1975: " + d1);
    Date d2 = new Date("1/1/1976");
    System.out.println("Date should be 1/1/1976: " + d2);
    Date d3 = new Date("1/2/1976");
    System.out.println("Date should be 1/2/1976: " + d3);

    Date d4 = new Date("2/27/1977");
    Date d5 = new Date("8/31/2110");

    /* I recommend you write code to test the isLeapYear function! */
		
		System.out.println("\nTesting toString");
		Date d1992 = new Date(3,24,1992);
		System.out.println("3/24/1992 " + d1992);

		System.out.println();
		System.out.println("Testing isLeapYear");
		System.out.println("1800 " + isLeapYear(1800) +" should be false");
		System.out.println("1900 " + isLeapYear(1900) + " should be false");
		System.out.println("1600 " + isLeapYear(1600) + " should be true");
		System.out.println("2000 " + isLeapYear(2000) + " should be true");

		System.out.println();
		System.out.println("Testing daysInMonth");
		System.out.println("Days in 3,2014 " + daysInMonth(3,2014) + " should be 31");
		System.out.println("Days in 2,1600 " + daysInMonth(2,1600) + " should be 29");
		System.out.println("Days in 4,2013 " + daysInMonth(4,2013) + " should be 30");

		System.out.println();
		System.out.println("Testing isValidDate");
		System.out.println("3,14,2014 " + isValidDate(3,14,2014) + " should be true");
		System.out.println("9,32,2 " + isValidDate(9,32,2) + " should be false");
		System.out.println("2,29,1600 " + isValidDate(2,29,1600) + " should be true");

    System.out.println("\nTesting before and after.");
    System.out.println(d2 + " after " + d1 + " should be true: " + 
                       d2.isAfter(d1));
    System.out.println(d3 + " after " + d2 + " should be true: " + 
                       d3.isAfter(d2));
    System.out.println(d1 + " after " + d1 + " should be false: " + 
                       d1.isAfter(d1));
    System.out.println(d1 + " after " + d2 + " should be false: " + 
                       d1.isAfter(d2));
    System.out.println(d2 + " after " + d3 + " should be false: " + 
                       d2.isAfter(d3));

    System.out.println(d1 + " before " + d2 + " should be true: " + 
                       d1.isBefore(d2));
    System.out.println(d2 + " before " + d3 + " should be true: " + 
                       d2.isBefore(d3));
    System.out.println(d1 + " before " + d1 + " should be false: " + 
                       d1.isBefore(d1));
    System.out.println(d2 + " before " + d1 + " should be false: " + 
                       d2.isBefore(d1));
    System.out.println(d3 + " before " + d2 + " should be false: " + 
                       d3.isBefore(d2));

    System.out.println("\nTesting difference.");
    System.out.println(d1 + " - " + d1  + " should be 0: " + 
                       d1.difference(d1));
    System.out.println(d2 + " - " + d1  + " should be 1: " + 
                       d2.difference(d1));
    System.out.println(d3 + " - " + d1  + " should be 2: " + 
                       d3.difference(d1));
    System.out.println(d3 + " - " + d4  + " should be -422: " + 
                       d3.difference(d4));
    System.out.println(d5 + " - " + d4  + " should be 48762: " + 
                       d5.difference(d4));
  }
}
