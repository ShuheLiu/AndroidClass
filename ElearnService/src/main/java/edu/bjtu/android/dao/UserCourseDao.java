package edu.bjtu.android.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.bjtu.android.entity.UserCourse;

@Component
public class UserCourseDao implements UserCourseMapper {

    @Autowired
    UserCourseMapper mapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        // TODO Auto-generated method stub
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UserCourse> selectAll() {
        // TODO Auto-generated method stub
        return mapper.selectAll();
    }

    @Override
    public List<UserCourse> selectByUserID() {
        // TODO Auto-generated method stub
        return mapper.selectByUserID();
    }
}
