package br.com.araujo.socialnetwork.resources;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.bean.UserBean;
import br.com.araujo.socialnetwork.dao.SocialNetworkDao;
import br.com.araujo.socialnetwork.dao.UserDao;
import org.apache.commons.lang3.RandomUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/social-network/{social-network}/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDao userDao;
    private final SocialNetworkDao socialNetworkDao;

    public UserResource(SocialNetworkDao socialNetworkDao, UserDao userDao) {
        this.socialNetworkDao = socialNetworkDao;
        this.userDao = userDao;
    }

    private SocialNetworkBean fetchSocialNetwork(String socialNetworkName) {
        return socialNetworkDao
                .getByName(socialNetworkName)
                .orElseThrow(() -> new NotFoundException("Rede social não encontrada."));
    }

    @POST
    public UserBean post(@BeanParam UserResourceBean userResourceBean) {
        final SocialNetworkBean socialNetworkBean = fetchSocialNetwork(userResourceBean.socialNetworkName);

        if (userDao.getByUserName(userResourceBean.name).isPresent())
            throw new ForbiddenException("Usuário já existente!");

        final UserBean userBean = new UserBean(
                RandomUtils.nextInt(1, 100),
                userResourceBean.name,
                userResourceBean.userName,
                socialNetworkBean
        );
        userDao.save(userBean);
        return userDao.get(userBean.getId())
                .orElseThrow(() -> new NotFoundException("Problemas ao tentar inserir usuário"));
    }

    @GET
    @Path("/{id}")
    public UserBean getById(@PathParam("social-network") String socialNetworkName, @PathParam("id") long id) {
        final SocialNetworkBean socialNetworkBean = fetchSocialNetwork(socialNetworkName);
        return userDao.get(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado na rede social " + socialNetworkBean.name));
    }

    @GET
    public List<UserBean> list(@PathParam("social-network") String socialNetworkName) {
        final SocialNetworkBean socialNetworkBean = fetchSocialNetwork(socialNetworkName);
        return userDao.getAll(socialNetworkBean);
    }
}
