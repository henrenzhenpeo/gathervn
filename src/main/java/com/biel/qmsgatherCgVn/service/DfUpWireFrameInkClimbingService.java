package com.biel.qmsgatherCgVn.service;

import com.biel.qmsgatherCgVn.domain.DfUpWireFrameInkClimbing;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author dafenqi
* @description 针对表【df_up_wire_frame_ink_climbing(线框油墨爬高)】的数据库操作Service
* @createDate 2025-07-24 15:46:00
*/
public interface DfUpWireFrameInkClimbingService extends IService<DfUpWireFrameInkClimbing> {

    public void importExcel(MultipartFile file , String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception;

}
