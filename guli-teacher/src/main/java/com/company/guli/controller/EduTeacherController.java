package com.company.guli.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.guli.bean.EduTeacher;
import com.company.guli.bean.query.TeacherQuery;
import com.company.guli.enums.ResultCodeEnume;
import com.company.guli.exception.EduException;
import com.company.guli.result.AppResponse;
import com.company.guli.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author guli
 * @since 2020-07-14
 */
@Api(tags = "讲师管理", description = "Teacher Controller")
@CrossOrigin
@RestController
@RequestMapping("/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("修改讲师")
    @PutMapping("/update")
    public AppResponse updateTeacherById(@RequestBody EduTeacher teacher) {

        try {
            teacherService.updateById(teacher);
            return AppResponse.ok(teacher);

        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("{id}")
    public AppResponse selectTeacherById(@ApiParam(name = "id", required = true, value = "讲师Id")
                                         @PathVariable("id") String id) {

        try {
            EduTeacher teacher = teacherService.getById(id);
            if(teacher == null){
                throw new EduException("没有讲师信息");
            }
            return AppResponse.ok(teacher);

        } catch (EduException e) {
            e.printStackTrace();
            return AppResponse.fail(e.getMsg());
        }
    }

    @ApiOperation("保存讲师")
    @PostMapping("/save")
    public AppResponse saveTeacher(@RequestBody EduTeacher teacher) {

        try {
            teacherService.save(teacher);
            return AppResponse.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

    @ApiOperation(value = "讲师条件查询分页列表")
    @PostMapping("/{page}/{limit}")
    public AppResponse selectTeacherBypageAndWrapper(
            @ApiParam(name = "page", required = true, value = "当前页")
             @PathVariable("page") Integer page,
             @PathVariable("limit")
            @ApiParam(name = "limit", required = true, value = "每页显示数量") Integer limit,
            @RequestBody TeacherQuery teacherQuery) {

        try {
            Page<EduTeacher> teacherPage = new Page<>(page, limit);
            teacherService.pageQuery(teacherPage, teacherQuery);

            Map<String, Object> result = new HashMap<>();
            result.put("teacherList", teacherPage.getRecords());
            result.put("total", teacherPage.getTotal());

            return AppResponse.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }


    @ApiOperation(value = "讲师分页列表")
    @GetMapping("/page")
    public AppResponse selectTeacherBypage(
            @ApiParam(name = "page", required = true, value = "当前页")
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam(name = "limit", required = true, value = "每页显示数量")
            @RequestParam(name = "limit", defaultValue = "5") Integer limit) {

        try {
            Page<EduTeacher> teacherPage = new Page<>(page, limit);
            teacherService.page(teacherPage, null);

            Map<String, Object> result = new HashMap<>();
            result.put("teacherList", teacherPage.getRecords());
            result.put("total", teacherPage.getTotal());

            return AppResponse.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }


    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/list")
    public AppResponse list() {

        try {
            List<EduTeacher> list = teacherService.list(null);
            return AppResponse.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

    @ApiOperation(value = "删除讲师")
    @DeleteMapping("/{id}")
    public AppResponse deleteTeacherById(
            @PathVariable("id") @ApiParam(name = "id", value = "讲师id", required = true)
                    String id) {

        try {
            teacherService.removeById(id);
            return AppResponse.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }
}

