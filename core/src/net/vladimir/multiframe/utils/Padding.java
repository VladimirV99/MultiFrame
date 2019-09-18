package net.vladimir.multiframe.utils;

public class Padding {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public Padding() {
        set(0, 0, 0, 0);
    }

    public Padding(int left, int top, int right, int bottom) {
        set(left, top, right, bottom);
    }

    public void set(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

}
