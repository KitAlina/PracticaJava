package com.example.practica;

public class SomeObject {

        private String service;
        private Double cost;

        private int id;
        public SomeObject(String service, Double cost) {
            this.service = service;
            this.cost = cost;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public Double getCost() {
            return cost;
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }

        public int getId() {
            return id;
        }
    }

