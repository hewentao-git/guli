package com.company.guli.service.impl;

import com.company.guli.bean.EduCourse;
import com.company.guli.bean.vo.CourseVo;
import com.company.guli.mapper.EduCourseMapper;
import com.company.guli.service.EduCourseDescriptionService;
import com.company.guli.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-20
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Override
    public String saveVo(CourseVo vo) {

//        1.添加课程
        baseMapper.insert(vo.getCourse());
//        2.获取课程id
        String id = vo.getCourse().getId();

        vo.getCourseDescription().setId(id);
        courseDescriptionService.save(vo.getCourseDescription());
        return id;
    }
}
