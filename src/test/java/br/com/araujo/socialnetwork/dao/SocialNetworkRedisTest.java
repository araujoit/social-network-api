package br.com.araujo.socialnetwork.dao;

import br.com.araujo.socialnetwork.DropwizardApp;
import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.configuration.TestConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.*;

public class SocialNetworkRedisTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE =
            new DropwizardAppRule<>(DropwizardApp.class, ResourceHelpers.resourceFilePath("test-config.yml"));

    final TestConfiguration.RedisConfiguration redis = RULE.getConfiguration().redis;

    @Test
    public void shouldCrud() {
        final SocialNetworkRedis socialNetworkRedis = new SocialNetworkRedis(redis.host);
        final UserRedis userRedis = new UserRedis(RULE.getObjectMapper(), redis.host);

        final SocialNetworkBean socialNetworkBean = new SocialNetworkBean(10, "social-network-name");
        socialNetworkRedis.delete(socialNetworkBean);

        final Optional<SocialNetworkBean> socialNetworkBeanRedis = socialNetworkRedis.get(socialNetworkBean.id);
        assertNotNull(socialNetworkBeanRedis);
        assertFalse(socialNetworkBeanRedis.isPresent());

        socialNetworkRedis.save(socialNetworkBean);

        final Optional<SocialNetworkBean> socialNetworkBeanRedisP = socialNetworkRedis.get(socialNetworkBean.id);
        assertNotNull(socialNetworkBeanRedisP);
        assertTrue(socialNetworkBeanRedisP.isPresent());

        socialNetworkRedis.delete(socialNetworkBeanRedisP.get());

        final Optional<SocialNetworkBean> socialNetworkBeanNotExistant = socialNetworkRedis.get(socialNetworkBean.id);
        assertNotNull(socialNetworkBeanNotExistant);
        assertFalse(socialNetworkBeanRedis.isPresent());
    }
}
