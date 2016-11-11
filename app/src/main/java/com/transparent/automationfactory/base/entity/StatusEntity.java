package com.transparent.automationfactory.base.entity;

/**
 * Created by yzbzz on 2016/11/11.
 */

public class StatusEntity extends BaseEntity {

    private String name;
    private String workModel;
    private String outputMA;
    private String outputVoltage;
    private String lineT;
    private String peopleCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkModel() {
        return workModel;
    }

    public void setWorkModel(String workModel) {
        this.workModel = workModel;
    }

    public String getOutputMA() {
        return outputMA;
    }

    public void setOutputMA(String outputMA) {
        this.outputMA = outputMA;
    }

    public String getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(String outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public String getLineT() {
        return lineT;
    }

    public void setLineT(String lineT) {
        this.lineT = lineT;
    }

    public String getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(String peopleCount) {
        this.peopleCount = peopleCount;
    }
}
