package shop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Класс магазин. В нем хранятся игрушки (методы для хранения, добавления, изымания, а так же обращения к свойствам игрушки, которые не может менять кто попало, только из самого магазина)
 *
 */

public class Shop {

    ArrayList <Toy> toys;

    /**
     * Потом можно было бы вместо аргументов сделать их конфигурационными константами. Но это уже усложнение. Keep it simple, stupid!
     * @param prizesFile
     */
    public Shop(String prizesFile) {
        this.toys = new ArrayList<>();
    }

    public void init(String warehouseFile) {

        try (BufferedReader loadIn = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(warehouseFile)))) {

            String s;
            while ((s = loadIn.readLine()) != null) {
                toys.add(
                        new Toy(
                                s.split("; ")));
            }


        } catch (Exception e) {
            System.out.println("Exception пойман и обработан");
            e.printStackTrace();
        }

    }

}


/**
 * Непубличный класс Игрушка, в котором хранятся свойства игрушки, а так же геттеры и сеттеры, к которым может обращаться магазин. А так же метод toString, чтобы добавить в файл
 * id игрушки, -- Геттер
 * текстовое название, - Геттер
 * количество - Геттер и сеттер
 * частота выпадения игрушки (вес в % от 100) - Геттер и сеттер
 */
class Toy {

    int id;
    String name;
    int count;
    int chance;

    public Toy(String[] args) {
        this.id = Integer.parseInt(args[0]);
        this.name = args[1];
        this.count = Integer.parseInt(args[2]);
        this.chance = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", chance=" + chance +
                '}';
    }
}
