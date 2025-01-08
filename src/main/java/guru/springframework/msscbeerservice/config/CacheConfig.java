package guru.springframework.msscbeerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;

import java.net.URISyntaxException;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

@EnableCaching
@Configuration
public class CacheConfig {
	@Bean
    public CacheManager cacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        javax.cache.CacheManager jCacheManager = null;
		try {
			jCacheManager = provider.getCacheManager(
			    getClass().getResource("/ehcache.xml").toURI(),
			    getClass().getClassLoader()
			);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new JCacheCacheManager(jCacheManager);
    }
}
