package task1.com.wenh.test;

import task1.com.wenh.dao.AnimalDao;
import task1.com.wenh.dao.impl.AnimalDaoImpl;
import task1.com.wenh.pojo.Animal;
import task1.com.wenh.utils.CheckUtils;
import task1.com.wenh.utils.ImplUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author LWenH
 * @version 1.1
 * <p>
 * 功能测试
 *
 * <p>整个项目的功能具体实现和对方法的调用都在这个类中</p>
 * @create 2020/9/29 - 15:08
 */
public class Test {
    public static void main(String[] args) {
        all:
        while (true) {
            System.out.println("=====欢迎=====");
            System.out.println("请选择您想要的操作");
            System.out.println("1 查询动物信息");
            System.out.println("2 多条件查询动物信息");
            System.out.println("3 增加动物信息");
            System.out.println("4 修改动物信息");
            System.out.println("5 删除动物信息");
            System.out.println("6 退出");
            System.out.println("请输入您的选择:");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();

            AnimalDao ad = new AnimalDaoImpl();
            ImplUtils iu = new ImplUtils();
            switch (choice) {
                case "1":
                    /*
                    查询动物信息
                     */
                    // 两种情况，文件为空和文件不为空但集合为空，都没有动物信息
                    if (! iu.ifNull() || ! iu.ifNoAnimal()) {
                        System.out.println("还没有添加任何动物信息,清先添加动物信息!");
                        System.out.println();
                        continue;
                    }
                    ad.select();
                    break;
                case "2":
                    /*
                    多条件查询动物信息
                     */

                    // 两种情况，文件为空和文件不为空但集合为空，都没有动物信息
                    if (! iu.ifNull() || ! iu.ifNoAnimal()) {
                        System.out.println("还没有添加任何动物信息,清先添加动物信息!");
                        System.out.println();
                        continue;
                    }
                    List<String> names = new ArrayList<>();
                    List<String> values = new ArrayList<>();
                    out:
                    while (true) {
                        System.out.println("请输入您的查询条件(输入back返回主页面):");
                        System.out.println("请输入您想要限制的属性值的名称:");
                        String name = scanner.nextLine();
                        if ("back".equalsIgnoreCase(name)) {
                            continue all;
                        }
                        names.add(name);
                        System.out.println("请输入您想要限制的属性值的值:");
                        String value = scanner.nextLine();
                        if ("back".equalsIgnoreCase(value)) {
                            continue all;
                        }
                        values.add(value);
                        while (true) {
                            System.out.println("您还有别的限制条件吗?(y/n)");
                            String s = scanner.nextLine();
                            if ("n".equalsIgnoreCase(s)) {
                                break out;
                            } else if ("y".equalsIgnoreCase(s)) {
                                continue out;
                            } else {
                                System.out.println("请按照提示规范输入选项！");
                            }
                        }
                    }
                    // 如果用户输入的属性名称任何一个动物都没有
                    if (! iu.checkName(names)) {
                        System.out.println("对不起，没有任何一个动物有包含您所输入的属性!");
                    } else if (! ad.selectByParam(values)) {
                        System.out.println("对不起，没有满足符合您条件的动物!");
                    }
                    break;
                case "3":
                    /*
                    增加动物信息
                     */
                    System.out.println("请输入您所要增加的动物的基本信息(输入back返回主界面):");
                    String species;
                    while (true) {
                        System.out.println("请输入动物的种类:");
                        species = scanner.nextLine();
                        if (! CheckUtils.checkProperty(species)) {
                            System.out.println("您输入的数据为空，请重新输入!");
                            continue;
                        }
                        if ("back".equalsIgnoreCase(species)) {
                            continue all;
                        }
                        break;
                    }
                    String gender;
                    while (true) {
                        System.out.println("请输入动物的性别:");
                        gender = scanner.nextLine();
                        if (! CheckUtils.checkProperty(gender)) {
                            System.out.println("您输入的数据为空，请重新输入!");
                            continue;
                        }
                        break;
                    }
                    String age;
                    while (true) {
                        System.out.println("请输入动物的年龄:");
                        age = scanner.nextLine();
                        if (! CheckUtils.checkProperty(age)) {
                            System.out.println("您输入的数据为空，请重新输入!");
                        } else if (CheckUtils.checkAge(age) == 0) {
                            System.out.println("请输入数字!");
                        } else if (CheckUtils.checkAge(age) == 1) {
                            System.out.println("您所输入的年龄不符合实际，请检查后重新输入!");
                        } else {
                            break;
                        }
                    }
                    Map<String, String> animalMap = new HashMap<>();
                    Animal animal = new Animal(species, Integer.parseInt(age), gender, animalMap);
                    do {
                        System.out.println("您还想增添别的属性吗？(y/n)");
                        String s = scanner.nextLine();
                        String s1;
                        if ("y".equalsIgnoreCase(s)) {
                            while (true) {
                                System.out.println("请输入您想要添加的属性名:");
                                s1 = scanner.nextLine();
                                if (! CheckUtils.checkProperty(s1)) {
                                    System.out.println("属性名不可为空，请重新输入!");
                                    continue;
                                }
                                break;
                            }
                            System.out.println("请输入您想要添加的属性值:");
                            String s2 = scanner.nextLine();
                            // 属性值没有做非空校验，因为我认为扩展的属性值应该被允许为空
                            animalMap.put(s1, s2);
                        } else if ("n".equalsIgnoreCase(s)) {
                            if (! iu.checkRepeat(animal)) {
                                System.out.println("动物信息库中已经有一条完全一样的信息,您确定要继续添加吗?(y/n)");
                                String s2 = scanner.nextLine();
                                if ("y".equalsIgnoreCase(s2)) {
                                    ad.add(animal);
                                } else if ("n".equalsIgnoreCase(s2)) {
                                    System.out.println("您下一步的选择是什么呢?");
                                    String s3;
                                    while (true) {
                                        System.out.println("1 查询动物信息");
                                        System.out.println("2 返回主界面");
                                        s3 = scanner.nextLine();
                                        if (! "1".equals(s3) && ! "2".equals(s3)) {
                                            System.out.println("您输入了无效的选项!请重新选择!");
                                            continue;
                                        }
                                        break;
                                    }
                                    if ("1".equals(s3)) {
                                        ad.select();
                                    } else {
                                        break;
                                    }
                                }
                            } else {
                                ad.add(animal);
                            }
                            break;
                        }
                    } while (true);
                    break;
                case "4":
                    /*
                    修改动物信息
                     */
                    if (! iu.ifNull() || ! iu.ifNoAnimal()) {
                        System.out.println("还没有添加任何动物信息,清先添加动物信息!");
                        System.out.println();
                        continue;
                    }
                    ad.select();
                    String num;
                    while (true) {
                        System.out.println("请选择您想要的操作:");
                        System.out.println("1 修改已有的属性");
                        System.out.println("2 增添新属性");
                        System.out.println("3 返回主界面");
                        num = scanner.nextLine();
                        if (! "1".equals(num) && ! "2".equals(num) && ! "3".equals(num)) {
                            System.out.println("您输入了无效的选项!请重新选择!");
                            continue;
                        }
                        break;
                    }
                    b:
                    switch (num) {
                        case "1":
                            /*
                            修改现有属性
                             */
                            String aid1;
                            while (true) {
                                System.out.println("请输入您想要修改的动物的id:");
                                aid1 = scanner.nextLine();
                                if (! iu.checkId(aid1)) {
                                    System.out.println("你所输入的id并不存在，请检查后重新输入!");
                                    continue;
                                }
                                break;
                            }
                            String name;
                            a:
                            while (true) {
                                System.out.println("请输入您想要修改的属性名称:");
                                name = scanner.nextLine();
                                // 如果用户输入的属性名不存在
                                if (! iu.checkExist(aid1, name)) {
                                    System.out.println("对不起，您想要修改的属性值并不存在！请选择您的下一步操作:");
                                    while (true) {
                                        System.out.println("1 重新输入属性");
                                        System.out.println("2 返回主界面");
                                        String s = scanner.nextLine();
                                        if ("1".equals(s)) {
                                            continue a;
                                        } else if ("2".equals(s)) {
                                            break b;
                                        } else {
                                            System.out.println("您选择了无效的选项，请重新选择!");
                                        }
                                    }
                                } else {
                                    String property;
                                    while (true) {
                                        System.out.println("请输入您想要修改的属性的新值:");
                                        property = scanner.nextLine();
                                        if (("种类".equals(name) || "性别".equals(name))) {
                                            if (! CheckUtils.checkProperty(property)) {
                                                System.out.println("基本属性值不允许为空,请重新输入!");
                                                continue;
                                            }
                                        }
                                        if ("年龄".equals(name)) {
                                            if (! CheckUtils.checkProperty(property)) {
                                                System.out.println("基本属性值不允许为空,请重新输入!");
                                                continue;
                                            } else if (CheckUtils.checkAge(property) == 0) {
                                                System.out.println("请输入数字!");
                                                continue;
                                            } else if (CheckUtils.checkAge(property) == 1) {
                                                System.out.println("您所输入的年龄不符合实际，请检查后重新输入!");
                                                continue;
                                            }
                                        }
                                        ad.set(num, aid1, name, property);
                                        break;
                                    }
                                }
                                break;
                            }
                            break;
                        case "2":
                            /*
                            增添新属性
                             */
                            String aid2;
                            while (true) {
                                System.out.println("请输入您想要修改的动物的id:");
                                aid2 = scanner.nextLine();
                                if (! iu.checkId(aid2)) {
                                    System.out.println("你所输入的id并不存在，请检查后重新输入!");
                                    continue;
                                }
                                break;
                            }
                            System.out.println("请输入您想要添加的属性名称:");
                            String name2;
                            while (true) {
                                name2 = scanner.nextLine();
                                if (! CheckUtils.checkProperty(name2)) {
                                    System.out.println("属性名不可为空,请重新输入!");
                                    continue;
                                }
                                break;
                            }
                            System.out.println("请输入您想要添加的属性的值:");
                            String property2 = scanner.nextLine();
                            ad.set(num, aid2, name2, property2);
                            break;
                        default:
                            break;
                    }
                    break;
                case "5":
                    /*
                    删除动物信息
                     */
                    if (! iu.ifNull() || ! iu.ifNoAnimal()) {
                        System.out.println("还没有添加任何动物信息,清先添加动物信息!");
                        System.out.println();
                        continue;
                    }
                    ad.select();
                    String num2;
                    while (true) {
                        System.out.println("请选择您想要的操作:");
                        System.out.println("1 删除整条动物信息");
                        System.out.println("2 删除某个动物的扩展属性");
                        System.out.println("3 返回主界面");
                        num2 = scanner.nextLine();
                        if (! "1".equals(num2) && ! "2".equals(num2) && ! "3".equals(num2)) {
                            System.out.println("您输入了无效的选项!请重新选择!");
                            continue;
                        }
                        break;
                    }

                    c:
                    switch (num2) {
                        case "1":
                            /*
                            删除整条动物信息
                             */
                            System.out.println("请输入您想要删除的动物的id:");
                            String aid2;
                            while (true) {
                                aid2 = scanner.nextLine();
                                if (! iu.checkId(aid2)) {
                                    System.out.println("你所输入的id并不存在，请检查后重新输入!");
                                    continue;
                                }
                                break;
                            }
                            ad.delete(num2, aid2, null);
                            break;
                        case "2":
                            /*
                            删除某个动物的扩展属性
                             */
                            System.out.println("请输入您想要删除的动物的id:");
                            while (true) {
                                aid2 = scanner.nextLine();
                                if (! iu.checkId(aid2)) {
                                    System.out.println("你所输入的id并不存在，请检查后重新输入!");
                                    continue;
                                } else if (! iu.checkMap(aid2)) {
                                    System.out.println("您选择的动物目前并没有任何扩展属性.不可删除!");
                                    break c;
                                }
                                break;
                            }
                            out:
                            while (true) {
                                System.out.println("请输入您想要删除的扩展属性名:");
                                String name = scanner.nextLine();
                                if ("种类".equals(name) || "年龄".equals(name) || "性别".equals(name) || "id".equalsIgnoreCase(name)) {
                                    System.out.println("基础属性不可删除!请选择您的操作:");
                                    while (true) {
                                        System.out.println("1 继续删除扩展属性");
                                        System.out.println("2 返回主界面");
                                        String choice1 = scanner.nextLine();
                                        if ("1".equals(choice1)) {
                                            continue out;
                                        } else if ("2".equals(choice1)) {
                                            break out;
                                        } else {
                                            System.out.println("您选择了无效选项,请重新输入!");
                                        }
                                    }
                                }
                                if (! iu.checkExist(aid2, name)) {
                                    System.out.println("您所要删除的属性并不存在!");
                                    while (true) {
                                        System.out.println("请选择您的操作:");
                                        System.out.println("1 重新输入属性名");
                                        System.out.println("2 返回主界面");
                                        String choice2 = scanner.nextLine();
                                        if ("1".equals(choice2)) {
                                            continue out;
                                        } else if ("2".equals(choice2)) {
                                            break out;
                                        } else {
                                            System.out.println("您选择了无效选项,请重新输入!");
                                        }
                                    }
                                } else {
                                    ad.delete(num2, aid2, name);
                                }
                                break;
                            }
                        default:
                            /*
                            3 代表返回主界面
                             */
                            break;
                    }
                    break;
                case "6":
                    /*
                    退出程序
                     */
                    System.out.println("谢谢您的使用！");
                    System.exit(0);
                    break;
                default:
                    System.out.println("您选择了无效选项，请重新选择！");
                    break;
            }
        }
    }
}
