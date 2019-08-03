package br.com.araujo.socialnetwork.dao;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SocialNetworkRedis implements Dao<SocialNetworkBean> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String MAIN_KEY = "socialnetwork";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Jedis jedis;

    public SocialNetworkRedis(String host) {
        this.jedis = new Jedis(host);
    }

    @Override
    public Optional<SocialNetworkBean> getByName(String name) {
        return getAll().stream()
                .filter(f -> f.name.equals(name))
                .findFirst();
    }

    @Override
    public Optional<SocialNetworkBean> get(long id) {
        final String hget = jedis.hget(MAIN_KEY, String.valueOf(id));
        try {
            if (StringUtils.isNotEmpty(hget))
                return Optional.ofNullable(MAPPER.readValue(hget, SocialNetworkBean.class));
        } catch (Exception e) {
            logger.error("Falha na tentativa de obter socialNetwork: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<SocialNetworkBean> getAll() {
        return jedis
                .hgetAll(MAIN_KEY)
                .values().stream()
                .map(f -> {
                    try {
                        return MAPPER.readValue(f, SocialNetworkBean.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    @Override
    public void save(SocialNetworkBean socialNetworkBean) {
        try {
            final String socialNetworkBeanJson = MAPPER.writeValueAsString(socialNetworkBean);
            final Long hset = jedis.hset(MAIN_KEY, String.valueOf(socialNetworkBean.id), socialNetworkBeanJson);
            logger.trace("Salvo {}", hset);
        } catch (JsonProcessingException e) {
            logger.error("Falha na tentativa de persistir o socialNetwork: " + socialNetworkBean.toString(), e);
        }
    }

    @Override
    public void update(SocialNetworkBean socialNetworkBean, String[] params) {
        final String socialNetWorkJson = jedis.hget(MAIN_KEY, String.valueOf(socialNetworkBean.id));
        try {
            final SocialNetworkBean socialNetworkBean1 = MAPPER.readValue(socialNetWorkJson, SocialNetworkBean.class);
            socialNetworkBean1.name = params[0];

            final String socialNetworkJsonModified = MAPPER.writeValueAsString(socialNetworkBean1);
            final Long hset = jedis.hset(MAIN_KEY, String.valueOf(socialNetworkBean.id), socialNetworkJsonModified);
            logger.trace("Atualizado {}", hset);
        } catch (IOException e) {
            logger.error("Falha na tentativa de atualizar socialNetwork: " + socialNetWorkJson, e);
        }
    }

    @Override
    public void delete(SocialNetworkBean socialNetworkBean) {
        final Long hdel = jedis.hdel(MAIN_KEY, String.valueOf(socialNetworkBean.id));
        logger.trace("Removido {}", hdel);
    }
}
