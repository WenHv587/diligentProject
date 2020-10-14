package task1.com.wenh.dao;

import task1.com.wenh.pojo.Animal;

import java.util.List;

/**
 * @author LWenH
 * @create 2020/9/29 - 14:04
 * <p>
 * 提供增删改查基本方法的接口
 */
public interface AnimalDao {
    /**
     * 增加动物信息
     *
     * @param animal 要添加的动物对象
     */
    void add(Animal animal);

    /**
     * 删除动物信息
     *
     * @param num  用户输入的标记
     * @param id   根据id删除动物信息
     * @param name 要删除的属性名
     */
    void delete(String num, String id, String name);

    /**
     * 修改动物信息
     *
     * @param num      用户选择标记
     * @param id       要修改的动物信息的id
     * @param name     要修改的属性名
     * @param property 要修改的属性的新值
     */
    void set(String num, String id, String name, String property);

    /**
     * 查询全部动物信息
     */
    void select();

    /**
     * 按条件查询动物信息
     *
     * @param params 用户输入属性值的集合
     * @return 有满足用户条件的动物返回true，否则返回false
     */
    boolean selectByParam(List<String> params);
}
