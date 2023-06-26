package com.springrest.servicerest.Core;

public class KeySchema {
    private String entityId;
    private String type;
    private Boolean optional;
    private String name;

    public KeySchema() {
    }

    public KeySchema(String entityId, String type, Boolean optional, String name) {
        this.entityId = entityId;
        this.type = type;
        this.optional = optional;
        this.name = name;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "KeySchema{" +
                "entityId='" + entityId + '\'' +
                ", type='" + type + '\'' +
                ", optional=" + optional +
                ", name='" + name + '\'' +
                '}';
    }

    public String toSchemaString() {
        return "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":"+this.optional.toString()+",\"field\":\"ENTITY_ID\"}],\"optional\":false,\"name\":\""+this.name+"\"},\"payload\":{\"ENTITY_ID\":\""+this.entityId+"\"}}";
    }
}