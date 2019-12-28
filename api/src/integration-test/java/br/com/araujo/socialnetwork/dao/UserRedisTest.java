package br.com.araujo.socialnetwork.dao;

import br.com.araujo.socialnetwork.DropwizardTest;
import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.bean.UserBean;
import br.com.araujo.socialnetwork.configuration.TestConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.*;

public class UserRedisTest {
    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE =
            new DropwizardAppRule<>(DropwizardTest.class, ResourceHelpers.resourceFilePath("test-config.yml"));

    final TestConfiguration.RedisConfiguration redis = RULE.getConfiguration().redis;

    @Test
    public void shouldCrud() {
        final UserRedis userRedis = new UserRedis(RULE.getEnvironment().getObjectMapper(), redis.host);
        SocialNetworkBean socialNetworkBean = new SocialNetworkBean(2, "social-network-bean");
        final UserBean userBean = new UserBean(1L, "name", "user-name", socialNetworkBean);
        userRedis.delete(userBean);
        userRedis.save(userBean);

        final Optional<UserBean> userBeanPersisted = userRedis.get(socialNetworkBean, userBean.getId());
        assertNotNull(userBeanPersisted);
        assertTrue(userBeanPersisted.isPresent());

        final UserBean userBean1 = userBeanPersisted.get();
        userRedis.delete(userBean1);

        final Optional<UserBean> userBeanNotExistant = userRedis.get(userBean1.getSocialNetwork(), userBean1.getId());
        assertNotNull(userBeanNotExistant);
        assertFalse(userBeanNotExistant.isPresent());
    }
}
