package pcsma.events.iiitd.pcma_project.populatingClass;

/**
 * Created by dell on 4/7/2015.
 */
public class DescussionsList {
    public String name,date,time,description;


    public DescussionsList(String name, String date, String time, String description){
        this.name=name;
        this.description=description;
        this.time=time;
        this.date=date;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
