package cn.gomro.mid.core.biz.student.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.entity.StudentForm;
import cn.gomro.mid.core.biz.student.service.IStudentSerivce;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@Stateless
public class StudentService extends AbstractSessionService<StudentEntity> implements IStudentSerivce {
    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<StudentEntity>> getItemsPaged(StudentEntity entity, Integer page, Integer size) {
        return null;
    }

    @Override
    public ReturnMessage<List<StudentEntity>> getStudent() {

        String jpql = "select i from StudentEntity i";
        Query query = em.createQuery(jpql);
//        query.setHint(Constants.JPA_CACHEABLE, true);
        List<StudentEntity> resultList = query.getResultList();
        return ReturnMessage.success(resultList);
    }

    @Override
    public ReturnMessage editStudent(Integer id, String name) {
        StudentEntity se = getByStudentId(id).getData();
        se.setName(name);
        this.editItem(se);
        return ReturnMessage.success(se);
    }

    @Override
    public ReturnMessage<StudentEntity> getByStudentId(Integer id) {
        String jpql = "select i from StudentEntity i where i.id=" + id;
        Query query = em.createQuery(jpql);
        StudentEntity se = (StudentEntity) query.getSingleResult();
        return ReturnMessage.success(se);

    }

    @Override
    public ReturnMessage delStudent(Integer id) {
        StudentEntity se = getByStudentId(id).getData();
        se.setDel(true);
        this.editItem(se);
        return ReturnMessage.success(se);
    }


    @Override
    public ReturnMessage<List<StudentEntity>> getStudentByPage(String username, Date startTime, Date endTime, int page, int rows) {
        String where = "where i.del=false";
        if (username != null) {
            where += " and i.name like :username ";

        }
        if (startTime != null) {
            where += " and i.time >= :starttime ";
        }
        if (endTime != null) {
            where += " and i.time <= :endtime ";
        }
        String jpql = "select i from StudentEntity i " + where + " order by i.id";
        String jpqlCount = "select count(i) from StudentEntity i " + where;
        Query query = em.createQuery(jpql, StudentEntity.class);
        Query countQuery = em.createQuery(jpqlCount);
        if (username != null) {
            query.setParameter("username", "%" + username + "%");
            countQuery.setParameter("username", "%" + username + "%");
        }

        if (startTime != null) {
            query.setParameter("starttime", startTime);
            countQuery.setParameter("starttime", startTime);
        }
        if (endTime != null) {
            query.setParameter("endtime", endTime);
            countQuery.setParameter("endtime", endTime);
        }
        int count = new Long((long) countQuery.getSingleResult()).intValue();
        int pages = rows == 0 ? 0 : (count / rows);
        if (count % rows != 0) pages++;
        if (page < 1) page = 1;
        if (page > pages) page = pages;

        int start = (page - 1) * rows;
        if (count > 0 && start < count) {
            query.setFirstResult(start);
            query.setMaxResults(rows);
        }
        List<StudentEntity> resultList = query.getResultList();
        return ReturnMessage.success(resultList);
    }

    @Override
    public int getStudentTotal(String username, Date startTime, Date endTime) {
        String where = "where i.del=false ";
        if (username != null) {
            where += " and i.name like :username ";
        }
        if (startTime != null) {
            where += " and i.time >= :starttime ";
        }
        if (endTime != null) {
            where += " and i.time <= :endtime ";
        }
        String jpqlCount = "select count(i) from StudentEntity i " + where;
        Query countQuery = em.createQuery(jpqlCount);
        if (username != null) {
            countQuery.setParameter("username", "%" + username + "%");
        }
        if (startTime != null) {
            countQuery.setParameter("starttime", startTime);
        }
        if (endTime != null) {
            countQuery.setParameter("endtime", endTime);
        }
        int count = new Long((long) countQuery.getSingleResult()).intValue();
        return count;
    }

}
