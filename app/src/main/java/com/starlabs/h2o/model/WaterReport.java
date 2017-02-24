package com.starlabs.h2o.model;

import android.location.Location;

import java.sql.Time;
import java.util.Date;

/**
 * Created by chase on 2/22/17.
 */

public class WaterReport {
    private Time creationTime;
    private int reportNumber;
    private String reporterName;
    private Location location;
    private WaterType type;
    private WaterCondition condition;

    public WaterReport(Time time, int reportNumber, String reporterName, Location location, WaterType type, WaterCondition condition) {
        creationTime = time;
        this.reportNumber = reportNumber;
        this.reporterName = reporterName;
        this.location = location;
        this.type = type;
        this.condition = condition;
    }

    /**
     * Gets the creation time
     *
     * @return The time the report was created
     */
    public Time getCreationTime() {
        return creationTime;
    }

    /**
     * Setter for the creation time
     *
     * @param creationTime The time the report was created
     */
    public void setCreationTime(Time creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Getter for the report number
     *
     * @return The number of the report
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Setter for the report number
     *
     * @param reportNumber The number of the report
     */
    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    /**
     * Getter for the reporterName
     *
     * @return The name of the user who created the report
     */
    public String getReporterName() {
        return reporterName;
    }

    /**
     * Setter for the reporterName
     *
     * @param reporterName The name of the user who created the report
     */
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    /**
     * Getter for the location
     *
     * @return The location the report was created
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Setter for the location
     *
     * @param location The location the report was created
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Getter for the waterType
     *
     * @return The type of the water
     */
    public WaterType getType() {
        return type;
    }

    /**
     * Setter for the waterType
     *
     * @param type The type of the water
     */
    public void setType(WaterType type) {
        this.type = type;
    }

    /**
     * Getter for the waterConditon
     *
     * @return The water condition
     */
    public WaterCondition getCondition() {
        return condition;
    }

    /**
     * Setter for the waterCondition
     *
     * @param condition The condition of the water
     */
    public void setCondition(WaterCondition condition) {
        this.condition = condition;
    }
}
