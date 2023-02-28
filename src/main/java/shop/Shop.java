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
                        new FileInputStream(ConfigLog.SHOP_WAREHOUSE_IN)))) {
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
            System.exit(1);
        }

    }

    class Game {
        Queue<String> prizes;
        int[][] chances;

        Game() {
            this.prizes = new LinkedList<>();
            this.chances = new int[toys.size()][2];
        }

        void prepare() {
            int sumChance = 0;
            for (int i = 0; i < chances.length; i++) {
                chances[i][0] = sumChance;
                sumChance = sumChance + toys.get(i).chance;
                chances[i][1] = sumChance;
            }
            for (int[] chance: chances) {
                System.out.printf("(%d, %d)\t", chance[0], chance[1]);
            }
        }

        int findPrizeIndex(int rndInt) {
            int i = toys.size() / 2;
            while(i != 0 || i != toys.size()-1) {
                if (chances[i][0] <= rndInt && rndInt < chances[i][0]) return i;
                else if (rndInt < chances[i][0]) i--;
                else if (rndInt >= chances[i][1]) i++;
            }
            return i;
        }

        void getPrize(int tryCount) {

            Random r = new Random();
            for (int i = 0; i < tryCount; i++) {
                int rndInt = r.nextInt(6);
                int prizeInd = this.findPrizeIndex(rndInt);

                if (toys.get(prizeInd).takeAsPrize()) {
                    prizes.add(toys.get(rndInt).getName());
                }
                else {
                    prizes.add("Неудача\n");
                    toys.get(prizeInd).setChance(0);
                    /**
                     * !!! нужно продумать, как в распределении рандома убрать игрушку из игры
                     */
                    //this.prepare(prizeInd);
                }
            }

        }

        void printPrizes() {
            if (!prizes.isEmpty()) {
                try (FileWriter loadOut = new FileWriter(ConfigLog.GAME_PRIZES_OUT)) {
                    for (String s : prizes) {
                        loadOut.append(
                                String.format("%s\n", s)
                        );
                    }
                } catch (IOException e) {
                    System.out.println("Internal error. We`re fixing it");
                    ConfigLog.log(e.getMessage(), this.getClass().getName());
                    System.exit(2);
                }
            }
        }

    }


    class Toy {

        private int id;
        private String name;
        private int count;
        private int chance;

        Toy(String[] args) {
            this.id = Integer.parseInt(args[0]);
            this.name = args[1];
            this.count = Integer.parseInt(args[2]);
            this.chance = Integer.parseInt(args[3]);
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

        public boolean takeAsPrize() {
            if (this.count == 0) return false;
            else this.count = count-1;
            return true;
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
}

