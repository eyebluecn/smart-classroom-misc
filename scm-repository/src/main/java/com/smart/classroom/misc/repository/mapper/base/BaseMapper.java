package com.smart.classroom.misc.repository.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {

    /**
     * 插入一个对象 返回影响的条数。要获取id使用 fsEvent.getId()
     */
    int insert(T t);

    /**
     * 批量插入对象
     */
    int insertBatch(@Param("list") List<T> list);

    /**
     * 按id删除
     */
    int deleteById(Long id);

    /**
     * 根据一系列id来删除对应的条目
     */
    int deleteByIds(@Param("list") List<Long> list);

    /**
     * 更新 返回影响的条数
     */
    int update(T fsEvent);

    /**
     * 按id查询
     */
    T queryById(Long id);

    /**
     * 根据一系列id来获取到对应的条目
     */
    List<T> queryByIds(@Param("list") List<Long> list);


}
