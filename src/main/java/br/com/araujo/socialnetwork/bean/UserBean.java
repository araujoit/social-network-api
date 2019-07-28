package br.com.araujo.socialnetwork.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

public class UserBean {
    private long id;
    private SocialNetworkBean socialNetwork;
    private String name;
    private String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss.SSS")
    private DateTime creationDate;

    public UserBean(long id, String name, String userName, SocialNetworkBean socialNetwork) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.creationDate = DateTime.now();
        this.socialNetwork = socialNetwork;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SocialNetworkBean getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetworkBean socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }
}
