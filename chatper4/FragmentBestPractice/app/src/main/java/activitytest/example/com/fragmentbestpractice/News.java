package activitytest.example.com.fragmentbestpractice;

public class News {
    private String title;
    private String content;

    public String getTitle(){
        return title;
    }
    public void getTitle(String title){
        this.title = title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content) { this.content = content; }
}