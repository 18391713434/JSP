package com.itcast;

import com.itcast.dao.IUserDao;
import com.itcast.domain.QueryVo;
import com.itcast.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class MybatisTest
{
    private InputStream in ;
    private SqlSession sqlSession;
    private IUserDao dao;
    @Before//用于在测试方法执行之前
    public void init()throws IOException{
        //读取配置文件，生成字节输入流
         in=Resources.getResourceAsStream("SqlMapConfig.xml");
        //获取SqlSessionFactory
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory=sqlSessionFactoryBuilder.build(in);
        //获取SqlSession对象
        sqlSession=sqlSessionFactory.openSession();
        //获取dao的代理对象
         dao = sqlSession.getMapper(IUserDao.class);
    }

    @After//用于在测试方法之后
    public void destory() throws IOException {
        //释放资源
        sqlSession.commit();
        sqlSession.close();
        in.close();

    }

    /**
     * 查询方法
     */
    @Test
    public void testFindAll()  {

        //执行方法
        List<User> users=dao.findAll();
        for(User user:users){
            System.out.println(user);
        }

    }

    /**
     * 保存方法
     */
    @Test
    public void testSave(){
        User user=new User();
        user.setUsername("mybatis save_user aa");
        user.setAddress("陕西省西安市");
        user.setSex("男");
        user.setBirthday(new Date());
        System.out.println("更新之前");
        System.out.println(user);
        dao.saveUser(user);
        System.out.println("更新之后");
        System.out.println(dao);


    }

    /**
     * 更新方法
     */
    @Test
    public void testUpdate(){
        User user=new User();
        user.setId(50);
        user.setUsername("mybatis save_user");
        user.setAddress("北京顺义区");
        user.setSex("男");
        user.setBirthday(new Date());
       dao.updateUser(user);

    }

    /**
     * 删除方法
     */
    @Test
    public void deleteUser(){

        dao.deleteUser(48);

    }

    /**
     * 查询一个方法
     *
     */
    @Test
    public void findByIdTest(){


        System.out.println(dao.findById(50));

    }


    /**
     * 根据姓名模糊查询查询一个方法
     *
     */
    @Test
    public void findByNameTest(){

        String username="王";
        List<User> users = dao.findByName("%" + username + "%");
        for(User user:users){
            System.out.println(user);
        }

    }


    @Test
    public void findTotalTest(){
        dao.findTotal();
    }

    /**
     * 测试使用QueryVo作为查询条件
      */
    @Test
    public void findByQueryVo(){
        QueryVo vo=new QueryVo();
        User user=new User();
        user.setUsername("%王%");
        vo.setUser(user);
        List<User> users = dao.findUserByVo(vo);
        for(User user1:users){
            System.out.println(user1);
        }

    }










}
