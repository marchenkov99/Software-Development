package models;import java.util.List;public class Security extends Person {    private List<Marker> markers;    private Chief chief;    public List<Marker> getMarkers() {        return markers;    }    public void setMarkers(List<Marker> markers) {        this.markers = markers;    }    public Chief getChief() {        return chief;    }    public void setChief(Chief chief) {        this.chief = chief;    }}