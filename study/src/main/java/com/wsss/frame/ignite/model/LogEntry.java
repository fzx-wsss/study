package com.wsss.frame.ignite.model;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String operation;
    private String data;
    private Date timestamp;
    private String nodeId;
    
    public LogEntry() {}
    
    public LogEntry(String id, String operation, String data, String nodeId) {
        this.id = id;
        this.operation = operation;
        this.data = data;
        this.timestamp = new Date();
        this.nodeId = nodeId;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    
    public String getNodeId() { return nodeId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }
    
    @Override
    public String toString() {
        return "LogEntry{" +
                "id='" + id + '\'' +
                ", operation='" + operation + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                ", nodeId='" + nodeId + '\'' +
                '}';
    }
}
