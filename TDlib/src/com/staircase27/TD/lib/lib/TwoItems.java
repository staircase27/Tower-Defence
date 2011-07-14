/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib;

/**
 * A container for 2 items of any 2 types.
 * @param <A> The type of the first item
 * @param <B> The type of the second item
 * @author Simon Armstrong
 */
public class TwoItems<A,B>{
    /** the first element*/
    private A a;
    /** the second element*/
    private B b;

    /**
     * Creates a new TwoItems with the specified elements
     * @param a the first elements
     * @param b the second elements
     */
    public TwoItems(A a,B b){
        this.a=a;
        this.b=b;
    }
    
    /**
     * Get the first element.
     * @return the first element.
     */
    public A getA() {
        return a;
    }

    /**
     * set the first element.
     * @param a the new first element
     */
    public void setA(A a) {
        this.a = a;
    }

    /**
     * get the second element.
     * @return the second element
     */
    public B getB() {
        return b;
    }

    /**
     * set the second element.
     * @param b the new second element
     */
    public void setB(B b) {
        this.b = b;
    }

    /**
     * Checks if the obj parameter is the same as this TwoItems.
     * 
     * A TwoItem is the same if they both have the same a and the same b object (Irrespective of types A and B)
     */
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
        StringBuilder buf = new StringBuilder("TwoItems(");
        buf.append(a);
        buf.append(',');
        buf.append(b);
        buf.append(')');
        return buf.toString();
    }
    
    
}
