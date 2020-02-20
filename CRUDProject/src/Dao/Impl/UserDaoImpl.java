package Dao.Impl;

import Dao.UserDao;
import Domain.User;
import Util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> findAll() {
        //使用jdbc操作数据库
        DataSource dataSource = JDBCUtils.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //定义sql
        String sql="select * from student";
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return list;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try{
        DataSource dataSource = JDBCUtils.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //定义sql
        String sql="select * from student where username = ? and password = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        DataSource dataSource = JDBCUtils.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //定义sql
        String sql="insert into student values(null,?,?,?,?,?,?,null,null)";
        String name = user.getName();

        jdbcTemplate.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());

    }
}
