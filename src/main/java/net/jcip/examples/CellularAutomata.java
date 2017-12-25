package net.jcip.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * CellularAutomata
 *
 * Coordinating computation in a cellular automaton with CyclicBarrier
 *
 * @author Brian Goetz and Tim Peierls
 */
public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count,
                new Runnable() {
                    public void run() {
                        mainBoard.commitNewValues();
                    }});
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++)
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) { this.board = board; }
        public void run() {

                try {
                    System.out.println("begin to exec");
                    TimeUnit.SECONDS.sleep(3);
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            return 0;
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++)
            new Thread(workers[i]).start();
    }

    interface Board {
        int getMaxX();
        int getMaxY();
        int getValue(int x, int y);
        int setNewValue(int x, int y, int value);
        void commitNewValues();
        boolean hasConverged();
        void waitForConvergence();
        Board getSubBoard(int numPartitions, int index);
    }

    static class MyBoard implements Board{

        List<MyBoard> subs = new ArrayList<>();

        @Override
        public int getMaxX() {
            return 0;
        }

        @Override
        public int getMaxY() {
            return 0;
        }

        @Override
        public int getValue(int x, int y) {
            return 0;
        }

        @Override
        public int setNewValue(int x, int y, int value) {
            return 0;
        }

        @Override
        public void commitNewValues() {
            System.out.println("commitNewValues");
        }

        @Override
        public boolean hasConverged() {
            return false;
        }

        @Override
        public void waitForConvergence() {
            System.out.println("waitForConvergence");
        }

        @Override
        public Board getSubBoard(int numPartitions, int index) {
            subs.add(index,new MyBoard());
            return subs.get(index);
        }
    }

    public static void main(String[] args){
        CellularAutomata cellularAutomata = new CellularAutomata(new MyBoard());
        cellularAutomata.start();
    }
}
