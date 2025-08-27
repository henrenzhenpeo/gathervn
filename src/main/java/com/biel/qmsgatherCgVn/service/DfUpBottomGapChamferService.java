package com.biel.qmsgatherCgVn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/26 23:07
 */
public interface DfUpBottomGapChamferService extends IService<DfUpBottomGapChamfer> {
    void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId)throws Exception;
}
