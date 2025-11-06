import java.util.*;

class Item {
    int id;
    int weight;
    int utility;
    boolean perishable;

    Item(int id, int weight, int utility, boolean perishable) {
        this.id = id;
        this.weight = weight;
        this.utility = utility;
        this.perishable = perishable;
    }
}

public class SwiftCargoKnapsack {

    
    static void boostPerishableUtility(List<Item> items) {
        for (Item it : items) {
            if (it.perishable) {
                it.utility = (int) (it.utility * 1.2);
            }
        }
    }

  
    static void displaySelectedItems(int[][] dp, List<Item> items, int capacity) {
        int i = items.size();
        int w = capacity;
        int totalWeight = 0;

        System.out.println("\nItems loaded in the truck:");
        while (i > 0 && w > 0) {
            if (dp[i][w] != dp[i - 1][w]) {
                Item item = items.get(i - 1);
                System.out.println("✅ Item " + item.id +
                        " | Weight: " + item.weight +
                        " | Utility: " + item.utility +
                        " | Perishable: " + (item.perishable ? "Yes" : "No"));
                totalWeight += item.weight;
                w -= item.weight;
            }
            i--;
        }

        System.out.println("Total Weight Loaded: " + totalWeight + " kg");
    }

    
    static int knapsackDP(List<Item> items, int capacity) {
        int n = items.size();
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (items.get(i - 1).weight <= w) {
                    dp[i][w] = Math.max(
                            items.get(i - 1).utility + dp[i - 1][w - items.get(i - 1).weight],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        displaySelectedItems(dp, items, capacity);
        return dp[n][capacity];
    }

    public static void main(String[] args) {
        System.out.println("SwiftCargo - Truck Loading Optimization (Knapsack with Perishables)");

        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 10, 60, true));
        items.add(new Item(2, 20, 100, false));
        items.add(new Item(3, 30, 120, true));
        items.add(new Item(4, 25, 90, false));
        items.add(new Item(5, 15, 75, true));

        int truckCapacity = 50;

        boostPerishableUtility(items);
        int maxUtility = knapsackDP(items, truckCapacity);

        System.out.println("\n📦 Maximum Total Utility (using DP): " + maxUtility);
    }
}
/*SwiftCargo - Truck Loading Optimization (Knapsack with Perishables)

Items loaded in the truck:
 Item 3 | Weight: 30 | Utility: 144 | Perishable: Yes
 Item 5 | Weight: 15 | Utility: 90 | Perishable: Yes
 Item 1 | Weight: 10 | Utility: 72 | Perishable: Yes
Total Weight Loaded: 55 kg

 Maximum Total Utility (using DP): 306

*/

