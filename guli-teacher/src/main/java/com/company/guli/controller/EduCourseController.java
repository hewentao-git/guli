package com.company.guli.controller;


import com.company.guli.bean.vo.CourseVo;
import com.company.guli.result.AppResponse;
import com.company.guli.service.EduCourseDescriptionService;
import com.company.guli.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-20
 */
@RestController
@CrossOrigin
@RequestMapping("/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    /**
     * 保存基本信息
     */
    @PostMapping("save")
    public AppResponse save(@RequestBody CourseVo vo){

        String courseId = courseService.saveVo(vo);

        return AppResponse.ok(courseId);

    }

}

