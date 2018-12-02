package disco.api.externalapi.googleplace;

import java.util.List;

public class GooglePlaceGETResponse {

    private String name;
    private Double lat;
    private Double lng;
    private Float rating;
    private List<String> type;
    private String address;
    private String photoURL;

    public GooglePlaceGETResponse() {

    }

    public GooglePlaceGETResponse(String name, Double lat, Double lng, Float rating, List<String> type, String address, String photoURL) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.rating = rating;
        this.type = type;
        this.address = address;
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
