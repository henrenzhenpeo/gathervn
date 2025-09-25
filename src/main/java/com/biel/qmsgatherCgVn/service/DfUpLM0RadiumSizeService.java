package com.biel.qmsgatherCgVn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biel.qmsgatherCgVn.domain.DfUpLM0RadiumSize;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/9/25 17:15
 */

public interface DfUpLM0RadiumSizeService extends IService<DfUpLM0RadiumSize> {

    /**
     * 2D镭码,镭码尺寸导入接口
     */
    void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId)throws Exception;
}
