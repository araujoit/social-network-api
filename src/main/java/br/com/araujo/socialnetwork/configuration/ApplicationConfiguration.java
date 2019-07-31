package br.com.araujo.socialnetwork.configuration;

import io.dropwizard.Configuration;

public class ApplicationConfiguration extends Configuration {
    public String version;

    public RedisConfiguration redis;

    public static class RedisConfiguration {
        public String host;
    }
}
