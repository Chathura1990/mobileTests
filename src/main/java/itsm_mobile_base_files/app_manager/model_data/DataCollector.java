package itsm_mobile_base_files.app_manager.model_data;

public class DataCollector {

    private String data;

    public String getData() {
        return data;
    }

    public DataCollector setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "DataCollector{" +
                "data='" + data + '\'' +
                '}';
    }
}
