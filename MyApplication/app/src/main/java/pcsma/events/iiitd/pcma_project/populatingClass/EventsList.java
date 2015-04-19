package pcsma.events.iiitd.pcma_project.populatingClass;

/**
 * Created by dell on 2/25/2015.
 */
public class EventsList {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String title;
    private String description;
    private String date;
    private String time;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public EventsList(String title, String description, String date, String time, String imageUrl){
        this.title=title;
        this.description=description;
        this.date=date;
        this.time=time;
        this.imageUrl=imageUrl;


    }
    public EventsList(String title, String description, String date, String time, String imageUrl, String id){
        this.title=title;
        this.description=description;
        this.date=date;
        this.time=time;
        this.imageUrl=imageUrl;
        this.id=id;


    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
