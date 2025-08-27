package com.biel.qmsgatherCgVn.service;

import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author dafenqi
* @description 针对表【df_up_screen_printing_varnish(丝印光油)】的数据库操作Service
* @createDate 2025-07-24 16:34:35
*/
public interface DfUpScreenPrintingVarnishService extends IService<DfUpScreenPrintingVarnish> {

    public void importExcel(MultipartFile file , String factory, String model, String process, String testProject, String uploadName,String batchId) throws Exception;


}
