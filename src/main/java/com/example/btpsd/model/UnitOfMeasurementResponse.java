package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UnitOfMeasurementResponse {

    @JsonProperty("d")
    private UnitOfMeasurementWrapper d;

    public List<UnitOfMeasurement> getUnitOfMeasurements() {
        return d.getResults();
    }

    public void setD(UnitOfMeasurementWrapper d) {
        this.d = d;
    }

    static class UnitOfMeasurementWrapper {
        @JsonProperty("results")
        private List<UnitOfMeasurement> results;

        public List<UnitOfMeasurement> getResults() {
            return results;
        }

        public void setResults(List<UnitOfMeasurement> results) {
            this.results = results;
        }
    }

}
