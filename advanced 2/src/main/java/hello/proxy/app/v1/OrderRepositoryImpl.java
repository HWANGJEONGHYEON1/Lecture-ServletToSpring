package hello.proxy.v1;

public class OrderRepositoryImpl implements OrderRepository {

    public void save(String itemId) {

        if (itemId.equals("ex")) {
            throw new IllegalArgumentException("예외발생");
        }

        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
