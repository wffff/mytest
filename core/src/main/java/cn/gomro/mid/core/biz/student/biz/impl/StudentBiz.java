package cn.gomro.mid.core.biz.student.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.student.biz.IStudentBiz;
import cn.gomro.mid.core.biz.student.biz.IStudentBizLocal;
import cn.gomro.mid.core.biz.student.biz.IStudentBizRemote;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.entity.StudentForm;
import cn.gomro.mid.core.biz.student.service.IStudentSerivce;
import cn.gomro.mid.core.common.DateUtils;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@Stateless
public class StudentBiz extends AbstractSessionBiz implements IStudentBizLocal, IStudentBizRemote {
    @EJB
    IStudentSerivce studentSerivce;

    @Override
    public ReturnMessage saveOrder(StudentForm sf) {
        StudentEntity se = new StudentEntity(sf.getName());
        return studentSerivce.addItem(se);
    }

    @Override
    public ReturnMessage<List<StudentEntity>> getStudent() {
        return studentSerivce.getStudent();
    }

    @Override
    public ReturnMessage<List<StudentEntity>> getStudentByPage(String username, String startTime, String endTime, int page, int rows) {
        Date start1 = DateUtils.getDateFromString(startTime);
        Date end1 = DateUtils.getDateFromString(endTime);
        return studentSerivce.getStudentByPage(username, start1, end1, page, rows);
    }

    @Override
    public int getStudentTotal(String username, String startTime, String endTime) {
        Date start1 = DateUtils.getDateFromString(startTime);
        Date end1 = DateUtils.getDateFromString(endTime);
        return studentSerivce.getStudentTotal(username, start1, end1);
    }

    @Override
    public ReturnMessage editStudent(Integer id, String name) {
        return studentSerivce.editStudent(id, name);
    }

    @Override
    public ReturnMessage delStudent(Integer id) {
        return studentSerivce.delStudent(id);
    }

}
