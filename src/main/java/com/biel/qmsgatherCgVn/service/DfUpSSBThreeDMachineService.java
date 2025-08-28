package com.biel.qmsgatherCgVn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biel.qmsgatherCgVn.domain.DfUpSSBThreeDMachine;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/28 15:39
 */
public interface DfUpSSBThreeDMachineService extends IService<DfUpSSBThreeDMachine> {
    void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId)throws  Exception;
}
