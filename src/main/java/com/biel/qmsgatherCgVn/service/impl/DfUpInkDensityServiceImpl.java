package com.biel.qmsgatherCgVn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biel.qmsgatherCgVn.domain.DfUpInkDensity;
import com.biel.qmsgatherCgVn.service.DfUpInkDensityService;
import com.biel.qmsgatherCgVn.mapper.DfUpInkDensityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author dafenqi
* @description 针对表【df_up_ink_density(油墨密度-od)】的数据库操作Service实现
* @createDate 2025-07-22 10:10:20
*/
@Service
@Transactional
public class DfUpInkDensityServiceImpl extends ServiceImpl<DfUpInkDensityMapper, DfUpInkDensity>
    implements DfUpInkDensityService{

}




