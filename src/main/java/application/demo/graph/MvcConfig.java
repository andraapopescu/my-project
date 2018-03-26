package application.demo.graph;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/graph").setViewName("graph");
		registry.addViewController("/skillStatistics").setViewName("skillStatistics");
		registry.addViewController("/statistics").setViewName("statistics");

	}

}