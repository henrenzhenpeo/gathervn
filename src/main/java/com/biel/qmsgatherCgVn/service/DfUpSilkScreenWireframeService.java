package com.biel.qmsgatherCgVn.service;

import com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author dafenqi
* @description 针对表【df_up_silk_screen_wireframe(丝印线框)】的数据库操作Service
* @createDate 2025-07-25 11:35:16
*/
public interface DfUpSilkScreenWireframeService extends IService<DfUpSilkScreenWireframe> {
    public void importExcel(MultipartFile file , String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception;

}
