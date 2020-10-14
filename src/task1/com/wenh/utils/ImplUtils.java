package task1.com.wenh.utils;

import task1.com.wenh.pojo.Animal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author LWenH
 * @create 2020/10/6 - 19:26
 * <p>
 * AnimalDaoImpl类需要用到的工具
 */
public class ImplUtils {
    private static File file = new File("animal.txt");

    /**
     * 读取存放动物信息的文件，得到动物信息的集合
     *
     * @return 返回一个List<Animal>
     */
    public List<Animal> getArray() {
        ObjectInputStream ois = null;
        List<Animal> animalList = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            animalList = (List<Animal>) ois.readObject();
        } catch (EOFException e) {
            /*
            EOFException异常是第一次读到流末尾之后抛出的一个异常，也可以说是一个标记，catch不作处理即可。
             */
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return animalList;
    }

    /**
     * 将存放动物信息的集合序列化，存到指定的文件中去
     *
     * @param array 存放动物信息的集合
     */
    public void toSerialization(List<Animal> array) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(array);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断存放动物信息的文件是否为空
     * <p>有可能出现文件为空，反序列化得到动物信息集合的时候就会出现空指针异常</p>
     *
     * @return 有动物信息则返回true, 否则返回false
     */
    public boolean ifNull() {
        boolean flag = false;
        if (file.length() != 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断存放动物信息的集合是否为空
     * <p>有可能出现文件不为空，但文件中存放一个空集合的情况</p>
     *
     * @return 为空返回false, 否则返回true
     */
    public boolean ifNoAnimal() {
        boolean flag = false;
        // 在文件不为空的情况下
        if (ifNull()) {
            List<Animal> animalList = getArray();
            if (animalList.size() != 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 校验用户输入的id是否正确
     *
     * @param id 用户输入的id
     * @return 有相应的id则返回true, 否则返回false
     */
    public boolean checkId(String id) {
        List<Animal> animalList = getArray();
        boolean flag = false;
        for (Animal animal : animalList) {
            if (id.trim().equalsIgnoreCase(animal.getId())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 按照年龄从小到大对动物信息进行排序
     */
    public void sort(List<Animal> list) {
        list.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                int num1 = o1.getAge() - o2.getAge();
                return num1 == 0 ? (o1.getId().compareTo(o2.getId())) : num1;
            }
        });
    }

    /**
     * 对添加的动物信息进行查重
     *
     * @param animal 用户添加的某一动物信息
     * @return 动物信息完全一样返回false, 不完全一样返回true
     */
    public boolean checkRepeat(Animal animal) {
        boolean flag = true;
        // 如果文件为空，则不可能有一样的信息，所以有动物信息再进行查重
        if (ifNull()) {
            List<Animal> animalList = getArray();
            for (Animal animal1 : animalList) {
                if (animal.getSpecie().equals(animal1.getSpecie())
                        && animal.getAge() == animal1.getAge()
                        && animal.getGender().equals(animal1.getGender())
                        && animal.getAnimalMap().equals(animal1.getAnimalMap())) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 在修改或删除已有的属性时，校验用户输入的属性是否存在
     *
     * @param property 用户要修改的属性值名称
     * @param id       动物信息的id
     * @return 存在返回true.不存在返回false
     */
    public boolean checkExist(String id, String property) {
        List<Animal> animalList = getArray();
        boolean flag = true;
        for (Animal animal : animalList) {
            if (id.trim().equalsIgnoreCase(animal.getId())) {
                Map<String, String> animalMap = animal.getAnimalMap();
                Set<String> set = animalMap.keySet();
                if (! set.contains(property) && ! "种类".equals(property) && ! "年龄".equals(property) && ! "性别".equals(property)) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 判断动物是否有扩展属性
     *
     * @param id 用户输入的id
     * @return 有扩展属性返回true, 没有则返回false
     */
    public boolean checkMap(String id) {
        List<Animal> animalList = getArray();
        for (Animal animal : animalList) {
            if (id.trim().equalsIgnoreCase(animal.getId())) {
                Map<String, String> animalMap = animal.getAnimalMap();
                if (animalMap.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 在组合条件查询时，对用户输入的属性名称进行校验
     *
     * @param names 用户输入的属性名称的数组
     * @return 如果用户输入的属性名称能被任何一个动物信息包含，则返回true，否则返回false
     */
    public boolean checkName(List<String> names) {
        List<Animal> animalList = getArray();
        boolean flag = false;
        for (String name : names) {
            if ("种类".equals(name) || "年龄".equals(name) || "性别".equals(name)) {
                flag = true;
            }
            for (Animal animal : animalList) {
                Map<String, String> animalMap = animal.getAnimalMap();
                Set<String> set = animalMap.keySet();
                if (set.contains(name)) {
                    // 只要包含就是true，无论包含几个
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
