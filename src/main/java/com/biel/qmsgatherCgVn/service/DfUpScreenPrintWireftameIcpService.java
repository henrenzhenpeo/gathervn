package com.biel.qmsgatherCgVn.service;

import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author dafenqi
* @description 针对表【df_up_screen_print_wireftame_icp(丝印线框icp)】的数据库操作Service
* @createDate 2025-07-22 16:36:25
*/
public interface DfUpScreenPrintWireftameIcpService extends IService<DfUpScreenPrintWireftameIcp> {

    public void importExcel(MultipartFile file ,String factory, String model,String process,String testProject,String uploadName,String batchId) throws Exception;

}
