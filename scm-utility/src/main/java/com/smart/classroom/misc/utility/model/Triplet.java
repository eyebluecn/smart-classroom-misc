package com.smart.classroom.misc.utility.model;

/**
 * 将三个值封装在一个类中
 * 从而方便多值返回使用
 *
 * @author lishuang
 * @version 2023-05-11
 */
public class Triplet<X, Y, Z> {

    private X x;
    private Y y;
    private Z z;

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }

    public Z getZ() {
        return z;
    }

    public void setZ(Z z) {
        this.z = z;
    }

    public Triplet(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}