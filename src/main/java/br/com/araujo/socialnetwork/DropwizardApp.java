package br.com.araujo.socialnetwork;

import br.com.araujo.socialnetwork.configuration.ApplicationConfiguration;
import br.com.araujo.socialnetwork.dao.SocialNetworkDao;
import br.com.araujo.socialnetwork.dao.UserDao;
import br.com.araujo.socialnetwork.resources.SocialNetworkResource;
import br.com.araujo.socialnetwork.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropwizardApp extends Application<ApplicationConfiguration> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
        final SocialNetworkDao socialNetworkDao = new SocialNetworkDao();
        final UserDao userDao = new UserDao();

        environment.jersey().register(new SocialNetworkResource(socialNetworkDao));
        environment.jersey().register(new UserResource(socialNetworkDao, userDao));

        logger.info("Social-Network-API iniciado com sucesso!");
    }
}
