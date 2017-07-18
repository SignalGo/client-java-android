package ir.atitec.signalgoApp;

import android.content.SharedPreferences;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by whiteman on 7/13/2016.
 */
public class PassengerInfo {

    private int id;
    private String name;
    private String family;
    private int sex;
    private int score;
    private String gmail;
    private String userName;
    private String password;
    private DateTime birthDay;
    private int jobId;
    private int jobPlaceId;
    private String reagentNumber;
    private int userId;
    private List<Integer> roles;
    private int regionId;
    @JsonIgnore
    private int regionStateId;


    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public DateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(DateTime birthDay) {
        this.birthDay = birthDay;
    }

    public int getJobPlaceId() {
        return jobPlaceId;
    }

    public void setJobPlaceId(int jobPlaceId) {
        this.jobPlaceId = jobPlaceId;
    }

    public String getReagentNumber() {
        return reagentNumber;
    }

    public void setReagentNumber(String reagentNumber) {
        this.reagentNumber = reagentNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getRegionStateId() {
        return regionStateId;
    }

    public void setRegionStateId(int regionStateId) {
        this.regionStateId = regionStateId;
    }

//    public static PassengerInfo getSelf() {
//        PassengerInfo p = new PassengerInfo();
//
//        p.setId(App.sp.getInt(App.context.getString(R.string.pref_id), -1));
//        p.setName(App.sp.getString(App.context.getString(R.string.pref_name), ""));
//        p.setFamily(App.sp.getString(App.context.getString(R.string.pref_family), ""));
//        p.setGmail(App.sp.getString(App.context.getString(R.string.pref_email), ""));
//        p.setSex(App.sp.getInt(App.context.getString(R.string.pref_gender), -1));
//        p.setJobId(App.sp.getInt(App.context.getString(R.string.pref_job), 0));
//        p.setJobPlaceId(App.sp.getInt(App.context.getString(R.string.pref_jobplace), 0));
//        p.setBirthDay(new DateTime(App.sp.getLong(App.context.getString(R.string.pref_birthday), 0)));
//        p.setUserId(App.sp.getInt(App.context.getString(R.string.pref_userid), 0));
//        p.setRegionId(App.sp.getInt(App.context.getString(R.string.pref_regionId), 0));
//        p.setRegionStateId(App.sp.getInt(App.context.getString(R.string.pref_regionStateId), 0));
//        return p;
//    }
//
//    public void save() {
//        SharedPreferences.Editor editor = App.sp.edit();
//        editor.putInt(App.context.getString(R.string.pref_id), id);
//        editor.putString(App.context.getString(R.string.pref_name), name);
//        editor.putString(App.context.getString(R.string.pref_family), family);
//        editor.putString(App.context.getString(R.string.pref_email), gmail);
//        editor.putInt(App.context.getString(R.string.pref_gender), sex);
//        editor.putInt(App.context.getString(R.string.pref_job), jobId);
//        editor.putInt(App.context.getString(R.string.pref_jobplace), jobPlaceId);
//        editor.putLong(App.context.getString(R.string.pref_birthday), birthDay != null ? birthDay.getMillis() : 0);
//        editor.putInt(App.context.getString(R.string.pref_userid), userId);
//        editor.putInt(App.context.getString(R.string.pref_regionId), regionId);
//        editor.putInt(App.context.getString(R.string.pref_regionStateId), regionStateId);
//        editor.commit();
//
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public boolean isSponsor() {
        if (roles == null)
            return false;
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i) == 6) {
                return true;
            }
        }
        return false;
    }
}
