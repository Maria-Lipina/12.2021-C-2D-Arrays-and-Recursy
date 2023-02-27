import shop.Shop;

public class Main {

    public static void main(String[] args) {
        Shop shop = new Shop("prizes.txt");
        shop.init("src/main/java/warehouse.txt");

    }
}
