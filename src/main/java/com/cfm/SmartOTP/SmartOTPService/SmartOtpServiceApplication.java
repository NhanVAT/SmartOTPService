package com.cfm.SmartOTP.SmartOTPService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.cfm.SmartOTP.SmartOTPService.entity"})
public class SmartOtpServiceApplication extends SpringBootServletInitializer implements
    CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(SmartOtpServiceApplication.class);

  /**
   * This is a helper Java class that provides an alternative to creating a {@code web.xml} This
   * will be invoked only when the application is deployed to a Servlet container like Tomcat, JBoss
   * etc.
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SmartOtpServiceApplication.class);
  }

  @Bean
  public CharacterEncodingFilter characterEncodingFilter() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);
    return characterEncodingFilter;
  }

  public static void main(String[] args) {
    SpringApplication.run(SmartOtpServiceApplication.class, args);
    log.info("Smart OTP Service is running");
  }


  @Override
  public void run(String... args) throws Exception {

  }
}
