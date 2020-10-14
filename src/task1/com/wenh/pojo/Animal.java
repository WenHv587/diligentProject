package task1.com.wenh.pojo;

import task1.com.wenh.utils.IDUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author LWenH
 * @create 2020/9/29 - 13:04
 * <p>
 * 对动物的基本描述类
 * 其中包含动物的四个基本属性(id, 种类, 性别, 年龄) 和后期可随意添加的可扩展属性(放在一个Map集合中)。
 */
public class Animal implements Serializable {
    private static final long serialVersionUID = 515778485071235413L;

    /**
     * 动物的id 由系统自动生成 并且用户后期不可进行修改
     */
    private String id = IDUtils.randomId();
    /**
     * 动物的种类
     */
    private String specie;
    /**
     * 动物的年龄
     */
    private int age;
    /**
     * 动物的性别
     */
    private String gender;
    /**
     * 存放扩展属性的集合
     */
    private Map<String, String> animalMap = new HashMap<>();

    public Animal() {
    }

    public Animal(String specie, int age, String gender, Map<String, String> animalMap) {
        this.specie = specie;
        this.age = age;
        this.gender = gender;
        this.animalMap = animalMap;
    }

    public String getId() {
        return id;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Map<String, String> getAnimalMap() {
        return animalMap;
    }

    public void setAnimalMap(Map<String, String> animalMap) {
        this.animalMap = animalMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                "id:" + id +
                        "---种类:" + specie +
                        "---年龄:" + age +
                        "---性别:" + gender);
        if (0 == animalMap.size()) {
            return sb.toString();
        } else {
            Set<String> animalSet = animalMap.keySet();
            for (String key : animalSet) {
                String value = animalMap.get(key);
                sb.append("---").append(key).append(":").append(value);
            }
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(id, animal.id) &&
                Objects.equals(specie, animal.specie) &&
                Objects.equals(gender, animal.gender) &&
                Objects.equals(animalMap, animal.animalMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specie, age, gender, animalMap);
    }
}
