package cn.gomro.mid.api.rest.studentApi;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.student.biz.IStudentBizLocal;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.entity.StudentForm;
import cn.gomro.mid.core.common.message.ReturnMessage;
import com.alibaba.fastjson.JSON;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@Path("/student")
@Produces(RestMediaType.JSON_HEADER)
public class StudentApi extends AbstractApi {
    @EJB
    private IStudentBizLocal studentBizLocal;

    public StudentApi() {

    }

    @POST
    @Path("/add")
    @Consumes(RestMediaType.FORM_HEADER)
    public ReturnMessage addStudent(@BeanParam StudentForm sf) {

        return studentBizLocal.saveOrder(sf);
    }

    @POST
    @Path("/del")
    @Consumes(RestMediaType.FORM_HEADER)
    public ReturnMessage delStudent(@FormParam("ids") String ids) {
        String[] s = ids.split(",");
        for (int i = 0; i < s.length; i++) {

            studentBizLocal.delStudent(Integer.valueOf(s[i]));
        }
        return ReturnMessage.success();
    }

    @POST
    @Path("/edit")
    @Consumes(RestMediaType.FORM_HEADER)
    public ReturnMessage editStudent(@FormParam("id") Integer id, @FormParam("name") String name) {
        return studentBizLocal.editStudent(id,name);
    }

    @GET
    @Path("/form")
    @Consumes(RestMediaType.FORM_HEADER)
    public String getStudentForm( @DefaultValue("1")  @QueryParam("page") Integer page,@DefaultValue("5") @QueryParam("rows") Integer rows,
                                  @QueryParam("username") String username,@QueryParam("startTime") String startTime,@QueryParam("endTime") String endTime) {

        int total=studentBizLocal.getStudentTotal(username,startTime,endTime);
        List<StudentEntity> list = studentBizLocal.getStudentByPage(username,startTime,endTime,page,rows).getData();
        String h = "{\"total\":" + total + ",\"rows\":[\n";
        String f = "]}";
        String b = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                b += list.get(i).toString();
            } else {
                b += list.get(i).toString() + ",";
            }

        }
        return h + b + f;
    }
}
