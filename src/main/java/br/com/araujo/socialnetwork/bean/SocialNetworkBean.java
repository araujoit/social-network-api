package br.com.araujo.socialnetwork.bean;

public class SocialNetworkBean {
    public int id;
    public String name;

    public SocialNetworkBean() {
        this(-1, "");
    }

    public SocialNetworkBean(int id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }
}
