package br.com.araujo.socialnetwork.resources;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public class UserResourceBean {
    public @PathParam("social-network")
    String socialNetworkName;

    public @QueryParam("username")
    String userName;

    public @QueryParam("name")
    String name;
}
