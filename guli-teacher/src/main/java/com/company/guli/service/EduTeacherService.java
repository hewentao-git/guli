package com.company.guli.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.guli.bean.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.guli.bean.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-14
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
}
