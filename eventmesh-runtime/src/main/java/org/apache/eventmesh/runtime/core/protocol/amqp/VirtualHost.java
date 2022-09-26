package org.apache.eventmesh.runtime.core.protocol.amqp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import lombok.Getter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * define virtual host in amqp
 * currently only support default vhost: "/"
 */
public class VirtualHost {
    @Getter
    private final String nameSpace;

    private static final LoadingCache<String, VirtualHost> cache;

    public static final VirtualHost DEFAULT_VHOST;

    private VirtualHost(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public static VirtualHost get(String nameSpace) {
        if (nameSpace != null && !nameSpace.isEmpty()) {
            try {
                return cache.get(nameSpace);
            } catch (ExecutionException | UncheckedExecutionException var2) {
                throw (RuntimeException) var2.getCause();
            }
        } else {
            throw new IllegalArgumentException("Invalid null namespace: " + nameSpace);
        }
    }

    static {
        cache = CacheBuilder.newBuilder()
                .maximumSize(100000L)
                .expireAfterAccess(30L, TimeUnit.MINUTES)
                .build(new CacheLoader<String, VirtualHost>() {
                    @Override
                    public VirtualHost load(String key) throws Exception {
                        return new VirtualHost(key);
                    }
                });
        DEFAULT_VHOST = get("/");
    }
}
