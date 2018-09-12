package com.example.myroom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import io.reactivex.Flowable;

//Dao 注解 - 标记数据操作类
@Dao
public interface UserDao {

    //Insert 插入操作 (OnConflictStrategy.REPLACE 指定 遇到冲突,新数据更换旧数据)，返回值为Long 为插入数据的rowId
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(User user);
    @Insert
    List<Long> insertAll(User...users);

    //Query 定义查询语句,返回LiveData<>可观察对象
    @Query("select * from MyUser")
    LiveData<List<User>> users();

    //Query返回一个属性的子集
    @Query("select first_name,last_name FROM MyUser")
    public List<NameTuple> loadFullName();

    //Query返回RxJava2中的flowable
    @Query("select first_name,last_name from MyUser where uid=:id")
    public Flowable<NameTuple>loadUserById(int id);

    //Queray直接返回Cusor
    @Query("select * from MyUser where age >:minAge")
    public Cursor loadRawUsersOlderThan(int minAge);

    //带参数请求
    @Query("select * from MyUser where uid in (:userIds)")
    List<User>loadAllByIds(int[] userIds);

    @Query("select * from MyUser where first_name LIKE:first AND last_name LIKE:last")
    User findByName(String first,String last);

    @Query("delete from MyUser where uid =:id")
    public int deleteByQuery(int id);


    //Delete删除操作，返回值表示 删除的个数.
    @Delete
    public int delete(User user);


    //Update （更新操作,其实可以完全被Insert所代替）,返回值代表受影响的行数
    @Update
    public int updateUsers(User... users);

}
