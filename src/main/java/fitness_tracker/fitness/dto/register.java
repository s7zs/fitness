package fitness_tracker.fitness.dto;

import java.util.Date;


public class register {
    
     
        private long userid;

  
        private String email;

        
        private String password;

      
        private String phonenumber;

       
        private char gender;

        private int age;

        private float weight;

       
        private float height;

       
        private String past_health_conditions;
private Date startdate;
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getPast_health_conditions() {
        return past_health_conditions;
    }

    public void setPast_health_conditions(String past_health_conditions) {
        this.past_health_conditions = past_health_conditions;
    }

    public Date getStartdate() {
        return startdate;
    }


}
