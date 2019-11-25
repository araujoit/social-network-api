package br.com.araujo.socialnetwork;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.configuration.ApplicationConfiguration;
import br.com.araujo.socialnetwork.dao.Dao;
import br.com.araujo.socialnetwork.dao.SocialNetworkRedis;
import br.com.araujo.socialnetwork.dao.UserRedis;
import br.com.araujo.socialnetwork.resources.ApiResource;
import br.com.araujo.socialnetwork.resources.SocialNetworkResource;
import br.com.araujo.socialnetwork.resources.UserResource;
import com.smoketurner.dropwizard.consul.ConsulBundle;
import com.smoketurner.dropwizard.consul.ConsulFactory;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DropwizardApp extends Application<ApplicationConfiguration> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SimpleDateFormat defineDatePattern() {
        final SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df;
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        // Add Consul Bundle
        bootstrap.addBundle(new MyConsulConfiguration("network"));
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setDateFormat(defineDatePattern());

        final Dao<SocialNetworkBean> socialNetworkDao =
                new SocialNetworkRedis(configuration.redis.host);
        final UserRedis userDao = new UserRedis(environment.getObjectMapper(), configuration.redis.host);

        environment.jersey().register(new ApiResource());
        environment.jersey().register(new SocialNetworkResource(socialNetworkDao));
        environment.jersey().register(new UserResource(socialNetworkDao, userDao));

        logger.info("Social-Network-API iniciado com sucesso!");
    }

    private static final class MyConsulConfiguration extends ConsulBundle<ApplicationConfiguration> {

        @SuppressWarnings("WeakerAccess")
        public MyConsulConfiguration(String name) {
            super(name);
        }

        @Override
        public void initialize(Bootstrap<?> bootstrap) {
            super.initialize(bootstrap);
        }

        @Override
        public ConsulFactory getConsulFactory(ApplicationConfiguration configuration) {
            return configuration.consul;
        }
    }
}
