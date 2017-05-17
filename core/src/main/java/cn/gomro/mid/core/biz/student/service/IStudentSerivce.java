package cn.gomro.mid.core.biz.student.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface IStudentSerivce extends IService<StudentEntity>{

    int getStudentTotal(String username,Date startTime, Date endTime);
    ReturnMessage<List<StudentEntity>> getStudent();

    ReturnMessage editStudent(Integer id, String name);
    ReturnMessage<StudentEntity> getByStudentId(Integer id);

    ReturnMessage delStudent(Integer id);

    ReturnMessage<List<StudentEntity>> getStudentByPage(String username, Date start1, Date end1, int page, int rows);
}
