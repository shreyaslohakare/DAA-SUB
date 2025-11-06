import java.util.*;

class Order {
    int order_id;
    long timestamp;
    double value;

    public Order(int order_id, long timestamp, double value) {
        this.order_id = order_id;
        this.timestamp = timestamp;
        this.value = value;
    }
}

public class Assignment1{

    
    public static void merge(List<Order> arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Order> L = new ArrayList<>(n1);
        List<Order> R = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++)
            L.add(arr.get(left + i));
        for (int j = 0; j < n2; j++)
            R.add(arr.get(mid + 1 + j));

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L.get(i).timestamp <= R.get(j).timestamp)
                arr.set(k++, L.get(i++));
            else
                arr.set(k++, R.get(j++));
        }

        while (i < n1)
            arr.set(k++, L.get(i++));
        while (j < n2)
            arr.set(k++, R.get(j++));
    }

    // Recursive Merge Sort
    public static void mergeSort(List<Order> arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

   
    public static List<Order> generateOrders(int n) {
        List<Order> orders = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int id = i + 1;
            long timestamp = rand.nextInt(1_000_000_000) + 1;
            double value = rand.nextInt(100_000) / 100.0;
            orders.add(new Order(id, timestamp, value));
        }
        return orders;
    }

    
    public static void printOrders(List<Order> orders, int limit) {
        int size = Math.min(limit, orders.size());
        for (int i = 0; i < size; i++) {
            Order o = orders.get(i);
            System.out.printf("Order %d | Timestamp: %d | Value: %.2f%n",
                              o.order_id, o.timestamp, o.value);
        }
    }

    public static void main(String[] args) {
        int n = 20; // number of orders (can increase up to 1 million)
        List<Order> orders = generateOrders(n);

        System.out.println("Orders before sorting:");
        printOrders(orders, 10);

        mergeSort(orders, 0, n - 1);

        System.out.println("\nOrders after sorting by timestamp:");
        printOrders(orders, 10);
    }
}
/*Orders after sorting by timestamp:
Order 19 | Timestamp: 59434133 | Value: 204.23
Order 18 | Timestamp: 101159402 | Value: 829.38
Order 10 | Timestamp: 151139710 | Value: 44.89
Order 13 | Timestamp: 165486533 | Value: 93.79
Order 20 | Timestamp: 167850383 | Value: 191.81
Order 17 | Timestamp: 267872822 | Value: 227.92
Order 5 | Timestamp: 283615858 | Value: 280.12
Order 4 | Timestamp: 321908167 | Value: 767.96
Order 9 | Timestamp: 376850089 | Value: 878.21
Order 7 | Timestamp: 432800253 | Value: 613.15
*/

