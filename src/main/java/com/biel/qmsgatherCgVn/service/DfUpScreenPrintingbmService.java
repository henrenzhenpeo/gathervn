package com.biel.qmsgatherCgVn.service;

import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author dafenqi
* @description 针对表【df_up_screen_printingbm(丝印BM2)】的数据库操作Service
* @createDate 2025-07-25 11:35:11
*/
public interface DfUpScreenPrintingbmService extends IService<DfUpScreenPrintingbm> {

    public void importExcel(MultipartFile file , String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception;

}
