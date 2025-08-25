package com.biel.qmsgatherCgVn.service;

import com.biel.qmsgatherCgVn.domain.DfUpFileMaterialSize;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author dafenqi
* @description 针对表【df_up_file_material_size(膜材尺寸)】的数据库操作Service
* @createDate 2025-07-28 14:16:14
*/
public interface DfUpFileMaterialSizeService extends IService<DfUpFileMaterialSize> {

    public void importExcel(MultipartFile file , String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception;

}
