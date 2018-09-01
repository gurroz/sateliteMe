package rmit.mad.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class RouteInfo implements Parcelable {

    private int trackableId;
    private String startDate;
    private String endDate;
    private int timeStopped;
    private String location;

    public RouteInfo(int trackableId, Date startingDate, int timeStopped, double lat, double lng) {
        this.trackableId = trackableId;
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        this.startDate = dateFormat.format(startingDate);

        Calendar targetCalStart = Calendar.getInstance();
        targetCalStart.setTime(startingDate);
        targetCalStart.set(Calendar.MINUTE, targetCalStart.get(Calendar.MINUTE) + timeStopped);

        this.endDate = dateFormat.format(targetCalStart.getTime());
        this.timeStopped = timeStopped;
        this.location = lat + ","+ lng;
    }

    public RouteInfo(Parcel in) {
        trackableId = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
        timeStopped = in.readInt();
        location = in.readString();
    }

    public int getTrackableId() {
        return trackableId;
    }

    public void setTrackableId(int trackableId) {
        this.trackableId = trackableId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTimeStopped() {
        return timeStopped;
    }

    public void setTimeStopped(int timeStopped) {
        this.timeStopped = timeStopped;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(trackableId);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeInt(timeStopped);
        dest.writeString(location);
    }

    public static final Parcelable.Creator<RouteInfo> CREATOR = new Parcelable.Creator<RouteInfo>() {
        public RouteInfo createFromParcel(Parcel in) {
            return new RouteInfo(in);
        }

        public RouteInfo[] newArray(int size) {
            return new RouteInfo[size];
        }
    };


}
