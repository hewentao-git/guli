package com.company.guli.service;

import com.company.guli.bean.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.guli.bean.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-20
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveVo(CourseVo vo);
}
