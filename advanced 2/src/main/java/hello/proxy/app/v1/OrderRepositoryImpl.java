package hello.proxy.app.v1;

public class OrderRepositoryImpl implements OrderRepository {

    public void save(String itemId) {

        if (itemId.equals("ex")) {
            throw new IllegalArgumentException("μμΈλ°μ");
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
