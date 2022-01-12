package com.marcs.app.featureAccess.client.domain;

/**
 * Feature object to map
 * 
 * @author Sam Butler
 * @since May 20, 2021
 */
public class Feature {

    private String app;

    private String feature;

    private String access;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
