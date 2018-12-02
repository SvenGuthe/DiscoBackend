package disco.api.externalapi.weather;

public class WeatherGETResponse {

    private String datetime;
    private String condition;
    private String conditionDescription;
    private String icon;
    private Integer temp_min;
    private Integer temp_max;

    public WeatherGETResponse(String datetime, String condition, String conditionDescription, String icon, Integer temp_min, Integer temp_max) {
        this.datetime = datetime;
        this.condition = condition;
        this.conditionDescription = conditionDescription;
        this.icon = icon;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Integer temp_min) {
        this.temp_min = temp_min;
    }

    public Integer getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Integer temp_max) {
        this.temp_max = temp_max;
    }
}
