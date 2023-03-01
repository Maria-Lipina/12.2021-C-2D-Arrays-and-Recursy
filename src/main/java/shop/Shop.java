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
        int sumChance;

        void prepare() {

            this.chances = new int[toys.size()][2];
            this.sumChance = 0;

            for (int i = 0; i < chances.length; i++) {
                chances[i][0] = sumChance;
                sumChance = sumChance + toys.get(i).chance;
                chances[i][1] = sumChance;
            }
        }

        void prepare(int emptyToyInd) {

            if (0 == emptyToyInd) {
                toys.get(emptyToyInd+1).chance =
                        toys.get(emptyToyInd+1).chance +
                                toys.get(emptyToyInd).chance/2;
            }
            if (0 < emptyToyInd && emptyToyInd < toys.size()-1) {
                toys.get(emptyToyInd+1).chance = toys.get(emptyToyInd+1).chance +
                        toys.get(emptyToyInd).chance/4;

                toys.get(emptyToyInd-1).chance = toys.get(emptyToyInd-1).chance +
                        toys.get(emptyToyInd).chance/4;
            }
            if (emptyToyInd == toys.size()-1) {
                toys.get(emptyToyInd-1).chance = toys.get(emptyToyInd-1).chance +
                        toys.get(emptyToyInd).chance/2;
            }

            toys.get(emptyToyInd).chance = toys.get(emptyToyInd).chance/2;
            this.prepare();
        }

        int findPrizeIndex(int rndInt) {

            int i = toys.size() / 2 - 1;
            while(i != 0 || i != toys.size()-1) {
                if (chances[i][0] <= rndInt && rndInt < chances[i][1]) {
                    return i;
                }
                else if (rndInt < chances[i][0]) i--;
                else if (rndInt >= chances[i][1]) i++;
            }
            return i;
        }

        void getPrize(int tryCount) {
            this.prizes = new LinkedList<>();
            Random r = new Random();

            for (int i = 0; i < tryCount; i++) {
                int rndInt = r.nextInt(sumChance);
                int prizeInd = this.findPrizeIndex(rndInt);
                if (toys.get(prizeInd).takeAsPrize()) {
                    prizes.add(toys.get(prizeInd).name);
                }
                else {
                    prizes.add("Неудача");
                    this.prepare(prizeInd);
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
        private final String name;
        private int count;
        private int chance;

        Toy(String[] args) {
            this.id = Integer.parseInt(args[0]);
            this.name = args[1];
            this.count = Integer.parseInt(args[2]);
            this.chance = Integer.parseInt(args[3]);
        }
        public boolean takeAsPrize() {
            if (this.count == 0) return false;
            else this.count = count-1;
            return true;
        }
    }
}

