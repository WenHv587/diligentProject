package task1.com.wenh.dao.impl;

import task1.com.wenh.dao.AnimalDao;
import task1.com.wenh.pojo.Animal;
import task1.com.wenh.utils.ImplUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author LWenH
 * @create 2020/9/29 - 14:04
 * <p>
 * 对动物进行增删改查的具体实现类。
 */
public class AnimalDaoImpl implements AnimalDao {
    private ImplUtils iu = new ImplUtils();

    /**
     * 增加动物信息
     *
     * @param animal 要添加的动物对象
     */
    @Override
    public void add(Animal animal) {
        List<Animal> animalList = new ArrayList<>();
        animalList.add(animal);
        // 如果原本的文件中已有信息，则需要把原本的动物信息加到这次的集合中，再去覆盖上次的文件。
        if (iu.ifNull()) {
            List<Animal> animals = iu.getArray();
            for (Animal animal1 : animals) {
                if (! animalList.contains(animal1)) {
                    animalList.add(animal1);
                }
            }
        }
        iu.toSerialization(animalList);
        System.out.println("已存储完毕!");
    }

    /**
     * 删除动物信息
     *
     * @param num  用户的选择信息
     * @param id   根据id删除动物信息
     * @param name 要删除的属性名
     */
    @Override
    public void delete(String num, String id, String name) {
        List<Animal> animalList = iu.getArray();
        /*
        分两种情况
        1. 删除整条动物的信息
        2. 删除动物的某个属性
         */
        switch (num) {
            case "1":
                /*
                删除整条动物信息
                 */
                Iterator<Animal> it = animalList.iterator();
                while (it.hasNext()) {
                    Animal animal = it.next();
                    if (animal.getId().equalsIgnoreCase(id.trim())) {
                        it.remove();
                    }
                }
                iu.toSerialization(animalList);
                System.out.println("已成功删除!");
                break;
            case "2":
                /*
                删除动物的某个扩展属性
                (我认为基本属性id、种类、年龄、性别是不应该被删除的，这是描述一个动物的基本内容)
                 */
                for (Animal animal : animalList) {
                    Map<String, String> animalMap = animal.getAnimalMap();
                    Set<String> animalSet = animalMap.keySet();
                    for (String key : animalSet) {
                        if (name.equals(key) && id.trim().equalsIgnoreCase(animal.getId())) {
                            animalMap.remove(name);
                            break;
                        }
                    }
                }
                iu.toSerialization(animalList);
                System.out.println("已成功删除!");
                break;
            default:
                break;
        }
    }

    /**
     * 修改动物信息
     *
     * @param num      用户选择标记
     * @param id       要修改的动物信息的id
     * @param name     要修改的属性名
     * @param property 要修改的属性的新值
     */
    @Override
    public void set(String num, String id, String name, String property) {
        List<Animal> animalList = iu.getArray();
        if ("1".equals(num)) {
            /*
            修改现有的属性
             */
            for (Animal animal : animalList) {
                if (animal.getId().equalsIgnoreCase(id.trim())) {
                    switch (name) {
                        case "种类":
                            animal.setSpecie(property);
                            System.out.println("已修改成功!");
                            break;
                        case "年龄":
                            animal.setAge(Integer.parseInt(property));
                            System.out.println("已修改成功!");
                            break;
                        case "性别":
                            animal.setGender(property);
                            System.out.println("已修改成功!");
                            break;
                        default:
                            // 与以上的三个基本属性都不匹配的话，需要修改的则是扩展属性
                            Map<String, String> animalMap = animal.getAnimalMap();
                            Set<String> animalSet = animalMap.keySet();
                            for (String key : animalSet) {
                                if (name.equals(key)) {
                                    animalMap.put(key, property);
                                    System.out.println("已修改成功!");
                                }
                            }
                    }
                }
            }
            iu.toSerialization(animalList);
        } else if ("2".equals(num)) {
            /*
            增添新的属性
             */
            for (Animal animal : animalList) {
                Map<String, String> animalMap = animal.getAnimalMap();
                if (animal.getId().equalsIgnoreCase(id.trim())) {
                    animalMap.put(name, property);
                    System.out.println("已修改成功!");
                }
            }
            iu.toSerialization(animalList);
        }
    }

    /**
     * 查询全部动物信息
     */
    @Override
    public void select() {
        List<Animal> animalList = iu.getArray();
        iu.sort(animalList);
        System.out.println("===============================查询结果===============================");
        for (Animal animal : animalList) {
            Map<String, String> animalMap = animal.getAnimalMap();
            // 默认的打印信息格式
            StringBuilder sb = new StringBuilder(
                    "id:" + animal.getId() +
                            "---种类:" + animal.getSpecie() +
                            "---年龄:" + animal.getAge() +
                            "---性别:" + animal.getGender());

            if (0 == animalMap.size()) {
                // 说明没有扩展属性，只有基本信息，使用默认格式即可
                System.out.println(sb.toString());
            } else {
                // 有扩展属性，需要遍历扩展属性的集合，并在默认格式后添加新属性
                Set<String> animalSet = animalMap.keySet();
                for (String key : animalSet) {
                    String value = animalMap.get(key);
                    sb.append("---").append(key).append(":").append(value);
                }
                System.out.println(sb.toString());
            }
        }
    }

    /**
     * 多条件组合查询动物信息
     *
     * @param params 用户传递的动物属性值的限制条件的集合
     * @return 如果查询到了相应的动物信息则返回true, 否则返回false;
     */
    @Override
    public boolean selectByParam(List<String> params) {
        List<Animal> animalList = iu.getArray();
        iu.sort(animalList);
        System.out.println("===============================查询结果===============================");
        boolean flag = false;
        for (Animal animal : animalList) {
            Map<String, String> animalMap = animal.getAnimalMap();
            Set<String> set = animalMap.keySet();
            // 在确定用户输入的属性存在的前提下
            // 这个集合是存放一条动物信息中所有属性值的集合
            List<String> valueList = new ArrayList<>();
            valueList.add(animal.getId());
            valueList.add(animal.getSpecie());
            valueList.add(animal.getAge() + "");
            valueList.add(animal.getGender());
            for (String key : set) {
                String value = animalMap.get(key);
                valueList.add(value);
            }
            if (valueList.containsAll(params)) {
                // 如果用户输入的限制属性值被某个动物信息全部包含
                // 默认的打印信息格式
                StringBuilder sb = new StringBuilder(
                        "id:" + animal.getId() +
                                "---种类:" + animal.getSpecie() +
                                "---年龄:" + animal.getAge() +
                                "---性别:" + animal.getGender());
                if (0 == animalMap.size()) {
                    // 说明没有扩展属性，只有基本信息，使用默认格式即可
                    System.out.println(sb.toString());
                    flag = true;
                } else {
                    // 有扩展属性，需要遍历扩展属性的集合，并在默认格式后添加新属性
                    Set<String> animalSet = animalMap.keySet();
                    for (String key2 : animalSet) {
                        String value2 = animalMap.get(key2);
                        sb.append("---").append(key2).append(":").append(value2);
                    }
                    System.out.println(sb.toString());
                    flag = true;
                }
            }
        }
        return flag;
    }
}
