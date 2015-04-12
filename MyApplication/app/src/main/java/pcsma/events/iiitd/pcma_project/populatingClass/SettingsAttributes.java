package pcsma.events.iiitd.pcma_project.populatingClass;


public class SettingsAttributes {
    private String heading;
    private String content;

    public SettingsAttributes(){

    }

    public SettingsAttributes(String heading,String content){
        this.heading=heading;
        this.content=content;

    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
