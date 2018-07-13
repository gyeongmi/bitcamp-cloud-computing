package bitcamp.pms.domain;

import java.sql.Date;

public class Classroom {
    protected int no;
    protected String title;
    protected Date startDate;
    protected Date endDate;
    protected String room;
    
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    

}
