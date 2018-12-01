package disco.api.externalapi.twitter;

import java.util.Date;

public class TwitterGETResponse {

    private String text;
    private String name;
    private Date date;

    public TwitterGETResponse() {

    }

    public TwitterGETResponse(String text, String name, Date date) {
        this.text = text;
        this.name = name;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
