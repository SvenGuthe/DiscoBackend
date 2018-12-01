package disco.api.externalapi.instagram;

public class InstagramGETResponse {

    private double lat;
    private double lng;
    private String name;
    private String imageURL;

    public InstagramGETResponse(double lat, double lng, String name, String imageURL) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.imageURL = imageURL;
    }

    public InstagramGETResponse(){

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
