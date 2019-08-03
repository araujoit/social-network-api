package br.com.araujo.socialnetwork;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.configuration.TestConfiguration;
import br.com.araujo.socialnetwork.dao.Dao;
import br.com.araujo.socialnetwork.dao.SocialNetworkRedis;
import br.com.araujo.socialnetwork.dao.UserRedis;
import br.com.araujo.socialnetwork.resources.SocialNetworkResource;
import br.com.araujo.socialnetwork.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DropwizardTest extends Application<TestConfiguration> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SimpleDateFormat defineDatePattern() {
        final SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df;
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setDateFormat(defineDatePattern());

        final Dao<SocialNetworkBean> socialNetworkDao = new SocialNetworkRedis(configuration.redis.host);
        final UserRedis userDao = new UserRedis(environment.getObjectMapper(), configuration.redis.host);

        environment.jersey().register(new SocialNetworkResource(socialNetworkDao));
        environment.jersey().register(new UserResource(socialNetworkDao, userDao));

        logger.info("Social-Network-API iniciado com sucesso!");
    }
}
