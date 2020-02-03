package com.assessment.cox.entity;

/**
 * Created by akhilesh on 2/1/20.
 */
public enum DaysOfWeek {
  MONDAY("Mon"), TUESDAY("Tue"),WEDNESDAY("Wed"),THURSDAY("Thurs"),FRIDAY("Fri"),SATURDAY("Sat"),SUNDAY("Sun"),Mon("MONDAY"), Tue("TUESDAY"),Wed("WEDNESDAY"),Thurs("THURSDAY"),Fri("FRIDAY"),Sat("SATURDAY"),Sun("SUNDAY");
  private String day;
  private DaysOfWeek(String day) {
    this.day = day;
  }
  public String day() {
    return day;
  }
  public String namespace() {
    return day;
  }
}
