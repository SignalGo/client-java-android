package ir.atitec.signalgoApp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import ir.atitec.signalgo.models.JSOGGenerator;

/**
 * Created by hamed on 2/7/2018.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class, property = JSOGGenerator.ID_KEY)
public class MyClass {
    public int userId;
    public int id;
    public String title;
    public String body;
}
