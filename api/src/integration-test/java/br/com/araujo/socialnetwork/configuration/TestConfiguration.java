package br.com.araujo.socialnetwork.configuration;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class TestConfiguration extends Configuration {

    @NotNull
    public RedisConfiguration redis;

    public static class RedisConfiguration {
        public String host;
    }
}
