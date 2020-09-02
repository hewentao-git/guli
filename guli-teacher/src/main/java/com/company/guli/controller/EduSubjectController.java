package com.company.guli.controller;


import com.company.guli.bean.EduSubject;
import com.company.guli.bean.vo.OneSubject;
import com.company.guli.result.AppResponse;
import com.company.guli.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-17
 */
@Api(tags = "课程管理", description = "Subject Controller")
@RestController
@CrossOrigin
@RequestMapping("/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    @ApiOperation(value = "新增二级分类")
    @PostMapping("saveLevelTwo")
    public AppResponse saveLevelTwo(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){

        boolean result = subjectService.saveTwo(subject);
        if(result){
            return AppResponse.ok("ok");
        }

        return AppResponse.fail("保存失败");

    }


    @ApiOperation(value = "新增一级分类")
    @PostMapping("saveOne")
    public AppResponse saveOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){

        boolean result = subjectService.saveOne(subject);
        if(result){
            return AppResponse.ok("ok");
        }else{
            return AppResponse.fail("删除失败");
        }
    }

    @ApiOperation(value = "删除二级分类")
    @DeleteMapping("/{id}")
    public AppResponse deleteById(@PathVariable  String id){
        boolean flag = subjectService.deleteById(id);
        if(flag){
            return AppResponse.ok("ok");
        } else {
            return AppResponse.fail(null);
        }
    }


    @ApiOperation("导入课程分类")
    @PostMapping("import")
    public AppResponse importSubject(MultipartFile file){
        //因为考虑到EXCL模板中数据不准确所以返回多个错误信息，那么多个错误信息放在集合中
        List<String> list = subjectService.importEXCL(file);

        if (list.size() == 0){
            return AppResponse.ok("ok");
        }

        return AppResponse.fail(list);
    }

    @ApiOperation("获取课程")
    @GetMapping("tree")
    public AppResponse treeSubject(){

        List<OneSubject> list = subjectService.getTree();

        if (list != null){
            return AppResponse.ok(list);
        }

        return AppResponse.fail(null);
    }

}

