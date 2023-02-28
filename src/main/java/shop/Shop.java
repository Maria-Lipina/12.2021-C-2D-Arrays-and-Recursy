package shop;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


class Shop {

    private ArrayList <Toy> toys;
    int totalCount;
    Game game;

    Shop() {
        this.toys = new ArrayList<>();
        this.game = new Game();
    }

    void init() {

        try (BufferedReader loadIn = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(ConfigLog.SHOP_WAREHOUSE)))) {
            String s;
            while ((s = loadIn.readLine()) != null) {
                toys.add(
                        new Toy(
                                s.split("; ")));

                totalCount = totalCount + toys.get(toys.size()-1).getCount();
            }
        } catch (Exception e) {
            System.out.println("Internal error. We`re fixing it");
            ConfigLog.log(e.getMessage(), this.getClass().getName());
        }

    }

    class Game {
        Queue<String> prizes;

        Game() {
            this.prizes = new LinkedList<>();
        }

        void prepare() {
            Random r = new Random();

            for (Toy toy: toys) {
                //Узнавать процент каждой игрушки в totalCount и назначать как вес каждому экземпляру
                float chance = r.nextFloat(toy.getCount()*0.3f, toy.getCount()*0.75f);

                if (chance > 0) toy.setChance(chance);
                else toy.setChance((int)(toy.getCount()*0.5));
            }

            toys.forEach(System.out::println);

//        return new LinkedList();
        }

        void getPrize(int tryCount) {

            Random r = new Random();
            for (int i = 0; i < tryCount; i++) {
                int index = r.nextInt(6);

                System.out.println(index + "Выпало на рандоме");
                System.out.println(toys.get(index).getName());
                System.out.println(toys.get(index).getCount() + "кол-во было");

                if (toys.get(index).getCount() > 0) {
                    toys.get(index).takeAsPrize();
                    prizes.add(toys.get(index).getName());
                }
                else {
                    prizes.add("Неудача\n");
                }

                System.out.println(toys.get(index).getCount() + "кол-во стало");
            }

        }

        void printPrizes() {
            try (FileWriter loadOut = new FileWriter(ConfigLog.GAME_PRIZES_OUT)) {
                for (String s : prizes) {
                    loadOut.append(
                            String.format("%s\n", s)
                    );
                }
            }
            catch (IOException e) {
                System.out.println("Internal error. We`re fixing it");
                ConfigLog.log(e.getMessage(), this.getClass().getName());
            }
        }

    }


    class Toy {

        private int id;
        private String name;
        private int count;
        private float chance;

        Toy(String[] args) {
            this.id = Integer.parseInt(args[0]);
            this.name = args[1];
            this.count = Integer.parseInt(args[2]);
            this.chance = 0;
        }

        int getId() {
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

        public void takeAsPrize() {
            this.count = count-1;
            //Здесь потом могут быть проверки на изменение веса игрушки в игре
        }

        public float getChance() {
            return chance;
        }

        public void setChance(float chance) {
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
}

