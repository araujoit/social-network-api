package br.com.araujo.socialnetwork.dao;

import br.com.araujo.socialnetwork.bean.SocialNetworkBean;
import br.com.araujo.socialnetwork.bean.UserBean;
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

public class UserRedis {
    private static final String MAIN_KEY = "userbean";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper;
    private final Jedis jedis;

    public UserRedis(ObjectMapper objectMapper, String host) {
        this.objectMapper = objectMapper;
        this.jedis = new Jedis(host);
    }

    public Optional<UserBean> getByName(SocialNetworkBean socialNetworkBean, String name) {
        return getAll(socialNetworkBean).stream()
                .filter(f -> f.getUserName().equals(name))
                .findFirst();
    }

    private String formatKey(SocialNetworkBean socialNetworkBean) {
        return String.format("{}#{}", MAIN_KEY, socialNetworkBean);
    }

    public Optional<UserBean> get(SocialNetworkBean socialNetworkBean, long id) {
        final String hget = jedis.hget(formatKey(socialNetworkBean), String.valueOf(id));
        try {
            if (StringUtils.isNotEmpty(hget))
                return Optional.ofNullable(objectMapper.readValue(hget, UserBean.class));
        } catch (Exception e) {
            logger.error("Falha na tentativa de obter socialNetwork: " + id, e);
        }
        return Optional.empty();
    }

    public List<UserBean> getAll(SocialNetworkBean socialNetworkBean) {
        return jedis
                .hgetAll(formatKey(socialNetworkBean))
                .values().stream()
                .map(f -> {
                    try {
                        return objectMapper.readValue(f, UserBean.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    public void save(UserBean userBean) {
        try {
            final String socialNetworkBeanJson = objectMapper.writeValueAsString(userBean);
            final Long hset = jedis.hset(formatKey(userBean.getSocialNetwork()), String.valueOf(userBean.getId()), socialNetworkBeanJson);
            logger.trace("Salvo {}", hset);
        } catch (JsonProcessingException e) {
            logger.error("Falha na tentativa de persistir o user: " + userBean.toString(), e);
        }
    }

    public void update(UserBean userBean, String[] params) {
        final String socialNetWorkJson = jedis.hget(formatKey(userBean.getSocialNetwork()), String.valueOf(userBean.getId()));
        try {
            final SocialNetworkBean socialNetworkBean1 = objectMapper.readValue(socialNetWorkJson, SocialNetworkBean.class);
            socialNetworkBean1.name = params[0];

            final String socialNetworkJsonModified = objectMapper.writeValueAsString(socialNetworkBean1);
            final Long hset = jedis.hset(formatKey(userBean.getSocialNetwork()), String.valueOf(userBean.getId()), socialNetworkJsonModified);
            logger.trace("Atualizado {}", hset);
        } catch (IOException e) {
            logger.error("Falha na tentativa de atualizar user: " + socialNetWorkJson, e);
        }
    }

    public void delete(UserBean userBean) {
        final Long hdel = jedis.hdel(formatKey(userBean.getSocialNetwork()), String.valueOf(userBean.getId()));
        logger.trace("Removido {}", hdel);
    }
}
