package correia.felipe.exo_app;

/**
 * Created by Felipe on 09/10/2017.
 */

public class PlanItem {
    private String devices_id;
    private String devices_number;
    private String devides_code;
    private String amount;
    private String code;
    private String description;
    private String trial_hold_setup_fee;
    private String trial_days;
    private String trial_enabled;
    private String name;



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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public void setDescription_plan(String description) {
        this.description = description;
    }

    public String getTrial_hold_setup_fee() {
        return trial_hold_setup_fee;
    }

    public void setTrial_hold_setup_fee(String trial_hold_setup_fee) {
        this.trial_hold_setup_fee = trial_hold_setup_fee;
    }

    public String getTrial_days() {
        return trial_days;
    }

    public void setTrial_days(String trial_days) {
        this.trial_days = trial_days;
    }

    public String getTrial_enabled() {
        return trial_enabled;
    }

    public void setTrial_enabled(String trial_enabled) {
        this.trial_enabled = trial_enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
