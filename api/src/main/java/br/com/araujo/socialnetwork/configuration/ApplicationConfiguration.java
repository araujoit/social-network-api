package br.com.araujo.socialnetwork.configuration;

import com.smoketurner.dropwizard.consul.ConsulFactory;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class ApplicationConfiguration extends Configuration {

    @NotNull
    public RedisConfiguration redis;

    @NotNull
    public ConsulFactory consul;

    public ConsulFactory getConsulFactory() {
        return consul;
    }

    public static class RedisConfiguration {
        public String host;
    }
}
