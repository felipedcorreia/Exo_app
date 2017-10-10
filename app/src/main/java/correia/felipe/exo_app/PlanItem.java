package correia.felipe.exo_app;

/**
 * Created by Felipe on 09/10/2017.
 */

public class PlanItem {
    private String devices_id;
    private String devices_number;
    private String devides_code;
    private String amounth;
    private String code;
    private String description;


    public PlanItem() {
        super();
    }


    public String getDevices_id() {
        return devices_id;
    }

    public void setDevices_id(String devices_id) {
        this.devices_id = devices_id;
    }

    public String getDevices_number() {
        return devices_number;
    }

    public void setDevices_number(String devices_number) {
        this.devices_number = devices_number;
    }

    public String getDevides_code() {
        return devides_code;
    }

    public void setDevides_code(String devides_code) {
        this.devides_code = devides_code;
    }

    public String getAmounth() {
        return amounth;
    }

    public void setAmounth(String amounth) {
        this.amounth = amounth;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
