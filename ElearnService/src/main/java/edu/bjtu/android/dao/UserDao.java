package edu.bjtu.android.dao;

import edu.bjtu.android.entity.Course;
import edu.bjtu.android.entity.User;
import edu.bjtu.android.entity.UserCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao implements UserMapper  {

    @Autowired
    UserMapper mapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        // TODO Auto-generated method stub
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(String name, String password, String email, String mobile,String level) {
        // TODO Auto-generated method stub
        return mapper.insert(name, password, email, mobile,level);
    }

    @Override
    public User selectByPrimaryKey(String id) {
        // TODO Auto-generated method stub
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAll() {
        // TODO Auto-generated method stub
        return mapper.selectAll();
    }

    /*@Override
    public List<Course> selectMy() {
        // TODO Auto-generated method stub
        return mapper.selectMy();
    }*/

    /*@Override
    public List<UserCourse> selectMy() {
        // TODO Auto-generated method stub
        return mapper.selectMy();
    }*/

    @Override
    public int updateByPrimaryKey(User record) {
        // TODO Auto-generated method stub
        return mapper.updateByPrimaryKey(record);

    }
}
