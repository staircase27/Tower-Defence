/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib;

/**
 *
 * @author Simon Armstrong
 */
public class TwoItems<A,B>{
    private A a;
    private B b;

    public TwoItems(A a,B b){
        this.a=a;
        this.b=b;
    }
    
    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TwoItems){
            TwoItems<?,?> o=(TwoItems<?,?>) obj;
            return a.equals(o.a) && b.equals(o.b);
        }
        return false;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.a != null ? this.a.hashCode() : 0);
        hash = 23 * hash + (this.b != null ? this.b.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(a);
        buf.append(',');
        buf.append(b);
        return buf.toString();
    }
    
    
}
