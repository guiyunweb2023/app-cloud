package org.example.gateway.service;

import org.example.api.vo.log.LogInfo;

/**
 * @author Ths Sun
 * @create 2020/7/23.
 */
public interface LogService {
    void saveLog(LogInfo info);
}
