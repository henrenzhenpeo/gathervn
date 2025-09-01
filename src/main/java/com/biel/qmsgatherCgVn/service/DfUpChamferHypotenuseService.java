package com.biel.qmsgatherCgVn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/8/27 16:02
 */
public interface DfUpChamferHypotenuseService extends IService<DfUpChamferHypotenuse> {
    void importExcel(MultipartFile file, String factory, String model, String process, String testProject, String uploadName, String batchId,String createTime)throws  Exception;
}
