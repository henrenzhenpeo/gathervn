package com.biel.qmsgatherCgVn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biel.qmsgatherCgVn.domain.DfUpRadiumCodeSize;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/26 13:37
 */
public interface DfUpRediumCodeSizeService extends IService<DfUpRadiumCodeSize> {
    void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId,String createTime);
}
