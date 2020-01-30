package com.example.mychatappnetty.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SpringSessionConfig {

  public SpringSessionConfig() {}

  @Bean
  public CookieSerializer httpSessionIdResolver() {
    DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
    // deal with Cross-domain problem
    cookieSerializer.setSameSite(null);
    System.out.println("set setSameSite to be null (default Lax)");
    return cookieSerializer;
  }
}
