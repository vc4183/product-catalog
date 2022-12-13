package services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("rest-properties")
@ApplicationScoped
public class RestProperties {

    @ConfigValue(watch = true)
    private Boolean maintenanceMode;

    public Boolean getMaintenanceMode(){
        return this.maintenanceMode;
    }

    public void setMaintenanceMode(final Boolean maintenanceMode){
        this.maintenanceMode = maintenanceMode;
    }
}
