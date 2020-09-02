package com.company.guli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.guli.bean.EduSubject;
import com.company.guli.bean.vo.OneSubject;
import com.company.guli.bean.vo.TwoSubject;
import com.company.guli.exception.EduException;
import com.company.guli.mapper.EduSubjectMapper;
import com.company.guli.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public boolean saveOne(EduSubject subject) {
        EduSubject subjectLevelOne = selectSubjectByName(subject.getTitle());

        if (subjectLevelOne == null) {
            return save(subject);
        }

        return false;

    }

    @Override
    public boolean deleteById(String id) {
        //根据ID查询数据库中是否存在此ID为ParentId (二级分类)
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<EduSubject> subjectList = baseMapper.selectList(wrapper);
        if (subjectList.size() != 0) {
            return false;
        }

        int i = baseMapper.deleteById(id);
        return i == 1;
    }

    @Override
    public boolean saveTwo(EduSubject subject) {
        EduSubject subjectLevelTwo = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParentId());
        if(subjectLevelTwo == null){
            return this.save(subject);
        }else{
            throw new EduException("类别已存在");
        }

    }

    private EduSubject selectSubjectByNameAndParentId(String title, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(wrapper);
    }

    private EduSubject selectSubjectByName(String title) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<OneSubject> getTree() {
        List<OneSubject> oneSubjectList = new ArrayList<>();
//        获取一级列表
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapper);
//        遍历一级
        for (EduSubject eduSubject : oneSubjects) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
//        获取二级列表
            QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", eduSubject.getId());
            List<EduSubject> twoSubjects = baseMapper.selectList(queryWrapper);
            //        遍历二级

            for (EduSubject subject : twoSubjects) {
                //        把二级复制到twosubject
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(subject, twoSubject);
                //        把two添加到one集合中
                oneSubject.getChildren().add(twoSubject);
            }
            oneSubjectList.add(oneSubject);
        }

        return oneSubjectList;
    }


    @Override
    public List<String> importEXCL(MultipartFile file) {

        List<String> msg = new ArrayList<>();
        try {
            //1、获取文件流
            InputStream inputStream = file.getInputStream();
            //2、创建workbook
            Workbook workbook = new XSSFWorkbook(inputStream);
            //3 根据workbook获取sheet
            Sheet sheet = workbook.getSheetAt(0);
            //获取表格最后一个索引位置也就是行数
            int lastRowNum = sheet.getLastRowNum();
            System.err.println("获取行数：" + lastRowNum);
            if (lastRowNum <= 1) {
                msg.add("请填写数据");
                return msg;
            }
            //遍历行数
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                Row rowData = sheet.getRow(rowNum);
                //根据每一行获取列表数据
                //获取第一列
                Cell cellOne = rowData.getCell(0);
                //根据cell获取第一列里面内容
                if (cellOne == null) {
                    msg.add("第" + rowNum + "行第一列数据为空");
                    //跳到下一行进行操作
                    continue;
                }
                String cellOneValue = cellOne.getStringCellValue();
                if (StringUtils.isEmpty(cellOneValue)) {
                    msg.add("第" + rowNum + "行第一列数据为空");
                    //跳到下一行进行操作
                    continue;
                }

                //cellOneValue 是一级分类名称
                //判断添加的一级分类名称在数据库表里面是否有相同的，如果没有添加
                EduSubject eduSubject = this.existOneSubject(cellOneValue);
                //为了后面添加二级分类，一级分类id
                String pid = null;
                if (eduSubject == null) { //表没有相同的一级分类
                    //添加到数据库里面
                    EduSubject subjectOne = new EduSubject();
                    subjectOne.setTitle(cellOneValue);
                    subjectOne.setParentId("0");
                    baseMapper.insert(subjectOne);
                    //添加一级分类之后，获取添加之后id值
                    pid = subjectOne.getId();
                } else {
                    //有相同的一级分类
                    pid = eduSubject.getId();
                }

                //获取第二列
                Cell cellTwo = rowData.getCell(1);
                if (cellTwo == null) {
                    msg.add("第" + rowNum + "行第二列数据为空");
                    continue;
                }
                // 根据cell获取第二列里面内容
                String cellTwoValue = cellTwo.getStringCellValue();
                if (StringUtils.isEmpty(cellTwoValue)) {
                    msg.add("第" + rowNum + "行第二列数据为空");
                    continue;
                }
                //cellTwoValue 是二级分类名称
                EduSubject eduSubjectTwo = this.existTwoSubject(cellTwoValue, pid);
                if (eduSubjectTwo == null) {
                    //没有相同的二级分类，实现二级分类添加
                    EduSubject subjectTwo = new EduSubject();
                    subjectTwo.setTitle(cellTwoValue);
                    subjectTwo.setParentId(pid);
                    baseMapper.insert(subjectTwo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }


    //2 判断二级分类名称是否有相同的
    private EduSubject existTwoSubject(String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);

        return baseMapper.selectOne(wrapper);
    }

    //1 判断一级分类名称是否相同
    private EduSubject existOneSubject(String name) {
        //查询数据库判断
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }

}

