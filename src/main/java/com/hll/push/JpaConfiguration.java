package com.hll.push;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Author: huangll
 * Written on 17/6/14.
 */
@Configuration
@EnableJpaRepositories("com.hll.push.dao")
public class JpaConfiguration {


}
