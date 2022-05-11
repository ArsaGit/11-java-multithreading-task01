public class Lucky {
    static int x = 0;
    static int count = 0;

    static class LuckyThread extends Thread {
        private static final Object resX = new Object();
        private static final Object resC = new Object();

        @Override
        public void run() {
            int tempCount = 0;
            while (true) {
                int tempX;
                synchronized (resX) {
                    if (x == 999999) break;

                    x++;
                    tempX = x;
                }
                if ((tempX % 10) + (tempX / 10) % 10 + (tempX / 100) % 10 ==
                        (tempX / 1000) % 10 + (tempX / 10000) % 10 + (tempX / 100000) % 10) {
                    System.out.printf("%s %d\n", Thread.currentThread().getName(), tempX);
                    tempCount++;
                }
            }
            synchronized (resC) {
                count = count + tempCount;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);

        int myX = 0, myCount = 0;   //55251
        while (myX < 999999) {
            myX++;
            if ((myX % 10) + (myX / 10) % 10 + (myX / 100) % 10 == (myX / 1000)
                    % 10 + (myX / 10000) % 10 + (myX / 100000) % 10) {
                //System.out.println(x);
                myCount++;
            }
        }
        System.out.printf("%d %d", count, myCount);
    }
}
