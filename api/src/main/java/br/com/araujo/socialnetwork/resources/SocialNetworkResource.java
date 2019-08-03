package br.com.araujo.socialnetwork.resources;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.dao.Dao;
import br.com.araujo.socialnetwork.dao.SocialNetworkDao;
import org.apache.commons.lang3.RandomUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/social-network")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SocialNetworkResource {
    private final Dao<SocialNetworkBean> socialNetworkDao;

    public SocialNetworkResource(Dao<SocialNetworkBean> socialNetworkDao) {
        this.socialNetworkDao = socialNetworkDao;
    }

    @GET
    @Path("/{name}")
    public SocialNetworkBean getByName(@PathParam("name") String name) {
        return socialNetworkDao
                .getByName(name)
                .orElseThrow(() -> new NotFoundException("Rede social não encontrada."));
    }

    @POST
    @Path("/{name}")
    public SocialNetworkBean post(@PathParam("name") String name) {
        if (socialNetworkDao.getByName(name).isPresent())
            throw new ForbiddenException(String.format("Rede social %s já existente!", name));

        socialNetworkDao.save(new SocialNetworkBean(RandomUtils.nextInt(1, 10), name));
        return getByName(name);
    }
}
