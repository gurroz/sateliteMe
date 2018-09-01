package rmit.mad.project.model;

import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

public abstract class Tracking {
    private static final String TAG = Tracking.class.getName();

    private String id;
    private String idTrackable;
    private String title;
    private Date targetStartTime;
    private Date targetFinishTime;
    private Date meetTime;
    private String actualLocation;
    private String meetLocation;

    public Tracking() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if(this.id == null) {
            this.id = md5(idTrackable + targetStartTime.toString() + (Math.random() * 1000));
        }
    }

    public String getIdTrackable() {
        return idTrackable;
    }

    public void setIdTrackable(String idTrackable) {
        this.idTrackable = idTrackable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTargetStartTime() {
        return targetStartTime;
    }

    public void setTargetStartTime(Date targetStartTime) {
        this.targetStartTime = targetStartTime;
    }

    public Date getTargetFinishTime() {
        return targetFinishTime;
    }

    public void setTargetFinishTime(Date targetFinishTime) {
        this.targetFinishTime = targetFinishTime;
    }

    public Date getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(Date meetTime) {
        this.meetTime = meetTime;
    }

    public String getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(String actualLocation) {
        this.actualLocation = actualLocation;
    }

    public String getMeetLocation() {
        return meetLocation;
    }

    public void setMeetLocation(String meetLocation) {
        this.meetLocation = meetLocation;
    }


    public boolean validateDates() {
        return !(meetTime.before(targetStartTime) || meetTime.after(targetFinishTime));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tracking tracking = (Tracking) o;
        return Objects.equals(id, tracking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tracking{");
        sb.append("id='").append(id).append('\'');
        sb.append(", idTrackable='").append(idTrackable).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", targetStartTime=").append(targetStartTime);
        sb.append(", targetFinishTime=").append(targetFinishTime);
        sb.append(", meetTime=").append(meetTime);
        sb.append(", actualLocation='").append(actualLocation).append('\'');
        sb.append(", meetLocation='").append(meetLocation).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static String md5(String s) {
        MessageDigest digest;
        String hash = "";
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Error generating id: {}", e);
        }
        return hash;
    }
}
