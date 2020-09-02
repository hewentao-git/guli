package com.company.guli.service;

import com.company.guli.bean.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.guli.bean.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-17
 */
public interface EduSubjectService extends IService<EduSubject> {
//根据传递的EXCL表格模板导入课程分类
    List<String> importEXCL(MultipartFile file);

    List<OneSubject> getTree();

    /**
     * 保存课程分类
     * @param subject
     * @return
     */

    boolean saveOne(EduSubject subject);
    boolean deleteById(String id);

    boolean saveTwo(EduSubject subject);
}
