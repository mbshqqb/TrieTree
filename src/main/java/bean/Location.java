package bean;

import java.util.HashMap;
import java.util.Map;

public class Location {
    String id;
    String pid;
    String name;
    String deep;
    String lat;
    String lng;
    String isz;
    public Location(){}
    public Location(String id, String pid, String name, String deep, String lat, String lng, String isz){
        this.id=id;
        this.pid=pid;
        this.name=name;
        this.deep=deep;
        this.lat=lat;
        this.lng=lng;
        this.isz=isz;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeep() {
        return deep;
    }

    public void setDeep(String deep) {
        this.deep = deep;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getIsz() {
        return isz;
    }

    public void setIsz(String isz) {
        this.isz = isz;
    }
}
