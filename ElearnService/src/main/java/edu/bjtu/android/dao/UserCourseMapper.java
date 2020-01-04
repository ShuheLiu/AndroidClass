package edu.bjtu.android.dao;
import java.util.List;

import edu.bjtu.android.entity.User;
import edu.bjtu.android.entity.UserCourse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCourseMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table course
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table course
     * @mbg.generated
     */
    List<UserCourse> selectAll();

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table course
     * @mbg.generated
     */
    List<UserCourse> selectByUserID();
}