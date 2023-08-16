package com.wsss.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class ButtonTimerJob implements Job {

/**
     * 核心方法,Quartz Job真正的执行逻辑.
     * @param context 中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("--------------定时任务执行逻辑---------------------");
    }
}