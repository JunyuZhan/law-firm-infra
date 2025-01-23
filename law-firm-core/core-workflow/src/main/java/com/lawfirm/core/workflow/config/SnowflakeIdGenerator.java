package com.lawfirm.core.workflow.config;

import org.flowable.common.engine.impl.cfg.IdGenerator;

/**
 * 雪花ID生成器
 */
public class SnowflakeIdGenerator implements IdGenerator {
    
    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = ~(-1L << workerIdBits);
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = ~(-1L << sequenceBits);
    private final long twepoch = 1288834974657L;
    
    public SnowflakeIdGenerator() {
        this(0L, 0L);
    }
    
    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than " + maxWorkerId + " or less than 0");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than " + maxDatacenterId + " or less than 0");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    
    @Override
    public synchronized String getNextId() {
        long timestamp = timeGen();
        
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        }
        
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        
        lastTimestamp = timestamp;
        
        long id = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
        
        return String.valueOf(id);
    }
    
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    
    private long timeGen() {
        return System.currentTimeMillis();
    }
} 