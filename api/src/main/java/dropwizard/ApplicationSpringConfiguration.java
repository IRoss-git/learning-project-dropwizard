package dropwizard;

import com.ilya.db.dao.DaoSpringConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DaoSpringConfiguration.class})
@ComponentScan(
        basePackages = {"com.ilya.service","com.ilya.db","dropwizard"},
        basePackageClasses = MainApplication.class)
public class ApplicationSpringConfiguration {


}
