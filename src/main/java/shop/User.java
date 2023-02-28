package shop;

import java.util.Scanner;

public class User {

    public void letsPlay() {
        Shop shop = new Shop();
        shop.init();
        shop.game.prepare();


        System.out.println("Hello! How many times do you want to get a toy? Insert an integer");
        shop.game.getPrize(this.getInput());
        shop.game.printPrizes();
        System.out.println("Done! Please get your prizes");

    }

    public int getInput() {
        Scanner sc = new Scanner(System.in);
        int tryCount = 0;
        try {
            tryCount = sc.nextInt();
        }
        catch (Exception e) {
            this.getInput();
        }
        return tryCount;
    }

}
