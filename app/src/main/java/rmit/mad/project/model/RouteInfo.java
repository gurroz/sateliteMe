package rmit.mad.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class RouteInfo implements Parcelable {

    private String trackableId;
    private String trackableName;
    private String meetingName;
    private String trackableType;
    private String startDate;
    private String endDate;
    private Date endDateValue;
    private int timeStopped;
    private String location;
    private String locationName;
    private String meetingTime;
    private String distanceTime;

    public RouteInfo(int trackableId, Date startingDate, int timeStopped, double lat, double lng) {
        this.trackableId = String.valueOf(trackableId);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        this.startDate = dateFormat.format(startingDate);

        Calendar targetCalStart = Calendar.getInstance();
        targetCalStart.setTime(startingDate);
        targetCalStart.set(Calendar.MINUTE, targetCalStart.get(Calendar.MINUTE) + timeStopped);

        this.endDateValue = targetCalStart.getTime();
        this.endDate = dateFormat.format(targetCalStart.getTime());
        this.timeStopped = timeStopped;
        this.location = lat + ","+ lng;
    }

    public RouteInfo(Parcel in) {
        trackableId = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        timeStopped = in.readInt();
        location = in.readString();
    }

    public void fillMissingInfo(Date meetingDate, String locationName, String trackableName, String trackabletype, String distance) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
        this.meetingTime = dateFormat.format(meetingDate);
        this.locationName = locationName;
        this.trackableName = trackableName;
        this.trackableType = trackabletype;
        this.distanceTime = distance;
    }

    public String getTrackableId() {
        return trackableId;
    }

    public void setTrackableId(String trackableId) {
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

    public String getTrackableName() {
        return trackableName;
    }

    public void setTrackableName(String trackableName) {
        this.trackableName = trackableName;
    }

    public Date getEndDateValue() {
        return endDateValue;
    }

    public void setEndDateValue(Date endDateValue) {
        this.endDateValue = endDateValue;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getTrackableType() {
        return trackableType;
    }

    public void setTrackableType(String trackableType) {
        this.trackableType = trackableType;
    }

    public String getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trackableId);
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RouteInfo{");
        sb.append("trackableId='").append(trackableId).append('\'');
        sb.append(", trackableName='").append(trackableName).append('\'');
        sb.append(", trackableType='").append(trackableType).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", endDateValue=").append(endDateValue);
        sb.append(", timeStopped=").append(timeStopped);
        sb.append(", location='").append(location).append('\'');
        sb.append(", locationName='").append(locationName).append('\'');
        sb.append(", meetingTime='").append(meetingTime).append('\'');
        sb.append(", distanceTime='").append(distanceTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
