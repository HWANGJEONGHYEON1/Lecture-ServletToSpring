package config;


import config.autoconfig.DispatcherConfiguration;
import config.autoconfig.TomcatWebserverConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({DispatcherConfiguration.class, TomcatWebserverConfiguration.class})
public @interface EnableMyAutoConfiguration {
}
