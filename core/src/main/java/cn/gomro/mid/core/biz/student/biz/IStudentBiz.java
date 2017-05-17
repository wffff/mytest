package cn.gomro.mid.core.biz.student.biz;

import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.entity.StudentForm;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface IStudentBiz {
    ReturnMessage saveOrder(StudentForm sf);

    ReturnMessage<List<StudentEntity>> getStudent();
    ReturnMessage<List<StudentEntity>> getStudentByPage(String username, String startTime, String endTime,int page,int rows);

    int getStudentTotal(String username, String startTime, String endTime);

    ReturnMessage editStudent(Integer id, String name);

    ReturnMessage delStudent(Integer id);

}
