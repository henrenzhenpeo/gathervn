package com.biel.qmsgatherCgVn.service;

import com.biel.qmsgatherCgVn.domain.DfUpTxPvdOverflowPlating;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author dafenqi
* @description 针对表【df_up_tx_pvd_overflow_plating(tx-pvd 溢镀)】的数据库操作Service
* @createDate 2025-07-28 14:14:39
*/
public interface DfUpTxPvdOverflowPlatingService extends IService<DfUpTxPvdOverflowPlating> {
    public void importExcel(MultipartFile file , String factory, String model, String process, String testProject,String uploadName, String batchId) throws Exception;

}
