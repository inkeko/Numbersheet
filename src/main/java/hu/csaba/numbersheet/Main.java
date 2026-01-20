package hu.csaba.numbersheet;


import hu.csaba.numbersheet.start.Start;

public class Main {
    public static void main(String[] args) {
        // a hibakezelést a Start osztály fogja végezni
        // a Main csak inditja  a programot
        new Start().run();
    }
}