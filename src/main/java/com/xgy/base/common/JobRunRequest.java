package com.xgy.base.common;

import lombok.Data;

@Data
public class JobRunRequest {

    private String jobName;

    private Long jobExecutionId;

    private boolean waitThread = true;
}
